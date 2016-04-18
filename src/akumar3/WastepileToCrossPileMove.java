package akumar3;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;

public class WastepileToCrossPileMove extends Move {

	Column wastepile;
	Card cardBeingDragged;
	Column crosspile;
	
	public WastepileToCrossPileMove(Column from, Card cardBeingDragged, Column dest) {
		this.cardBeingDragged = cardBeingDragged;
		this.wastepile = from;
		this.crosspile = dest;
	}

	@Override
	public boolean doMove(Solitaire game) {
		
		if (!valid(game)) {
			return false;
		}
		
		crosspile.add(cardBeingDragged);
		game.updateScore(+1);
		return true;

	}

	@Override
	public boolean undo(Solitaire game) {
		wastepile.add(crosspile.get());
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !wastepile.empty();
	}

}
