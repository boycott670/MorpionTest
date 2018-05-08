package com.sqli.nespresso.morpion;

import java.util.Optional;

import com.sqli.nespresso.morpion.exceptions.BoxAlreadySelectedException;

public final class Box
{
  static Box empty()
  {
    return new Box();
  }

  private Optional<Player> playedBy;

  private Box()
  {
    playedBy = Optional.empty();
  }

  void play(final Player player)
  {
    if (playedBy.isPresent())
    {
      throw new BoxAlreadySelectedException();
    }

    playedBy = Optional.ofNullable(player);
  }

  public boolean isEmpty()
  {
    return !playedBy.isPresent();
  }

  public Player playedBy()
  {
    return playedBy.orElse(null);
  }

  String display()
  {
    return playedBy.map(Player::display)
        .orElse(" ");
  }
}
