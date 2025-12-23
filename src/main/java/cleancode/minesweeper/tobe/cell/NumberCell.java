package cleancode.minesweeper.tobe.cell;

public class NumberCell extends Cell {

    private final int nearbyLandMineCount;

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public String getSign() {
        if (this.isOpened()) return String.valueOf(this.nearbyLandMineCount);
        if (this.isFlagged) return FLAG_SIGN;
        return UNCHECKED_SIGN;
    }
}
