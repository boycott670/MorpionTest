package com.sqli.nespresso.morpion.entities;

import java.util.Optional;

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
    playedBy = Optional.of(player);
  }
  
  public Optional<Player> player()
  {
    return playedBy;
  }
}
