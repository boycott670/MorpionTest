package sqli.morpion.parsers;

import sqli.morpion.utils.MorpionCoordinates;
import sqli.morpion.utils.MorpionSize;
import sqli.morpion.utils.PlayerEntry;

public final class DefaultMorpionParser implements MorpionParser
{

  private final String[] splitUsing (final String line, final String separator)
  {
    return line.split(separator);
  }

  @Override
  public MorpionSize parseMorpionSize(String morpionSize)
  {
    final String[] tokens = splitUsing(morpionSize, "x");
    
    return MorpionSize.of(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
  }

  @Override
  public PlayerEntry parsePlayerEntry(String playerEntry)
  {
    final String[] tokens = splitUsing(playerEntry, ":");
    
    return PlayerEntry.of(tokens[0], tokens[1].charAt(0));
  }

  @Override
  public MorpionCoordinates parseMorpionCoordinates(String morpionCoordinates)
  {
    final String[] tokens = splitUsing(morpionCoordinates, "x");
    
    return MorpionCoordinates.of(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
  }
  
}
