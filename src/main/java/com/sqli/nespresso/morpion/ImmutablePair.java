package com.sqli.nespresso.morpion;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

final class ImmutablePair<F, S>
{
  static <F, S> ImmutablePair<F, S> of(final F first, final S second)
  {
    return new ImmutablePair<F, S>(first, second);
  }
  
  private final Entry<F, S> entry;
  
  private ImmutablePair(final F first, final S second)
  {
    entry = new SimpleImmutableEntry<>(first, second);
  }
  
  F getFirst()
  {
    return entry.getKey();
  }
  
  S getSecond()
  {
    return entry.getValue();
  }
}
