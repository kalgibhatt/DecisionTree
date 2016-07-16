package spiffy.core.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;

import spiffy.core.util.CollectionHelper;

public class StringHelperTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void in_illegalCall() {
		assertTrue(StringHelper.in("search"));
	}
	
	@Test
	public void in_inCollection() {
		assertTrue(StringHelper.in("search", "back", "search"));
		assertTrue(StringHelper.in("search", "search", "back"));
		assertTrue("can search empty str", StringHelper.in("", "search", "", "back"));
		
	}
	
	@Test
	public void in_not_In_Collection() {
		assertFalse(StringHelper.in("search", "SEARCH", "back"));
		assertFalse(StringHelper.in(null, "search"));
		assertFalse("search needs trimming to be true", StringHelper.in("  search  ", "search", "back"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void inAndNonEmpty_illegalCall() {
		assertTrue(StringHelper.inAndNonEmpty("search"));
	}
	
	@Test
	public void inAndNonEmpty_inCollection() {
		assertTrue(StringHelper.inAndNonEmpty("search", "back", "search"));
		assertTrue(StringHelper.inAndNonEmpty("search", "search", "back"));
		assertTrue("trimmed search", StringHelper.inAndNonEmpty("  search  ", "search", "back"));
		
	}
	
	@Test
	public void inAndNonEmpty_not_In_Collection() {
		assertFalse("can't search empty str", StringHelper.inAndNonEmpty("", "search", "", "back"));
		assertFalse(StringHelper.inAndNonEmpty("search", "SEARCH", "back"));
		assertFalse(StringHelper.inAndNonEmpty(null, "search"));
	}
	
	@Test
	public void join_should_return_empty_on_empy() {
		assertEquals("empty", "", StringHelper.join(", ", new String[0]));
	}
	
	@Test
	public void join_should_return_foo_deli_bar_on_foo_bar() {
		assertEquals("foo, bar, baz", StringHelper.join(", ", "foo", "bar", "baz"));
	}
	
	@Test
	public void join_should_return_foo_deli_bar_on_iterator_foo_bar() {
		final Iterator<String> i = CollectionHelper.arrayList("foo", "bar", "baz").iterator();
		assertEquals("iterator join", "foo, bar, baz", StringHelper.join(", ", i));
	}
	
	@Test
	public void join_should_return_foo_on_arg_foo() {
		assertEquals("foo", StringHelper.join(", ", "foo"));
	}
	
	@Test
	public void join_should_return_null_on_arg_null_iterator() {
		assertNull(StringHelper.join(", ", (Iterator<?>) null));
	}
	
	@Test
	public void join_should_return_null_on_arg_null_string_arry() {
		assertNull(StringHelper.join(", ", (String[]) null));
	}
	
	@Test
	public void make_code_coverage_happy() {
		new StringHelper();
	}
	
	@Test
	public void removeAll_input_matching_all() {
		assertEquals("", StringHelper.removeAll(" \n", '\n', ' '));
	}
	
	@Test
	public void removeAll_input_matching_almost_all() {
		assertEquals("a", StringHelper.removeAll(" a \n", '\n', ' '));
	}
	
	@Test
	public void repeat_left_join() {
		assertEquals("fully fill", "100###", StringHelper.repeatRightJoin("100", 6, "#"));
		
		assertEquals("partial fill due to filling with ## and must reach an even number", //
				"100##", //
				StringHelper.repeatRightJoin("100", 6, "##"));
		
		assertEquals("fully fill with multiple arguments round-robin'ing", //
				"100#!#", //
				StringHelper.repeatRightJoin("100", 6, "#", "!"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void repeat_left_join_fail_arg_baseString() {
		assertEquals("", StringHelper.repeatRightJoin(null, 6, "#"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void repeat_left_join_fail_arg_fill() {
		assertEquals("", StringHelper.repeatRightJoin("lenis6", 5, (String[]) null));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void repeat_left_join_fail_resultSizeTooSmall() {
		assertEquals("", StringHelper.repeatRightJoin("lenis6", 5, "#"));
	}
	
	// public void trim_null_input_one_trimarg() {
	// assertNull(StringHelper.trim(null, " "));
	// }
	//
	// @Test
	// public void trim_empty_input_one_trimarg() {
	//
	// }
	//
	// @Test
	// public void trim_input_matching_all_one_trimarg() {
	//
	// }
	//
	// @Test
	// public void trim_input_matching_some_one_trimarg() {
	//
	// }
	//
	// @Test
	// public void trim_input_matching_all_mult_trimarg() {
	//
	// }
	//
	// @Test
	// public void trim_input_matching_some_mult_trimarg() {
	//
	// }
	
	@Test
	public void repeat_right_join() {
		assertEquals("no change due to size", "100", StringHelper.repeatLeftJoin("100", 3, "#"));
		assertEquals("no change due to no filling", "100", StringHelper.repeatLeftJoin("100", 3, new String[0]));
		assertEquals("fully fill", "###100", StringHelper.repeatLeftJoin("100", 6, "#"));
		
		assertEquals("partial fill due to filling with ## and must reach an even number", //
				"##100", //
				StringHelper.repeatLeftJoin("100", 6, "##"));
		
		assertEquals("fully fill with multiple arguments round-robin'ing", //
				"#!#100", //
				StringHelper.repeatLeftJoin("100", 6, "#", "!"));
	}
	
	@Test
	public void toString_should_handle_exception() {
		final String str = StringHelper.toString(new Exception("hello"));
		assertTrue(str.indexOf("java.lang.Exception: hello") > -1);
	}
}
