package akumar3;


import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;


/**
 * Move card from top of deck to top of wastepile
 *
 */
public class DealCardToWastepileMove extends Move {
	
	Deck deck;
	Column wastepile;
	
	public DealCardToWastepileMove(Deck deck, Column wastepile) {
		this.deck = deck;
		this.wastepile = wastepile;
	}

	@Override
	public boolean doMove(Solitaire game) {
		
		if (!valid(game)) {
			return false;
		}
		
		Card card = deck.get();
		wastepile.add(card);
		game.updateNumberCardsLeft(-1);
		return true;

	}

	@Override
	public boolean undo(Solitaire game) {
		Card c = wastepile.get();
		deck.add(c);
		game.updateNumberCardsLeft(+1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty();
	}

}
