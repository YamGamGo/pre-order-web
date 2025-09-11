package org.example.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SetTan<E> implements Iterable<E> {
    private Object[] data;
    private int size;

    public SetTan() {
        data = new Object[10];
        size = 0;
    }

    // 중복체크 후 추가
    public boolean add(E element) {
        if (contains(element)) {
            return false;  // 이미 있으면 추가 안 함
        }

        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }

        data[size++] = element;
        return true;
    }

    // 포함 여부 검사
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) return true;
        }
        return false;
    }

    // 요소 삭제
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                for (int j = i; j < size -1; j++) {
                    data[j] = data[j + 1];
                }
                data[--size] = null;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) data[i] = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) data[cursor++];
            }
        };
    }
}
