package sqli.morpion.parsers;

import java.util.function.Function;

import sqli.morpion.entities.Pair;

public class MorpionParser
{
	private static <F, S> Pair<F, S> parse(final String haystack, final String delimitor, final Function<? super String, ? extends F> firstTokenParser, final Function<? super String, ? extends S> secondTokenParser)
	{
		final String[] tokens = haystack.split(delimitor);
		
		return new Pair<>(firstTokenParser.apply(tokens[0]), secondTokenParser.apply(tokens[1]));
	}
	
	public Pair<Integer, Integer> morpionParser(String morpionDimension, String separator)
	{
		return parse(morpionDimension, separator, Integer::parseInt, Integer::parseInt);
	}

	public Pair<String, String> playerParser(String player, String separator)
	{
		return parse(player, separator, Function.identity(), Function.identity());
	}

	public Pair<Integer, Integer> positionParser(String postion, String separator)
	{
		return morpionParser(postion, separator);		
	}
}
