package com.sqli.challenge;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class MorpionStateReport
{
  private final MorpionSlot[] slots;
  
  private final MorpionSlotsExtract extract;
  
  MorpionStateReport(final MorpionSlot[] slots, final int sizeOfVectorToValidate)
  {
    extract = new MorpionSlotsExtract(this.slots = slots, sizeOfVectorToValidate);
  }
  
  Optional<Player> winnerInVector(final MorpionSlot[] vector)
  {
    return vector[0].isRowWon(Arrays.copyOfRange(vector, 1, vector.length));
  }
  
  private Optional<Player> winner()
  {
    final Stream<MorpionSlot[]> allVectors = Stream.of(
        extract.rows(),
        extract.columns(),
        extract.diagonal(),
        extract.reverseDiagonal()
    )
        .flatMap(vectors -> StreamSupport.stream(vectors.spliterator(), false));
    
    return allVectors.map(this::winnerInVector)
        .filter(Optional::isPresent)
        .findFirst()
        .flatMap(Function.identity());
  }
  
  private String reportDraw(final Player firstPlayer, final Player secondPlayer, final Player lastPlayingPlayer)
  {
    final long numberOfEmptySlots = Arrays.stream(slots).filter(MorpionSlot::isNotPlayedYet).count();
    
    if (numberOfEmptySlots == 0)
    {
      return "Game Over, equality";
    }
    
    final long remainingPlays = numberOfEmptySlots / 2;
    
    final ToLongFunction<Player> getRemainingPlaysFor = player -> remainingPlays + (lastPlayingPlayer != player ? 1 : 0);
    
    final long remainingPlaysForFirstPlayer = getRemainingPlaysFor.applyAsLong(firstPlayer);
    
    final long remainingPlaysForSecondPlayer = getRemainingPlaysFor.applyAsLong(secondPlayer);
    
    return String.format("%d games for %s, %d games for %s", remainingPlaysForFirstPlayer, firstPlayer, remainingPlaysForSecondPlayer, secondPlayer);
  }
  
  String report(final Player firstPlayer, final Player secondPlayer, final Player lastPlayingPlayer)
  {
    final Optional<Player> winnner = winner();
    
    if (winnner.isPresent())
    {
      return String.format("Game Over, %s is a winner", winnner.get());
    }
    
    return reportDraw(firstPlayer, secondPlayer, lastPlayingPlayer);
  }
}
