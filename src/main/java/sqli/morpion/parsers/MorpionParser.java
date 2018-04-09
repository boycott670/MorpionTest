package sqli.morpion.parsers;

import sqli.morpion.utils.MorpionCoordinates;
import sqli.morpion.utils.MorpionSize;
import sqli.morpion.utils.PlayerEntry;

public interface MorpionParser
{
  MorpionSize parseMorpionSize (final String morpionSize);
  PlayerEntry parsePlayerEntry (final String playerEntry);
  MorpionCoordinates parseMorpionCoordinates (final String morpionCoordinates);
}
