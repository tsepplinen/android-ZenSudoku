package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SudokuGrid {

    private TableLayout sudokuContainer;
    private Activity parent;
    private CellSelectListener cellSelectListener;
    private ArrayList<SudokuCellGroup> rows;
    private ArrayList<SudokuCellGroup> columns;
    private ArrayList<SudokuCellGroup> squares;
    private SudokuCell selectedCell;

    public SudokuGrid(Activity parent, CellSelectListener cellSelectListener) {
        this.parent = parent;
        this.cellSelectListener = cellSelectListener;
    }

    public void setSudoku(List<Integer> sudokuData) {

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, parent.getResources().getDisplayMetrics());

        sudokuContainer = (TableLayout) parent.findViewById(R.id.sudokuContainer);

        createEmptyCellGroups();

        int index = 0;
        for (int y = 0; y < 9; y++) {
            // create new row
            TableRow tableRow = new TableRow(sudokuContainer.getContext());
            sudokuContainer.addView(tableRow);

            for (int x = 0; x < 9; x++) {
                final SudokuCell cell = new SudokuCell(parent, x, y);
                cell.setOnClickListener(cellSelectListener);
                cell.setInitialValue(sudokuData.get(index));
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

    private void addToGroups(SudokuCell cell) {
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

    public void selectCell(SudokuCell cell) {
        if (this.selectedCell != null) {
            this.selectedCell.setBackgroundResource(R.drawable.cell);
        }
        this.selectedCell = cell;
        cell.setBackgroundResource(R.drawable.selected);
    }
}
