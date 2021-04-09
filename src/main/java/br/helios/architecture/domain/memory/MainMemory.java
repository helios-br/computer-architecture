package br.helios.architecture.domain.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMemory {

	public final List<Word> words = new ArrayList<>();
	public final List<Block> blocks = new ArrayList<>();
	public final Map<Integer, Block> wordBlockMap = new HashMap<>();

	public MainMemory(int numberOfWords, int numberWordsInBlocks) {
		Block block = new Block();
		for (int i = 0; i < numberOfWords; i++) {
			Word word = new Word();
			words.add(word);
			block.addWord(word);
			wordBlockMap.put(word.id, block);
			if ((i + 1) % numberWordsInBlocks == 0) {
				blocks.add(block);
				block = new Block();
			}
		}
	}

	public Block getBlock(int blockId) {
		for (Block block : blocks) {
			if (block.id == blockId) {
				return block;
			}
		}
		throw new IllegalStateException("Block not found");
	}

	public static int findBlockByWord(int wordId, MainMemory mainMemory) {
		Block block = mainMemory.wordBlockMap.get(wordId);
		if (block == null) {
			throw new IllegalStateException("Block not found!");
		}
		return block.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("-- Main memory -- \n");
		builder.append("Total words: " + words.size() + "\n");
		builder.append("Total blocks: " + blocks.size() + "\n");
//		for (Block block : blocks) {
//			builder.append(String.format("Words in block (%s): ", block.id));
//			block.words.stream().forEach(word -> builder.append(word.id + " "));
//			builder.append("\n");
//		}
		return builder.toString();
	}
}
