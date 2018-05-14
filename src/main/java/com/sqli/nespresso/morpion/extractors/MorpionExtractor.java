package com.sqli.nespresso.morpion.extractors;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public interface MorpionExtractor
{
	  void setMorpionSlots(final MorpionSlot[] slots);
	  
	  void setMorpionSize(final ImmutablePair<Integer, Integer> size);
	  
  MorpionSlot[][] extractorRows();
  
  MorpionSlot[][] extractorColumns();
  
  MorpionSlot[] extractorDiagonal();
  
  MorpionSlot[] extractorReverseDiagonal();
}
