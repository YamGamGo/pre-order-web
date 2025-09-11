package org.example.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class VectorTan<E> implements Iterable<E> {
    private Object[] data;
    private int size;

    public VectorTan() {
        data = new Object[10];
        size = 0;
    }

    public synchronized void add(E element) {
        ensureCapacity(); //현재 저장 공간(data 배열)의 용량이 충분한지 확인하고,부족하면 배열 크기를 늘려서 저장 공간을 확보.
        data[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public synchronized E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    public synchronized E remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked") E removed = (E) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
        return removed;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    private synchronized void ensureCapacity() {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index out of range: " + index);
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;

            public boolean hasNext() {
                return cursor < size;
            }

            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) data[cursor++];
            }
        };
    }
}

