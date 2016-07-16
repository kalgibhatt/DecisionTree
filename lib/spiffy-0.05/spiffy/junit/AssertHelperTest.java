package spiffy.junit;

import java.util.ArrayList;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import spiffy.core.util.CollectionHelper;

public class AssertHelperTest {
	@Test(expected = AssertionFailedError.class)
	public void assertEmpty_fail() {
		AssertHelper.assertEmpty("foo", CollectionHelper.arrayList("foo"));
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEmpty_null_fail() {
		AssertHelper.assertEmpty("foo", null);
	}
	
	@Test
	public void assertEquals_byte() {
		final byte[] bexp = new byte[] { 1, 2 };
		final byte[] bact = new byte[] { 1, 2 };
		AssertHelper.assertEquals("foo", (byte[]) null, (byte[]) null);
		AssertHelper.assertEquals("foo", bexp, bact);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEquals_byte_fail() {
		final byte[] bexp = new byte[] { 1, 2 };
		final byte[] bact = new byte[] { 1, 1 };
		AssertHelper.assertEquals("foo", bexp, bact);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEquals_byte_null_fail() {
		final byte[] bexp = new byte[] { 1, 2 };
		AssertHelper.assertEquals("foo", bexp, null);
	}
	
	@Test
	public void assertEquals_int() {
		final int[] bexp = new int[] { 1, 2 };
		final int[] bact = new int[] { 1, 2 };
		AssertHelper.assertEquals("foo", (int[]) null, (int[]) null);
		AssertHelper.assertEquals("foo", bexp, bact);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEquals_int_fail() {
		final int[] bexp = new int[] { 1, 2 };
		final int[] bact = new int[] { 1, 1 };
		AssertHelper.assertEquals("foo", bexp, bact);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEquals_int_null_fail() {
		final int[] bexp = new int[] { 1, 1 };
		AssertHelper.assertEquals("foo", bexp, null);
	}
	
	@Test
	public void assertEquals_Object() {
		final Object[] bexp = new Object[] { 1, 2 };
		final Object[] bact = new Object[] { 1, 2 };
		AssertHelper.assertEquals("foo", (Object[]) null, (Object[]) null);
		AssertHelper.assertEquals("foo", bexp, bact);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEquals_Object_fail() {
		final Object[] bexp = new Object[] { 1, 2 };
		final Object[] bact = new Object[] { 1, 1 };
		AssertHelper.assertEquals("foo", bexp, bact);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertEquals_Object_null_fail() {
		final Object[] bexp = new Object[] { 1, 2 };
		AssertHelper.assertEquals("foo", bexp, null);
	}
	
	@Test
	public void assertGT_double() {
		AssertHelper.assertGreaterThan("foo", 1.0, 2.0);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertGT_fail_double() {
		AssertHelper.assertGreaterThan("foo", 1.0, 1.0);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertGT_fail_int() {
		AssertHelper.assertGreaterThan("foo", 1, 1);
	}
	
	@Test
	public void assertGT_int() {
		AssertHelper.assertGreaterThan("foo", 1, 2);
	}
	
	@Test
	public void assertIsEmpty() {
		AssertHelper.assertEmpty("foo", new ArrayList<String>());
	}
	
	@Test
	public void assertNotEquals_double() {
		AssertHelper.assertNotEquals("foo", 1.0, 2.0);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertNotEquals_double_fail() {
		AssertHelper.assertNotEquals("foo", 1.0, 1.0);
	}
	
	@Test
	public void assertNotEquals_int() {
		AssertHelper.assertNotEquals("foo", 1, 2);
	}
	
	@Test(expected = AssertionFailedError.class)
	public void assertNotEquals_int_fail() {
		AssertHelper.assertNotEquals("foo", 1, 1);
	}
	
	@Test
	public void shutup_coverage() {
		new AssertHelper();
	}
	
}
