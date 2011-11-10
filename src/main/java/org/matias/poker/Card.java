package org.matias.poker;


public class Card {

	/**
	 * Mask to select the suit part of a card in its bit representation.
	 * 
	 * @see asBits()
	 */
	public static final int SUIT_MASK = 122880;

	/**
	 * Mask to select the face part of a card in its bit representation.
	 * 
	 * @see asBits()
	 */
	public static final int FACE_MASK = 8191;

	public enum Suit {
		CLUBS("C"), DIAMONDS("D"), HEARTS("H"), SPADES("S");

		private String representation;

		private Suit(String representation) {
			this.representation = representation;
		}

		public String asString() {
			return representation;
		}
	}

	public enum Face {
		TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN(
				"T"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

		private String representation;

		private Face(String representation) {
			this.representation = representation;
		}

		public String asString() {
			return representation;
		}
	}

	private final Suit suit;
	private final Face face;

	public Card(Suit suit, Face face) {
		if (suit == null || face == null) {
			throw new RuntimeException("You must specify a valid suit and face");
		}
		this.suit = suit;
		this.face = face;
	}

	public Suit getSuit() {
		return suit;
	}

	public Face getFace() {
		return face;
	}

}
