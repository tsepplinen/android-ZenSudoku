package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.view.View;

/**
 * Listens to click on number buttons to select a number.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
class NumberSelectListener implements View.OnClickListener {

    private PlayActivity playActivity;

    /**
     * Creates a new {@link NumberSelectListener} initialized with the given activity.
     *
     * @param playActivity The play activity using this listener.
     */
    public NumberSelectListener(PlayActivity playActivity) {
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        playActivity.selectNumber(v);
    }
}
