package com.miracle;

import java.util.Scanner;

public class FillForm {
	public static void main(String[] args) {
		int m, n;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		m = scanner.nextInt();
		n = scanner.nextInt();
		int[][] intA = new int[m][n];
		int start = 1;
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				if (0 == j) {
					if (0 == i) {
						intA[i][j] = 1;
					} else {
						intA[i][j] = intA[i-1][j] + 1;
					}
				} else {
					intA[i][j] = intA[i][j-1] + m;
				}
			}
		}
		
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				if (j == n-1) {
					System.out.print(intA[i][j]);
				} else {
					System.out.print(intA[i][j] + " ");
				}
			}
			System.out.println();
		}
		return;
	}	
}
