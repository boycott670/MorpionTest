package sqli.morpion.entities;

import java.util.ArrayList;
import java.util.List;

public class Line {
    List<Box> boxes = new ArrayList<>();

    public Line(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Line() {
        
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
