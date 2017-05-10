package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

/**
 * Represent visual grid of the sudoku game.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SudokuGrid {

    /**
     * The parent activity.
     */
    private Activity parent;

    /**
     * Listens to cell selections.
     */
    private CellSelectListener cellSelectListener;

    /**
     * Row groups of the grids cells.
     */
    private ArrayList<SudokuCellGroup> rows;

    /**
     * Column groups of the grids cells.
     */
    private ArrayList<SudokuCellGroup> columns;

    /**
     * 3x3 Square groups of the grids cells.
     */
    private ArrayList<SudokuCellGroup> squares;

    /**
     * Currently selected cell.
     */
    private SudokuGridCell selectedCell;

    /**
     * Handle to number group manager.
     */
    private NumberGroupManager numberGroupManager;

    /**
     * Sudoku Data to display in the grid.
     */
    private Sudoku sudokuData;

    /**
     * Creates a sudoku grid with given values.
     *
     * @param parent             Parent of the grid.
     * @param cellSelectListener Handle to cell selection listener.
     */
    public SudokuGrid(Activity parent, CellSelectListener cellSelectListener) {
        this.parent = parent;
        this.cellSelectListener = cellSelectListener;
        this.numberGroupManager = new NumberGroupManager();
    }

    /**
     * Sets the sudoku data for this grid.
     *
     * @param sudokuData The sudoku data to set to this grid.
     */
    public void setSudoku(Sudoku sudokuData) {
        this.sudokuData = sudokuData;

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, parent.getResources().getDisplayMetrics());

        TableLayout sudokuContainer = (TableLayout) parent.findViewById(R.id.sudokuContainer);

        createEmptyCellGroups();

        int index = 0;
        for (int y = 0; y < 9; y++) {
            // create new row
            TableRow tableRow = new TableRow(sudokuContainer.getContext());
            sudokuContainer.addView(tableRow);

            for (int x = 0; x < 9; x++) {
                final SudokuGridCell cell = new SudokuGridCell(parent, x, y);
                cell.setOnClickListener(cellSelectListener);
                cell.setInitialValue(sudokuData.getInitial(x, y));
                cell.setValue(sudokuData.get(x,y));
                numberGroupManager.add(cell);
                index++;

                tableRow.addView(cell);

                TableRow.LayoutParams params = (TableRow.LayoutParams) cell.getLayoutParams();
                params.width = buttonSize;
                params.height = buttonSize;
                cell.setLayoutParams(params);

                addToGroups(cell);
            }
        }
    }

    /**
     * Adds a cell to its groups.
     *
     * @param cell The cell to add to groups.
     */
    private void addToGroups(SudokuGridCell cell) {
        int x = cell.getCellX();
        int y = cell.getCellY();

        int squareNum = findSquareGroupFor(x, y);

        SudokuCellGroup colGroup = columns.get(x);
        colGroup.addCell(cell);
        cell.setColumnGroup(colGroup);

        SudokuCellGroup rowGroup = rows.get(y);
        rowGroup.addCell(cell);
        cell.setRowGroup(rowGroup);

        SudokuCellGroup squareGroup = squares.get(squareNum);
        squareGroup.addCell(cell);
        cell.setSquareGroup(squareGroup);
    }

    /**
     * Finds a square group for given cell coordinates.
     *
     * @param x X coordinate of the cell.
     * @param y Y coordinate of the cell.
     * @return One dimensional index to square groups.
     */
    private int findSquareGroupFor(int x, int y) {
        int column = x / 3;
        int row = y / 3;
        return column + (row * 3);
    }

    /**
     * Creates empty cell groups.
     */
    private void createEmptyCellGroups() {
        rows = new ArrayList<>();
        columns = new ArrayList<>();
        squares = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            rows.add(new SudokuCellGroup());
            columns.add(new SudokuCellGroup());
            squares.add(new SudokuCellGroup());
        }
    }

    /**
     * Selects a cell at position x, y.
     *
     * @param x X coordinate of the cell to select.
     * @param y Y coordinate of the cell to select.
     */
    public void selectCell(int x, int y) {
        SudokuGridCell cell = rows.get(y).getCells().get(x);
        selectCell(cell);
    }

    /**
     * Selects a cell by reference.
     *
     * @param cell The cell to set as selected.
     */
    public void selectCell(SudokuGridCell cell) {
        if (selectedCell != null) {
            selectedCell.setSelect(false);

            selectedCell.getRowGroup().setHighlight(false);
            selectedCell.getColumnGroup().setHighlight(false);
        }

        this.selectedCell = cell;
        selectedCell.setSelect(true);

        cell.getRowGroup().setHighlight(true);
        cell.getColumnGroup().setHighlight(true);

        numberGroupManager.highlightNumbers(cell.getValue());
    }

    /**
     * Places a number to the selected cell.
     *
     * @param number The number to place to the cell.
     */
    public void placeNumberToSelected(int number) {
        if (selectedCell != null) {
            int oldValue = selectedCell.getValue();
            if (oldValue != number) {
                numberGroupManager.remove(selectedCell);
                selectedCell.setValue(number);
                if (number != 0) {
                    sudokuData.place(number, selectedCell.getCellX(), selectedCell.getCellY());
                } else {
                    sudokuData.remove(selectedCell.getCellX(), selectedCell.getCellY());
                }
                numberGroupManager.add(selectedCell);
                numberGroupManager.highlightNumbers(number);
            }
        }
    }
}
