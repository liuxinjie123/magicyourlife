package com.miracle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public final class Main {
	static int size = 6;
	static Node[][] buf = new Node[size][size];

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String content = scanner.next();
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				Node node = new Node();
				node.row = i;
				node.column = j;
				String str = scanner.next();
				if (str.contains("/")) {
					String[] nums = str.split("/");
					node.type = 2;
					node.left = nums[0].equals("-") ? 0: Integer.valueOf(nums[0]).intValue();
					node.right = nums[1].equals("-") ? 0: Integer.valueOf(nums[1]).intValue();
					buf[i][j] = node;
				} else {
					node.type = 1;
					node.value = str.equals("-") ? 0:Integer.valueOf(str).intValue();
					buf[i][j] = node;
				}
			}
		}

		int count = 26;
		while (count > 0) {
			for (int i=0; i<size; i++) {
				for (int j=0; j<size; j++) {
					Node node = buf[i][j];
					if (!node.isNotEmpty()) {
						node = findRightValue(node);
						buf[i][j] = node;
						if (node.isNotEmpty()) {
							count--;
						}
					}
				}
			}
		}


		System.out.println();
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				Node node = buf[i][j];
				if (j != size - 1) {
					if (node.type == 1) {
						System.out.print(node.value + " ");
					} else {
						System.out.print(node.left + "/" + node.right + " ");
					}
				} else {
					if (node.type == 1) {
						System.out.print(node.value);
					} else {
						System.out.print(node.left + "/" + node.right);
					}
				}
			}
			System.out.println();
		}

		return;
	}

	static class Node {
		int row;
		int column;
		// 1 一个值， 2， 2个值，分左右值
		int type;
		int value;
		int left;
		int right;
		boolean isNotEmpty() {
			return (type==1 && value != 0) || (type==2 && left != 0 && right != 0);
		}

	}

	static Node findRightValue(Node node) {
		Set<Integer> valSet = new HashSet<Integer>(){{
			add(1);
			add(2);
			add(3);
			add(4);
			add(5);
			add(6);
			add(7);
			add(8);
			add(9);
		}};
		if (node.isNotEmpty()) {
			return node;
		}
		Set<Integer> temp = valSet;
		for (int i=0; i<size; i++) {
			if (i == node.column) {
				continue;
			}
			if (buf[node.row][i].isNotEmpty()) {
				if (1 == node.type) {
					temp.remove(node.value);
				} else {
					temp.remove(node.left);
					temp.remove(node.right);
				}
			} else {
				if (0 != node.left) {
					temp.remove(node.left);
				}
				if (0 != node.right) {
					temp.remove(node.right);
				}
			}
		}
		for (int i=0; i<size; i++) {
			if (i == node.row) {
				continue;
			}
			if (buf[i][node.column].isNotEmpty()) {
				if (1 == node.type) {
					temp.remove(node.value);
				} else {
					temp.remove(node.left);
					temp.remove(node.right);
				}
			} else {
				if (0 != node.left) {
					temp.remove(node.left);
				}
				if (0 != node.right) {
					temp.remove(node.right);
				}
			}
		}
		if (node.type == 1) {
			if (temp.size() == 1) {
				Iterator it = temp.iterator();
				node.value = (int) it.next();
				return node;
			}
		} else if (node.type == 2) {
			if (0 != node.left) {
				temp.remove(node.left);
			}
			if (0 != node.right) {
				temp.remove(node.right);
			}
			if (temp.size() == 1) {
				Iterator it = temp.iterator();
				if (0 == node.left && 0 != node.right) {
					node.left = (int) it.next();
				} else if (0 != node.left && 0 == node.right) {
					node.right = (int) it.next();
				}
				return node;
			} else if (temp.size() == 2) {
				Iterator it = temp.iterator();
				int small = (int) it.next();
				int large = (int) it.next();
				if (small > large) {
					int tempValue = small;
					small = large;
					large = tempValue;
				}
				if (0 == node.left && 0 == node.right) {
					node.left = small;
					node.right = large;
					return node;
				} else {
					if (0 == node.left) {
						if (small < node.right && large > node.right) {
							node.left = small;
						}
					} else if (0 == node.left) {
						if (small < node.left && large > node.left) {
							node.right = large;
						}
					}
					return node;
				}
			} else {
				int small = 0;
				int large = 0;
				int smallTimes = 0;
				int largeTimes = 0;

				for (int item : temp) {
					if (item > node.left) {
						large = item;
						largeTimes++;
					}
					if (item < node.right) {
						small = item;
						smallTimes++;
					}
				}
				if (0 == node.left && 0 != node.right && smallTimes == 1) {
					node.left = small;
				} else if (0 != node.left && 0 == node.right && largeTimes == 1) {
					node.right = large;
				}
				return node;
			}
		}
		return node;
	}
}
