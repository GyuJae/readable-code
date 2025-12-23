package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Random;

public class Minesweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final int LAND_MINE_COUNT = 10;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    private final GameBoard gameBoard = GameBoard.fromSize(BOARD_ROW_SIZE, BOARD_COL_SIZE);
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    public void run() {
        this.consoleOutputHandler.printGameStartComments();

        initializeGame();
        while (true) {
            try {
                this.consoleOutputHandler.showBoard(this.gameBoard);
                if (this.doesUserWinTheGame()) {
                    this.consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (this.doesUserLoseTheGame()) {
                    this.consoleOutputHandler.printGameLosingComment();
                    break;
                }
                String cellInput = this.getCellInputFromUser();
                String userActionInput = this.getUserActionInputFromUser();
                char cellInputCol = getSelectedColIndex(cellInput);
                char cellInputRow = getSelectedRowIndex(cellInput);
                this.actOnCell(cellInputCol, cellInputRow, userActionInput);
            } catch (GameException e) {
                this.consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) {
                this.consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private char getSelectedRowIndex(String cellInput) {
        return cellInput.charAt(1);
    }

    private char getSelectedColIndex(String cellInput) {
        return cellInput.charAt(0);
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex >= BOARD_ROW_SIZE) {
            throw new GameException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a' -> {
                return 0;
            }
            case 'b' -> {
                return 1;
            }
            case 'c' -> {
                return 2;
            }
            case 'd' -> {
                return 3;
            }
            case 'e' -> {
                return 4;
            }
            case 'f' -> {
                return 5;
            }
            case 'g' -> {
                return 6;
            }
            case 'h' -> {
                return 7;
            }
            case 'i' -> {
                return 8;
            }
            case 'j' -> {
                return 9;
            }
            default -> throw new GameException("잘못된 입력입니다.");
        }
    }


    private int countNearbyLandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && this.gameBoard.isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && this.gameBoard.isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && this.gameBoard.isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && this.gameBoard.isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && this.gameBoard.isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && this.gameBoard.isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && this.gameBoard.isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && this.gameBoard.isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private void checkIfGameIsOver() {
        if (this.gameBoard.isAllCellChecked()) {
            gameStatus = 1;
        }
    }

    private void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                this.gameBoard.updateCell(row, col, Cell.create());
            }
        }
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            int col = new Random().nextInt(BOARD_COL_SIZE);
            this.gameBoard.turnOnLandMine(row, col);
        }
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (this.gameBoard.isLandMineCell(row, col)) {
                    continue;
                }
                this.gameBoard.updateNearbyLandMineCount(row, col, countNearbyLandMines(row, col));
            }
        }
    }

    private void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }

        Cell cell = this.gameBoard.findCell(row, col);

        if (cell.isOpened()) {
            return;
        }
        if (this.gameBoard.isLandMineCell(row, col)) {
            return;
        }

        cell.open();

        if (cell.hasLandMineCount()) {
            return;
        }

        this.openSurroundedCells(row - 1, col - 1);
        this.openSurroundedCells(row - 1, col);
        this.openSurroundedCells(row - 1, col + 1);
        this.openSurroundedCells(row, col - 1);
        this.openSurroundedCells(row, col + 1);
        this.openSurroundedCells(row + 1, col - 1);
        this.openSurroundedCells(row + 1, col);
        this.openSurroundedCells(row + 1, col + 1);
    }

    private void actOnCell(char cellInputCol, char cellInputRow, String userActionInput) {
        int selectedColIndex = this.convertColFrom(cellInputCol);
        int selectedRowIndex = this.convertRowFrom(cellInputRow);

        if (this.doesUserChooseToPlantFlag(userActionInput)) {
            this.gameBoard.flag(selectedRowIndex, selectedColIndex);
            this.checkIfGameIsOver();
            return;
        }

        if (this.doesUserChooseToOpenCell(userActionInput)) {
            if (this.gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                this.gameBoard.open(selectedRowIndex, selectedColIndex);
                this.changeGameStatusToLose();
                return;
            }

            this.openSurroundedCells(selectedRowIndex, selectedColIndex);
            this.checkIfGameIsOver();
            return;
        }

        throw new GameException("잘못된 번호를 선택하셨습니다.");
    }

    private String getUserActionInputFromUser() {
        this.consoleOutputHandler.printCommentForUserAction();
        return this.consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        this.consoleOutputHandler.printCommentForSelectingCell();
        return this.consoleInputHandler.getUserInput();
    }


}
