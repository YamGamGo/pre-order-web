package org.example.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTan<E> implements Iterable<E> {

    private class Node {
        E value;
        Node next;

        Node(E value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    public void add(E item) {
        addLast(item);
    }

    public void addFirst(E item) {
        Node node = new Node(item);
        node.next = head;
        head = node;
        if (tail == null) tail = head;
        size++;
    }

    public void addLast(E item) {
        Node node = new Node(item);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public E removeFirst() {
        if (head == null) throw new NoSuchElementException("List is empty");
        E value = head.value;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return value;
    }

    public E removeLast() {
        if (head == null) throw new NoSuchElementException("List is empty");
        if (head.next == null) {
            E value = head.value;
            head = tail = null;
            size--;
            return value;
        }

        Node prev = head;
        while (prev.next != tail) {
            prev = prev.next;
        }
        E value = tail.value;
        tail = prev;
        tail.next = null;
        size--;
        return value;
    }

    public E get(int index) {
        checkIndex(index);
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.value;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node current = head;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}

