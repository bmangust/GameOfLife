package life;
import java.util.List;
import java.util.ArrayList;


class Cell {
    private int x;
    private int y;
    private boolean isAlive;

    public Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    protected char renderCell() {
        return isAlive ? 'O' : ' ';
    }

    protected boolean willItLive (int numberOfNeighbours) {
        return numberOfNeighbours > 1 && numberOfNeighbours < 4;
    }

    protected boolean isAlive() {
        return isAlive;
    }

    protected List<Integer> getCoordinates() {
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }
}
