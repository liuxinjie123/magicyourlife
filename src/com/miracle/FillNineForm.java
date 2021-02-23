package com.miracle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class FillNineForm {
    static int size = 6;
    static Node[][] buf = new Node[size][size];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                Node node = new Node();
                node.row = i;
                node.column = j;
                String str = scanner.next();
                if (str.contains("/")) {
                    String[] nums = str.split("/");
                    node.type = 2;
                    node.left = nums[0].equals("-") ? 0: Integer.valueOf(nums[0]);
                    node.right = nums[1].equals("-") ? 0: Integer.valueOf(nums[1]);
                    buf[i][j] = node;
                } else {
                    node.type = 1;
                    node.value = str.equals("-") ? 0:Integer.valueOf(str).intValue();
                    buf[i][j] = node;
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

        int count = 0;
        while (count < 36) {
            count = 0;
            for (int i=0; i<size; i++) {
                for (int j=0; j<size; j++) {
                    if (buf[i][j].isNotEmpty()) {
                        count++;
                    } else {
                        buf[i][j] = findRightValue(buf[i][j]);
                        if (buf[i][j].isNotEmpty()) {
                            count++;
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
        Set<Integer> temp = new HashSet<Integer>(){{
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
        // 删除同行中已存在的数字
        for (int i=0; i<size; i++) {
            if (i == node.column) {
                continue;
            }
            Node newNode = buf[node.row][i];
            if (newNode.isNotEmpty()) {
                if (1 == newNode.type) {
                    temp.remove(newNode.value);
                } else {
                    temp.remove(newNode.left);
                    temp.remove(newNode.right);
                }
            } else {
                if (2 == newNode.type) {
                    if (0 != newNode.left) {
                        temp.remove(newNode.left);
                    }
                    if (0 != newNode.right) {
                        temp.remove(newNode.right);
                    }
                }
            }
        }
        // 删除同列的元素
        for (int i=0; i<size; i++) {
            if (i == node.row) {
                continue;
            }
            Node newNode = buf[i][node.column];
            if (newNode.isNotEmpty()) {
                if (1 == newNode.type) {
                    temp.remove(newNode.value);
                } else {
                    temp.remove(newNode.left);
                    temp.remove(newNode.right);
                }
            } else {
                if (2 == newNode.type) {
                    if (0 != newNode.left) {
                        temp.remove(newNode.left);
                    }
                    if (0 != newNode.right) {
                        temp.remove(newNode.right);
                    }
                }
            }
        }

        // 删除小方框中的元素
        int left = node.row <= 1 ? 0 : (node.row <= 3 ? 2 : 4);
        int right = node.column <= 2 ? 0 : 3;

        for (int m=left; m<left+2; m++) {
            for (int n=right; n<right+3; n++) {
                Node newNode = buf[m][n];
                if (newNode.isNotEmpty()) {
                    if (1 == newNode.type) {
                        temp.remove(newNode.value);
                    } else {
                        temp.remove(newNode.left);
                        temp.remove(newNode.right);
                    }
                } else {
                    if (2 == node.type) {
                        if (0 != newNode.left) {
                            temp.remove(newNode.left);
                        }
                        if (0 != newNode.right) {
                            temp.remove(newNode.right);
                        }
                    }
                }
            }
        }

        if (node.type == 1 && temp.size() == 1) {
            Iterator it = temp.iterator();
            node.value = (int) it.next();
            return node;
        } else if (node.type == 2) {
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
                    if (0 == node.left && small < node.right && large > node.right) {
                        node.left = small;
                        return node;
                    } else if (0 == node.right && small < node.left && large > node.left) {
                        node.right = large;
                        return node;
                    }
                }
            } else {
                int small = 0;
                int large = 0;
                int smallTimes = 0;
                int largeTimes = 0;

                for (int item : temp) {
                    if (0 != node.left && item > node.left) {
                        large = item;
                        largeTimes++;
                    }
                    if (0 != node.right && item < node.right) {
                        small = item;
                        smallTimes++;
                    }
                }
                if (0 == node.left && 0 != node.right && smallTimes == 1) {
                    node.left = small;
                    return node;
                } else if (0 != node.left && 0 == node.right && largeTimes == 1) {
                    node.right = large;
                    return node;
                }
            }
        }
        return node;
    }
}
