package org.example.datastructure;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class PriorityQueueTan<E extends Comparable<E>> {
    private Object[] heap;
    private int size;

    public PriorityQueueTan() {
        heap = new Object[10];
        size = 0;
    }

    public void add(E e) {
        ensureCapacity();
        heap[size] = e;
        upHeap(size);
        size++;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return (E) heap[0];
    }

    @SuppressWarnings("unchecked")
    public E poll() {
        if (isEmpty()) throw new NoSuchElementException();
        E result = (E) heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        downHeap(0);
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    @SuppressWarnings("unchecked")
    private void upHeap(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (((E) heap[idx]).compareTo((E) heap[parent]) >= 0) break;
            swap(idx, parent);
            idx = parent;
        }
    }

    @SuppressWarnings("unchecked")
    private void downHeap(int idx) {
        while (idx * 2 + 1 < size) {
            int left = idx * 2 + 1;
            int right = idx * 2 + 2;
            int smaller = left;

            if (right < size && ((E) heap[right]).compareTo((E) heap[left]) < 0) {
                smaller = right;
            }

            if (((E) heap[idx]).compareTo((E) heap[smaller]) <= 0) break;
            swap(idx, smaller);
            idx = smaller;
        }
    }

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}

