package akumar3;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class CrossPileToFoundationMove extends Move {
	
	Pile foundation;
	Column crosssource;
	Card cardBeingDragged;
	int firstFoundationRank;
	
	public CrossPileToFoundationMove(Column from, Card cardBeingDragged, Pile dest, int firstFoundationRank) {
		this.cardBeingDragged = cardBeingDragged;
		this.crosssource = from;
		this.foundation = dest;
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
		
		crosssource.add(foundation.get());
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		
	
		if (foundation.empty()) {
			if (cardBeingDragged.getRank() == firstFoundationRank) {
				return true;
			}
		}
		else if ((cardBeingDragged.isAce()) && (foundation.peek().getRank() == foundation.peek().KING) && (cardBeingDragged.getSuit() == foundation.peek().getSuit()))
			return true;
		else if ((cardBeingDragged.getRank() == foundation.rank() + 1) && (cardBeingDragged.getSuit() == foundation.peek().getSuit()))
			return true;
		return false;
	}

}
