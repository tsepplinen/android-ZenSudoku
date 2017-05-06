package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.io.Serializable;

public class ConfirmNewGameDialogFragment extends DialogFragment {


    /**
     * Creates an instance of dialog fragment.
     *
     * @param difficulty Chosen game difficulty.
     * @return created instance.
     */
    public static ConfirmNewGameDialogFragment newInstance(Difficulty difficulty) {
        ConfirmNewGameDialogFragment fragment = new ConfirmNewGameDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("difficulty", difficulty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_confirm_new_game)
                .setPositiveButton(R.string.dialog_confirm_new_game_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Difficulty diff = null;
                        final Bundle args = getArguments();
                        if (args != null) {
                            diff = (Difficulty) args.getSerializable("difficulty");
                        }

                        Intent intent = new Intent(getContext(), PlayActivity.class);
                        intent.putExtra("difficulty", diff);
                        intent.putExtra("continue", false);
                        startActivity(intent);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
}


