package akumar3;

import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

public class FourSeasonsFoundationController extends java.awt.event.MouseAdapter {
	
	protected FourSeasons theGame;

	/** The specific Foundation pileView being controlled. */
	protected PileView src;
	/**
	 * FoundationController constructor comment.
	 */
	protected int firstFoundationCard;
	
	public FourSeasonsFoundationController(FourSeasons thegame, PileView foundationView, int firstFoundationCard) {
		
		this.firstFoundationCard = firstFoundationCard;
		this.theGame = thegame;
		this.src = foundationView;
		
		// TODO Auto-generated constructor stub
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
		Pile foundation = (Pile) src.getModelElement();
		
		if (fromWidget.getModelElement().getName().equals("wastepile")){
			
			Column wastepile = (Column) fromWidget.getModelElement();
			
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			
			Move move = new WastepileToFoundationMove(wastepile, theCard, foundation, firstFoundationCard);
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
			
			Move move = new CrossPileToFoundationMove(from, theCard, foundation, firstFoundationCard);
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
