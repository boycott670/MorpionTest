package com.sqli.nespresso.morpion.reporters;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.extractors.MorpionExtractor;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class DefaultMorpionStateReporter implements MorpionStateReporter
{
  
  private MorpionSlot[] morpionSlots;

  private ImmutablePair<Integer, Integer> morpionSize;
	  
  private MorpionExtractor morpionExtractor;
  
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

  private Optional<Player> winner(final MorpionSlot[] slots)
  {
    final Predicate<MorpionSlot[]> allFilled = slotsToValidate -> Arrays.stream(slotsToValidate)
        .map(MorpionSlot::player)
        .allMatch(Optional::isPresent);
    
    final Predicate<MorpionSlot[]> isWon = slotsToValidate -> Arrays.stream(slotsToValidate)
        .map(MorpionSlot::player)
        .map(Optional::get)
        .map(Player::playingBy)
        .map(String::valueOf)
        .collect(Collectors.joining())
        .chars()
        .distinct()
        .count() == 1;
    
    if (allFilled.test(slots) && isWon.test(slots))
    {
      return Arrays.stream(slots)
          .map(MorpionSlot::player)
          .map(Optional::get)
          .findFirst();
    }
    
    return Optional.empty();
  }
  
  @Override
  public MorpionStateReport getReport()
  {
    morpionExtractor.setMorpionSlots(morpionSlots);
    
    morpionExtractor.setMorpionSize(morpionSize);
    
    final Stream<MorpionSlot[]> allRows = Arrays.stream(morpionExtractor.extractorRows());
    
    final Stream<MorpionSlot[]> allColumns = Arrays.stream(morpionExtractor.extractorColumns());
    
    final Stream<MorpionSlot[]> twoDiagonals = Stream.of(morpionExtractor.extractorDiagonal(), morpionExtractor.extractorReverseDiagonal());
    
    final Optional<Player> winner = Stream.of(allRows, allColumns, twoDiagonals)
        .flatMap(Function.identity())
        .map(this::winner)
        .filter(Optional::isPresent)
        .findFirst()
        .map(Optional::get);    
    
    return new MorpionStateReport()
    {
      
      @Override
      public boolean isIncomplete()
      {
        return Arrays.stream(morpionSlots)
            .map(MorpionSlot::player)
            .anyMatch(((Predicate<Optional<?>>) Optional::isPresent).negate());
      }

      @Override
      public Optional<Player> winner()
      {
        return winner;
      }
    };
  }

}
