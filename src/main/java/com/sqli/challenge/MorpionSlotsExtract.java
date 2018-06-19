package com.sqli.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

final class MorpionSlotsExtract
{
  private final MorpionSlot[] slots;
  
  private final int sizeOfVectorToValidate;
  
  MorpionSlotsExtract(final MorpionSlot[] slots, final int sizeOfVectorToValidate)
  {
    this.slots = slots;
    
    this.sizeOfVectorToValidate = sizeOfVectorToValidate;
  }
  
  MorpionSlotsVectors rows()
  {
    final MorpionSlotsVectors vectors = new MorpionSlotsVectors();
    
    for (int rowIndex = 0; rowIndex < sizeOfVectorToValidate; rowIndex++)
    {
      vectors.addVector(Arrays.copyOfRange(slots, sizeOfVectorToValidate * rowIndex, sizeOfVectorToValidate * rowIndex + sizeOfVectorToValidate));
    }
    
    return vectors;
  }
  
  MorpionSlotsVectors columns()
  {
    final MorpionSlotsVectors vectors = new MorpionSlotsVectors();
    
    for (int columnIndex = 0; columnIndex < sizeOfVectorToValidate; columnIndex++)
    {
      final Collection<MorpionSlot> vectorSlots = new ArrayList<>();
      
      for (int slotIndex = columnIndex ; slotIndex < slots.length ; slotIndex += sizeOfVectorToValidate)
      {
        vectorSlots.add(slots[slotIndex]);
      }
      
      vectors.addVector(vectorSlots.toArray(new MorpionSlot[0]));
    }
    
    return vectors;
  }
  
  MorpionSlotsVectors diagonal()
  {
    final MorpionSlotsVectors vectors = new MorpionSlotsVectors();
    
    final Collection<MorpionSlot> vectorSlots = new ArrayList<>();
    
    for (int slotIndex = 0 ; slotIndex < slots.length ; slotIndex += sizeOfVectorToValidate + 1)
    {
      vectorSlots.add(slots[slotIndex]);
    }
    
    vectors.addVector(vectorSlots.toArray(new MorpionSlot[0]));
    
    return vectors;
  }
  
  MorpionSlotsVectors reverseDiagonal()
  {
    final MorpionSlotsVectors vectors = new MorpionSlotsVectors();
    
    final Collection<MorpionSlot> vectorSlots = new ArrayList<>();
    
    for (int slotIndex = sizeOfVectorToValidate - 1 ; slotIndex < slots.length ; slotIndex += sizeOfVectorToValidate - 1)
    {
      vectorSlots.add(slots[slotIndex]);
    }
    
    vectors.addVector(vectorSlots.toArray(new MorpionSlot[0]));
    
    return vectors;
  }
}
