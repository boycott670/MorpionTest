package sqli.morpion.utils;

public final class PlayerEntry extends ImmutablePair<String, Character>
{
  private PlayerEntry(String first, Character second)
  {
    super(first, second);
  }
  
  public static PlayerEntry of (final String playerName, final char morpionCode)
  {
    return new PlayerEntry(playerName, morpionCode);
  }
  
  public String getPlayerName ()
  {
    return first;
  }
  
  public Character getMorpionCode ()
  {
    return second;
  }
}
