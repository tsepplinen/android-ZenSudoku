package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.view.View;

class CellSelectListener implements View.OnClickListener {
    private MainActivity mainActivity;

    public CellSelectListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.selectCell(v);
    }
}
