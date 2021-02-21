package com.miracle;

import java.math.BigDecimal;
import java.util.Scanner;

public final class PrintFeibo {

	public static void main(String[] args) {
		StringBuffer buffer = new StringBuffer("");
		int count = 0;
		for (int i = 0; i < 1000;) {
			String res = getFeiBo(count);
			buffer.append(res);
			i += res.length();
			if (i > 1000) {
				break;
			}
			count++;
		}

		int n;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		System.out.println(buffer.toString().substring(0, n));
		return;
	}

	static String getFeiBo(int n) {
		if (n < 2) {
			return String.valueOf(1);
		} else {
			BigDecimal temp1 = BigDecimal.ONE;
			BigDecimal temp2 = BigDecimal.ONE;
			BigDecimal sum = BigDecimal.ZERO;
			for (int i = 2; i <= n; i++) {
				sum = temp1.add(temp2);
				temp1 = temp2;
				temp2 = sum;
			}
			return String.valueOf(sum);
		}
	}

}
