package com.sqli.nespresso.morpion.status;

import com.sqli.nespresso.morpion.Box;
import com.sqli.nespresso.morpion.Player;

public interface MorpionStatus
{
  void setBoxes(final Box[] boxes);

  boolean isIncomplete();

  boolean isDraw();

  Player winningPlayer();
}
