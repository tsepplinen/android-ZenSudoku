package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.view.View;

class NumberSelectListener implements View.OnClickListener {

    private PlayActivity playActivity;

    public NumberSelectListener(PlayActivity playActivity) {

        this.playActivity = playActivity;
    }

    @Override
    public void onClick(View v) {
        playActivity.selectNumber(v);
    }
}
