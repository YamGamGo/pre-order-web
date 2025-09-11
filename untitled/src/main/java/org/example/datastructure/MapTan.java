package org.example.datastructure;

import java.util.Objects;

public class MapTan<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MapTan() {
        table = new Entry[10]; // 고정 배열
        size = 0;
    }

    public void put(K key, V value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(table[i].key, key)) {
                table[i].value = value;
                return;
            }
        }
        table[size++] = new Entry<>(key, value);
    }

    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(table[i].key, key)) {
                return table[i].value;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(table[i].key, key)) return true;
        }
        return false;
    }

    public void remove(K key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(table[i].key, key)) {
                for (int j = i; j < size - 1; j++) {
                    table[j] = table[j + 1];
                }
                table[--size] = null;
                return;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
