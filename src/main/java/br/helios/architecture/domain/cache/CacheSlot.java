package br.helios.architecture.domain.cache;

import java.util.List;

import br.helios.architecture.domain.memory.Block;
import br.helios.architecture.domain.memory.Word;

public class CacheSlot {

	private static int TOTAL = 0;

	public final int id;
	private Block blockCopy;

	public CacheSlot() {
		this.id = TOTAL;
		TOTAL++;
	}

	public void save(Block block) {
		blockCopy = block.copy();
	}

	public Block getBlock() {
		return blockCopy;
	}

	public Word getFirstWord() {
		return blockCopy != null ? blockCopy.getFirstWord() : null;
	}

	public List<Word> getWords() {
		return blockCopy != null ? blockCopy.words : null;
	}
}
