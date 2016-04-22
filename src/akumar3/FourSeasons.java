package akumar3;

import java.awt.Dimension;
import java.util.Random;

import heineman.klondike.KlondikeDeckController;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.common.view.RowView;
import ks.launcher.Main;

public class FourSeasons extends Solitaire {

	Deck deck;
	Pile piles[];
	Column columns[];
	Column wastepile;
	DeckView deckView;
	PileView pileViews[];
	ColumnView columnViews[];
	RowView wastepileView;
	IntegerView scoreView;
	IntegerView numLeftView;
	int firstFoundationRank;
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "akumar3-FourSeasons";
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return getScore().getValue()==52;
	}
	

	@Override
	public Dimension getPreferredSize() {
	
	  return new Dimension (2000, 1000);
	
	}
	 

	@Override
	public void initialize() {
		//Deck of cards to be dealt
		// initialize model
		
				initializeModel(getSeed());
				initializeView();
				initializeControllers();
				

				
				updateScore(1);
				updateNumberCardsLeft (46);
			}

	private void initializeControllers() {
		
		deckView.setMouseAdapter(new FourSeasonsDeckController (this, deck, wastepile));
		deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		wastepileView.setMouseAdapter(new FourSeasonsWastepileController (this, wastepileView));
		wastepileView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		wastepileView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		for (int i = 0; i < 5; i++) {
			columnViews[i].setMouseAdapter(new FourSeasonsCrosspileController (this, columnViews[i]));
			columnViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
			columnViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
			
		}
		
		for (int i = 0; i < 4; i++) {
			pileViews[i].setMouseAdapter(new FourSeasonsFoundationController (this, pileViews[i], firstFoundationRank));
			pileViews[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
			pileViews[i].setUndoAdapter (new SolitaireUndoAdapter(this));
			
		}
		
	}

	private void initializeView() {
		
		CardImages ci = getCardImages();

		deckView = new DeckView (deck);
		deckView.setBounds (30,120 + ci.getHeight()*7 , ci.getWidth(), ci.getHeight());
		//deckView.setBounds (30,40 , ci.getWidth(), ci.getHeight());
		container.addWidget (deckView);

		pileViews = new PileView[4];
		// create BuildablePileViews, one after the other (default to 13 full cards -- more than we'll need)
		for (int pileNum = 0; pileNum < 4; pileNum++) {
			pileViews[pileNum] = new PileView (piles[pileNum]);
			pileViews[pileNum].setBounds (100 + (20*(pileNum+1)) + (pileNum*ci.getWidth()), 40, ci.getWidth(), ci.getHeight());
			container.addWidget (pileViews[pileNum]);
		}
		
		columnViews = new ColumnView[5];
		for (int col = 0; col < 5; col++) {
			columnViews[col] = new ColumnView (columns[col]);
			columnViews[col].setBounds (10 + (20*(col+1)) + (col*ci.getWidth()), 70 + ci.getHeight(), ci.getWidth(), ci.getHeight()*5);
			container.addWidget (columnViews[col]);
		}
		wastepileView = new RowView(wastepile);
		wastepileView.setBounds(50 + ci.getWidth(), 120 + (ci.getHeight()*7), 16*ci.getWidth(), ci.getHeight());
		container.addWidget(wastepileView);
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize (36);
		scoreView.setBounds (90+17*ci.getWidth(), 120 + 7*ci.getHeight(), 100, 60);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (36);
		numLeftView.setBounds (200+17*ci.getWidth(), 120 + 7*ci.getHeight(), 100, 60);
		container.addWidget (numLeftView);
		
	}

	private void initializeModel(int seed) {
		deck = new Deck("deck");
		deck.create(seed);
		model.addElement(deck);
		
		piles = new Pile[4];
		
		for(int i = 0; i < 4; i++) {
			piles[i] = new Pile("pile" + Integer.toString(i+1));
			model.addElement(piles[i]);
		}
		Card card = deck.get();
		piles[0].add(card);
		firstFoundationRank = card.getRank();
		
		
		columns = new Column[5];
		
		for(int j = 0; j < 5; j++) {
			columns[j] = new Column("column" + Integer.toString(j+1));
			model.addElement(columns[j]);
			Card temp = deck.get();
			columns[j].add(temp);

		}
		
		
		wastepile = new Column ("wastepile");
		model.addElement(wastepile);
		

		updateScore(0);
		
	}
	
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		
		//Main.generateWindow(new FourSeasons(), new Random().nextInt());
		//If want to test the game being won.
		Main.generateWindow(new FourSeasons(), Deck.OrderByRank);

	}
		
		
}

