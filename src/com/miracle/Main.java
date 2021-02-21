package com.miracle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Main {
	
	public static void main(String[] args) {
		int n;
		Scanner scanner = new Scanner(System.in);
		n = scanner.nextInt();
		
		int[] a = new int[n];
		for (int i=0; i<n; i++) {
			a[i] = scanner.nextInt();
		}
		for (int k=0; k<n; k++) {
			Node node = getNode(a[k]);
			List<Node> list = getNodeList(node);
			System.out.println("Case " + (k+1) + ": " + list.get(0).lValue + "/" + list.get(0).rValue);
		}
		return;
	}
	
	private static Node getNode(int n) {
		int a = log(n, 2);
		if (Math.pow(2, a) == n) {
			return new Node(a+1, 1);
		} else {
			return new Node(a+1, n-(int)Math.pow(2, a) + 1);
		}
	}
	
	private static Node getParent(Node node) {
		Node parent = new Node();
		parent.left = node.left-1;
		parent.right = node.right % 2 == 0 ? node.right / 2 : node.right/2 + 1;
		return parent;
	}
	
	private static List<Node> getNodeList(Node node) {
		List<Node> list = new ArrayList();
		list.add(node);
		while (!(node.left==1 && node.right==1)) {
			node = getParent(node);
			list.add(node);
		}
		for (int i=list.size()-1; i>=0; i--) {
			if (i == list.size() - 1) {
				list.get(i).lValue = 1;
				list.get(i).rValue = 1;
			} else {
				if (list.get(i).right % 2 == 1) {
					list.get(i).lValue = list.get(i+1).lValue;
					list.get(i).rValue = list.get(i+1).lValue + list.get(i+1).rValue;
				} else {
					list.get(i).lValue = list.get(i+1).lValue + list.get(i+1).rValue;
					list.get(i).rValue = list.get(i+1).rValue;
				}
			}
		}
		return list;
	}
	

	private static int log(int num, int base) {
		int res = 0;
		while (num >= 2) {
			num = num / 2;
			res++;
		}
		return res;
	}
	
	private static class Node {
		int left;
		int right;
		int lValue = 1;
		int rValue = 1;
		public Node() {}
		public Node(int left, int right) {
			this.left = left;
			this.right = right;
		}
	}	

}
