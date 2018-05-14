package com.sqli.nespresso.morpion;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.displayers.DefaultMorpionDisplayer;
import com.sqli.nespresso.morpion.displayers.MorpionDisplayer;
import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.extractors.DefaultMorpionExtractor;
import com.sqli.nespresso.morpion.extractors.MorpionExtractor;
import com.sqli.nespresso.morpion.parsers.DefaultMorpionParser;
import com.sqli.nespresso.morpion.parsers.MorpionParser;
import com.sqli.nespresso.morpion.reporters.DefaultMorpionStateReporter;
import com.sqli.nespresso.morpion.reporters.MorpionStateReport;
import com.sqli.nespresso.morpion.reporters.MorpionStateReporter;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class Morpion
{
  public static final String LINE_SEPARATOR = "\n";
  
  private final MorpionParser parser;
  
  private final MorpionDisplayer displayer;
  
  private final MorpionExtractor extractor;
  
  private final MorpionStateReporter stateReporter;
  
  private final ImmutablePair<Integer, Integer> size;
  
  private final Player firstPlayer;
  
  private final Player secondPlayer;
  
  private final MorpionSlot[] slots;
  
  Morpion(final String size, final String player1, final String player2)
  {
    parser = new DefaultMorpionParser();
    
    displayer = new DefaultMorpionDisplayer();
    
    extractor = new DefaultMorpionExtractor();
    
    stateReporter = new DefaultMorpionStateReporter();
    
    this.size = parser.parseMorpionSize(size);
    
    final Function<String, Player> toPlayer = ((Function<String, ImmutablePair<String, Character>>)parser::parseMorpionPlayer).andThen(Player::fromPair);
    
    firstPlayer = toPlayer.apply(player1);
    
    secondPlayer = toPlayer.apply(player2);
    
    slots = Stream.generate(MorpionSlot::empty)
        .limit(this.size.getFirst() * this.size.getSecond())
        .toArray(MorpionSlot[]::new);
    
    extractor.setMorpionSlots(slots);
    
    extractor.setMorpionSize(this.size);
    
    stateReporter.setMorpionExtractor(extractor);
    
    stateReporter.setMorpionSlots(slots);
    
    stateReporter.setFirstPlayer(firstPlayer);
    
    stateReporter.setSecondPlayer(secondPlayer);
  }
  
  private Player getPlayerByName(final String name)
  {
	  return Objects.equals(name, firstPlayer.getName()) ? firstPlayer : secondPlayer;
  }
  
  void play(final String player, final String slotCoordinates)
  {
    final ImmutablePair<Integer, Integer> parsedSlotCoordinates = parser.parseMorpionSlotCoordinates(slotCoordinates);
    
    slots[size.getFirst() * parsedSlotCoordinates.getFirst() + parsedSlotCoordinates.getSecond()].fill(getPlayerByName(player));
  }
  
  String report()
  {
    final MorpionStateReport stateReport = stateReporter.getReport();
    
    final Optional<Player> winner = stateReport.winner();
    
    if (winner.isPresent())
    {
      return String.format("Game Over, %s is a winner", winner.get().getName());
    }
    else if (stateReport.isIncomplete())
    {
      final long remainingSlots = Arrays.stream(slots).map(MorpionSlot::display).filter(" "::equals).count();
      
      return String.format("%d games for %s, %d games for %s", remainingSlots / 2 + (remainingSlots % 2), firstPlayer.getName(), remainingSlots / 2, secondPlayer.getName());
    }
    else
    {
      return "Game Over, equality";
    }
  }
  
  public String display()
  {
    return displayer.display(size, slots);
  }
}
