package com.sqli.nespresso.morpion.displayers;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.extractors.MorpionExtractor;
import com.sqli.nespresso.morpion.reporters.MorpionStateReport;
import com.sqli.nespresso.morpion.reporters.MorpionStateReporter;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class DefaultMorpionStateReportDisplayer implements MorpionStateReportDisplayer
{
  private MorpionSlot[] morpionSlots;

  private ImmutablePair<Integer, Integer> morpionSize;

  private MorpionExtractor morpionExtractor;

  private Player firstPlayer;

  private Player secondPlayer;
  
  private MorpionStateReporter reporter;

  @Override
  public void setMorpionSlots(MorpionSlot[] morpionSlots)
  {
    this.morpionSlots = morpionSlots;
  }

  @Override
  public void setMorpionSize(ImmutablePair<Integer, Integer> morpionSize)
  {
    this.morpionSize = morpionSize;
  }

  @Override
  public void setMorpionExtractor(MorpionExtractor morpionExtractor)
  {
    this.morpionExtractor = morpionExtractor;
  }

  @Override
  public void setFirstPlayer(Player player)
  {
    this.firstPlayer = player;
  }

  @Override
  public void setSecondPlayer(Player player)
  {
    this.secondPlayer = player;
  }

  @Override
  public void setMorpionStateReporter(MorpionStateReporter reporter)
  {
    this.reporter = reporter;
  }

  @Override
  public String report()
  {
    reporter.setMorpionSlots(morpionSlots);
    
    reporter.setMorpionSize(morpionSize);
    
    reporter.setMorpionExtractor(morpionExtractor);
    
    reporter.setFirstPlayer(firstPlayer);
    
    reporter.setSecondPlayer(secondPlayer);
    
    final MorpionStateReport report = reporter.getReport();
    
    final Optional<Player> winner = report.winner();
    
    if (winner.isPresent())
    {
      return String.format("Game Over, %s is a winner", winner.get().name());
    }
    else if (report.isIncomplete())
    {
      final long remainingEmptyMorpionSlotsByHalf = Arrays.stream(morpionSlots)
          .map(MorpionSlot::player)
          .filter(((Predicate<Optional<?>>) Optional::isPresent).negate())
          .count() / 2;
      
      return String.format("%d games for %s, %d games for %s", remainingEmptyMorpionSlotsByHalf + (remainingEmptyMorpionSlotsByHalf % 2), firstPlayer.name(), remainingEmptyMorpionSlotsByHalf, secondPlayer.name());
    }
    else
    {
      return "Game Over, equality";
    }
  }
}
