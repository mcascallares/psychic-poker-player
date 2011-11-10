package org.matias.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.matias.poker.Card.Face;
import org.matias.poker.Card.Suit;

public class Parser {

	private final Map<String, Suit> suits;
	private final Map<String, Face> faces;

	public Parser() {
		super();
		suits = new HashMap<String, Suit>();
		suits.put("C", Suit.CLUBS);
		suits.put("D", Suit.DIAMONDS);
		suits.put("H", Suit.HEARTS);
		suits.put("S", Suit.SPADES);

		faces = new HashMap<String, Face>();
		faces.put("A", Face.ACE);
		faces.put("2", Face.TWO);
		faces.put("3", Face.THREE);
		faces.put("4", Face.FOUR);
		faces.put("5", Face.FIVE);
		faces.put("6", Face.SIX);
		faces.put("7", Face.SEVEN);
		faces.put("8", Face.EIGHT);
		faces.put("9", Face.NINE);
		faces.put("T", Face.TEN);
		faces.put("J", Face.JACK);
		faces.put("Q", Face.QUEEN);
		faces.put("K", Face.KING);
	}

	/**
	 * Parse games in one line per game fashion.
	 * @param inputs
	 * @return
	 */
	List<List<Card>> parseGames(String[] inputs) {
		List<List<Card>> cards = new LinkedList<List<Card>>();
		for (String input : inputs) {
			cards.add(parseCards(input));
		}
		return cards;
	}

	private List<Card> parseCards(String input) {
		String[] cards = input.split(" ");
		validateInput(cards);
		List<Card> ret = new ArrayList<Card>(10);
		for (String cardInput : cards) {
			ret.add(new Card(suits.get(cardInput.substring(1, 2)), faces.get(cardInput.substring(0,
					1))));
		}
		return ret;
	}

	private void validateInput(String[] cards) {
		if (cards == null || cards.length != 10) {
			throw new RuntimeException("Invalid input");
		}
	}
}
