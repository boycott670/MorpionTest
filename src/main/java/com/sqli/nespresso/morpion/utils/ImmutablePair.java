package com.sqli.nespresso.morpion.utils;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

public final class ImmutablePair<F, S>
{
  public static <F, S> ImmutablePair<F, S> of(final F first, final S second)
  {
    return new ImmutablePair<F, S>(first, second);
  }
  
  private final Entry<F, S> entry;
  
  private ImmutablePair(final F first, final S second)
  {
    entry = new SimpleImmutableEntry<>(first, second);
  }
  
  public F getFirst()
  {
    return entry.getKey();
  }
  
  public S getSecond()
  {
    return entry.getValue();
  }
}
