package com.sqli.nespresso.morpion;

interface MorpionParser
{
  ImmutablePair<Integer, Integer> parseMorpionSize(final String size);
  
  ImmutablePair<Integer, Integer> parseMorpionSlotCoordinates(final String coordinates);
  
  ImmutablePair<String, Character> parseMorpionPlayer(final String player);
}
