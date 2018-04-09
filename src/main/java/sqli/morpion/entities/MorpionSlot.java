package sqli.morpion.entities;

import sqli.morpion.BoxAlreadySelectedException;

public final class MorpionSlot
{
  private Player player;
  
  public void play (final Player player)
  {
    if (this.player != null)
    {
      throw new BoxAlreadySelectedException();
    }
    
    this.player = player;
  }
  
  public char display ()
  {
    return player != null ? player.getMorpionCode() : ' ';
  }
}
