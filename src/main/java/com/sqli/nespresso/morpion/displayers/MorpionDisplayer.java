package com.sqli.nespresso.morpion.displayers;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public interface MorpionDisplayer
{
  String display(final ImmutablePair<Integer, Integer> size, final MorpionSlot[] slots);
}
