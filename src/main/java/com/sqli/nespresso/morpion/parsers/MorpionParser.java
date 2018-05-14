package com.sqli.nespresso.morpion.parsers;

import com.sqli.nespresso.morpion.utils.ImmutablePair;

public interface MorpionParser
{
  ImmutablePair<Integer, Integer> parseMorpionSize(final String size);
  
  ImmutablePair<Integer, Integer> parseMorpionSlotCoordinates(final String coordinates);
  
  ImmutablePair<String, Character> parseMorpionPlayer(final String player);
}
