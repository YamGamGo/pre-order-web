package org.example.datastructure;

import java.util.NoSuchElementException;

public class QueueTan<E> {
    private Object[] data;
    private int front;  // 첫 번째 요소 위치
    private int rear;   // 다음 요소 넣을 위치
    private int size;

    public QueueTan() {
        data = new Object[10];
        front = 0;
        rear = 0;
        size = 0;
    }

    public boolean enqueue(E element) {
        if (size == data.length) {
            // 배열 크기 늘리기 (원형 큐라 복잡하니 그냥 새 배열로 재배치)
            int newCapacity = data.length * 2;
            Object[] newData = new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[(front + i) % data.length];
            }
            data = newData;
            front = 0;
            rear = size;
        }

        data[rear] = element;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public E dequeue() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");
        E element = (E) data[front];
        data[front] = null;front = (front + 1) % data.length;
        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");
        return (E) data[front];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
