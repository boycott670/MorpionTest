package com.sqli.nespresso.morpion.entities;

import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class Player
{
  public static Player fromPair(final ImmutablePair<String, Character> pair)
  {
    return new Player(pair.getFirst(), pair.getSecond());
  }
  
  private final String name;
  private final char playing;

  private Player(String name, char playing)
  {
    this.name = name;
    this.playing = playing;
  }

  public String getName()
  {
    return name;
  }
  
  public String display()
  {
    return String.valueOf(playing);
  }
}
