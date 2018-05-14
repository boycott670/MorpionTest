package com.sqli.nespresso.morpion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
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
  
  private final Map<String, Player> players;
  
  private final MorpionSlot[] slots;
  
  Morpion(final String size, final String player1, final String player2)
  {
    parser = new DefaultMorpionParser();
    
    displayer = new DefaultMorpionDisplayer();
    
    extractor = new DefaultMorpionExtractor();
    
    stateReporter = new DefaultMorpionStateReporter();
    
    this.size = parser.parseMorpionSize(size);
    
    players = Stream.of(player1, player2)
        .map(parser::parseMorpionPlayer)
        .map(Player::fromPair)
        .collect(Collectors.toMap(Player::getName, Function.identity()));
    
    slots = Stream.generate(MorpionSlot::empty)
        .limit(this.size.getFirst() * this.size.getSecond())
        .toArray(MorpionSlot[]::new);
  }
  
  void play(final String player, final String slotCoordinates)
  {
    final ImmutablePair<Integer, Integer> parsedSlotCoordinates = parser.parseMorpionSlotCoordinates(slotCoordinates);
    
    slots[size.getFirst() * parsedSlotCoordinates.getFirst() + parsedSlotCoordinates.getSecond()].fill(players.get(player));
  }
  
  String report()
  {
    final List<Player> players = new ArrayList<>(this.players.values());
    
    stateReporter.setFirstPlayer(players.get(0));
    
    stateReporter.setSecondPlayer(players.get(1));
    
    stateReporter.setMorpionExtractor(extractor);
    
    stateReporter.setMorpionSize(size);
    
    stateReporter.setMorpionSlots(slots);
    
    final MorpionStateReport stateReport = stateReporter.getReport();
    
    final Optional<Player> winner = stateReport.winner();
    
    if (winner.isPresent())
    {
      return String.format("Game Over, %s is a winner", winner.get().getName());
    }
    else if (stateReport.isIncomplete())
    {
      final long remainingSlots = Arrays.stream(slots).map(MorpionSlot::display).filter(" "::equals).count();
      
      return String.format("%d games for %s, %d games for %s", remainingSlots / 2 + (remainingSlots % 2), players.get(0).getName(), remainingSlots / 2, players.get(1).getName());
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
