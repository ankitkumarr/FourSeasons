package akumar3;

import java.awt.Dimension;

import heineman.Klondike;
import ks.common.games.Solitaire;
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
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "akumar3-FourSeasons";
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}
	


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

				
				updateScore(0);
				updateNumberCardsLeft (52);
			}

	private void initializeControllers() {
		// TODO Auto-generated method stub
		
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
			columnViews[col].setBounds (10 + (20*(col+1)) + (col*ci.getWidth()), 70 + ci.getHeight(), ci.getWidth(), ci.getHeight());
			container.addWidget (columnViews[col]);
		}
		wastepileView = new RowView(wastepile);
		wastepileView.setBounds(50 + ci.getWidth(), 120 + (ci.getHeight()*7), ci.getWidth(), ci.getHeight());
		container.addWidget(wastepileView);
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize (14);
		scoreView.setBounds (90+12*ci.getWidth(), 120 + 7*ci.getHeight(), 100, 60);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (14);
		numLeftView.setBounds (200+12*ci.getWidth(), 120 + 7*ci.getHeight(), 100, 60);
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
		
		columns = new Column[5];
		
		for(int j = 0; j < 5; j++) {
			columns[j] = new Column("column" + Integer.toString(j+1));
			model.addElement(columns[j]);
		}
		
		wastepile = new Column ("wastepile");
		model.addElement(wastepile);
		
		updateNumberCardsLeft(52);
		updateScore(0);
		
	}
	
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new FourSeasons(), Deck.OrderBySuit);
	}
		
		
}

