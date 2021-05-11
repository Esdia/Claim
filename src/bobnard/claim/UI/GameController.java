package bobnard.claim.UI;

import bobnard.claim.model.Game;

public class GameController {
    Game game;

    GameController(Game game) {
        this.game = game;
    }

    void interpretClick(int x, int y) {
        System.out.println("Mouse click on (" + x + ", " + y + ")");
    }
}
