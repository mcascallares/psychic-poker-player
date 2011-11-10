package org.matias.poker;

import java.util.LinkedList;
import java.util.List;

import org.matias.poker.HandScorer.HandScore;

public class BestScorePicker {

	private HandScorer pokerScorer;

	public BestScorePicker(HandScorer pokerScorer) {
		super();
		this.pokerScorer = pokerScorer;
	}

	public HandScore pickBestScore(List<Card> hand, List<Card> deck) {
		// by the default the lowest score
		HandScore maxScore = HandScore.HIGHEST_CARD;
		
		// generates all possible games based on combinatories of hand and decks
		List<List<Card>> games = generateAllPossibleGames(hand, deck);
		for (List<Card> game : games) {
			HandScore score = pokerScorer.score(game);
			if (score.ordinal() < maxScore.ordinal())  {
				maxScore = score;
			}
		}
		return maxScore;
	}

	private List<List<Card>> generateAllPossibleGames(List<Card> hand, List<Card> deck) {
		List<List<Card>> handCombinatory = generateHandCombinatory(hand);
		for (List<Card> currentHand : handCombinatory) {
			pickFromDeck(currentHand, deck);
		}
		return handCombinatory;
	}

	private void pickFromDeck(List<Card> currentHand, List<Card> deck) {
		// in the deck the order matters!
		int cardsToPickFromDeck = 5 - currentHand.size();
		currentHand.addAll(deck.subList(0, cardsToPickFromDeck));
	}

	private List<List<Card>> generateHandCombinatory(List<Card> hand) {
		List<List<Card>> ret = new LinkedList<List<Card>>();
		for (int i = 0; i < 5; i++) {
			CombinatoryGenerator generator = new CombinatoryGenerator(5, i);
			while (generator.hasMore()) {
				List<Card> toAdd = new LinkedList<Card>();
				for (int index : generator.getNext()) {
					toAdd.add(hand.get(index));
				}
				ret.add(toAdd);
			}
		}
		return ret;
	}
}
