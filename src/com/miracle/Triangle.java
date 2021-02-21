package com.miracle;

import java.util.HashMap;
import java.util.Scanner;

public class Triangle {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		int n;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		n = scanner.nextInt();
		HashMap<String, Integer> map = new HashMap<>();
		for (int i=1; i<=n; i++) {
			for (int j=1; j<=i; j++) {
				String key = i + "_" + j;
				if (j == 1) {
					map.put(key, 1);
					System.out.print(1 + " ");
				} else if (j == i) {
					map.put(key, 1);
					System.out.print(1 + " ");
				} else { 
					String key1 = (i-1) + "_" + (j-1);
					String key2 = (i-1) + "_" + j;
					int value = map.get(key1) + map.get(key2);
					map.put(key, value);
					System.out.print(value + " ");
				}
			}
			System.out.println();
		}
		return;
	}

}
