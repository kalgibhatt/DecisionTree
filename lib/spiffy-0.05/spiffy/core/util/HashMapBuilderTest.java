package spiffy.core.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class HashMapBuilderTest {
	
	@Test
	public void build_a_map() {
		final HashMap<String, Integer> dictionary = new HashMapBuilder<String, Integer>().add("one", 1).add("two", 2)
				.build();
		assertEquals(2, dictionary.size());
		assertEquals(1, dictionary.get("one").intValue());
	}
}
