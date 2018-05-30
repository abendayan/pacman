package utils;

import java.util.*;

/*
    A counter keeps track of counts for a set of keys
    It's an extension of the HashMap of java
 */
public class Counter<T> {
    public final Map<T, Float> counts = new HashMap<>();
    Random rand = new Random();

    public void add(T t) {
        counts.merge(t, 1f, Float::sum);
    }

    public void addNumberTime(T t, float number) {
        counts.put(t, number);
    }

    public void addNumberExist(T t, float number) {
        try {

            counts.put(t, counts.get(t) + number);
        }
        catch (java.lang.NullPointerException e) {
            addNumberTime(t, number);
        }
    }

    public float count(T t) {
        return counts.getOrDefault(t, 0f);
    }

    public int size() {
        return counts.size();
    }

    public T sample() {
        List<Float> values = new ArrayList<>(counts.values());
        List<T> keys = new ArrayList<>(counts.keySet());
        float choice = new Random().nextFloat();
        Float total = values.get(0);
        int i = 0;
        while(choice > total && i < values.size() - 1) {
            i++;
            total += values.get(i);
        }
        return keys.get(i);
    }
}