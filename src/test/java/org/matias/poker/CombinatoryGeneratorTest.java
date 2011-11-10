package org.matias.poker;

import org.junit.Test;
import org.matias.poker.CombinatoryGenerator;

public class CombinatoryGeneratorTest {

	private CombinatoryGenerator generator;

	@Test
	public void testGeneration() {
		String[] elements = {"A", "B", "C", "D", "E"};
		generator = new CombinatoryGenerator(5, 3);
		while (generator.hasMore()) {
			StringBuilder ret = new StringBuilder();
			int[] indexes = generator.getNext();
			for (int i : indexes) {
				ret.append(elements[i]);
			}
			System.out.println(ret.toString());
		}
	}
}
