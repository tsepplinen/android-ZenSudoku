package fi.tamk.tiko.seppalainen.toni.zensudoku;

import android.app.Activity;

/**
 * Provides access to single instance of {@link SaveManager}.
 */
public class SaveManagerProvider {

    private static SaveManager saveManager;


    public static void init() {
        saveManager = new SaveManager();
    }

    public static SaveManager getSaveManager() {
        return saveManager;
    }
}
