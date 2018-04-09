package sqli.morpion;

import sqli.morpion.entities.Player;

public final class MorpionVector
{
  private Player firstPlayedWith;
  private int playedCounter;
  private final int morpionSize;
  
  private boolean won = false;
  
  public MorpionVector(int morpionSize)
  {
    this.morpionSize = morpionSize;
  }

  public boolean play (final Player currentPlayer)
  {
    if (firstPlayedWith == null)
    {
      firstPlayedWith = currentPlayer;
    }
    
    if (firstPlayedWith.compareTo(currentPlayer) == 0)
    {
      if (++playedCounter == morpionSize)
      {
        return won = true;
      }
    }
    
    return won;
  }
  
  public boolean isWon ()
  {
    return won;
  }
  
  public Player getPlayer ()
  {
    return firstPlayedWith;
  }
}
