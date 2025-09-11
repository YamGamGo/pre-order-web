package org.example.datastructure;

import java.util.Objects;

public class HashMapTan<K, V> {
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K k, V v, Node<K, V> n) {
            key = k;
            value = v;
            next = n;
        }
    }

    private final int CAPACITY = 16;
    private Node<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMapTan() {
        table = new Node[CAPACITY];
    }

    public void put(K key, V value) {
        int index = index(key);
        Node<K, V> head = table[index];

        for (Node<K, V> node = head; node != null; node = node.next) {
            if (Objects.equals(node.key, key)) {
                node.value = value;
                return;
            }
        }

        Node<K, V> newNode = new Node<>(key, value, head);
        table[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = index(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public void remove(K key) {
        int index = index(key);
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                if (prev == null) table[index] = node.next;
                else prev.next = node.next;
                size--;
                return;
            }
            prev = node;
            node = node.next;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int index(K key) {
        return (key == null ? 0 : key.hashCode() & 0x7fffffff) % CAPACITY;
    }
}

