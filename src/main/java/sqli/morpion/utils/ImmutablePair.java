package sqli.morpion.utils;

public abstract class ImmutablePair<F, S>
{
  final F first;
  final S second;
  
  ImmutablePair(F first, S second)
  {
    this.first = first;
    this.second = second;
  }
}
