package spelling;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}


	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
		// Implement this method.
		TrieNode currentNode = root;
		TrieNode nextNode;

		if(word == null || isWord(word))
			return false;

		word = word.toLowerCase();
		for(Character c : word.toCharArray()) {
			nextNode = currentNode.getChild(c);
			if(nextNode == null)
				nextNode = currentNode.insert(c);
			currentNode = nextNode;
		}

		currentNode.setEndsWord(true);
		size++;
		return true;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    // Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String word)
	{
	    // Implement this method
		TrieNode currentNode = root;

		if (word == null || word.length() == 0)
			return false;

		word = word.toLowerCase();
		for (Character c : word.toCharArray()) {
			currentNode = currentNode.getChild(c);
			// if next doesn't exist, return false
			if (currentNode == null)
				return false;
		}

		return currentNode.endsWord();
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions

		 int number = 0;
		 TrieNode currentNode = root;
		 TrieNode nextNode;
		 List<String> returnList = new LinkedList<>();
		 Queue<TrieNode> queue = new LinkedList<>();
		 prefix = prefix.toLowerCase();

		 for (char letter : prefix.toCharArray()) {
			 nextNode = currentNode.getChild(letter);
			 if (nextNode == null) {
				 return returnList;
			 }
			 currentNode = nextNode;
		 }

		 queue.add(currentNode);
		 while (!queue.isEmpty() && number < numCompletions) {
			 nextNode = queue.remove();
			 if (nextNode.endsWord()) {
				 returnList.add(nextNode.getText());
				 number++;
			 }
			 for (char c : nextNode.getValidNextCharacters()) {
				 queue.add(nextNode.getChild(c));
			 }
		 }

		 return returnList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}