package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.view.View;

class CellSelectListener implements View.OnClickListener {
    private PlayActivity playActivity;

    public CellSelectListener(PlayActivity playActivity) {
        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        playActivity.selectCell(v);
    }
}
