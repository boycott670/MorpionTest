package sqli.morpion.entities;

public final class Player implements Comparable<Player>
{
  private final String name;
  private final char morpionCode;
  
  public Player(String name, char morpionCode)
  {
    this.name = name;
    this.morpionCode = morpionCode;
  }

  public String getName()
  {
    return name;
  }

  public char getMorpionCode()
  {
    return morpionCode;
  }

  @Override
  public int compareTo(Player other)
  {
    return other == null ? 1 : (name == null ? (other.getName() == null ? 0 : -1) : name.compareTo(other.getName()));
  }
}
