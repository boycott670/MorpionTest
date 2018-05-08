package com.sqli.nespresso.morpion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.parsers.DefaultMorpionParser;
import com.sqli.nespresso.morpion.parsers.MorpionParser;
import com.sqli.nespresso.morpion.status.DefaultMorpionStatus;
import com.sqli.nespresso.morpion.status.MorpionStatus;

final class Morpion
{
  private final MorpionParser parser = new DefaultMorpionParser();
  private final MorpionStatus status = new DefaultMorpionStatus();

  private final int size;
  private final int flattenedSize;

  private final Map<String, Player> players;

  private final Box[] boxes;

  Morpion(final String size, final String player1, final String player2)
  {
    this.size = parser.parseSize(size);
    flattenedSize = Double.valueOf(Math.pow(this.size, 2))
        .intValue();

    players = Collections.unmodifiableMap(Stream.of(player1, player2)
        .map(parser::parsePlayer)
        .collect(Collectors.toMap(Player::getName, Function.identity(), (duplicatePlayer1, duplicatePlayer2) ->
        {
          throw new IllegalArgumentException("Duplicate player names");
        }, LinkedHashMap::new)));

    boxes = Stream.generate(Box::empty)
        .limit(flattenedSize)
        .toArray(Box[]::new);
  }

  void play(final String player, final String coordinates)
  {
    final Entry<Integer, Integer> parsedCoordinates = parser.parseCoordinates(coordinates);

    boxes[parsedCoordinates.getKey() * this.size + parsedCoordinates.getValue()].play(players.get(player));
  }

  String report()
  {
    status.setBoxes(boxes);

    return Optional.ofNullable(status.winningPlayer())
        .map(Player::getName)
        .map(winner -> String.format("Game Over, %s is a winner", winner))
        .orElse(status.isIncomplete()
            ? String.format("%d games for %s, %d games for %s", flattenedSize / 2, players.keySet()
                .iterator()
                .next(), flattenedSize / 2 - (flattenedSize % 2), new ArrayList<>(players.keySet()).get(1))
            : "Game Over, equality");
  }

  String display()
  {
    return IntStream.range(0, size)
        .mapToObj(rowIndex -> IntStream.range(rowIndex * size, rowIndex * size + size)
            .mapToObj(index -> boxes[index])
            .map(Box::display)
            .collect(Collectors.joining("|")))
        .collect(Collectors.joining("\n")) + "\n";
  }
}
