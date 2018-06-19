package com.sqli.challenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

final class MorpionSlotsVectors implements Iterable<MorpionSlot[]>
{
  
  private final Collection<MorpionSlot[]> vectors;
  
  MorpionSlotsVectors()
  {
    vectors = new ArrayList<>();
  }
  
  void addVector(final MorpionSlot[] entry)
  {
    vectors.add(entry);
  }

  @Override
  public Iterator<MorpionSlot[]> iterator()
  {
    return vectors.iterator();
  }

}
