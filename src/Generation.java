package life;
import life.Cell;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

class Generation {
    private int size;
    private static int generationNumber = 1;
    private long seed;
    private Cell[][] currentGeneration;

    public Generation(int size, long seed) {
        this(size);
        this.seed = seed;
        populate();
    }

    public Generation(int size) {
        Random random = new Random();
        long seed = random.nextLong();
        this.size = size;
        this.seed = seed;
        this.currentGeneration = new Cell[size][size];
        populate();
    }

    private Generation(Cell[][] currentGeneration, int size, long seed) {
        this.size = size;
        this.seed = seed;
        this.currentGeneration = currentGeneration;
    }

    private void populate() {
        Random random = new Random(seed);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                boolean isAlive = random.nextBoolean();
                currentGeneration[x][y] = new Cell(x, y, isAlive);
            }
        }
    }

    private Cell[][] getNewGenerationArray() {
        Cell[][] newG = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Cell current = currentGeneration[x][y];
                if (current.isAlive()) {
                    newG[x][y] = new Cell(x, y, current.willItLive(this.countAliveNeighbours(current)));
                } else {
                    boolean reborn = this.countAliveNeighbours(current) == 3;
                    newG[x][y] = new Cell(x, y, reborn);
                }
            }
        }
        return newG;
    }

    public Generation getNewGeneration() {
        Cell[][] newG = getNewGenerationArray();
        generationNumber += 1;
        return new Generation(newG, this.size, this.seed);
    }

    private int countAliveNeighbours(Cell cell) {
        List<Cell> neighbours = getNeighbours(cell);
        int cnt = 0;
        for (Cell c : neighbours) {
            cnt += c.isAlive() ? 1 : 0;
        }
        return cnt;
    }

    private List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        List<Integer> coord = cell.getCoordinates();

        int x = coord.get(0);
        int y = coord.get(1);
        int top = (y + this.size - 1) % this.size;
        int bottom = (y + this.size + 1) % this.size;
        int left = (x + this.size - 1) % this.size;
        int right = (x + this.size + 1) % this.size;

        neighbours.add(this.currentGeneration[left][top]);
        neighbours.add(this.currentGeneration[x][top]);
        neighbours.add(this.currentGeneration[right][top]);
        neighbours.add(this.currentGeneration[left][y]);
        neighbours.add(this.currentGeneration[right][y]);
        neighbours.add(this.currentGeneration[left][bottom]);
        neighbours.add(this.currentGeneration[x][bottom]);
        neighbours.add(this.currentGeneration[right][bottom]);

        return neighbours;
    }

    public int countAlive() {
        int cnt = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cnt += currentGeneration[y][x].isAlive() ? 1 : 0;
            }
        }
        return cnt;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public Cell[][] getArrayOfCells() {
        return currentGeneration;
    }
}

