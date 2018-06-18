package com.sqli.challenge;

import java.util.function.Function;

final class MorpionParse
{
	static final class Pair<F,S>
	{
		private final F first;
		private final S second;
		
		private Pair(F first, S second)
		{
			this.first = first;
			this.second = second;
		}

		F getFirst()
		{
			return first;
		}

		S getSecond()
		{
			return second;
		}
	}
	
	private <F, S> Pair<F, S> parse(final String token, final char delimiter, final Function<? super String, ? extends F> firstTokenParser, final Function<? super String, ? extends S> secondTokenParser)
	{
		final String[] tokens = token.split(String.valueOf(delimiter));
		
		final String firstToken = tokens[0];
		
		final String secondToken = tokens[1];
		
		return new Pair<>(firstTokenParser.apply(firstToken), secondTokenParser.apply(secondToken));
	}
	
	int parseSize(final String size)
	{
		final Pair<Integer, Integer> parsedSize = parseCoordinates(size);
		
		return parsedSize.first * parsedSize.second;
	}
	
	Player parsePlayer(final String player)
	{
		final Pair<String, Character> parsedPlayer = parse(player, ':', Function.identity(), token -> token.charAt(0));
		
		return new Player(parsedPlayer.first, parsedPlayer.second);
	}
	
	Pair<Integer, Integer> parseCoordinates(final String coordinates)
	{
		return parse(coordinates, 'x', Integer::valueOf, Integer::valueOf);
	}
}
