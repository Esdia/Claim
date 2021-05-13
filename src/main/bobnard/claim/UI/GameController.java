package bobnard.claim.UI;

import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;
import bobnard.claim.model.Player;

import javax.swing.*;

public class GameController {
    private final CFrame gameUI ;
    private final Game game;

    public GameController(Game game, CFrame frame) {
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
