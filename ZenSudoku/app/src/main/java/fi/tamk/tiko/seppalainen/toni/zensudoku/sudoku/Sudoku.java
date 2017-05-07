package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

import android.util.JsonWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Sudoku {

    private final Random rng = new Random();
    private long seed;
    private List<NumberGroup> rows;
    private List<NumberGroup> columns;
    private List<NumberGroup> squares;
    private List<SudokuCell> data;
    private List<SudokuCell> initialData;
    private List<SudokuCell> resultData;
    private ArrayList<Integer> index;
    private int difficulty;

    SudokuSolver solver = new SudokuSolver();
    private int filledCells = 0;

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

    public Sudoku(int difficulty) {
        seed = rng.nextLong();
        rng.setSeed(seed);
        init(difficulty);
    }

    public Sudoku(int difficulty, long seed) {
        this.seed = seed;
        rng.setSeed(seed);
        init(difficulty);
    }

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

    private List<SudokuCell> copy(List<SudokuCell> data) {
        ArrayList<SudokuCell> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(new SudokuCell(data.get(i)));
        }
        return list;
    }


    // Removes enough numbers to meet the difficulty requirement.
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

    private int randomFrom(List<Integer> valids) {
//        int r = (int) (Math.random() * valids.size());
        int r = rng.nextInt(valids.size());
        return valids.remove(r);
    }

    private List<Integer> validNumbers(int x, int y) {
        List<Integer> valids = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (canPlace(i, x, y)) {
                valids.add(i);
            }
        }
        return valids;
    }

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

    private void initData(List<Integer> data) throws IllegalArgumentException {
        this.data = new ArrayList<>(81);
        if (data.size() == 81) {
            for (int i = 0; i < 81; i++) {
                int y = i / 9;
                int x = i - (9 * y);
                int num = data.get(i);
                if (num < 0 || num > 9) {
                    throw new IllegalArgumentException("Data must contain only numbers 0 to 9, got " + num);
                }
                SudokuCell cell = new SudokuCell(num);
                cell.setX(x);
                cell.setY(y);
                this.data.add(cell);
                rows.get(y).add(num);
                columns.get(x).add(num);
                int thing = (y / 3) * 3 + x / 3;
                squares.get(thing).add(num);
            }
        } else {
            throw new IllegalArgumentException("Illegal sudoku size " + data.size() + ", must be 81");
        }
    }

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

    private int getSquareIndex(int x, int y) {
        return (y / 3) * 3 + x / 3;
    }

    public void place(int num, int x, int y) {
        int old = data.get(y * 9 + x).getNum();
        data.get(y * 9 + x).setNum(num);
        rows.get(y).add(num);
        columns.get(x).add(num);
        squares.get(getSquareIndex(x, y)).add(num);

        if (old == 0 && num != 0){
            filledCells++;
        }
    }

    public int get(int x, int y) {
        return data.get(y * 9 + x).getNum();
    }

    public int getInitial(int x, int y) {
        return initialData.get(y * 9 + x).getNum();
    }

    public boolean isEmpty(int x, int y) {
        return get(x, y) == 0;
    }

    public void remove(int x, int y) {
        int num = data.get(y * 9 + x).getNum();
        rows.get(y).remove(num);
        columns.get(x).remove(num);
        squares.get(getSquareIndex(x, y)).remove(num);
        place(0, x, y);
        filledCells--;
    }

    private void remove(SudokuCell cell) {
        remove(cell.getX(), cell.getY());
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

    public long getSeed() {
        return seed;
    }

    public List<SudokuCell> getInitialData() {
        return initialData;
    }

    public List<SudokuCell> getResultData() {
        return resultData;
    }

    public List<SudokuCell> getData() {
        return data;
    }

    public int getDifficulty() {
        return difficulty;
    }

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
