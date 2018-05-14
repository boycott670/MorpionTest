package com.sqli.nespresso.morpion;

final class Player
{
  static Player fromPair(final ImmutablePair<String, Character> pair)
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

  String getName()
  {
    return name;
  }
  
  String display()
  {
    return String.valueOf(playing);
  }
}
