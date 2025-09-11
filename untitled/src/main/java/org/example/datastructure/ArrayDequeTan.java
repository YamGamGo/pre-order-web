package org.example.datastructure;

import java.util.NoSuchElementException;

public class ArrayDequeTan<E> {
    private Object[] data;
    private int front;
    private int rear;
    private int size;

    public ArrayDequeTan() {
        data = new Object[16];
        front = 0;
        rear = 0;
        size = 0;
    }

    private int capacity() {
        return data.length;
    }

    private void ensureCapacity() {
        if (size == capacity()) {
            Object[] newData = new Object[capacity() * 2];
            for (int i = 0; i < size; i++) {
                newData[i] = data[(front + i) % capacity()];
            }
            data = newData;
            front = 0;
            rear = size;
        }
    }

    public void addFirst(E e) {
        ensureCapacity();
        front = (front - 1 + capacity()) % capacity();
        data[front] = e;
        size++;
    }

    public void addLast(E e) {
        ensureCapacity();
        data[rear] = e;
        rear = (rear + 1) % capacity();
        size++;
    }

    @SuppressWarnings("unchecked")
    public E removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        E value = (E) data[front];
        data[front] = null;
        front = (front + 1) % capacity();
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public E removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        rear = (rear - 1 + capacity()) % capacity();
        E value = (E) data[rear];
        data[rear] = null;
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public E peekFirst() {
        if (isEmpty()) return null;
        return (E) data[front];
    }

    @SuppressWarnings("unchecked")
    public E peekLast() {
        if (isEmpty()) return null;
        int lastIndex = (rear - 1 + capacity()) % capacity();
        return (E) data[lastIndex];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

