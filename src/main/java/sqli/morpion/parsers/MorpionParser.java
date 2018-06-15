package sqli.morpion.parsers;


import sqli.morpion.entities.Pair;

public class MorpionParser {
    public Pair<Integer, Integer> morpionParser(String morpionDimension, String separator) {

        Integer line    = Integer.parseInt(morpionDimension.split(separator)[0]);

        Integer column  = Integer.parseInt(morpionDimension.split(separator)[1]);

        return new Pair<>(line , column);

    }



    public Pair<String, String> playerParser(String player, String separator) {

        String  playerName    = player.split(separator)[0];

        String  character     = player.split(separator)[1];

        return new Pair<>(playerName , character);

    }
    public Pair<Integer, Integer> positionParser(String postion, String separator) {

        Integer  playerName    =Integer.parseInt(postion.split(separator)[0]);

        Integer  character     = Integer.parseInt(postion.split(separator)[1]);

        return new Pair<>(playerName , character);

    }


}
