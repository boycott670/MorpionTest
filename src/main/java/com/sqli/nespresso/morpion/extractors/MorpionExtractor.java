package com.sqli.nespresso.morpion.extractors;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public interface MorpionExtractor
{
  MorpionSlot[][] extractorRows(final ImmutablePair<Integer, Integer> size, final MorpionSlot[] slots);
  
  MorpionSlot[][] extractorColumns(final ImmutablePair<Integer, Integer> size, final MorpionSlot[] slots);
  
  MorpionSlot[] extractorDiagonal(final ImmutablePair<Integer, Integer> size, final MorpionSlot[] slots);
  
  MorpionSlot[] extractorReverseDiagonal(final ImmutablePair<Integer, Integer> size, final MorpionSlot[] slots);
}
