package com.miracle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static int size = 6;
    static Node[][] buf = new Node[size][size];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i=0; i<size; i++) {
        	int k=0;
            for (int j=0; j<size; j++) {
                Node node = new Node();
                node.row = i;
                node.column = j;
                String str = scanner.next();
                if (str.contains("/")) {
                    String[] nums = str.split("/");
                    node.type = 2;
                    int left = nums[0].equals("-") ? 0: Integer.valueOf(nums[0]);
                    int right = nums[1].equals("-") ? 0: Integer.valueOf(nums[1]);
                    node.left = left;
                    node.right = right;
                    buf[i][j] = node;              
                } else {
                    node.type = 1;
                    int value = str.equals("-") ? 0:Integer.valueOf(str);
                    node.value = value;
                    buf[i][j] = node;
                }
            }
        }

        int count = 0;
        int times = 0;
        while (count < 36) {
            count = 0;
            times++;
            for (int i=0; i<size; i++) {
                for (int j=0; j<size; j++) {
                    if (buf[i][j].isNotEmpty()) {
                        count++;
                    } else {
                        buf[i][j] = findRightValue(buf[i][j], times);
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
        // 1-单个数， 2-2个数
        int type;
        int value;
        int left;
        int right;
        boolean isNotEmpty() {
            return (type==1 && value != 0) || (type==2 && left != 0 && right != 0);
        }
        Set<Integer> availValues = new HashSet<>();
    }

    static Node findRightValue(Node node, int times) {
        Set<Integer> temp = removeErrorOne(node);
        
        if (node.type == 1) {
        	if (temp.size() == 1) {
        		Iterator it = temp.iterator();
            	node.value = (int) it.next();
            	return node;
        	}
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
//                if (times > 5) {
                	findRightValueTeam(times);
//                }
            }
        }
        return node;
    }
    

    private static Set<Integer> removeErrorOne(Node node) {
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
    	// 删除同行已存在的数据
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
        // 删除同列已存在的数据
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

        // 删除 3*2 方框中的数据
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
        }

        // type=2时，删除大于右边的值 或 小于左边的值
        if (2 == node.type) {
            if (0 != node.left && 0 == node.right) {
                for (int i=1; i<node.left; i++) {
                    temp.remove(i);
                }
            }
            if (0 == node.left && 0 != node.right) {
                for (int i=node.right+1; i<=9; i++) {
                    temp.remove(i);
                }
            }
        }
        return temp;
	}

	private static void findRightValueTeam(int cycles) {
        look:
        for (int m=0; m<6; m+=2) {
        	for (int n=0; n<6; n+=3) {
                Set<Integer> temp1 = new HashSet<Integer>(){{
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

        		for (int i=m; i<m+2; i++) {
                	for (int j=n; j<n+3; j++) {
                		Node newNode = buf[i][j];
                		if (!newNode.isNotEmpty()) {
                			newNode.availValues = removeErrorOne(newNode);
                			buf[i][j] = newNode;
                		}
                		if (newNode.type == 1 && newNode.value != 0) {
                			temp1.remove(newNode.value);
                		} else if (newNode.type == 2) {
                			if (newNode.left != 0) {
                				temp1.remove(newNode.left);
                			}
                			if (newNode.right != 0) {
                				temp1.remove(newNode.right);
                			}
                		}
                	}
                }

                for (int item : temp1) {
                    int times = 0;
                	for (int i=m; i<m+2; i++) {
                    	for (int j=n; j<n+3; j++) {
                    		Node newNode = buf[i][j];
                    		if (!newNode.isNotEmpty() && newNode.availValues.contains(item)) {
                    			times++;
                    		}
                    	}
                	}
                	if (times == 1) {
                		for (int i=m; i<m+2; i++) {
                    		for (int j=n; j<n+3; j++) {
                    			Node newNode = buf[i][j];
                    			if (!newNode.isNotEmpty() && newNode.availValues.contains(item)) {
                    				if (newNode.type == 1) {
                    					newNode.value = item;
                                        buf[i][j] = newNode;
                                    } else {
                    					if (0 == newNode.left && 0 != newNode.right) {
                    						newNode.left = item;
                    					} else if (0 != newNode.left && 0 == newNode.right) {
                    						newNode.right = item;
                    					} else if (0 == newNode.left && 0 == newNode.right) {
                    						int maxValue = item;
                    						int minValue = item;
                    						for (int item1 : newNode.availValues) {
                    							if (maxValue < item1) {
                    								maxValue = item1;
                    							}
                    							if (minValue > item1) {
                    								minValue = item1;
                    							}
                    						}
                    						if (maxValue == item) {
                    							newNode.right = item;
                    						}
                    						if (minValue == item) {
                    							newNode.left = item;
                    						}
                    					}
                    					newNode.availValues.remove(item);
                    					buf[i][j] = newNode;            					
                    				}
                                    temp1.remove(item);
                                    break look;
                    			}
                    		}
                    	}
                	}
                }
        	}
        }

        loop:
        for (int i=0; i<size; i++) {
            Set<Integer> temp1 = new HashSet<Integer>(){{
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

            for (int j=0; j<size; j++) {
                Node newNode = buf[j][i];
                if (!newNode.isNotEmpty()) {
                    newNode.availValues = removeErrorOne(newNode);
                }
                buf[j][i] = newNode;
                if (1 == newNode.type && 0 != newNode.value) {
                    temp1.remove(newNode.value);
                } else if (2 == newNode.type) {
                    if (0 != newNode.left) {
                        temp1.remove(newNode.left);
                    }
                    if (0 != newNode.right) {
                        temp1.remove(newNode.right);
                    }
                }
            }

            for (int item : temp1) {
                int times = 0;
                for (int k=0; k<size; k++) {
                    Node newNode = buf[k][i];
                    if (!newNode.isNotEmpty() && newNode.availValues.contains(item)) {
                        times++;
                    }
                }

                if (times == 1) {
                    for (int k = 0; k < size; k++) {
                        Node newNode = buf[k][i];
                        if (!newNode.isNotEmpty() && newNode.availValues.contains(item)) {
                            if (newNode.type == 1) {
                                newNode.value = item;
                                buf[k][i] = newNode;
                            } else {
                                if (0 == newNode.left && 0 != newNode.right) {
                                    newNode.left = item;
                                } else if (0 != newNode.left && 0 == newNode.right) {
                                    newNode.right = item;
                                } else if (0 == newNode.left && 0 == newNode.right) {
                                    int maxValue = item;
                                    int minValue = item;
                                    for (int item1 : newNode.availValues) {
                                        if (maxValue < item1) {
                                            maxValue = item1;
                                        }
                                        if (minValue > item1) {
                                            minValue = item1;
                                        }
                                    }
                                    if (maxValue == item) {
                                        newNode.right = item;
                                    }
                                    if (minValue == item) {
                                        newNode.left = item;
                                    }
                                }
                                newNode.availValues.remove(item);
                                buf[k][i] = newNode;
                            }
                            temp1.remove(item);
                            break loop;
                        }
                    }
                }
            }
        }

        loop1:
        for (int i=0; i<size; i++) {
            Set<Integer> temp1 = new HashSet<Integer>(){{
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

            for (int j=0; j<size; j++) {
                Node newNode = buf[i][j];
                if (!newNode.isNotEmpty()) {
                    newNode.availValues = removeErrorOne(newNode);
                }
                buf[i][j] = newNode;
                if (1 == newNode.type && 0 != newNode.value) {
                    temp1.remove(newNode.value);
                } else if (2 == newNode.type) {
                    if (0 != newNode.left) {
                        temp1.remove(newNode.left);
                    }
                    if (0 != newNode.right) {
                        temp1.remove(newNode.right);
                    }
                }
            }

            for (int item : temp1) {
                int times = 0;
                for (int k=0; k<size; k++) {
                    Node newNode = buf[i][k];
                    if (!newNode.isNotEmpty() && newNode.availValues.contains(item)) {
                        times++;
                    }
                }

                if (times == 1) {
                    for (int k = 0; k < size; k++) {
                        Node newNode = buf[i][k];
                        if (!newNode.isNotEmpty() && newNode.availValues.contains(item)) {
                            if (newNode.type == 1) {
                                newNode.value = item;
                                buf[i][k] = newNode;
                            } else {
                                if (0 == newNode.left && 0 != newNode.right) {
                                    newNode.left = item;
                                } else if (0 != newNode.left && 0 == newNode.right) {
                                    newNode.right = item;
                                } else if (0 == newNode.left && 0 == newNode.right) {
                                    int maxValue = item;
                                    int minValue = item;
                                    for (int item1 : newNode.availValues) {
                                        if (maxValue < item1) {
                                            maxValue = item1;
                                        }
                                        if (minValue > item1) {
                                            minValue = item1;
                                        }
                                    }
                                    if (maxValue == item) {
                                        newNode.right = item;
                                    }
                                    if (minValue == item) {
                                        newNode.left = item;
                                    }
                                }
                                newNode.availValues.remove(item);
                                buf[i][k] = newNode;
                            }
                            temp1.remove(item);
                            break loop1;
                        }
                    }
                }
            }
        }
	}
    
}