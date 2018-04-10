package sqli.morpion.entities;

import sqli.morpion.BoxAlreadySelectedException;

public final class MorpionSlot
{
  private Player player;
  
  public void play (final Player player)
  {
    if (isPlayed())
    {
      throw new BoxAlreadySelectedException();
    }
    
    this.player = player;
  }
  
  public boolean isPlayed ()
  {
    return player != null;
  }
  
  public char display ()
  {
    return player != null ? player.getMorpionCode() : ' ';
  }
}
