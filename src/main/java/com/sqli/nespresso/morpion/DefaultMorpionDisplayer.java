package com.sqli.nespresso.morpion;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class DefaultMorpionDisplayer implements MorpionDisplayer
{

  private String displayRow(final MorpionSlot[] row)
  {
    return Arrays.stream(row).map(MorpionSlot::display).collect(Collectors.joining("|"));
  }
  
  @Override
  public String display(ImmutablePair<Integer, Integer> size, MorpionSlot[] slots)
  {
    return IntStream.range(0, size.getFirst())
        .mapToObj(rowIndex -> Arrays.copyOfRange(slots,
            size.getFirst() * rowIndex,
            size.getFirst() * rowIndex + size.getSecond()))
        .map(this::displayRow)
        .collect(Collectors.joining(Morpion.LINE_SEPARATOR)) + Morpion.LINE_SEPARATOR;
  }

}
