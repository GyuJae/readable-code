package cleancode.minesweeper.tobe;

public class Cell {
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";
    private boolean isLandMine;
    private int nearbyLandMineCount;
    private boolean isFlagged;
    private boolean isOpened;

    private Cell(boolean isLandMine, int nearbyLandMineCount, boolean isFlagged, boolean isOpened) {
        this.isLandMine = isLandMine;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }


    public static Cell create() {
        return new Cell(false, 0, false, false);
    }

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return this.isFlagged || this.isOpened;
    }

    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public boolean hasLandMineCount() {
        return this.nearbyLandMineCount != 0;
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    public boolean isLandMine() {
        return this.isLandMine;
    }

    public String getSign() {
        if (this.isOpened) {
            if (this.isLandMine) {
                return LAND_MINE_SIGN;
            }
            if (this.hasLandMineCount()) {
                return String.valueOf(this.nearbyLandMineCount);
            }
            return EMPTY_SIGN;
        }

        if (this.isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }
}
