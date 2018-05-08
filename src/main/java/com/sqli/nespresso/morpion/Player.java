package com.sqli.nespresso.morpion;

import java.util.Objects;

public final class Player
{
  public static Player playing(final String name, final char playing)
  {
    return new Player(name, playing);
  }

  private final String name;
  private final char playing;

  private Player(String name, char playing)
  {
    this.name = name;
    this.playing = playing;
  }

  final String getName()
  {
    return name;
  }

  @Override
  public boolean equals(Object obj)
  {
    return obj instanceof Player ? Objects.equals(name, ((Player) obj).name) : false;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(name);
  }

  String display()
  {
    return String.valueOf(playing);
  }
}
