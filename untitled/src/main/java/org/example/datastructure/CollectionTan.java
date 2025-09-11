package org.example.datastructure;

import java.util.Collection;
import java.util.Iterator;

public class CollectionTan implements Collection<String> {
    private String[] data = new String[10];
    private int size = 0;

    @Override
    public boolean add(String s) {
        data[size++] = s;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public String next() {
                return data[index++];
            }
        };
    }

    @Override public boolean isEmpty() { return size == 0; }
    @Override public void clear() { size = 0; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] a) { return null; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean addAll(Collection<? extends String> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
}
