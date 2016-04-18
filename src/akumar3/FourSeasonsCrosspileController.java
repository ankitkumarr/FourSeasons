package akumar3;

import java.awt.event.MouseEvent;

import heineman.Klondike;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FourSeasonsCrosspileController extends java.awt.event.MouseAdapter{


	protected FourSeasons theGame;

	/** The specific Foundation pileView being controlled. */
	protected ColumnView src;
	/**
	 * FoundationController constructor comment.
	 */
	public FourSeasonsCrosspileController(FourSeasons thegame, ColumnView crosspileview ) {
		
		this.theGame = thegame;
		this.src = crosspileview;
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * <p>
	 * A bit of a challenge to construct the appropriate move, because cards
	 * can be dragged both from the WastePile (as a CardView object) and the 
	 * BuildablePileView (as a ColumnView).
	 * @param me java.awt.event.MouseEvent
	 */
	
	public void mousePressed(MouseEvent me) {
		Container c = theGame.getContainer();
		
		/** Return if there is no card to be chosen. */
		Column crosspile = (Column) src.getModelElement();
		if (crosspile.count() == 0) {
			c.releaseDraggingObject();
			return;
		}
	
		// Get a card to move from PileView. Note: this returns a CardView.
		// Note that this method will alter the model for PileView if the condition is met.
		CardView cardView = src.getCardViewForTopCard (me);
		
		// an invalid selection of some sort.
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}
		
		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("WastePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
	
		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (cardView, me);
		
		// Tell container which src widget initiated the drag
		c.setDragSource(src);
	
		// The only widget that could have changed is ourselves. If we called refresh, there
		// would be a flicker, because the dragged widget would not be redrawn. We simply
		// force the WastePile's image to be updated, but nothing is refreshed on the screen.
		// This is patently OK because the card has not yet been dragged away to reveal the
		// card beneath it.  A bit tricky and I like it!
		src.redraw();
		
	}
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();
		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile OR waste Pile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragsource in container.");
			c.releaseDraggingObject();
			return;
		}

		// Determine the To Pile
		Column crosspile = (Column) src.getModelElement();
		
		if (fromWidget.getModelElement().getName().equals("wastepile")){
			
			Column wastepile = (Column) fromWidget.getModelElement();
			
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			
			Move move = new WastepileToCrossPileMove(wastepile, theCard, crosspile);
			if(move.doMove(theGame)) {
				theGame.pushMove (move);     // Successful Move has been Move
				
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}
		
		else  if ((fromWidget.getModelElement().getName().equals("column1")) || (fromWidget.getModelElement().getName().equals("column2")) ||
				(fromWidget.getModelElement().getName().equals("column3")) || (fromWidget.getModelElement().getName().equals("column4")) || 
				(fromWidget.getModelElement().getName().equals("column5")))  {
			
			Column from = (Column) fromWidget.getModelElement();
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			
			Move move = new CrossPileToCrossPileMove(from, theCard, crosspile);
			if(move.doMove(theGame)) {
				theGame.pushMove (move);     // Successful Move has been Move
				
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}
		
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
	}

}
