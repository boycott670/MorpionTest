package com.sqli.nespresso.morpion;

interface MorpionDisplayer
{
  String display(final ImmutablePair<Integer, Integer> size, final MorpionSlot[] slots);
}
