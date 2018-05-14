package com.sqli.nespresso.morpion;

import java.util.Arrays;

final class DefaultMorpionParser implements MorpionParser
{

  @Override
  public ImmutablePair<Integer, Integer> parseMorpionSize(String size)
  {
    final int[] sizeTokens = Arrays.stream(size.split("x")).mapToInt(Integer::parseInt).toArray();
    
    return ImmutablePair.of(sizeTokens[0], sizeTokens[1]);
  }

  @Override
  public ImmutablePair<Integer, Integer> parseMorpionSlotCoordinates(String coordinates)
  {
    return parseMorpionSize(coordinates);
  }

  @Override
  public ImmutablePair<String, Character> parseMorpionPlayer(String player)
  {
    final String[] playerTokens = player.split(":");
    
    return ImmutablePair.of(playerTokens[0], playerTokens[1].charAt(0));
  }

}
