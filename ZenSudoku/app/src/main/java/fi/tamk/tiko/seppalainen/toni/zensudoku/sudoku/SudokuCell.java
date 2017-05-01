package fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku;

public class SudokuCell {

    private Integer num;
    private int x;
    private int y;

    public SudokuCell(Integer num) {
        this.num = num;
    }

    public SudokuCell(SudokuCell sudokuCell) {
        setNum(sudokuCell.getNum());
        setX(sudokuCell.getX());
        setY(sudokuCell.getY());
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
