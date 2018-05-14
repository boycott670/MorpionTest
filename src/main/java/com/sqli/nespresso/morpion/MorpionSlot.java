package com.sqli.nespresso.morpion;

import java.util.Optional;

import com.sqli.nespresso.morpion.exceptions.BoxAlreadySelectedException;

final class MorpionSlot
{
  static MorpionSlot empty()
  {
    return new MorpionSlot(Optional.empty());
  }

  private Optional<Player> playedBy;

  private MorpionSlot(Optional<Player> playedBy)
  {
    this.playedBy = playedBy;
  }

  void fill(final Player player)
  {
    if (playedBy.isPresent())
    {
      throw new BoxAlreadySelectedException();
    }
    
    playedBy = Optional.of(player);
  }
  
  String display()
  {
    return playedBy.map(Player::display).orElse(" ");
  }
}
