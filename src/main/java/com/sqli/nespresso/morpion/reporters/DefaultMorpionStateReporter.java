package com.sqli.nespresso.morpion.reporters;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.entities.Player;
import com.sqli.nespresso.morpion.extractors.MorpionExtractor;

public final class DefaultMorpionStateReporter implements MorpionStateReporter
{
  
	  private MorpionSlot[] morpionSlots;
	  
  private MorpionExtractor morpionExtractor;

  private Player firstPlayer;
  
  private Player secondPlayer;
  
  @Override
  public void setMorpionSlots(MorpionSlot[] slots)
  {
    this.morpionSlots = slots;
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

  private String winner(final MorpionSlot[] slots)
  {
    final String slotsRepr = Arrays.stream(slots).map(MorpionSlot::display).collect(Collectors.joining());
    
    if (slotsRepr.replaceAll("\\s+", "").length() == slots.length && slotsRepr.chars().distinct().count() == 1)
    {
      return String.valueOf(slotsRepr.charAt(0));
    }
    
    return null;
  }
  
  @Override
  public MorpionStateReport getReport()
  {
    final Optional<String> winner = Stream.concat(Stream.of(morpionExtractor.extractorRows(),
        morpionExtractor.extractorColumns())
        .flatMap(Arrays::stream),
        Stream.of(morpionExtractor.extractorDiagonal(),
            morpionExtractor.extractorReverseDiagonal()))
        .map(this::winner)
        .filter(Objects::nonNull)
        .findFirst();
    
    return new MorpionStateReport()
    {
      
      @Override
      public boolean isIncomplete()
      {
        return Arrays.stream(morpionSlots)
            .map(MorpionSlot::display)
            .filter(" "::equals)
            .findFirst()
            .isPresent();
      }

      @Override
      public Optional<Player> winner()
      {
        return winner.map(playing -> Objects.equals(firstPlayer.display(), playing) ? firstPlayer : secondPlayer);
      }
    };
  }

}
