package org.matias.poker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible of generating the score for a specified hand. The
 * implementation is based in bitwise operation (upon face and suit of cards)
 * and fast lookups (binary integer searches) for those checking that cannot be
 * done with bitwise like straights.
 * 
 * @author matias
 * 
 */
public class HandScorer {

	public enum HandScore {
		STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIRS, ONE_PAIR, HIGHEST_CARD;
	}

	/**
	 * Face masks to identify all possible straights. Is sorted so is possible
	 * to implement a bynary search. Note: value 31 represent a high-end
	 * straight (10, J, Q, K, Ace) and value 7681 represents low-end straight
	 * (Ace, 2, 3, 4, 5).
	 */
	private static final int[] possibleStraights = new int[] { 31, 62, 124, 248, 496, 992, 1984,
			3968, 7681, 7936 };

	public HandScore score(List<Card> hand) {
		int[] cardsAsBits = new int[5];
		for (int i = 0; i < hand.size(); i++) {
			cardsAsBits[i] = asBits(hand.get(i));
		}

		int oredFace = (cardsAsBits[0] | cardsAsBits[1] | cardsAsBits[2] | cardsAsBits[3] | cardsAsBits[4])
				& Card.FACE_MASK;
		switch (numberOfEnabledBits(oredFace)) {
		case 5:
			return straightOrFlushOrHighCard(oredFace, cardsAsBits);

		case 4:
			return HandScore.ONE_PAIR;

		case 3:
			return twoPairsOrThreeOfAKind(cardsAsBits);

		case 2:
			return fullHouseOrFourOfAKind(cardsAsBits);

		case 1:
			return HandScore.FOUR_OF_A_KIND;
		}

		throw new RuntimeException("Invalid card masks");
	}

	private HandScore straightOrFlushOrHighCard(int oredFace, int[] cardsAsBits) {
		boolean isStraight = Arrays.binarySearch(possibleStraights, oredFace) >= 0;
		boolean isFlush = (cardsAsBits[0] & cardsAsBits[1] & cardsAsBits[2] & cardsAsBits[3]
				& cardsAsBits[4] & Card.SUIT_MASK) != 0;

		if (isStraight && isFlush) {
			return HandScore.STRAIGHT_FLUSH;
		} else if (isStraight) {
			return HandScore.STRAIGHT;
		} else if (isFlush) {
			return HandScore.FLUSH;
		} else {
			return HandScore.HIGHEST_CARD;
		}
	}

	private HandScore fullHouseOrFourOfAKind(int[] cardsAsBits) {
		return generateFrequencyMap(cardsAsBits).containsValue(4) ? HandScore.FOUR_OF_A_KIND
				: HandScore.FULL_HOUSE;
	}

	private HandScore twoPairsOrThreeOfAKind(int[] cardsAsBits) {
		return generateFrequencyMap(cardsAsBits).containsValue(3) ? HandScore.THREE_OF_A_KIND
				: HandScore.TWO_PAIRS;
	}

	/**
	 * Count number occurrences per cards based on their face.
	 * 
	 * @param cardsAsBits
	 * @return map containing card face mask and number of ocurrences.
	 */
	private Map<Integer, Integer> generateFrequencyMap(int[] cardsAsBits) {
		Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
		for (int i = 0; i < cardsAsBits.length; i++) {
			int card = cardsAsBits[i] & Card.FACE_MASK;
			int ocurrences = frequency.containsKey(card) ? frequency.get(card) : 0;
			frequency.put(card, ++ocurrences);
		}
		return frequency;
	}

	private int numberOfEnabledBits(int number) {
		int count = 0;
		while (number != 0) {
			if ((number & 1) == 1) {
				++count;
			}
			number = number >> 1;
		}
		return count;
	}

	/**
	 * Returns a bit representation of the card. This representation follow this
	 * schema:
	 * 
	 * |C|D|H|S|2|3|4|5|6|7|8|9|10|J|Q|K|A|
	 * 
	 * @return integer with bitmask representation.
	 */
	private int asBits(Card card) {
		int ret = 0;
		switch (card.getSuit()) {
		case CLUBS:
			ret += 65536;
			break;
		case DIAMONDS:
			ret += 32768;
			break;
		case HEARTS:
			ret += 16384;
			break;
		case SPADES:
			ret += 8192;
			break;
		}
		switch (card.getFace()) {
		case TWO:
			ret += 4096;
			break;
		case THREE:
			ret += 2048;
			break;
		case FOUR:
			ret += 1024;
			break;
		case FIVE:
			ret += 512;
			break;
		case SIX:
			ret += 256;
			break;
		case SEVEN:
			ret += 128;
			break;
		case EIGHT:
			ret += 64;
			break;
		case NINE:
			ret += 32;
			break;
		case TEN:
			ret += 16;
			break;
		case JACK:
			ret += 8;
			break;
		case QUEEN:
			ret += 4;
			break;
		case KING:
			ret += 2;
			break;
		case ACE:
			ret += 1;
			break;
		}
		return ret;
	}

}
