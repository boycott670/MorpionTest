package sqli.morpion.validators;

import sqli.morpion.entities.Box;
import sqli.morpion.entities.Line;
import sqli.morpion.entities.Morpion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MorpionValidator {

    public static boolean isBoxEmpty(Box box) {
        return box.getCharacter() == Morpion.EMPTY_SLOT;
    }

    public static Line isThereWinner(List<Line> board) {
        Line line =lineWinner(board);
        if (Objects.nonNull(line)){
            return line;
        }
        return null;
    }

    private static Line lineWinner(List<Line> board) {
        ArrayList<Line>  allLines = new ArrayList<>(board);
        allLines.addAll(getVerticalLines(board));
       allLines.addAll(getDiagonals(board));
        for (Line line : allLines) {
            if (isLineWinner(line)) return line;
        }
        return null;
    }


    private static List<Line> getVerticalLines(List<Line> board) {
        List<Line> verticalLines = new ArrayList<>();
        for (int column = 0; column < board.get(0).getBoxes().size(); column++) {
            int finalColumn = column;
            verticalLines.add(new Line(board.stream().map(line -> line.getBoxes().get(finalColumn)).collect(Collectors.toList())));
        }
        return verticalLines;
    }

    private static List<Line> getDiagonals(List<Line> board) {
        List<Line> diagonals = new ArrayList<>();
        Line line = new Line();
        for (int row = 0; row < board.size(); row++) {
            line.getBoxes().add(board.get(row).getBoxes().get(row));

        }
        diagonals.add(line);
        line = new Line();
        for (int row = 0; row < board.size(); row++) {
            line.getBoxes().add(board.get(row).getBoxes().get(board.get(row).getBoxes().size()-row-1));
        }
        diagonals.add(line);

        return diagonals;
    }

    private static boolean isLineWinner(Line line) {
        return line.getBoxes().stream().allMatch((box) -> box.getCharacter().equals(Morpion.X_SLOT)) || line.getBoxes().stream().allMatch((box) -> box.getCharacter().equals(Morpion.O_SLOT));
    }

    public static boolean isEquality(List<Line> board) {
            for (Box box: getFlatBoxes(board)
                    ) {
                if (box.getCharacter().equals(Morpion.EMPTY_SLOT)) return false;
            }
        return true;
    }

    private  static List<Box> getFlatBoxes(List<Line> board) {
        return board.stream()
                .flatMap(line -> line.getBoxes().stream())
                .collect(Collectors.toList());
    }
}
