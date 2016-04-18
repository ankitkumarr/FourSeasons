package akumar3;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;

public class CrossPileToCrossPileMove extends Move {
	
	Column crossdest;
	Column crosssource;
	Card cardBeingDragged;
	
	public CrossPileToCrossPileMove(Column from, Card cardBeingDragged, Column dest) {
		this.cardBeingDragged = cardBeingDragged;
		this.crosssource = from;
		this.crossdest = dest;
	}

	@Override
	public boolean doMove(Solitaire game) {
		
		if (!valid(game)) {
			return false;
		}
		
		crossdest.add(cardBeingDragged);
		//game.updateScore(+1);
		return true;

	}

	@Override
	public boolean undo(Solitaire game) {
		
		crosssource.add(crossdest.get());
		//game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		
		
		//TODO: EMPTY?
		if (crossdest.empty())
			return true;
		if ((crossdest.peek().isAce()) && (cardBeingDragged.getRank() == cardBeingDragged.KING))
			return true;
		if ((cardBeingDragged.getRank() == crossdest.rank() - 1))
			return true;
		return false; 

	}

}
