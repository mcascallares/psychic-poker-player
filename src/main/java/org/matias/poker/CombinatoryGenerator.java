package org.matias.poker;

import java.math.BigInteger;

/**
 * Extracted algorith from http://www.merriampark.com/comb.html.
 * 
 * 
 */
public class CombinatoryGenerator {

	private int[] indexes;
	private int n;
	private int r;
	private BigInteger numLeft;
	private BigInteger total;

	public CombinatoryGenerator(int n, int r) {
		this.n = n;
		this.r = r;
		indexes = new int[r];
		BigInteger nFact = getFactorial(n);
		BigInteger rFact = getFactorial(r);
		BigInteger nminusrFact = getFactorial(n - r);
		total = nFact.divide(rFact.multiply(nminusrFact));
		reset();
	}

	public void reset() {
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = i;
		}
		numLeft = new BigInteger(total.toString());
	}

	public BigInteger getNumLeft() {
		return numLeft;
	}

	public boolean hasMore() {
		return numLeft.compareTo(BigInteger.ZERO) == 1;
	}

	public BigInteger getTotal() {
		return total;
	}

	private static BigInteger getFactorial(int n) {
		BigInteger fact = BigInteger.ONE;
		for (int i = n; i > 1; i--) {
			fact = fact.multiply(new BigInteger(Integer.toString(i)));
		}
		return fact;
	}

	/**
	 * Generate next combination (algorithm from Rosen p. 286)
	 * 
	 * @return
	 */
	public int[] getNext() {
		if (numLeft.equals(total)) {
			numLeft = numLeft.subtract(BigInteger.ONE);
			return indexes;
		}
		int i = r - 1;
		while (indexes[i] == n - r + i) {
			i--;
		}
		indexes[i] = indexes[i] + 1;
		for (int j = i + 1; j < r; j++) {
			indexes[j] = indexes[i] + j - i;
		}
		numLeft = numLeft.subtract(BigInteger.ONE);
		return indexes;
	}
}