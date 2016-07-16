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
public class ThreeDHashMapTest {
final static String KEY1 = "key1";
final static String KEY2 = "key2";
final static String KEY3 = "key3";
final static String VALUE1 = "VAL1";
final static String VALUE2 = "VAL2";

@Test
// no need to test more since we use the hashmap's methods
public void dim1_should_have_two_keys() {
	final ThreeDHashMap<String, String, String, String> map = new ThreeDHashMap<String, String, String, String>();
	map.set(KEY1, KEY1, KEY1, VALUE1);
	map.set(KEY2, KEY1, KEY2, null);
	final Set<String> set = map.keySet();
	assertEquals(2, set.size());
	assertTrue(set.contains(KEY1));
	assertTrue(set.contains(KEY2));
}

@Test
// no need to test dim3 since dim2 and dim3 uses the hashmap's methods
public void dim2_should_have_one_keys() {
	final ThreeDHashMap<String, String, String, String> map = new ThreeDHashMap<String, String, String, String>();
	map.set(KEY1, KEY1, KEY1, VALUE1);
	map.set(KEY2, KEY1, KEY2, null);
	final Set<String> set = map.get(KEY1).keySet();
	assertEquals(1, set.size());
	assertTrue(set.contains(KEY1));
}

@Test
public void containsKey() {
	final ThreeDHashMap<String, String, String, String> map = new ThreeDHashMap<String, String, String, String>();
	map.set(KEY1, KEY1, KEY1, VALUE1);
	map.set(KEY1, KEY1, KEY2, null);
	
	assertTrue("elem exists", map.containsKey(KEY1, KEY1, KEY1));
	assertTrue("null exists", map.containsKey(KEY1, KEY1, KEY2));
	assertFalse("unknown does not exists", map.containsKey(KEY2, KEY2, KEY2));
}

@Test
public void set_get() {
	final ThreeDHashMap<String, String, String, String> map = new ThreeDHashMap<String, String, String, String>();
	map.set(KEY1, KEY1, KEY1, VALUE1);
	map.set(KEY1, KEY1, KEY2, VALUE2);
	
	assertEquals(VALUE1, map.get(KEY1, KEY1, KEY1));
	assertEquals(VALUE2, map.get(KEY1, KEY1, KEY2));
	assertNull(map.get(KEY1, KEY2, KEY1));
}

@Test
public void size() {
	final ThreeDHashMap<String, String, String, String> map = new ThreeDHashMap<String, String, String, String>();
	map.set(KEY1, KEY1, KEY1, VALUE1);
	map.set(KEY1, KEY1, KEY2, VALUE1);
	map.set(KEY1, KEY2, KEY3, VALUE1);
	
	assertEquals("outer map has ", 1, map.size());
	assertEquals("inner map key1 has ", 2, map.size(KEY1));
	assertEquals("inner map key2 has ", 0, map.size(KEY2));
	assertEquals("inner inner map key1,1 has ", 2, map.size(KEY1, KEY1));
	assertEquals("inner inner map key1,2 has ", 1, map.size(KEY1, KEY2));
	assertEquals("inner inner map key1,3 has ", 0, map.size(KEY1, KEY3));
}
}
