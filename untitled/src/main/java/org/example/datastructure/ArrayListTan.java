package org.example.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayListTan<E> implements Iterable<E> {
    private Object[] data;
    private int size;

    public ArrayListTan() {
        data = new Object[10];
        size = 0;
    }

    public void add(E element) {
        ensureCapacity();
        data[size++] = element;
    }

    public void add(int index, E element) {
        checkPositionIndex(index);
        ensureCapacity();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkElementIndex(index);
        return (E) data[index];
    }

    public E remove(int index) {
        checkElementIndex(index);
        @SuppressWarnings("unchecked") E removed = (E) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
        return removed;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) return true;
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) return i;
        }
        return -1;
    }

    public int lastIndexOf(E element) {
        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(element)) return i;
        }
        return -1;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index out of range: " + index);
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index out of range: " + index);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int cursor = 0;

            public boolean hasNext() {
                return cursor < size;
            }

            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                @SuppressWarnings("unchecked")
                E result = (E) data[cursor++];
                return result;
            }
        };
    }
}
