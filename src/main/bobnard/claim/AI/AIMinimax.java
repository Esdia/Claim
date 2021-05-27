package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Deck;
import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;

public class AIMinimax extends AI {
    private final Difficulty difficulty;

    private final Hand possibleOpponentCards;

    public AIMinimax(Game game, int id, Difficulty difficulty) {
        super(game, id);
        this.difficulty = difficulty;

        this.possibleOpponentCards = new Hand();
        this.initOpponentCards();
    }

    private void initOpponentCards() {
        possibleOpponentCards.addAll(new Deck());
        possibleOpponentCards.sort();
        possibleOpponentCards.removeAll(this.getCards());
    }

    /* Called when the AI can see a card that is not in its hand */
    @Override
    public void showCard(Card card) {
        this.possibleOpponentCards.remove(card);
    }

    int nextCard() {
        /* Useless switch for now, will become useful when adding more difficulties */
        Node node = switch (this.difficulty) {
            case EASY -> new NodeEasy(game, this.getCards(), possibleOpponentCards, this.getId(), false);
        };

        return node.getNextMove();
    }

    @Override
    public void action() {
        this.play(this.nextCard());
    }
}
