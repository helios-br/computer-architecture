package br.helios.architecture.domain.cache;

import static br.helios.architecture.domain.memory.MainMemory.findBlockByWord;
import static java.lang.Integer.valueOf;
import static java.lang.String.format;

import java.util.LinkedList;
import java.util.List;

import br.helios.architecture.domain.memory.Block;
import br.helios.architecture.domain.memory.MainMemory;
import br.helios.architecture.domain.memory.Word;

public class LruFullyAssociativeCache extends Cache {

	// Priority queue (slots ids)
	private List<Integer> queue = new LinkedList<>();

	public LruFullyAssociativeCache(MainMemory mainMemory, int numberOfSlots) {
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
				slotIndexForInsertion = slot.id;
			} else if (block != null && block.id == blockId) {
				this.totalCacheHits++;
				updateQueue(slot.id);
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
		updateQueue(slotIndexForInsertion);
		return block.getWord(wordId);
	}

	private void updateQueue(int slotId) {
		boolean removed = queue.remove(valueOf(slotId));
		if (removed) {
			System.out.println("slot updated (" + slotId + ")");
		} else {
			System.out.println("new slot added (" + slotId + ")");
		}
		queue.add(valueOf(slotId));
	}

	@Override
	public String getState() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.getState());
		builder.append("\nSlots priorities\n");
		for (int i = 0; i < queue.size(); i++) {
			builder.append(format(" [prior-%s] %s", i, queue.get(i)));
		}
		return builder.toString();
	}

}
