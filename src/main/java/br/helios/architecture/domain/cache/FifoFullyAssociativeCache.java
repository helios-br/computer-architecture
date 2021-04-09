package br.helios.architecture.domain.cache;

import static br.helios.architecture.domain.memory.MainMemory.findBlockByWord;

import java.util.LinkedList;
import java.util.List;

import br.helios.architecture.domain.memory.Block;
import br.helios.architecture.domain.memory.MainMemory;
import br.helios.architecture.domain.memory.Word;

public class FifoFullyAssociativeCache extends Cache {

	// FIFO queue (blocks ids)
	private List<Integer> queue = new LinkedList<>();

	public FifoFullyAssociativeCache(MainMemory mainMemory, int numberOfSlots) {
		super(mainMemory, numberOfSlots);
	}

	@Override
	public Word read(int wordId) {
		int blockId = findBlockByWord(wordId, getMainMemory());
		int slotIndexForInsertion = -1;

		for (int i = 0; i < this.getSlots().size(); i++) {
			CacheSlot slot = this.getSlots().get(i);
			Block block = slot.getBlock();
			if (block == null && slotIndexForInsertion < 0) {
				slotIndexForInsertion = i;
			} else if (block != null && block.id == blockId) {
				this.totalCacheHits++;
				return block.getWord(wordId);
			}
		}

		// Processing cache miss

		this.totalCacheMisses++;

		if (slotIndexForInsertion < 0)

		{
			slotIndexForInsertion = queue.remove(0);
		}

		CacheSlot slot = this.getSlots().get(slotIndexForInsertion);
		Block block = getMainMemory().getBlock(wordId);
		slot.save(block);
		queue.add(slot.id);
		return block.getWord(wordId);
	}

}
