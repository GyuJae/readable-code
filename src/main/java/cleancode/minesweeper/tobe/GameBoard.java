package cleancode.minesweeper.tobe;

import java.util.Arrays;

public class GameBoard {
    private final Cell[][] board;


    private GameBoard(Cell[][] board) {
        this.board = board;
    }

    static GameBoard fromSize(int rowSize, int colSize) {
        return new GameBoard(new Cell[rowSize][colSize]);
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = this.findCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = this.findCell(rowIndex, colIndex);
        cell.open();
    }

    public Cell findCell(int rowIndex, int colIndex) {
        return this.board[rowIndex][colIndex];
    }

    public void updateCell(int rowIndex, int colIndex, Cell cell) {
        this.board[rowIndex][colIndex] = cell;
    }

    public void turnOnLandMine(int rowIndex, int colIndex) {
        Cell cell = this.findCell(rowIndex, colIndex);
        cell.turnOnLandMine();
    }

    public void updateNearbyLandMineCount(int rowIndex, int colIndex, int count) {
        Cell cell = this.findCell(rowIndex, colIndex);
        cell.updateNearbyLandMineCount(count);
    }

    public int getRowSize() {
        return this.board.length;
    }

    public int getColSize() {
        return this.board[0].length;
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = this.findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(this.board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    public boolean isLandMineCell(int rowIndex, int colIndex) {
        Cell cell = this.findCell(rowIndex, colIndex);
        return cell.isLandMine();
    }
}
