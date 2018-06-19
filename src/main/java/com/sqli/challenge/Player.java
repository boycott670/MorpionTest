package com.sqli.challenge;

import java.util.Objects;

final class Player
{
	private final String name;
	
	private final char token;

	Player(String name, char token)
	{
		this.name = name;
		this.token = token;
	}
	
	boolean hasName(final String name)
	{
		return Objects.equals(this.name, name);
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	char display()
	{
	  return token;
	}
}
