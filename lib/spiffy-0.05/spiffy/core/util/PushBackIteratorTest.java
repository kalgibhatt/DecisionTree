package spiffy.core.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PushBackIteratorTest {
	final List<String> l = CollectionHelper.arrayList("1", "2", "3");
	
	@Test(expected = RuntimeException.class)
	public void remove_should_not_work() {
		final PushBackIterator<String> iterator = new PushBackIterator<String>(l.iterator());
		iterator.remove();
	}
	
	@Test
	public void should_iterate_as_normal() {
		
		int nextCallCounter = 0;
		for(final PushBackIterator<String> iterator = new PushBackIterator<String>(l.iterator()); iterator.hasNext();) {
			iterator.next();
			nextCallCounter++;
		}
		
		Assert.assertEquals("no pushback", l.size(), nextCallCounter);
	}
	
	@Test(expected = IllegalStateException.class)
	public void should_not_pushback_before_next_call() {
		final PushBackIterator<String> iterator = new PushBackIterator<String>(l.iterator());
		iterator.pushBack();
	}
	
	@Test(expected = IllegalStateException.class)
	public void should_only_pushback_once_in_a_row() {
		final PushBackIterator<String> iterator = new PushBackIterator<String>(l.iterator());
		iterator.next();
		iterator.pushBack();
		iterator.pushBack();
	}
	
	@Test
	public void should_push_back_an_element() {
		
		int nextCallCounter = 0;
		boolean hasPushedBack = false;
		for(final PushBackIterator<String> iterator = new PushBackIterator<String>(l.iterator()); iterator.hasNext();) {
			final String s = iterator.next();
			nextCallCounter++;
			if( s.equals("2") && hasPushedBack == false ) { // avoid infinite
				// loop by only pushing back once
				iterator.pushBack();
				hasPushedBack = true;
			}
		}
		
		Assert.assertEquals("one pushback = one extra next call", l.size() + 1, nextCallCounter);
	}
	
	@Test
	public void should_push_back_last_elem() {
		int nextCallCounter = 0;
		boolean hasPushedBack = false;
		for(final PushBackIterator<String> iterator = new PushBackIterator<String>(l.iterator()); iterator.hasNext();) {
			final String s = iterator.next();
			nextCallCounter++;
			if( s.equals("3") && hasPushedBack == false ) { // avoid infinite loop by only pushing back once
				iterator.pushBack();
				hasPushedBack = true;
			}
		}
		
		Assert.assertEquals("one pushback = one extra next call", l.size() + 1, nextCallCounter);
	}
	
}
