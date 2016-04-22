package akumar3;

import java.awt.event.MouseEvent;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.view.Widget;
import ks.launcher.Main;
import ks.tests.model.ModelFactory;

public class TestFourSeasons extends TestCase {
	
	FourSeasons game;
	GameWindow gw;
	
	public void setUp() {
		game = new FourSeasons();
		gw = Main.generateWindow(game, Deck.OrderByRank);
	}
	
	public void tearDown() {
		gw.dispose();
	}
	
	public MouseEvent createPressed (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_PRESSED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 0, false);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createRightClick (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_PRESSED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 0, true);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createReleased (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_RELEASED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 0, false);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createClicked (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 1, false);
		return me;
	}
	
	/** (dx,dy) are offsets into the widget space. Feel Free to Use as Is. */
	public MouseEvent createDoubleClicked (Solitaire game, Widget view, int dx, int dy) {
		MouseEvent me = new MouseEvent(game.getContainer(), MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, 
				view.getX()+dx, view.getY()+dy, 2, false);
		return me;
	}
	
	public void testDealCard() {
		//ModelFactory.init(game.deck, "KC");
		MouseEvent ev1 = createPressed(game, game.deckView,1,1);
		game.deckView.getMouseManager().handleMouseEvent(ev1);
		System.out.println(game.wastepile);
		assertTrue(game.wastepile.peek().isFaceCard());
		
	}
	
	public void testCrosspileToFoundationMoveControllers() {
		//Card c = game.columns[0].peek();
		
		ModelFactory.init(game.columns[0], "KD" );
		ModelFactory.init(game.piles[3], "");
		ModelFactory.init(game.columns[1], "AH" );
		ModelFactory.init(game.piles[2], "KH");
		
		MouseEvent ev1 = createPressed(game, game.columnViews[0],0,0);
		game.columnViews[0].getMouseManager().handleMouseEvent(ev1);
		
		MouseEvent ev2 = createReleased(game, game.pileViews[3],0,0);
		game.pileViews[3].getMouseManager().handleMouseEvent(ev2);
		
		MouseEvent ev3 = createPressed(game, game.columnViews[1],0,0);
		game.columnViews[1].getMouseManager().handleMouseEvent(ev3);
		
		MouseEvent ev4 = createReleased(game, game.pileViews[2],0,0);
		game.pileViews[2].getMouseManager().handleMouseEvent(ev4);
		
		assertTrue(game.piles[3].count() == 1);
		//assertTrue(game.piles[3].peek().equals(c));
		assertTrue(game.columns[0].count() == 0);
		
		assertTrue(game.piles[2].count() == 2);
		assertTrue(game.columns[1].count() == 0);
		
		
	}
	
	public void testCrosspileToFoundationMove() {
		
		ModelFactory.init(game.piles[0], "KD");
		ModelFactory.init(game.columns[1], "KH" );
		ModelFactory.init(game.piles[2], "");
		CrossPileToFoundationMove tm1 = new CrossPileToFoundationMove(game.columns[1], game.columns[1].peek(), game.piles[2], game.firstFoundationRank);
		assertTrue(tm1.doMove(game));
	}
	
	public void testWastePileController() {
		ModelFactory.init(game.wastepile, "KD" );
		ModelFactory.init(game.piles[3], "");
		
		MouseEvent ev1 = createPressed(game, game.wastepileView,0,0);
		game.wastepileView.getMouseManager().handleMouseEvent(ev1);
		
		MouseEvent ev2 = createReleased(game, game.pileViews[3],0,0);
		game.pileViews[3].getMouseManager().handleMouseEvent(ev2);
		System.out.println(game.piles[3].count());
		assertTrue(game.piles[3].count() == 0);
	}
	
	public void testWastePileToCrosspileControllers() {
		ModelFactory.init(game.wastepile, "KD" );
		ModelFactory.init(game.columns[1], "AH" );
		
		MouseEvent ev1 = createPressed(game, game.wastepileView,0,0);
		game.wastepileView.getMouseManager().handleMouseEvent(ev1);
		
		MouseEvent ev3 = createReleased(game, game.columnViews[1],0,0);
		game.columnViews[1].getMouseManager().handleMouseEvent(ev3);
		
		assertTrue(game.columns[1].count() == 2);
	}
	
	public void testCrossPileControllers() {
		ModelFactory.init(game.columns[1], "KH" );
		ModelFactory.init(game.columns[2], "QH" );
		
		MouseEvent ev1 = createPressed(game, game.columnViews[2],0,0); 
		game.columnViews[2].getMouseManager().handleMouseEvent(ev1);
		
		MouseEvent ev2 = createReleased(game, game.columnViews[1],0,0); 
		game.columnViews[1].getMouseManager().handleMouseEvent(ev2);
		
		assertTrue(game.columns[1].count() == 2);
		
	}
	

}
