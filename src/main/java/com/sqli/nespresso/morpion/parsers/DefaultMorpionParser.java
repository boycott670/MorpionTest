package com.sqli.nespresso.morpion.parsers;

import java.util.Arrays;
import java.util.Map.Entry;

import com.sqli.nespresso.morpion.Player;

public final class DefaultMorpionParser implements MorpionParser
{

  @Override
  public int parseSize(String size)
  {
    return Integer.valueOf(size.split("x")[0]);
  }

  @Override
  public Player parsePlayer(String player)
  {
    final String[] tokens = player.split(":");

    return Player.playing(tokens[0], tokens[1].charAt(0));
  }

  @Override
  public Entry<Integer, Integer> parseCoordinates(String coordinates)
  {
    final int[] tokens = Arrays.stream(coordinates.split("x"))
        .mapToInt(Integer::parseInt)
        .toArray();

    return new Entry<Integer, Integer>()
    {

      @Override
      public Integer setValue(Integer value)
      {
        throw new UnsupportedOperationException();
      }

      @Override
      public Integer getValue()
      {
        return tokens[1];
      }

      @Override
      public Integer getKey()
      {
        return tokens[0];
      }
    };
  }

}
