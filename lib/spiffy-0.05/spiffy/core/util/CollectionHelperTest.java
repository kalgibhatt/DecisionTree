package spiffy.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

public class CollectionHelperTest {
	@Test
	public void arryList_should_create_an_ArrayList() {
		final ArrayList<String> al = CollectionHelper.arrayList("a", "b", "c");
		assertEquals("lenght is correct", 3, al.size());
		assertEquals("Random elem is correct", "b", al.get(1));
	}
	
	@Test
	public void arryList_should_give_null_on_null() {
		final ArrayList<String> al = CollectionHelper.arrayList((String[]) null);
		assertNull(al);
	}
	
	@Test
	public void arryListOfObject_should_create_an_ArrayList_of_object() {
		final ArrayList<? super Object> al = CollectionHelper.arrayListObjects(1, "a", 2.4);
		assertEquals("lenght is correct", 3, al.size());
		assertEquals("Random elem is correct", "a", al.get(1));
	}
	
	@Test(expected = IllegalStateException.class)
	public void firstOnly_fail_on_2_elem_coll() {
		final ArrayList<String> l = CollectionHelper.arrayList("hello", "world");
		assertEquals(null, CollectionHelper.firstOnly(l));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void firstOnly_fail_on_null_coll() {
		assertEquals(null, CollectionHelper.firstOnly(null));
	}
	
	@Test
	public void firstOnly_should_return_first_elem_on_1_elem_coll() {
		final ArrayList<String> l = CollectionHelper.arrayList("hello");
		assertEquals("hello", CollectionHelper.firstOnly(l));
	}
	
	@Test
	public void make_code_coverage_happy() {
		new CollectionHelper();
	}
	
}
