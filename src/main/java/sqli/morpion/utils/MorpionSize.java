package sqli.morpion.utils;

public final class MorpionSize extends ImmutablePair<Integer, Integer>
{
  private MorpionSize(Integer first, Integer second)
  {
    super(first, second);
  }
  
  public static MorpionSize of (final int rows, final int columns)
  {
    return new MorpionSize(rows, columns);
  }
  
  public Integer getRows ()
  {
    return first;
  }
  
  public Integer getColumns ()
  {
    return second;
  }
}
