package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.view.View;

class NumberSelectListener implements View.OnClickListener {

    private MainActivity mainActivity;

    public NumberSelectListener(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        mainActivity.selectNumber(v);
    }
}
