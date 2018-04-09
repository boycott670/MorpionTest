package sqli.morpion.reporters;

import sqli.morpion.Morpion;
import sqli.morpion.entities.MorpionSlot;

public interface MorpionReporter
{
  String report (final Morpion morpion);
  String display (final MorpionSlot[] slots);
}
