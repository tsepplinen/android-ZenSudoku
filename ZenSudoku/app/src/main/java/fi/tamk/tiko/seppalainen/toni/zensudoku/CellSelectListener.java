package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.view.View;

/**
 * Listens to sudoku cell selection events.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
class CellSelectListener implements View.OnClickListener {
    private PlayActivity playActivity;

    /**
     * Creates a new listener for the play activity.
     *
     * @param playActivity Context for the play activity.
     */
    public CellSelectListener(PlayActivity playActivity) {
        this.playActivity = playActivity;
    }

    /**
     * Handles the click event on a cell by selecting that cell.
     *
     * @param v Clicked View component.
     */
    @Override
    public void onClick(View v) {
        playActivity.selectCell(v);
    }
}
