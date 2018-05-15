package com.sqli.nespresso.morpion.displayers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sqli.nespresso.morpion.Morpion;
import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class DefaultMorpionDisplayer implements MorpionDisplayer
{

  private String displayRow(final MorpionSlot[] row)
  {
    return Arrays.stream(row)
        .map(slot -> slot.player()
            .map(Player::playingBy)
            .orElse(' '))
        .map(String::valueOf)
        .collect(Collectors.joining("|"));
  }
  
  @Override
  public String display(ImmutablePair<Integer, Integer> size, MorpionSlot[] slots)
  {
    final List<MorpionSlot> slotsAsList = Arrays.asList(slots);
    
    return IntStream.iterate(0, previousRowIndex -> previousRowIndex + size.getSecond())
        .limit(size.getFirst())
        .mapToObj(rowIndex -> IntStream.range(rowIndex, rowIndex + size.getSecond())
            .mapToObj(slotsAsList::get)
            .toArray(MorpionSlot[]::new))
        .map(this::displayRow)
        .collect(Collectors.joining(Morpion.LINE_SEPARATOR)) + Morpion.LINE_SEPARATOR;
  }

}
