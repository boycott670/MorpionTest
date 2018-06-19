package com.sqli.challenge;

import java.util.Objects;
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
	
	Optional<Player> isRowWon(final MorpionSlot... othersInRow)
	{
	  if (player.isPresent())
	  {
	    for (final MorpionSlot other : othersInRow)
	    {
	      if (!other.player.isPresent() || !Objects.equals(player.get(), other.player.get()))
	      {
	        return Optional.empty();
	      }
	    }
	    
	    return player;
	  }
	  
	  return Optional.empty();
	}
	
	char display()
	{
	  return player.map(Player::display).orElse(' ');
	}
}
