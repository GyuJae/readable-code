package cleancode.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] landMineCounts = new Integer[8][10];
    private static boolean[][] landMines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();

        Scanner scanner = new Scanner(System.in);
        initializeGame();
        while (true) {
            showBoard();
            if (doesUserWinTheGame()) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (doesUserLoseTheGame()) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            String cellInput = getCellInputFromUser(scanner);
            String userActionInput = getUserActionInputFromUser(scanner);
            char cellInputCol = getSelectedColIndex(cellInput);
            char cellInputRow = getSelectedRowIndex(cellInput);
            int selectedColIndex = convertColFrom(cellInputCol);
            int selectedRowIndex = convertRowFrom(cellInputRow);
            if (doesUserChooseToPlantFlag(userActionInput)) {
                board[selectedRowIndex][selectedColIndex] = "⚑";
                checkIfGameIsOver();
            } else if (doesUserChooseToOpenCell(userActionInput)) {
                if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                    board[selectedRowIndex][selectedColIndex] = "☼";
                    changeGameStatusToLose();
                    continue;
                } else {
                    open(selectedRowIndex, selectedColIndex);
                }
                checkIfGameIsOver();
            } else {
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return landMines[selectedRowIndex][selectedColIndex];
    }

    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static char getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return cellInputRow;
    }

    private static char getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return cellInputCol;
    }

    private static String getUserActionInputFromUser(Scanner scanner) {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        String userActionInput = scanner.nextLine();
        return userActionInput;
    }

    private static String getCellInputFromUser(Scanner scanner) {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        String cellInput = scanner.nextLine();
        return cellInput;
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
    }

    private static int convertColFrom(char cellInputCol) {
        int selectedColIndex;
        switch (cellInputCol) {
            case 'a':
                selectedColIndex = 0;
                break;
            case 'b':
                selectedColIndex = 1;
                break;
            case 'c':
                selectedColIndex = 2;
                break;
            case 'd':
                selectedColIndex = 3;
                break;
            case 'e':
                selectedColIndex = 4;
                break;
            case 'f':
                selectedColIndex = 5;
                break;
            case 'g':
                selectedColIndex = 6;
                break;
            case 'h':
                selectedColIndex = 7;
                break;
            case 'i':
                selectedColIndex = 8;
                break;
            case 'j':
                selectedColIndex = 9;
                break;
            default:
                selectedColIndex = -1;
                break;
        }
        return selectedColIndex;
    }

    private static void checkIfGameIsOver() {
        if (isAllCellOpened()) {
            gameStatus = 1;
        }
    }

    private static boolean isAllCellOpened() {
        boolean isAllOpened = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                if (board[row][col].equals("□")) {
                    isAllOpened = false;
                }
            }
        }
        return isAllOpened;
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < 8; row++) {
            System.out.printf("%d  ", row + 1);
            for (int j = 0; j < 10; j++) {
                System.out.print(board[row][j] + " ");
            }
            System.out.println();
        }
    }

    private static void initializeGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = "□";
            }
        }
        for (int i = 0; i < 10; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            landMines[row][col] = true;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (!isLandMineCell(row, col)) {
                    if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
                        count++;
                    }
                    if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
                        count++;
                    }
                    if (row - 1 >= 0 && col + 1 < 10 && isLandMineCell(row - 1, col + 1)) {
                        count++;
                    }
                    if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
                        count++;
                    }
                    if (col + 1 < 10 && isLandMineCell(row, col + 1)) {
                        count++;
                    }
                    if (row + 1 < 8 && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
                        count++;
                    }
                    if (row + 1 < 8 && isLandMineCell(row + 1, col)) {
                        count++;
                    }
                    if (row + 1 < 8 && col + 1 < 10 && isLandMineCell(row + 1, col + 1)) {
                        count++;
                    }
                    landMineCounts[row][col] = count;
                    continue;
                }
                landMineCounts[row][col] = 0;
            }
        }
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        if (!board[row][col].equals("□")) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        if (landMineCounts[row][col] != 0) {
            board[row][col] = String.valueOf(landMineCounts[row][col]);
            return;
        } else {
            board[row][col] = "■";
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
