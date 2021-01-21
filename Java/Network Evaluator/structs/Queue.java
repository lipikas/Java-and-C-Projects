package structs;

import java.util.NoSuchElementException;
public class Queue<T> {
	static class Node<E> {
		E data; 
		Node<E> next;
		Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	private Node<T> rear;
	private int size;

	public Queue() {
		rear = null;
		size = 0;
	}
	public void enqueue(T item) {
		Node<T> ptr = new Node<T>(item, null);
		if (rear == null) {
			ptr.next = ptr;
		} else {
			ptr.next = rear.next;
			rear.next = ptr;
		}
		size++;
		rear = ptr;
	}
	public T dequeue() 
	throws NoSuchElementException {
		if (rear == null) {
			throw new NoSuchElementException("empty queue");
		}
		T hold = rear.next.data;
		if (size == 1) {
			rear = null;
		} else {
			rear.next = rear.next.next;
		}
		size--;
		return hold;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public int size() {
		return size;
	}
	public void clear() {
		rear = null;
		size = 0;
	}
}
