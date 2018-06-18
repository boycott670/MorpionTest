package com.sqli.challenge;

import java.util.Arrays;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import com.sqli.challenge.MorpionParse.Pair;

final class Morpion
{
	private final Player firstPlayer;
	
	private final Player secondPlayer;
	
	private Player lastPlayingPlayer;
	
	private final MorpionSlot[] slots;
	
	Morpion(final String size, final String firstPlayer, final String secondPlayer)
	{
		final MorpionParse morpionParse = new MorpionParse();
		
		this.firstPlayer = morpionParse.parsePlayer(firstPlayer);
		
		this.secondPlayer = morpionParse.parsePlayer(secondPlayer);
		
		lastPlayingPlayer = this.firstPlayer;
		
		slots = Stream.generate(MorpionSlot::new)
				.limit(morpionParse.parseSize(size))
				.toArray(MorpionSlot[]::new);
	}
	
	void play(final String playerName, final String coordinates)
	{
		final Pair<Integer, Integer> parsedCoordinates = new MorpionParse().parseCoordinates(coordinates);
		
		final int rows = new Double(Math.sqrt(slots.length)).intValue();
		
		final Player player = firstPlayer.hasName(playerName) ? firstPlayer : secondPlayer;
		
		lastPlayingPlayer = player;
		
		slots[rows * parsedCoordinates.getFirst() + parsedCoordinates.getSecond()].play(player);
	}
	
	String report()
	{
		final long numberOfEmptySlots = Arrays.stream(slots).filter(MorpionSlot::isNotPlayedYet).count();
		
		final long remainingPlays = numberOfEmptySlots / 2;
		
		final ToLongFunction<Player> getRemainingPlaysFor = player -> remainingPlays + (lastPlayingPlayer != player ? 1 : 0);
		
		final long remainingPlaysForFirstPlayer = getRemainingPlaysFor.applyAsLong(firstPlayer);
		
		final long remainingPlaysForSecondPlayer = getRemainingPlaysFor.applyAsLong(secondPlayer);
		
		return String.format("%d games for %s, %d games for %s", remainingPlaysForFirstPlayer, firstPlayer, remainingPlaysForSecondPlayer, secondPlayer);
	}
}
