package akumar3;


import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Move;


public class FourSeasonsDeckController extends SolitaireReleasedAdapter  {
	
	/** The game. */
	protected FourSeasons theGame;

	/** The WastePile of interest. */
	protected Column wastePile;

	/** The Deck of interest. */
	protected Deck deck;

	/**
	 * KlondikeDeckController constructor comment.
	 */
	public FourSeasonsDeckController(FourSeasons theGame, Deck d, Column wastePile) {
		super(theGame);

		this.theGame = theGame;
		this.wastePile = wastePile;
		this.deck = d;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event. In this case,
	 * no drag is ever achieved, and we simply deal upon the pres.
	 */
	public void mousePressed (java.awt.event.MouseEvent me) {

		// Attempting a DealFourCardMove
		Move m = new DealCardToWastepileMove (deck, wastePile);
		if (m.doMove(theGame)) {
			theGame.pushMove (m);     // Successful Move has been Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
	}
}
