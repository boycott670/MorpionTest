package com.sqli.challenge;

import java.util.Optional;

final class MorpionSlot
{
	private Optional<Player> player;
	
	MorpionSlot()
	{
		player = Optional.empty();
	}
	
	void play(final Player player)
	{
		if (this.player.isPresent())
		{
			throw new BoxAlreadySelectedException();
		}
		
		this.player = Optional.of(player);
	}
	
	boolean isNotPlayedYet()
	{
		return !player.isPresent();
	}
}
