package Trie;

import java.util.Arrays;
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

	public void setWord(String word) {
		this.word = word;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

}

public class TernaryTrie {
	private Node root;

	public TernaryTrie() {
		root = null;
	}

	public void insert(String word) {
		if (word != null && !word.isEmpty())
			root = insert(word, 0, root);
	}

	private int maxFreq(Node a) {
		int maxi = 0;
		if (a.mid == null) {
			maxi = a.freq;
		} else {
			if (a.mid != null) {
				maxi = Math.max(a.max, a.mid.max);
			}
			if (a.mid != null && a.mid.left != null) {
				maxi = Math.max(a.max, a.mid.left.max);
			}
			if (a.mid != null && a.mid.right != null) {
				maxi = Math.max(a.max, a.mid.right.max);
			}
		}
		return maxi;
	}

	// insert  character along with frequency of the character which is
	// updated on every insertion .
	private Node insert(String word, int index, Node node) {
		char ch = word.charAt(index);
		if (node == null) {
			node = new Node(ch);
			node.freq = 0;
//			System.out.print(node.val+"   ");
		//	System.out.print(node.freq+"   ");
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
//
//				System.out.print(node.val+"   ");
//				System.out.print(node.freq+"   ");
//				System.out.print(index);
			}
		}
//		node.max = maxFreq(node);
		return node;
	}

//search based on prefix of word it will give list of sentences or word if present otherwise it will give not found result  
	private Queue<Pair> search(String word) {
		Queue<Pair> words = new PriorityQueue<Pair>(Comparator.comparing(Pair::getFreq).reversed());
		Node node = startsWith(word, 0, root);
		startsWith(words, "", word.substring(0, word.length() - 1), node);
		return words;
	}

	public Node startsWith(String prefix, int index, Node node) {

		if (node == null) {
			return null;
		}
		if (prefix.charAt(index) < node.val) {
			return startsWith(prefix, index, node.left);
		} else if (prefix.charAt(index) > node.val) {
			return startsWith(prefix, index, node.right);
		} else {
			if (index == prefix.length() - 1) {
				return node;
			} else {
				return startsWith(prefix, index + 1, node.mid);
			}
		}
	}

	private void startsWith(Queue<Pair> wordList, String temp, String prefix, Node node) {
		if (node == null)
			return;

		temp += node.val;
		if (node.freq != 0) {
			wordList.add(new Pair(prefix + temp, node.freq));
		}

		if (node.mid != null) {
			startsWith(wordList, temp, prefix, node.mid);
		}
		if (node.mid != null && node.mid.left != null) {
			startsWith(wordList, temp, prefix, node.mid.left);
		}

		if (node.mid != null && node.mid.right != null) {
			startsWith(wordList, temp, prefix, node.mid.right);
		}

		temp = temp.substring(0, temp.length() - 1);
	}

	public void insertAll(String[] words) {
		for(String w:words) {
		  insert(w);
		}
		//Arrays.asList(words).stream().forEach(word -> insert(word));
	}

	public static void main(String[] args) {
		TernaryTrie ternaryTrie = new TernaryTrie();
		
		String words[] = { "harry potter", "harry", "potter", "harry" };
		ternaryTrie.insertAll(words);
		
		test(words,"h",ternaryTrie);
		System.out.println("test 2------------------");
	//	test(words,"p",ternaryTrie);

		test(words,"p",ternaryTrie);

	}

	

	private static void test(String[] words,String prefix,TernaryTrie ternaryTrie) {
		// type head search based on top k frequency .
		Queue<Pair> wordList = ternaryTrie.search(prefix);
		while (!wordList.isEmpty()) {
			Pair p = wordList.poll();
			System.out.println(p.word + "," + p.freq);
		}
	}

}
