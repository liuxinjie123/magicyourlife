package com.miracle;

import java.util.Scanner;
import java.util.Arrays;

public class Rank {
	public static void main(String[] args) {
		int[] num = new int[5];
		System.out.print("ÇëÊäÈë5¸öÊı×Ö£º");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 5; i++) {
			num[i] = sc.nextInt();
		}
		Arrays.sort(num);
		for (int i : num) {
			System.out.print(i + " ");
		}
		return;
	}
}
