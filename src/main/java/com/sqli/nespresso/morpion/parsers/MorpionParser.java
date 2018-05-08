package com.sqli.nespresso.morpion.parsers;

import java.util.Map.Entry;

import com.sqli.nespresso.morpion.Player;

public interface MorpionParser
{
  int parseSize(final String size);

  Player parsePlayer(final String player);

  Entry<Integer, Integer> parseCoordinates(final String coordinates);
}
