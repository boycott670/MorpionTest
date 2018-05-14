package com.sqli.nespresso.morpion.reporters;

import java.util.Optional;

import com.sqli.nespresso.morpion.entities.Player;

public interface MorpionStateReport
{
  Optional<Player> winner();
  
  boolean isEquality();
  
  boolean isIncomplete();
}
