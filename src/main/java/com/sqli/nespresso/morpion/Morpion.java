package com.sqli.nespresso.morpion;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Morpion
{
  static final String LINE_SEPARATOR = "\n";
  
  private final MorpionParser parser;
  
  private final MorpionDisplayer displayer;
  
  private final ImmutablePair<Integer, Integer> size;
  
  private final Map<String, Player> players;
  
  private final MorpionSlot[] slots;
  
  Morpion(final String size, final String player1, final String player2)
  {
    parser = new DefaultMorpionParser();
    
    displayer = new DefaultMorpionDisplayer();
    
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
    return null;
  }
  
  String display()
  {
    return displayer.display(size, slots);
  }
}
