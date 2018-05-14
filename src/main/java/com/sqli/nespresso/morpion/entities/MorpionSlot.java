package com.sqli.nespresso.morpion.entities;

import java.util.Optional;

import com.sqli.nespresso.morpion.exceptions.BoxAlreadySelectedException;

public final class MorpionSlot
{
  public static MorpionSlot empty()
  {
    return new MorpionSlot(Optional.empty());
  }

  private Optional<Player> playedBy;

  private MorpionSlot(Optional<Player> playedBy)
  {
    this.playedBy = playedBy;
  }

  public void fill(final Player player)
  {
    if (playedBy.isPresent())
    {
      throw new BoxAlreadySelectedException();
    }
    
    playedBy = Optional.of(player);
  }
  
  public String display()
  {
    return playedBy.map(Player::display).orElse(" ");
  }
}
