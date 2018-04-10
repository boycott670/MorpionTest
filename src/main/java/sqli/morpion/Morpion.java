package sqli.morpion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import sqli.morpion.entities.MorpionSlot;
import sqli.morpion.entities.Player;
import sqli.morpion.parsers.DefaultMorpionParser;
import sqli.morpion.parsers.MorpionParser;
import sqli.morpion.reporters.DefaultMorpionReporter;
import sqli.morpion.reporters.MorpionReporter;
import sqli.morpion.utils.MorpionCoordinates;
import sqli.morpion.utils.MorpionSize;
import sqli.morpion.utils.PlayerEntry;

public final class Morpion
{
  public static final String LINE_SEPARATOR = String.format("%n");
  
  private final MorpionParser morpionParser = new DefaultMorpionParser();
  private final MorpionReporter morpionReporter = new DefaultMorpionReporter();
  
  private final MorpionSize morpionSize;
  
  private final Map<String, Player> players;
  private final Player playerOne, playerTwo;
  
  private final MorpionVector[] morpionVectors;
  
  private final MorpionSlot[] slots;
  
  public Morpion (final String morpionSize, final String playerOneEntry, final String playerTwoEntry)
  {
    this.morpionSize = morpionParser.parseMorpionSize(morpionSize);
    
    final PlayerEntry parsedPlayerOneEntry = morpionParser.parsePlayerEntry(playerOneEntry);
    final PlayerEntry parsedPlayerTwoEntry = morpionParser.parsePlayerEntry(playerTwoEntry);
    
    players = Stream.of(parsedPlayerOneEntry, parsedPlayerTwoEntry)
      .collect(Collectors.toMap(
        PlayerEntry::getPlayerName,
        playerEntry -> new Player(playerEntry.getPlayerName(), playerEntry.getMorpionCode())));
    
    playerOne = players.get(parsedPlayerOneEntry.getPlayerName());
    playerTwo = players.get(parsedPlayerTwoEntry.getPlayerName());
    
    morpionVectors = IntStream.rangeClosed(1, this.morpionSize.getRows() * 2 + 2)
      .mapToObj(__ -> new MorpionVector(this.morpionSize.getRows()))
      .toArray(MorpionVector[]::new);
    
    slots = IntStream.rangeClosed(1, this.morpionSize.getRows() * this.morpionSize.getColumns())
      .mapToObj(__ -> new MorpionSlot())
      .toArray(MorpionSlot[]::new);
  }

  public void play (final String playerName, final String morpionCoordinates)
  {
    final MorpionCoordinates parsedMorpionCoordinates = morpionParser.parseMorpionCoordinates(morpionCoordinates);
    
    final List<Integer> checkIndexes = new ArrayList<>(Arrays.asList(parsedMorpionCoordinates.getRow(), morpionSize.getRows() + parsedMorpionCoordinates.getColumn()));
    
    if (parsedMorpionCoordinates.getRow() == parsedMorpionCoordinates.getColumn())
    {
      checkIndexes.add(2 * morpionSize.getRows());
    }
    
    if (parsedMorpionCoordinates.getRow() + parsedMorpionCoordinates.getColumn() == morpionSize.getRows() - 1)
    {
      checkIndexes.add(2 * morpionSize.getRows() + 1);
    }
    
    for (final Integer checkIndex : checkIndexes)
    {
      morpionVectors[checkIndex].play(players.get(playerName));
    }
    
    slots[morpionSize.getRows() * parsedMorpionCoordinates.getRow() + parsedMorpionCoordinates.getColumn()].play(players.get(playerName));
  }
  
  public String report ()
  {
    return morpionReporter.report(this);
  }
  
  public String display ()
  {
    return morpionReporter.display(slots);
  }

  public MorpionVector[] getMorpionVectors()
  {
    return morpionVectors;
  }

  public MorpionSize getMorpionSize()
  {
    return morpionSize;
  }

  public long getPlayedCounter()
  {
    return Arrays.stream(slots)
        .filter(MorpionSlot::isPlayed)
        .count();
  }
  
  public Player getFirstPlayer ()
  {
    return playerOne;
  }
  
  public Player getSecondPlayer ()
  {
    return playerTwo;
  }
}
