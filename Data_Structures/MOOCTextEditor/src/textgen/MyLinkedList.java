package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {

		// Implement this method
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		size = 0;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 * @return boolean
	 */
	public boolean add(E element) {
		// Implement this method
		if(element == null)
			throw new NullPointerException();

		LLNode<E> lastNode = tail.prev;

		LLNode<E> node = new LLNode<>(element, lastNode, tail);
		lastNode.next = node;
		tail.prev = node;

		size++;
		return true;
	}

	/** Get the element at position index 
	 *
	 * @param index where element is to get
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 * @return E element
	 **/
	public E get(int index) {
		// Implement this method
		return getNode(index).data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param index where the element should be added
	 * @param element The element to add
	 * @throws IndexOutOfBoundsException if index not in list
	 * @throws NullPointerException if element is null
	 */
	public void add(int index, E element ) {
		// Implement this method
		if(element == null)
			throw new NullPointerException();

		if(index == 0 && size == 0) {
			add(element);
			return;
		}

		LLNode<E> currentNode = getNode(index);
		LLNode<E> previousNode = currentNode.prev;

		LLNode<E> node = new LLNode<>(element, previousNode, currentNode);
		previousNode.next = node;
		currentNode.prev = node;

		size++;
	}


	/**
	 * Return the size of the list
	 **/
	public int size() {
		// Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// Implement this method
		LLNode<E> currentNode = getNode(index);
		LLNode<E> previousNode = currentNode.prev;
		LLNode<E> nextNode = currentNode.next;

		previousNode.next = nextNode;
		nextNode.prev = previousNode;

		size--;
		return currentNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 * @throws NullPointerException if element is null
	 */
	public E set(int index, E element) {
		// Implement this method
		if(element == null)
			throw new NullPointerException();

		LLNode<E> node = getNode(index);
		E returnData = node.data;

		node.data = element;
		return returnData;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @return The node at the index
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	private LLNode<E> getNode(int index) {
		// check if index in range first
		if(index > size - 1 || index < 0)
			throw new IndexOutOfBoundsException();

		// assign appropriate nodes
		LLNode<E> prevNode = head;
		LLNode<E> currentNode;

		// loop through nodes until right one is obtained
		for(int position = 0; position < size; position++) {
			currentNode = prevNode.next;
			if(position == index) {
				return currentNode;
			}
			prevNode = currentNode;
		}
		return prevNode;
	}
}

class LLNode<E> {

	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// Implement: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {

		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> prev, LLNode<E> next) {

		this.data = e;
		this.prev = prev;
		this.next = next;
	}

}
