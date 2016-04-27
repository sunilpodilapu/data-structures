package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// Implement this method
		String[] sourceWords = sourceText.split("\\s+");
		starter = "";
		ListNode node;

		for(int i = 0; i < sourceWords.length; i++) {
			int starterLoc = containsWord(starter);
			String word = sourceWords[i];

			if (starterLoc > -1) {
				wordList.get(starterLoc).addNextWord(word);
			} else {
				node = new ListNode(starter);
				wordList.add(node);
				node.addNextWord(word);
			}

			starter = word;
			if(i + 1 >= sourceWords.length && starterLoc < 0) {
				node = new ListNode(starter);
				wordList.add(node);
				node.addNextWord("");
			}
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // Implement this method
		String output = "";
		starter = "";
		String randomWord;
		ListNode node;
		int wordCount = 0;
		int totalWords = wordCount();

		// make sure enough words first
		if(!(wordCount < totalWords))
			return output;

		node = wordList.get(0);
		while(wordCount < numWords) {
			for(ListNode searchNode : wordList) {
				if(searchNode.getWord().equals(starter)) {
					node = searchNode;
					break;
				}
			}

			randomWord = node.getRandomNextWord(rnGenerator);
			output += randomWord;
			starter = randomWord;

			wordCount++;
			if(wordCount < numWords)
				output += " ";

		}

		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// Implement this method.
		wordList = new LinkedList<>();
		starter = "";
		train(sourceText);
	}

	public int wordCount()
	{
		int wordCount = 0;

		for(ListNode node : wordList)
			wordCount += node.size();

		return wordCount;
	}

	public int containsWord(String word)
	{
		for(int index = 0; index < wordList.size(); index++) {
			if(wordList.get(index).getWord().equals(word))
				return index;
		}
		return -1;
	}


	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args passed by user
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		System.out.println(gen.generateText(20));

		System.out.println("Test 1 --------");
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));

		System.out.println("\nTest 2 --------");
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

	public int size() {
		return nextWords.size();
	}
	
}


