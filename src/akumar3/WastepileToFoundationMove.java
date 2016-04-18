package akumar3;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class WastepileToFoundationMove extends Move {
	
	Column wastepile;
	Card cardBeingDragged;
	Pile foundation;
	int firstFoundationRank;
	
	public WastepileToFoundationMove(Column from, Card cardBeingDragged, Pile foundation, int firstFoundationRank) {
		this.cardBeingDragged = cardBeingDragged;
		this.wastepile = from;
		this.foundation = foundation;
		this.firstFoundationRank = firstFoundationRank;
	}

	@Override
	public boolean doMove(Solitaire game) {
		if (!valid(game)) {
			return false;
		}
		
		foundation.add(cardBeingDragged);
		game.updateScore(+1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		wastepile.add(foundation.get());
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		
		
		//TODO check if wastepile can be empty
		if (!wastepile.empty() && foundation.empty()) {
			if (cardBeingDragged.getRank() == firstFoundationRank) {
				return true;
			}
		}
		else if (!wastepile.empty() && (cardBeingDragged.isAce()) && (foundation.peek().getRank() == foundation.peek().KING) && (cardBeingDragged.getSuit() == foundation.peek().getSuit()))
			return true;
		else if (!wastepile.empty() && (cardBeingDragged.getRank() == foundation.rank() + 1) && (cardBeingDragged.getSuit() == foundation.peek().getSuit()))
			return true;
		return false;
	}

}
