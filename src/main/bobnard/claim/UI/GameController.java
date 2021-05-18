package bobnard.claim.UI;

import bobnard.claim.UI.CFrame;
import bobnard.claim.model.Game;

public class GameController {
    private final bobnard.claim.UI.CFrame gameUI ;
    private final Game game;

    public GameController(Game game, bobnard.claim.UI.CFrame frame) {
        this.gameUI = frame;
        this.game = game;
    }

    void interpretClick(int x, int y) {
        System.out.println("Mouse click on (" + x + ", " + y + ")");
    }

    CFrame getGameUI(){
        return gameUI;
    }
}
