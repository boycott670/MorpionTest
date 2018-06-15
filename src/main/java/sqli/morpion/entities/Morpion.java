package sqli.morpion.entities;

import sqli.morpion.exceptions.BoxAlreadySelectedException;
import sqli.morpion.parsers.MorpionParser;
import sqli.morpion.validators.MorpionValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Morpion {


    public static final Character EMPTY_SLOT = ' ';
    public static final Character X_SLOT = 'X';
    public static final Character O_SLOT = 'O';
    public static final String LINE_SEPARATOR ="\n" ;
    public static final String EQUALITY_REPORT ="Game Over, equality" ;
    public static final String GAME_IS_OVER_WITH_WINNER_REPORT ="Game Over, %s is a winner" ;
    public static final String GAME_NOT_OVER_REPORT ="%d games for %s, %d games for %s" ;

    private Player firstPlayer;
    private Player secondPlayer;
    private List<Line> board = new ArrayList<>();
    private MorpionParser parser = new MorpionParser();



    public Morpion(String board, String player1, String player2) {
        this.board =MorpionBoard.buildBoard(board);
        Pair<String,String> p1 = parser.playerParser(player1,":");
        Pair<String,String> p2 = parser.playerParser(player2,":");
        firstPlayer = new Player(p1.getFirst(),p1.getSecond().charAt(0));
        secondPlayer = new Player(p2.getFirst(),p2.getSecond().charAt(0));
    }

    public void play(String player, String position) {
        Pair<Integer,Integer> parsedPosition = parser.positionParser(position,"x");
        Player currentPlayer = getPlayerByName(player);
        Box box  =board.get(parsedPosition.getFirst()).getBoxes().get(parsedPosition.getSecond());
        if (MorpionValidator.isBoxEmpty(box)){
            board.get(parsedPosition.getFirst()).getBoxes().get(parsedPosition.getSecond()).setCharacter(currentPlayer.getCharacter());

        }else throw new BoxAlreadySelectedException();

    }

    private Player getPlayerByName(String name) {
        if (firstPlayer.getName().equals(name)) return firstPlayer;
        else return secondPlayer;
    }
    private Player getPlayerByCharacter(Character character) {
        if (firstPlayer.getCharacter().equals(character)) return firstPlayer;
        else return secondPlayer;
    }


    public String report() {
        Line line = MorpionValidator.isThereWinner(board);
        if (Objects.nonNull(line)){
            return String.format(GAME_IS_OVER_WITH_WINNER_REPORT,getPlayerByCharacter(line.getBoxes().get(0).getCharacter()).getName());
        }else if (MorpionValidator.isEquality(board)){
            return EQUALITY_REPORT;
        }else {
            int emptyBoxesCount = emptyBoxesCount();
            return String.format(GAME_NOT_OVER_REPORT, emptyBoxesCount / 2 + emptyBoxesCount % 2, firstPlayer.getName(), emptyBoxesCount / 2, secondPlayer.getName());
        }

    }

    private  int emptyBoxesCount() {
        return (int) this.board.stream()
                .flatMap(line -> line.getBoxes().stream())
                .filter(box -> box.getCharacter().equals(EMPTY_SLOT)).count();
    }


    public String display() {
        StringBuilder stringBuilder =  new StringBuilder();
        for (Line line:board){
            for (int i=0;i<line.getBoxes().size();i++)
                stringBuilder.append(line.getBoxes().get(i).getCharacter()).append("|");
            stringBuilder = new StringBuilder(stringBuilder.toString().substring(0,stringBuilder.toString().lastIndexOf("|")));
            stringBuilder.append(LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }
}
