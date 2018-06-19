package com.sqli.challenge;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.sqli.challenge.MorpionParse.Pair;

final class Morpion
{
  static final CharSequence LINE_SEPARATOR = "\n";
  
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
	
	int rows()
	{
	  return new Double(Math.sqrt(slots.length)).intValue();
	}
	
	void play(final String playerName, final String coordinates)
	{
		final Pair<Integer, Integer> parsedCoordinates = new MorpionParse().parseCoordinates(coordinates);
		
		final int rows = rows();
		
		final Player player = firstPlayer.hasName(playerName) ? firstPlayer : secondPlayer;
		
		lastPlayingPlayer = player;
		
		slots[rows * parsedCoordinates.getFirst() + parsedCoordinates.getSecond()].play(player);
	}
	
	String report()
	{
	  return new MorpionStateReport(slots, rows()).report(firstPlayer, secondPlayer, lastPlayingPlayer);
	}
	
	String display()
	{
    return IntStream.iterate(0, currentRowIndex -> currentRowIndex + rows())
        .limit(rows())
        .mapToObj(rowIndex -> Arrays.stream(Arrays.copyOfRange(slots, rowIndex, rowIndex + rows()))
            .map(MorpionSlot::display)
            .map(String::valueOf)
            .collect(Collectors.joining()))
        .collect(Collectors.joining(LINE_SEPARATOR)) + LINE_SEPARATOR;
	}
}
