package com.sqli.nespresso.morpion.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.Box;
import com.sqli.nespresso.morpion.Player;

public final class DefaultMorpionStatus implements MorpionStatus
{
  private int size;
  private Box[] boxes;

  @Override
  public void setBoxes(Box[] boxes)
  {
    this.boxes = boxes;
    size = Double.valueOf(Math.sqrt(boxes.length))
        .intValue();
  }

  @Override
  public boolean isIncomplete()
  {
    return Arrays.stream(boxes)
        .filter(Box::isEmpty)
        .findAny()
        .isPresent();
  }

  @Override
  public boolean isDraw()
  {
    return Objects.isNull(winningPlayer());
  }

  @Override
  public Player winningPlayer()
  {
    return Stream.<Supplier<Stream<IntStream>>>of(this::rows, this::columns, this::diagonal, this::reverseDiagonal)
        .map(Supplier::get)
        .map(this::checkWinningPlayer)
        .filter(Optional::isPresent)
        .findFirst()
        .orElse(Optional.empty())
        .orElse(null);
  }

  private Stream<IntStream> rows()
  {
    return IntStream.iterate(0, n -> n + size)
        .mapToObj(index -> IntStream.range(index, index + size))
        .limit(size);
  }

  private Stream<IntStream> columns()
  {
    return IntStream.range(0, size)
        .mapToObj(index -> IntStream.iterate(index, n -> n + size)
            .limit(size));
  }

  private Stream<IntStream> diagonal()
  {
    return Stream.of(IntStream.iterate(0, n -> n + size + 1)
        .limit(size));
  }

  private Stream<IntStream> reverseDiagonal()
  {
    return Stream.of(IntStream.iterate(size - 1, n -> n + size - 1)
        .limit(size));
  }

  private Optional<Player> checkWinningPlayer(final Box[] boxes)
  {
    return Optional.ofNullable(new HashSet<>(Arrays.asList(boxes)
        .stream()
        .map(Box::playedBy)
        .collect(Collectors.toList())).size() == 1 ? boxes[0].playedBy() : null);
  }

  private Optional<Player> checkWinningPlayer(final Stream<IntStream> range)
  {
    return range.map(indexes -> indexes.mapToObj(index -> boxes[index])
        .toArray(Box[]::new))
        .map(this::checkWinningPlayer)
        .reduce(Optional.empty(), (r1, r2) -> r1.isPresent() ? r1 : r2);
  }

}
