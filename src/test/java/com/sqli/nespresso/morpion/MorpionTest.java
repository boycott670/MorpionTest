package com.sqli.nespresso.morpion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sqli.nespresso.morpion.exceptions.BoxAlreadySelectedException;

public class MorpionTest
{
  @Test
  public void remainingGames()
  {
    Morpion game = new Morpion("3x3", "player1:X", "player2:O");
    game.play("player1", "0x0");
    game.play("player2", "0x1");
    assertEquals("4 games for player1, 3 games for player2", game.report());
  }

  @Test(expected = BoxAlreadySelectedException.class)
  public void boxAlreadySelected()
  {
    Morpion game = new Morpion("3x3", "player1:X", "player2:O");
    game.play("player1", "0x0");
    game.play("player2", "0x1");
    game.play("player1", "0x1");
  }

  @Test
  public void winner()
  {
    Morpion game = new Morpion("3x3", "player1:X", "player2:O");
    game.play("player1", "0x0");
    game.play("player2", "0x1");
    game.play("player1", "1x1");

    game.play("player2", "0x2");
    game.play("player1", "2x2");
    assertEquals("Game Over, player1 is a winner", game.report());
  }

  @Test
  public void displayEqualityGame()
  {
    Morpion game = new Morpion("3x3", "player1:X", "player2:O");
    game.play("player1", "0x0");
    game.play("player2", "1x1");
    game.play("player1", "0x1");
    game.play("player2", "0x2");
    game.play("player1", "2x0");
    game.play("player2", "1x0");
    game.play("player1", "1x2");
    game.play("player2", "2x1");
    game.play("player1", "2x2");
    assertEquals("Game Over, equality", game.report());
    String expectedDisplay = new StringBuilder().append("X|X|O")
        .append("\n")
        .append("O|O|X")
        .append("\n")
        .append("X|O|X")
        .append("\n")
        .toString();
    assertEquals(expectedDisplay.toString(), game.display());
  }
}
