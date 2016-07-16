package spiffy.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

/**
 * @author kasper graversen
 */
public class TwoDHashMapTest {
final static String KEY1 = "key1";
final static String KEY2 = "key2";
final static String KEY3 = "key3";
final static String VALUE1 = "VAL1";
final static String VALUE2 = "VAL2";

@Test
public void containsKey() {
	final TwoDHashMap<String, String, String> map = new TwoDHashMap<String, String, String>();
	map.set(KEY1, KEY1, VALUE1);
	map.set(KEY1, KEY2, null);
	
	assertTrue("elem exists", map.containsKey(KEY1, KEY1));
	assertTrue("null exists", map.containsKey(KEY1, KEY2));
	assertFalse("unknown does not exists", map.containsKey(KEY2, KEY2));
}

@Test
public void set_get() {
	final TwoDHashMap<String, String, String> map = new TwoDHashMap<String, String, String>();
	map.set(KEY1, KEY1, VALUE1);
	map.set(KEY1, KEY2, VALUE2);
	
	assertEquals(VALUE1, map.get(KEY1, KEY1));
	assertEquals(VALUE2, map.get(KEY1, KEY2));
	assertNull(map.get(KEY2, KEY1));
}

@Test
public void size() {
	final TwoDHashMap<String, String, String> map = new TwoDHashMap<String, String, String>();
	map.set(KEY1, KEY1, null);
	map.set(KEY1, KEY2, null);
	map.set(KEY1, KEY3, null);
	
	assertEquals("outer map has ", 1, map.size());
	assertEquals("inner map key1 has ", 3, map.size(KEY1));
	assertEquals("outer map key2 has ", 0, map.size(KEY2));
}

@Test
// no need to test more since we use the hashmap's methods
public void dim1_should_have_two_keys() {
	final TwoDHashMap<String, String, String> map = new TwoDHashMap<String, String, String>();
	map.set(KEY1, KEY1, VALUE1);
	map.set(KEY2, KEY1, VALUE1);
	final Set<String> set = map.keySet();
	assertEquals(2, set.size());
	assertTrue(set.contains(KEY1));
	assertTrue(set.contains(KEY2));
}

}
