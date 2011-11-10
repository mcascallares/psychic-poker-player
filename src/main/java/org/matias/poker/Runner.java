package org.matias.poker;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.matias.poker.HandScorer.HandScore;

public class Runner {

	private Parser parser;
	private BestScorePicker bestScorePicker;

	public Runner() {
		this.parser = new Parser();
		this.bestScorePicker = new BestScorePicker(new HandScorer());
	}

	public void solve(String[] inputs) {
		List<List<Card>> games = parser.parseGames(inputs);
		for (List<Card> game : games) {
			List<Card> hand = game.subList(0, 5);
			List<Card> deck = game.subList(5, 10);
			HandScore bestScore = bestScorePicker.pickBestScore(hand, deck);
			System.out.println(MessageFormat.format("Hand: {0} Deck: {1} Best hand: {2}",
					prettyPrint(hand), prettyPrint(deck), bestScore));
		}
	}

	private String prettyPrint(List<Card> hand) {
		StringBuilder ret = new StringBuilder();
		for (Card card : hand) {
			ret.append(card.getFace().asString()).append(card.getSuit().asString()).append(" ");
		}
		return ret.toString();
	}

	public static void main(String[] args) throws IOException {
		String[] inputs = new FileLineReader("input").read();
		new Runner().solve(inputs);
	}

}
