package com.miracle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TEST {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        System.out.println(" size: " + set1.size());

        set1.remove(2);

        System.out.println(" size: " + set1.size());

        Iterator it = set1.iterator();
        System.out.println(it.next());
        System.out.println(it.next());

        for (int item : set1) {
            System.out.println(" item: " + item);
        }
    }
}
