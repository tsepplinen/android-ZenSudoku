package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

class ButtonBox {
    private static final float TEXT_SIZE = 40;
    private final LinearLayout numbersContainer;

    public ButtonBox(Activity context, NumberSelectListener numberSelectListener) {

        numbersContainer = (LinearLayout) context.findViewById(R.id.numbersContainer);

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 68, context.getResources().getDisplayMetrics());

        LinearLayout row1 = new LinearLayout(context);
        row1.setOrientation(LinearLayout.HORIZONTAL);
        row1.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout row2 = new LinearLayout(context);
        row2.setOrientation(LinearLayout.HORIZONTAL);
        row2.setGravity(Gravity.CENTER_HORIZONTAL);


        for (int i = 1; i <= 5; i++) {
            Button button = new Button(context);
            button.setText("" + i);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
            button.setGravity(Gravity.CENTER);
            row1.addView(button);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            button.setLayoutParams(params);

            button.setOnClickListener(numberSelectListener);
        }

        for (int i = 6; i <= 9; i++) {
            Button button = new Button(context);
            button.setText("" + i);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
            button.setGravity(Gravity.CENTER);
            row2.addView(button);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            button.setLayoutParams(params);

            button.setOnClickListener(numberSelectListener);
        }

        Button button = new Button(context);
        button.setText("X");
        button.setGravity(Gravity.CENTER);
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE);
        row2.addView(button);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        params.width = buttonSize;
        params.height = buttonSize;
        button.setLayoutParams(params);

        button.setOnClickListener(numberSelectListener);


        numbersContainer.addView(row1);
        numbersContainer.addView(row2);
    }


}
