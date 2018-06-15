package sqli.morpion.entities;

public class Box {
    Character character;

    public Box(Character character) {
        this.character = character;
    }


    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
