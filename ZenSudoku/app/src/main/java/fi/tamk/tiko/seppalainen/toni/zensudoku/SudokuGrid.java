package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import fi.tamk.tiko.seppalainen.toni.zensudoku.sudoku.Sudoku;

public class SudokuGrid {

    private TableLayout sudokuContainer;
    private Activity parent;
    private CellSelectListener cellSelectListener;
    private ArrayList<SudokuCellGroup> rows;
    private ArrayList<SudokuCellGroup> columns;
    private ArrayList<SudokuCellGroup> squares;
    private SudokuGridCell selectedCell;
    private NumberGroupManager numberGroupManager;
    private Sudoku sudokuData;

    public SudokuGrid(Activity parent, CellSelectListener cellSelectListener) {
        this.parent = parent;
        this.cellSelectListener = cellSelectListener;
        this.numberGroupManager = new NumberGroupManager();
    }

    public void setSudoku(Sudoku sudokuData) {
        this.sudokuData = sudokuData;

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, parent.getResources().getDisplayMetrics());

        sudokuContainer = (TableLayout) parent.findViewById(R.id.sudokuContainer);

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

    private int findSquareGroupFor(int x, int y) {
        int column = x / 3;
        int row = y / 3;
        return column + (row*3);
    }

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

    public void selectCell(int x, int y) {
        SudokuGridCell cell = rows.get(y).getCells().get(x);
        selectCell(cell);
    }

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

    public void placeNumberToSelected(int number) {
        if (selectedCell != null) {
            int oldValue = selectedCell.getValue();
            if (oldValue != number) {
                numberGroupManager.remove(selectedCell);
                selectedCell.setValue(number);
                sudokuData.place(number, selectedCell.getCellX(), selectedCell.getCellY());
                numberGroupManager.add(selectedCell);
                numberGroupManager.highlightNumbers(number);
            }
        }
    }

    public void refresh() {
        for (int y = 0; y < rows.size(); y++) {
            List<SudokuGridCell> row = rows.get(y).getCells();
            int rowSize = row.size();
            for (int x = 0; x < rowSize; x++) {
                int num = sudokuData.get(x, y);
                row.get(x).setValue(num);
            }
        }
    }
}
