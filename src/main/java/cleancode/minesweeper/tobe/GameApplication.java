package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.VeryBeginnerGameLevel;

public class GameApplication {
    static void main(String[] args) {
        GameLevel gameLevel = new VeryBeginnerGameLevel();

        Minesweeper minesweeper = Minesweeper.fromGameLevel(gameLevel);
        minesweeper.run();
    }
}
