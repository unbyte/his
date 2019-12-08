package lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MyHashMap<K, V> implements Map<K, V> {
    private Entry<K, V>[] buckets;
    private int capacity; // 16

    private int size = 0;

    private final double lf = 0.75;

    public MyHashMap() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[this.capacity];
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        if (size == lf * capacity) {
            // 重哈希
            Entry<K, V>[] old = buckets;

            capacity *= 2;
            size = 0;
            buckets = new Entry[capacity];

            for (Entry<K, V> e : old) {
                while (e != null) {
                    put(e.key, e.value);
                    e = e.next;
                }
            }
        }
        Entry<K, V> entry = new Entry<>(key, value, null);

        int bucket = getHash(key) % getBucketSize();

        Entry<K, V> existing = buckets[bucket];
        // 看看key是不是已经存在了
        if (existing == null) {
            buckets[bucket] = entry;
            size++;
        } else {
            while (existing.next != null) {
                if (existing.key.equals(key)) {
                    existing.value = value;
                    return value;
                }
                existing = existing.next;
            }

            if (existing.key.equals(key)) {
                existing.value = value;
            } else {
                existing.next = entry;
                size++;
            }
        }
        return value;
    }

    // 未实现
    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    // 未实现
    @Override
    public void clear() {

    }

    // 未实现
    @Override
    public Set<K> keySet() {
        return Collections.emptySet();
    }

    // 未实现
    @Override
    public Collection<V> values() {
        return Collections.emptyList();
    }

    // 未实现
    @Override
    @SuppressWarnings("unsafe")
    public Set<Map.Entry<K, V>> entrySet() {
        return Collections.emptySet();
    }

    // 未实现
    @Override
    public boolean equals(Object o) {
        return false;
    }

    // 未实现
    @Override
    public int hashCode() {
        return 0;
    }

    // 未实现
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    // 未实现
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    // 未实现
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    }

    // 未实现
    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }

    // 未实现
    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    // 未实现
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    // 未实现
    @Override
    public V replace(K key, V value) {
        return null;
    }

    // 未实现
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    // 未实现
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    // 未实现
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    // 未实现
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        Entry<K, V> bucket = buckets[getHash((K) key) % getBucketSize()];

        while (bucket != null) {
            if (Objects.equals(key, bucket.key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object key) {
        int bucket = getHash((K) key) % getBucketSize();

        Entry<K, V> existing = buckets[bucket];
        // 看看key是不是已经存在了
        return existing != null;
    }

    @Override
    // 未实现
    public boolean containsValue(Object value) {
        return false;
    }

    private int getBucketSize() {
        return buckets.length;
    }

    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : buckets) {
            sb.append("[");
            while (entry != null) {
                sb.append(entry);
                if (entry.next != null) {
                    sb.append(", ");
                }
                entry = entry.next;
            }
            sb.append("]");
        }
        return "{" + sb.toString() + "}";
    }

    @AllArgsConstructor
    @Getter
    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;

            if (obj instanceof Entry) {
                Entry entry = (Entry) obj;

                return key.equals(entry.getKey()) &&
                        value.equals(entry.getValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 13;
            hash = 17 * hash + ((key == null) ? 0 : key.hashCode());
            hash = 17 * hash + ((value == null) ? 0 : value.hashCode());
            return hash;
        }

        @Override
        public String toString() {
            return "{" + key + ", " + value + "}";
        }
    }
}