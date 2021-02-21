package com.miracle;

import java.util.Scanner;

public final class Main {	
	public static void main(String[] args) {
		char[] buf = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

		Scanner scanner = new Scanner(System.in);
		String str = scanner.next();
		
		int count = 0;
		int num = 0;
		for (int i=0; i<str.length();) {
			if (str.charAt(i) == buf[num]) {
				num++;
				i++;
				boolean success = false;
				while (i<str.length() && num <= 25) {
					if (str.charAt(i) == buf[num]) {
						i++;
						num++;
						if (buf[num] == 'z') {
							success = true;
							break;
						}
					} else {
						num++;
						if (num > 25) {
							break;
						}
						count++;
					}					
				}
				if (!success) {
					if (str.charAt(str.length()-1) != 'z') {
						count += (int)buf[25] - (int)str.charAt(str.length()) - 1;
					}
				}
			} 
		}
		System.out.println(count);
	}	
}
