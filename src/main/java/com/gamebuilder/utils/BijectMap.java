package com.gamebuilder.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BijectMap<K, V> {
    private final ConcurrentHashMap<K, V> keyToValue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<V, K> valueToKey = new ConcurrentHashMap<>();

    public BijectMap() {
    }

    public BijectMap(BijectMap<K, V> other) {
        Objects.requireNonNull(other, "other must not be null");
        this.keyToValue.putAll(other.keyToValue);
        this.valueToKey.putAll(other.valueToKey);
    }

    public V getByKey(K key) {
        return keyToValue.get(key);
    }

    public K getByValue(V value) {
        return valueToKey.get(value);
    }

    public boolean containsKey(K key) {
        return keyToValue.containsKey(key);
    }

    public boolean containsValue(V value) {
        return valueToKey.containsKey(value);
    }

    public Set<K> keySet() {
        return Set.copyOf(keyToValue.keySet());
    }

    public Collection<V> values() {
        return List.copyOf(keyToValue.values());
    }

    public V put(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(value, "value must not be null");

        // Atomically remove old reverse mapping for the value (if any),
        // then remove old forward mapping for the key (if any), then insert.
        K oldKeyForValue = valueToKey.remove(value);
        if (oldKeyForValue != null && !oldKeyForValue.equals(key)) {
            keyToValue.remove(oldKeyForValue);
        }

        V oldValue = keyToValue.put(key, value);
        if (oldValue != null && !oldValue.equals(value)) {
            valueToKey.remove(oldValue);
        }

        valueToKey.put(value, key);
        return oldValue;
    }

    public void putAll(BijectMap<K, V> other) {
        Objects.requireNonNull(other, "other must not be null");
        for (Map.Entry<K, V> entry : other.keyToValue.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public V removeByKey(K key) {
        Objects.requireNonNull(key, "key must not be null");
        V value = keyToValue.remove(key);
        if (value != null) {
            valueToKey.remove(value);
        }
        return value;
    }

    public K removeByValue(V value) {
        Objects.requireNonNull(value, "value must not be null");
        K key = valueToKey.remove(value);
        if (key != null) {
            keyToValue.remove(key);
        }
        return key;
    }

    public void clear() {
        keyToValue.clear();
        valueToKey.clear();
    }

    public int size() {
        return keyToValue.size();
    }

    public boolean isEmpty() {
        return keyToValue.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BijectMap)) return false;
        BijectMap<?, ?> bijectMap = (BijectMap<?, ?>) o;
        return keyToValue.equals(bijectMap.keyToValue);
    }

    @Override
    public int hashCode() {
        return keyToValue.hashCode();
    }

    @Override
    public String toString() {
        return keyToValue.toString();
    }
}