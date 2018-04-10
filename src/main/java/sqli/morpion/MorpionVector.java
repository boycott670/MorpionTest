package sqli.morpion;

import java.util.Objects;

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

  public void play (final Player currentPlayer)
  {
    if (firstPlayedWith == null)
    {
      firstPlayedWith = currentPlayer;
    }
    
    if (Objects.equals(firstPlayedWith, currentPlayer))
    {
      if (++playedCounter == morpionSize)
      {
        won = true;
      }
    }
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
