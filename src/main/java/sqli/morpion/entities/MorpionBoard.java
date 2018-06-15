package sqli.morpion.entities;

import sqli.morpion.parsers.MorpionParser;

import java.util.ArrayList;
import java.util.List;

public class MorpionBoard {
    public static  List<Line> buildBoard(String board){
        MorpionParser morpionParser = new MorpionParser();
        Pair<Integer,Integer> size = morpionParser.morpionParser(board,"x");
        Line line = new Line();
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<Box> boxes = new ArrayList<>();
        for (int i=0;i<size.getFirst();i++){
            for (int j=0;j<size.getSecond();j++){
                boxes.add(new Box(Morpion.EMPTY_SLOT));
            }
            line.setBoxes(boxes);
            lines.add(line);
            line= new Line();
            boxes = new ArrayList<>();
        }
        return lines;
    }
}
