package sqli.morpion.utils;

public final class MorpionCoordinates extends ImmutablePair<Integer, Integer>
{
  private MorpionCoordinates(Integer first, Integer second)
  {
    super(first, second);
  }
  
  public static MorpionCoordinates of (final int row, final int column)
  {
    return new MorpionCoordinates(row, column);
  }
  
  public Integer getRow ()
  {
    return first;
  }
  
  public Integer getColumn ()
  {
    return second;
  }
}
