package org.example.datastructure;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackTan<E> implements Iterable<E> {
    private Object[] data;
    private int size;

    public StackTan() {
        data = new Object[10];
        size = 0;
    }

    public void push(E item) {
        ensureCapacity();
        data[size++] = item;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) throw new EmptyStackException();
        E value = (E) data[--size];
        data[size] = null;
        return value;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) throw new EmptyStackException();
        return (E) data[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = size - 1;

            public boolean hasNext() {
                return index >= 0;
            }

            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) data[index--];
            }
        };
    }
}

