package com.sqli.nespresso.morpion.reporters;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.extractors.MorpionExtractor;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public interface MorpionStateReporter
{
  void setMorpionSlots(final MorpionSlot[] morpionSlots);

  void setMorpionSize(final ImmutablePair<Integer, Integer> morpionSize);

  void setMorpionExtractor(final MorpionExtractor morpionExtractor);

  void setFirstPlayer(final Player player);

  void setSecondPlayer(final Player player);

  MorpionStateReport getReport();
}
