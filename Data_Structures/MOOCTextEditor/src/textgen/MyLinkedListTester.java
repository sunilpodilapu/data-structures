/**
 * 
 */
package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<>();
		longerList = new MyLinkedList<>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}


		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// Implement: Add more tests here
		try {
			list1.remove(-1);
			fail("Removed negative index");
		} catch(IndexOutOfBoundsException e) {}

		try {
			emptyList.remove(0);
			fail("Removed element from empty list");
		} catch (IndexOutOfBoundsException e) {}

		try {
			longerList.remove(20);
			fail("Removed element too large");
		} catch(IndexOutOfBoundsException e) {}


		int mid = longerList.remove(6);
		longerList.add(6, 17);
		assertEquals("Remove: check mid is correct", 6, mid);
		assertEquals("Remove: check element 8 is correct", (Integer)8, longerList.get(8));
		assertEquals("Remove: check size is correct", 10, longerList.size());
		assertEquals("Remove: element at 6 wrong", (Integer)17, longerList.get(6));
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // Implement this test
		try {
			longerList.add(null);
			fail("Null value added somehow");
		} catch(NullPointerException e) {}

		boolean result = list1.add(100);
		assertEquals("Add: did not return true", true, result);
		assertEquals("Add: size should be 4", 4, list1.size());
		assertEquals("Add: Element 0 should be 65", (Integer)65, list1.get(0));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// Implement this test
		assertEquals("Size: empty list didn't return 0", 0, emptyList.size());
		assertEquals("Size: long list size failed", LONG_LIST_LENGTH, longerList.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // Implement this test
		try {
			emptyList.add(-1, 2);
			fail("Added negative index");
		} catch(IndexOutOfBoundsException e) {}

		try {
			emptyList.add(1, 2);
			fail("Added index too high");
		} catch(IndexOutOfBoundsException e) {}

		try {
			list1.add(2, null);
			fail("Add a null value somehow");
		} catch(NullPointerException e) {}

		list1.add(0, 1);
		assertEquals("AddIndex: Index 0 should be 1", (Integer)1, list1.get(0));
		assertEquals("AddIndex: Size should be 4", 4, list1.size());
		assertEquals("AddIndex: Index 3 should be 42", (Integer)42, list1.get(3));

		longerList.add(0, 33);
		assertEquals("AddIndex: Index 0 should be 33", (Integer)33, longerList.get(0));
		assertEquals("AddIndex: Size should be 11", 11, longerList.size());
		assertEquals("AddIndex: Index 10 should be 9", (Integer) 9, longerList.get(10));

		int element = longerList.remove(0);
		int removed = longerList.remove(4);
		longerList.add(4, 14);
		assertEquals("AddIndex: Index 4 should be 14", (Integer)14, longerList.get(4));
		assertEquals("AddIndex: Size should be 10", 10, longerList.size());
		assertEquals("AddIndex: Index 9 should be 9", (Integer)9, longerList.get(9));
		assertEquals("AddIndex: Element should be 33", 33, element);
		assertEquals("AddIndex: Element should be 4", 4, removed);

		emptyList.add(0, 5);
		assertEquals("AddIndex: Index 0 should be 5", (Integer)5, emptyList.get(0));
		assertEquals("AddIndex: Size should be 1", 1, emptyList.size());
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // Implement this test
		try {
			list1.set(-1, 34);
			fail("Set negative index");
		} catch(IndexOutOfBoundsException e) {}

		try {
			list1.set(4, 4);
			fail("Set index out of range");
		} catch(IndexOutOfBoundsException e) {}

		try {
			emptyList.set(0, 1);
			fail("Set empty list element");
		} catch(IndexOutOfBoundsException e) {}

		try {
			list1.set(1, null);
			fail("Set to null value somehow");
		} catch(NullPointerException e) {}

		int old = list1.set(1, 100);
		assertEquals("Set: Check element 1", (Integer)100, list1.get(1));
		assertEquals("Set: Check element removed", 21, old);
	}
	
}
