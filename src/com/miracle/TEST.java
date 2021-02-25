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
        
        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(2);
        
        System.out.println("\n");
        
        Iterator<Integer> it1 = set1.iterator();
        int num1 = it1.next();
        int num2 = it1.next();
        Iterator<Integer> it2 = set2.iterator();
        int num3 = it2.next();
        int num4 = it2.next();
        
        if ((num1 == num3 && num2 == num4) || (num1 == num4 && num2 == num3)) {
        	System.out.println("TRUE");
        } else {
        	System.out.println("FALSE");
        }
        
    }
}
