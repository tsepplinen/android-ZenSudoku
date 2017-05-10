package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents the data model behind a sudoku game grid.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class Sudoku {

    /**
     * Handle to a seedable random number generator.
     */
    private final Random rng = new Random();

    /**
     * Seed used to generate this sudoku.
     */
    private long seed;

    /**
     * Row number groups.
     */
    private List<NumberGroup> rows;

    /**
     * Column number groups.
     */
    private List<NumberGroup> columns;

    /**
     * 3x3 square number groups.
     */
    private List<NumberGroup> squares;

    /**
     * All numbers contained in the grid.
     */
    private List<SudokuCell> data;

    /**
     * Initially generated numbers.
     */
    private List<SudokuCell> initialData;

    /**
     * Correct solutions numbers.
     */
    private List<SudokuCell> resultData;

    /**
     * List of shuffled indices for random generation of sudoku.
     */
    private ArrayList<Integer> index;

    /**
     * Difficulty of this sudoku data.
     */
    private int difficulty;

    /**
     * Sudoku solver used when generating a new sudoku.
     */
    SudokuSolver solver = new SudokuSolver();

    /**
     * Amount of cells filled in the sudoku grid.
     */
    private int filledCells = 0;

    /**
     * Creates a new sudoku with the given data without random generation.
     *
     * @param seed        Seed of this sudoku.
     * @param data        All numbers in the grid.
     * @param initialData Initial numbers for the grid.
     * @param resultData  Correct solutions numbers.
     * @param difficulty  Difficulty of this sudoku.
     */
    public Sudoku(long seed, List<SudokuCell> data, List<SudokuCell> initialData, List<SudokuCell> resultData, int difficulty) {
        this.seed = seed;
        this.initialData = initialData;
        this.resultData = resultData;
        this.difficulty = difficulty;
        createNumberGroups();

        // Place all numbers from data
        createEmptyData();
        for (SudokuCell cell : data) {
            place(cell.getNum(), cell.getX(), cell.getY());
        }
    }

    /**
     * Creates a new randomly generated sudoku with the given difficulty.
     *
     * @param difficulty Difficulty to generate with.
     */
    public Sudoku(int difficulty) {
        seed = rng.nextLong();
        rng.setSeed(seed);
        init(difficulty);
    }

    /**
     * Creates a new randomly generated sudoku with the given difficulty and seed.
     *
     * @param difficulty Difficulty to generate with.
     * @param seed       Seed to use for generation.
     */
    public Sudoku(int difficulty, long seed) {
        this.seed = seed;
        rng.setSeed(seed);
        init(difficulty);
    }

    /**
     * Initializes and generates a sudoku.
     *
     * @param difficulty Difficulty used in the generation.
     */
    private void init(int difficulty) {
        if (difficulty < 22) {
            this.difficulty = 22;
        } else {
            this.difficulty = difficulty;
        }
        createNumberGroups();
        index = new ArrayList<>();
        generate();
    }

    /**
     * Generates a random sudoku.
     */
    private void generate() {
        createEmptyData();
        placeRandom(0, 0);
        // Save completed result
        resultData = new ArrayList<>();
        resultData = copy(data);
        // shuffle array of indices
        for (int i = 0; i < 81; i++) {
            index.add(i);
        }
        Collections.shuffle(index, rng);
        filledCells = 81;
        boolean success = unsolve(0);
        System.out.println("success = " + success);
        // Save initial values
        initialData = copy(data);
    }

    /**
     * Copies a list of sudoku cells into a new list.
     *
     * @param data Input list of SudokuCells.
     * @return Copy of the input list.
     */
    private List<SudokuCell> copy(List<SudokuCell> data) {
        ArrayList<SudokuCell> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(new SudokuCell(data.get(i)));
        }
        return list;
    }


    /**
     * Recursively removes enough numbers to meet the difficulty requirement.
     *
     * @param i Current recursive depth of the algorithm.
     * @return True if done, false otherwise.
     */
    private boolean unsolve(int i) {
        if (filledCells <= difficulty) {
            return true;
        }

        if (i > 80) {
            return false;
        }
        SudokuCell cell = data.get(index.get(i));
        int num = cell.getNum();
        // Clear cell
        remove(cell.getX(), cell.getY());
        boolean canRemove = (i < 2) || solver.isUnique(this);

        if (canRemove) {
            boolean next = unsolve(i + 1);
            if (next) {
                return true;
            }
        }
        place(num, cell.getX(), cell.getY());
        return unsolve(i + 1);
    }

    /**
     * Initializes the data with empty SudokuCells.
     */
    private void createEmptyData() {
        data = new ArrayList<>(81);
        for (int i = 0; i < 81; i++) {
            int y = i / 9;
            int x = i - (9 * y);
            SudokuCell cell = new SudokuCell(0);
            cell.setX(x);
            cell.setY(y);
            data.add(cell);
        }
    }

    /**
     * Selects and removes a random number from a list.
     *
     * @param valids Valid choise to choose from.
     * @return The chosen number from the list.
     */
    private int randomFrom(List<Integer> valids) {
        int r = rng.nextInt(valids.size());
        return valids.remove(r);
    }

    /**
     * Generates a list of valid numbers for a given cell in the grid.
     *
     * @param x X coordinate of the cell.
     * @param y Y coordinate of the cell.
     * @return List of valid numbers for the given cell.
     */
    private List<Integer> validNumbers(int x, int y) {
        List<Integer> valids = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (canPlace(i, x, y)) {
                valids.add(i);
            }
        }
        return valids;
    }

    /**
     * Recursively places random valid numbers into the grid until its filled.
     *
     * @param x X coordinate of currently processed cell.
     * @param y Y coordinate of currently processed cell.
     * @return True if done, false otherwise.
     */
    private boolean placeRandom(int x, int y) {
        // If past last row
        if (y > 8) {
            return true;
        }

        List<Integer> valids = validNumbers(x, y);
        if (valids.isEmpty()) {
            return false;
        } else {
            boolean ok = false;
            while (!ok && !valids.isEmpty()) {
                int num = randomFrom(valids);
                place(num, x, y);
                int nx = x + 1;
                int ny = y;
                if (nx > 8) {
                    ny++;
                    nx = 0;
                }
                ok = placeRandom(nx, ny);
                if (!ok) {
                    remove(x, y);
                }
            }
            return ok;
        }

    }

    /**
     * Creates empty number groups.
     */
    private void createNumberGroups() {
        rows = new ArrayList<>(9);
        columns = new ArrayList<>(9);
        squares = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            rows.add(new NumberGroup());
            columns.add(new NumberGroup());
            squares.add(new NumberGroup());
        }
    }

    /**
     * Checks if a number can be placed in a cell.
     *
     * @param num Number to be placed.
     * @param x   X coordinate of the cell to place into.
     * @param y   Y coordinate of the cell to place into.
     * @return True if placing number is allowed, false otherwise.
     */
    public boolean canPlace(int num, int x, int y) {
        boolean value = false;
        try {
            value = !(rows.get(y).has(num) ||
                    columns.get(x).has(num) ||
                    squares.get(getSquareIndex(x, y)).has(num));
        } catch (Exception e) {
            e.printStackTrace();
            value = false;
        }
        return value;
    }

    /**
     * Maps grid x and y coordinates into a one dimensional index of the 3x3 square.
     *
     * @param x X coordinate of a cell.
     * @param y Y coordinate of a cell.
     * @return One dimensional index calculated from x and y coordiates
     */
    private int getSquareIndex(int x, int y) {
        return (y / 3) * 3 + x / 3;
    }

    /**
     * Places a given number into a cell in the grid.
     *
     * @param num Number to place.
     * @param x   Cells X coordinate.
     * @param y   Cells Y coordinate.
     */
    public void place(int num, int x, int y) {
        int old = data.get(y * 9 + x).getNum();
        data.get(y * 9 + x).setNum(num);
        rows.get(y).add(num);
        columns.get(x).add(num);
        squares.get(getSquareIndex(x, y)).add(num);

        if (old == 0 && num != 0) {
            filledCells++;
        }
    }

    /**
     * Gets the number contained within a cell.
     *
     * @param x Cells X coordinate.
     * @param y Cells Y coordinate.
     * @return Number in the given cell.
     */
    public int get(int x, int y) {
        return data.get(y * 9 + x).getNum();
    }

    /**
     * Retrieves an initial value for a cell with the given coordinates
     *
     * @param x Cells X coordinate.
     * @param y Cells Y coordinate.
     * @return Value initially placed into the cell.
     */
    public int getInitial(int x, int y) {
        return initialData.get(y * 9 + x).getNum();
    }

    /**
     * Checks if the cell is void of any number.
     *
     * @param x Cells X coordinate.
     * @param y Cells Y coordinate.
     * @return True if the cell is empty, false otherwise.
     */
    public boolean isEmpty(int x, int y) {
        return get(x, y) == 0;
    }

    /**
     * Removes a number value from the given cell.
     *
     * @param x Cells X coordinate.
     * @param y Cells Y coordinate.
     */
    public void remove(int x, int y) {
        int num = data.get(y * 9 + x).getNum();
        rows.get(y).remove(num);
        columns.get(x).remove(num);
        squares.get(getSquareIndex(x, y)).remove(num);
        place(0, x, y);
        filledCells--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                sb.append(get(x, y));
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves the seed this sudoku was generated with.
     *
     * @return The seed.
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Retrieves the data initially generated for the sudoku.
     *
     * @return List of initial values.
     */
    public List<SudokuCell> getInitialData() {
        return initialData;
    }

    /**
     * Retrieves the correct solution of the sudoku.
     *
     * @return List of correct numbers.
     */
    public List<SudokuCell> getResultData() {
        return resultData;
    }

    /**
     * Retrieves the data entered into the grid.
     *
     * @return List of all the numbers in the grid.
     */
    public List<SudokuCell> getData() {
        return data;
    }

    /**
     * Retrieves the sudokus difficulty.
     *
     * @return Difficulty of this sudoku.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Checks if the data is currently correct accordnig to the solution.
     *
     * @return True if the data contains no erros, false otherwise.
     */
    public boolean isCorrect() {
        for (int i = 0; i < data.size(); i++) {
            int placed = data.get(i).getNum();
            int correct = resultData.get(i).getNum();
            if (placed != 0 && placed != correct) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the sudoku puzzle is solved.
     *
     * @return True if the puzzle is solved, false otherwise.
     */
    public boolean isSolved() {
        System.out.println("Sudoku.isSolved");
        System.out.println("filledCells = " + filledCells);
        if (filledCells == 81) {
            for (int i = 0; i < data.size(); i++) {
                int placed = data.get(i).getNum();
                int correct = resultData.get(i).getNum();
                if (placed != correct) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Places a random hint into the grid.
     *
     * @return Data about the added hint.
     */
    public HintData useHint() {
        if (filledCells < 80) {
            int i = 0;
            for (i = rng.nextInt(81); i < 81; i++) {
                // If cell is empty
                SudokuCell cell = data.get(i);
                if (cell.getNum() == 0) {
                    int correct = resultData.get(i).getNum();
                    int x = cell.getX();
                    int y = cell.getY();
                    place(correct, cell.getX(), cell.getY());
                    return new HintData(x, y, correct);
                }
            }
            for (i = 0; i < 81; i++) {
                // If cell is empty
                SudokuCell cell = data.get(i);
                if (cell.getNum() == 0) {
                    int correct = resultData.get(i).getNum();
                    int x = cell.getX();
                    int y = cell.getY();
                    place(correct, cell.getX(), cell.getY());
                    return new HintData(x, y, correct);
                }
            }
        }
        return null;
    }
}
