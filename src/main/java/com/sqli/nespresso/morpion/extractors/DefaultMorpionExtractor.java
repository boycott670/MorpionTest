package com.sqli.nespresso.morpion.extractors;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class DefaultMorpionExtractor implements MorpionExtractor
{

  @Override
  public MorpionSlot[][] extractorRows(ImmutablePair<Integer, Integer> size, MorpionSlot[] slots)
  {
    return IntStream.iterate(0, previousRowIndex -> previousRowIndex + size.getSecond())
        .limit(size.getFirst())
        .mapToObj(rowIndex -> Arrays.copyOfRange(slots, rowIndex, rowIndex + size.getSecond()))
        .toArray(MorpionSlot[][]::new);
  }

  @Override
  public MorpionSlot[][] extractorColumns(ImmutablePair<Integer, Integer> size, MorpionSlot[] slots)
  {
    return IntStream.iterate(0, previousColumnIndex -> previousColumnIndex + 1)
        .limit(size.getSecond())
        .mapToObj(columnIndex -> IntStream.iterate(columnIndex, previousIndex -> previousIndex + size.getSecond())
            .limit(size.getFirst())
            .mapToObj(index -> slots[index])
            .toArray(MorpionSlot[]::new))
        .toArray(MorpionSlot[][]::new);
  }

  @Override
  public MorpionSlot[] extractorDiagonal(ImmutablePair<Integer, Integer> size, MorpionSlot[] slots)
  {
    return IntStream.iterate(0, previousIndex -> previousIndex + size.getSecond() + 1)
        .limit(size.getFirst())
        .mapToObj(index -> slots[index])
        .toArray(MorpionSlot[]::new);
  }

  @Override
  public MorpionSlot[] extractorReverseDiagonal(ImmutablePair<Integer, Integer> size, MorpionSlot[] slots)
  {
    return IntStream.iterate(size.getSecond() - 1, previousIndex -> previousIndex + size.getSecond() - 1)
        .limit(size.getFirst())
        .mapToObj(index -> slots[index])
        .toArray(MorpionSlot[]::new);
  }

}
