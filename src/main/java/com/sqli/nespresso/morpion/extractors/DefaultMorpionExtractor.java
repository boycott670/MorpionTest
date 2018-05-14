package com.sqli.nespresso.morpion.extractors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.sqli.nespresso.morpion.entities.MorpionSlot;
import com.sqli.nespresso.morpion.utils.ImmutablePair;

public final class DefaultMorpionExtractor implements MorpionExtractor
{

	private List<MorpionSlot> slots;
	
	private ImmutablePair<Integer, Integer> size;

  @Override
	public void setMorpionSlots(MorpionSlot[] slots)
	{
		this.slots = Arrays.asList(slots);
	}

	@Override
	public void setMorpionSize(ImmutablePair<Integer, Integer> size)
	{
		this.size = size;
	}
	
	private MorpionSlot[] extractSlots(final IntStream indexes)
	{
		return indexes.mapToObj(slots::get).toArray(MorpionSlot[]::new);
	}
	
	private MorpionSlot[][] extractSlots(final Stream<IntStream> sequenceOfIndexes)
	{
		return sequenceOfIndexes.map(this::extractSlots).toArray(MorpionSlot[][]::new);
	}

	@Override
  public MorpionSlot[][] extractorRows()
  {
	  return extractSlots(IntStream.iterate(0, previousRowIndex -> previousRowIndex + size.getSecond()).limit(size.getFirst()).mapToObj(rowIndex -> IntStream.range(rowIndex, rowIndex + size.getSecond())));
  }

  @Override
  public MorpionSlot[][] extractorColumns()
  {
    return extractSlots(IntStream.iterate(0, previousColumnIndex -> previousColumnIndex + 1)
        .limit(size.getSecond())
        .mapToObj(columnIndex -> IntStream.iterate(columnIndex, previousIndex -> previousIndex + size.getSecond())
            .limit(size.getFirst())));
  }

  @Override
  public MorpionSlot[] extractorDiagonal()
  {
    return extractSlots(IntStream.iterate(0, previousIndex -> previousIndex + size.getSecond() + 1).limit(size.getFirst()));
  }

  @Override
  public MorpionSlot[] extractorReverseDiagonal()
  {
    return extractSlots(IntStream.iterate(size.getSecond() - 1, previousIndex -> previousIndex + size.getSecond() - 1).limit(size.getFirst()));
  }

}
