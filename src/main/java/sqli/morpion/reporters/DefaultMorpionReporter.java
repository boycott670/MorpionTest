package sqli.morpion.reporters;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import sqli.morpion.Morpion;
import sqli.morpion.MorpionVector;
import sqli.morpion.entities.MorpionSlot;
import sqli.morpion.entities.Player;

public final class DefaultMorpionReporter implements MorpionReporter
{
  @Override
  public String report(Morpion morpion)
  {
    final Optional<Player> winner = Arrays.stream(morpion.getMorpionVectors())
      .filter(MorpionVector::isWon)
      .findAny()
      .map(MorpionVector::getPlayer);
    
    if (winner.isPresent())
    {
      return String.format("Game Over, %s is a winner", winner.get().getName());
    }
    
    if (morpion.getPlayedCounter() == morpion.getMorpionSize().getRows() * morpion.getMorpionSize().getColumns())
    {
      return "Game Over, equality";
    }
    
    final int leftMorpionSlots = morpion.getMorpionSize().getRows() * morpion.getMorpionSize().getColumns() - morpion.getPlayedCounter();
    
    return String.format("%d games for %s, %d games for %s", leftMorpionSlots / 2 + leftMorpionSlots % 2, morpion.getFirstPlayer().getName(), leftMorpionSlots / 2, morpion.getSecondPlayer().getName());
  }

  @Override
  public String display(MorpionSlot[] slots)
  {
    final int size = (int)Math.sqrt(slots.length);
    
    return IntStream.range(0, size)
      .mapToObj(rowIndex -> IntStream.range(0, size)
          .mapToObj(colIndex -> String.valueOf(slots[size * rowIndex + colIndex].display()))
          .collect(Collectors.joining("|")))
      .collect(Collectors.joining(Morpion.LINE_SEPARATOR)) + Morpion.LINE_SEPARATOR;
  }
}
