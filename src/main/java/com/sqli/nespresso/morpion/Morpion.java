package com.sqli.nespresso.morpion;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.displayers.DefaultMorpionDisplayer;
import com.sqli.nespresso.morpion.displayers.DefaultMorpionStateReportDisplayer;
import com.sqli.nespresso.morpion.displayers.MorpionDisplayer;
import com.sqli.nespresso.morpion.displayers.MorpionStateReportDisplayer;
import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.exceptions.BoxAlreadySelectedException;
import com.sqli.nespresso.morpion.extractors.DefaultMorpionExtractor;
import com.sqli.nespresso.morpion.parsers.DefaultMorpionParser;
import com.sqli.nespresso.morpion.parsers.MorpionParser;
import com.sqli.nespresso.morpion.reporters.DefaultMorpionStateReporter;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class Morpion
{
  public static final String LINE_SEPARATOR = "\n";
  
  private final MorpionParser parser;
  
  private final MorpionDisplayer displayer;
  
  private final MorpionStateReportDisplayer reportDisplayer;
  
  private final Player firstPlayer;
  
  private final Player secondPlayer;
  
  private final ImmutablePair<Integer, Integer> size;
  
  private final MorpionSlot[] slots;
  
  Morpion(final String size, final String player1, final String player2)
  {
    parser = new DefaultMorpionParser();
    
    displayer = new DefaultMorpionDisplayer();
    
    reportDisplayer = new DefaultMorpionStateReportDisplayer();
    
    final Function<String, Player> toPlayer = ((Function<String, ImmutablePair<String, Character>>)parser::parseMorpionPlayer).andThen(Player::fromPair);
    
    firstPlayer = toPlayer.apply(player1);
    
    secondPlayer = toPlayer.apply(player2);
    
    this.size = parser.parseMorpionSize(size);
    
    slots = Stream.generate(MorpionSlot::empty)
        .limit(this.size.getFirst() * this.size.getSecond())
        .toArray(MorpionSlot[]::new);
  }
  
  private Player getPlayerByName(final String name)
  {
	  return Objects.equals(name, firstPlayer.name()) ? firstPlayer : secondPlayer;
  }
  
  void play(final String player, final String slotCoordinates)
  {
    final ImmutablePair<Integer, Integer> parsedSlotCoordinates = parser.parseMorpionSlotCoordinates(slotCoordinates);
    
    final int slotIndex = size.getFirst() * parsedSlotCoordinates.getFirst() + parsedSlotCoordinates.getSecond();
    
    if (slots[slotIndex].player().isPresent())
    {
      throw new BoxAlreadySelectedException();
    }
    
    slots[slotIndex].fill(getPlayerByName(player));
  }
  
  String report()
  {
    reportDisplayer.setMorpionSlots(slots);

    reportDisplayer.setMorpionSize(this.size);

    reportDisplayer.setMorpionExtractor(new DefaultMorpionExtractor());

    reportDisplayer.setFirstPlayer(firstPlayer);

    reportDisplayer.setSecondPlayer(secondPlayer);

    reportDisplayer.setMorpionStateReporter(new DefaultMorpionStateReporter());

    return reportDisplayer.report();
  }
  
  public String display()
  {
    return displayer.display(size, slots);
  }
}
