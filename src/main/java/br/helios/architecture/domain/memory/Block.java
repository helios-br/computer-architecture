package br.helios.architecture.domain.memory;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

public class Block {

	private static int TOTAL = 0;

	public final int id;
	public List<Word> words = new ArrayList<>();

	public Block() {
		this.id = TOTAL;
		TOTAL++;
	}

	private Block(Block block) {
		this.id = block.id;
		block.words.stream().forEach(word -> this.words.add(word.copy()));
	}

	public void addWord(Word word) {
		words.add(word);
	}

	public Word getFirstWord() {
		return words.isEmpty() ? null : words.get(0);
	}

	public Word getWord(int wordId) {
		for (Word word : words) {
			if (word.id == wordId) {
				return word;
			}
		}
		throw new IllegalStateException(format("Word (%s) not found on block (%s)", wordId, id));
	}

	public Block copy() {
		return new Block(this);
	}
}
