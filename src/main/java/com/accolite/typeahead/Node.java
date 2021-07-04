package com.accolite.typeahead;

class Node {
	Node left, mid, right = null;
	char val;
	boolean isEnd;
	int freq;
	int max;

	public Node() {
	}

	public Node(char val) {
		this.val = val;
	}
}
