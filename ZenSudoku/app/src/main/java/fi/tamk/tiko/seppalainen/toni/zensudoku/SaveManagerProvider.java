package fi.tamk.tiko.seppalainen.toni.zensudoku;

/**
 * Provides access to single instance of {@link SaveManager}.
 *
 * @author Toni Seppäläinen toni.seppalainen@cs.tamk.fi
 * @version 2017.0509
 * @since 1.7
 */
public class SaveManagerProvider {

    /**
     * The instance of {@link SaveManager}
     */
    private static SaveManager saveManager;


    /**
     * Initializes this class with the instance of {@link SaveManager}
     */
    public static void init() {
        saveManager = new SaveManager();
    }

    /**
     * Retrieves the {@link SaveManager} instance.
     *
     * @return The handle to save manager.
     */
    public static SaveManager getSaveManager() {
        return saveManager;
    }
}
