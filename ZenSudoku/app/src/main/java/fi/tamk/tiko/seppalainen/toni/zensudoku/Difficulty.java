package fi.tamk.tiko.seppalainen.toni.zensudoku;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    public static Difficulty fromInt(int value) {
        switch (value) {
            case 40:
                return MEDIUM;
            case 30:
                return HARD;
            default:
            case 50:
                return EASY;
        }
    }
}
