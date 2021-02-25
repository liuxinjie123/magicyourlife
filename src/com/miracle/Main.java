package com.miracle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static int size = 6;
    static Node[][] buf = new Node[size][size];
    static Set<Integer>[][] avail = new HashSet[size][size];

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
                	removeErrorOne(buf[i][j]);
                }
            }
            
            removeErrorTwo();
            
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
        Set<Integer> temp = avail[node.row][node.column];
        
        if (node.type == 1) {
        	if (temp.size() == 1) {
        		Iterator<Integer> it = temp.iterator();
            	node.value = (int) it.next();
            	return node;
        	}
        } else if (node.type == 2) {
            if (temp.size() == 1) {
                Iterator<Integer> it = temp.iterator();
                if (0 == node.left && 0 != node.right) {
                    node.left = (int) it.next();
                } else if (0 != node.left && 0 == node.right) {
                    node.right = (int) it.next();
                }
                return node;
            } else if (temp.size() == 2) {
                Iterator<Integer> it = temp.iterator();
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
    

    private static void removeErrorOne(Node node) {
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
                if (1 == newNode.type) {
                    if (0 != newNode.value) {
                        temp.remove(newNode.value);
                    }
                } else {
                    if (0 != newNode.left) {
                        temp.remove(newNode.left);
                    }
                    if (0 != newNode.right) {
                        temp.remove(newNode.right);
                    }
                }
            }
        }
        avail[node.row][node.column] = temp;        
    }

    private static void removeErrorTwo() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                for (int k=0; k<size; k++) {
                    if (j != k) {
                        if (!buf[i][j].isNotEmpty() && !buf[i][k].isNotEmpty() && checkEquals(avail[i][j], avail[i][k])) {
                            for (int l=0; l<size; l++) {
                                if (l != j && l != k && !buf[i][l].isNotEmpty() && null != avail[i][l]) {
                                    avail[i][l].removeAll(avail[i][j]);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                for (int k=0; k<size; k++) {
                    if (j != k) {
                        if (!buf[j][i].isNotEmpty() && !buf[k][i].isNotEmpty() && checkEquals(avail[j][i], avail[k][i])) {
                            for (int l=0; l<size; l++) {
                                if (l != j && l != k && !buf[l][i].isNotEmpty() && null != avail[l][i]) {
                                    avail[l][i].removeAll(avail[j][i]);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        for (int m=0; m<6; m+=2) {
        	for (int n=0; n<6; n+=3) {
        		
        		for (int i=m; i<m+2; i++) {
                	for (int j=n; j<n+3; j++) {
                		
                		for (int k=m; k<m+2; k++) {
                			for (int l=n; l<n+3; l++) {
                				
                				if (!(i==k && j==l) && !buf[i][j].isNotEmpty() && !buf[k][l].isNotEmpty()) {
	                				if (checkEquals(avail[i][j], avail[k][l])) {
	                					for (int x=m; x<m+2; x++) {
	                                		for (int y=n; y<n+3; y++) {
	                                			if (!(x==i && y==j) && !(x==k && y==l)) {
	                                				avail[x][y].remove(avail[i][j]);
	                                			}
	                                		}
	                					}
	                				}
                				}
                			}
                		}
                	}
        		}
        	}
        }
    }

    private static boolean checkEquals(Set<Integer> set1, Set<Integer> set2) {
        if (null == set1 || null == set2 || set1.size() != set2.size() || set1.size() != 2) {
            return false;
        }
        Iterator<Integer> it1 = set1.iterator();
        int num1 = it1.next();
        int num2 = it1.next();
        Iterator<Integer> it2 = set2.iterator();
        int num3 = it2.next();
        int num4 = it2.next();
        if ((num1 == num3 && num2 == num4) || (num1 == num4 && num2 == num3)) {
        	return true;
        }
        return false;
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
                		    removeErrorOne(newNode);
                		    removeErrorTwo();
                			newNode.availValues = avail[newNode.row][newNode.column];
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
                    		if (!newNode.isNotEmpty() && null != newNode.availValues && newNode.availValues.contains(item)) {
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
//                    					newNode.availValues.remove(item);
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
                    removeErrorOne(newNode);
                    removeErrorTwo();
                    newNode.availValues = avail[newNode.row][newNode.column];
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
                    if (!newNode.isNotEmpty() && null != newNode.availValues && newNode.availValues.contains(item)) {
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
//                                newNode.availValues.remove(item);
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
                    removeErrorOne(newNode);
                    removeErrorTwo();
                    newNode.availValues = avail[newNode.row][newNode.column];
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
                    if (!newNode.isNotEmpty() && null != newNode.availValues && newNode.availValues.contains(item)) {
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