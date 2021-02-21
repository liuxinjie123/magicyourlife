package com.miracle;

import java.util.Scanner;

public class BinaryTree {
	public static void main(String[] args) {
		double n;
		Scanner scanner = new Scanner(System.in);
		n = Double.valueOf(scanner.nextInt());
		for (int i=1; i<=n; i++) {
			for (int j=1; j<Math.pow(2, i); j++) {
				if (i == 1 && j == 1) {
					System.out.println("case " + i + ":" + 1);
				} else {
					Place place = new Place(i, j);
					Place parent = getParentPlace(place);
					if (j%2 == 1) {
						System.out.println("case " + i + ":" + parent.left + "/" + (parent.left + parent.right));
					} else {
						System.out.println("case " + i + ":" + (parent.left + parent.right) + "/" + parent.right);
					}
				}
			}
		}
		return;
	}	
	
	private static Place getPlace(double n) {
		Place place = new Place();
		for (int i=1; i<=n; i++) {
			if (n == Math.pow(2, i)-1) {
				place.left = i;
				place.right = Math.pow(2, i-1);
				return place;
			} else if (n > Math.pow(2, i)-1 && n < Math.pow(2, i+1)-1) {
				place.left = i+1;
				place.right = n - Math.pow(2, i)+1;
				return place;
			}
		}
		return place;
	}
	
	private static Place getParentPlace(Place place) {
		Place parent = new Place();
		parent.left = place.left-1;
		parent.right = place.right % 2 == 0 ? place.right / 2 : place.right/2 + 1;
		return place;
	}
	
	private static Place getLeftSonPlace(Place place) {
		Place son = new Place();
		son.left = place.left + 1;
		son.right = 2*place.right - 1;
		return son;
	}
	
	private static Place getRightSonPlace(Place place) {
		Place son = new Place();
		son.left = place.left + 1;
		son.right = 2*place.right;
		return son;
	}

	static class Place {
		double left;
		double right;
		public Place() {}
		public Place(int left, int right) {
			this.left = left;
			this.right = right;
		}
	}
}
