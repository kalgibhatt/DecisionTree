package spiffy.core.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class DateHelperTest {
	
	@SuppressWarnings("deprecation")
	@Test
	public void should_create_date() {
		assertEquals("2007/4/16", new Date(2007 - 1900, 3, 16), DateHelper.date(2007, 4, 16));
		assertEquals("2007/4/16", new Date(2007 - 1900, 3, 16, 2, 3), DateHelper.date(2007, 4, 16, 2, 3, 0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void should_fail_create_date_on_lenient_date() {
		DateHelper.date(2007, 13, 33);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void should_fail_create_date_on_lenient_date_hour_min_sec() {
		DateHelper.date(2007, 13, 33, 0, 0, 0);
		
	}
	
	@Test
	public void shutup_coverage() {
		new DateHelper();
	}
	
}
