package com.accolite.typeahead;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class Pair {
	String word;
	int freq;

	public Pair(String word, int freq) {
		this.word = word;
		this.freq = freq;
	}

	public String getWord() {
		return word;
	}

	public int getFreq() {
		return freq;
	}
}

public class TernaryTrie {
	private Node root;

	public TernaryTrie() {
		root = null;
	}

	public void insert(String word) {
		if (word != null && !word.isEmpty())
			root = insert(word.toLowerCase(), 0, root);
	}

	// insert character along with frequency of the character which is
	// updated on every insertion .
	private Node insert(String word, int index, Node node) {
		char ch = word.charAt(index);
		if (node == null) {
			node = new Node(ch);
			node.freq = 0;
		}

		if (ch < node.val) {
			node.left = insert(word, index, node.left);
		} else if (ch > node.val) {
			node.right = insert(word, index, node.right);
		} else {
			if (index != word.length() - 1) {
				node.mid = insert(word, index + 1, node.mid);
			} else {
				node.freq++;
			}
		}
		return node;
	}

//search based on prefix of word it will give list of sentences or word if present otherwise it will give not found result  
	private Queue<Pair> search(String word, int topFreq) {
		Queue<Pair> words = new PriorityQueue<>(Comparator.comparing(Pair::getFreq).reversed());
		word = word.toLowerCase();
		Node node = findMatchingPathEndNode(word, 0, root);

		if (node != null) {
			computeMatchingTerms(words, "", word.substring(0, word.length() - 1), node, topFreq);
		}
		return words;
	}

	public Node findMatchingPathEndNode(String prefix, int index, Node node) {

		if (node == null) {
			return null;
		}
		if (prefix.charAt(index) < node.val) {
			return findMatchingPathEndNode(prefix, index, node.left);
		} else if (prefix.charAt(index) > node.val) {
			return findMatchingPathEndNode(prefix, index, node.right);
		} else {
			if (index == prefix.length() - 1) {
				return node;
			} else {
				return findMatchingPathEndNode(prefix, index + 1, node.mid);
			}
		}
	}

	private void computeMatchingTerms(Queue<Pair> wordList, String temp, String prefix, Node node, int topFreq) {
		if (node == null && wordList.size() > topFreq)
			return;

		temp += node.val;
		if (node.freq != 0) {
			wordList.add(new Pair(prefix + temp, node.freq));
		}

		if (node.mid != null) {
			computeMatchingTerms(wordList, temp, prefix, node.mid, topFreq);
		}
		if (node.mid != null && node.mid.left != null) {
			computeMatchingTerms(wordList, temp, prefix, node.mid.left, topFreq);
		}

		if (node.mid != null && node.mid.right != null) {
			computeMatchingTerms(wordList, temp, prefix, node.mid.right, topFreq);
		}
	}

	public void insertAll(String[] words) {
		for (String w : words) {
			insert(w);
		}
	}

	private static void print(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) {
		TernaryTrie ternaryTrie = new TernaryTrie();

		Pair[] testData = new Pair[] { new Pair("Harry", 200), new Pair("Harry Potter", 300), new Pair("Hogwarts", 400),
				new Pair("Quidditch", 150) };
		// type head search based on top k frequency .
		int topFreq = 10;
		for (Pair testDatum : testData) {
			for (int i = 0; i < testDatum.getFreq(); i++) {
				ternaryTrie.insert(testDatum.getWord());
			}
		}

		test("h", ternaryTrie, topFreq);
		test("ha", ternaryTrie, topFreq);
		test("Harry", ternaryTrie, topFreq);
		test("harry p", ternaryTrie, topFreq);
		test("p", ternaryTrie, topFreq);
		test("q", ternaryTrie, topFreq);
	}

	private static void test(String prefix, TernaryTrie ternaryTrie, int topFreq) {
		// type head search based on top k frequency.
		print("\n\nTC Start: Searching for " + prefix + " freq::" + topFreq);
		Queue<Pair> wordList = ternaryTrie.search(prefix, topFreq);
		while (!wordList.isEmpty()) {
			Pair p = wordList.poll();
			print(p.word + "," + p.freq);
		}
		print("TC End");
	}

}