package br.helios.architecture.domain.cache;

import static br.helios.architecture.domain.memory.MainMemory.findBlockByWord;

import java.util.HashMap;
import java.util.Map;

import br.helios.architecture.domain.memory.Block;
import br.helios.architecture.domain.memory.MainMemory;
import br.helios.architecture.domain.memory.Word;

public class DirectMappedCache extends Cache {

	/**
	 * Maps block "id" with cache slot
	 */
	private Map<Integer, CacheSlot> blockSlotMap = new HashMap<>();

	public DirectMappedCache(MainMemory mainMemory, int numberOfSlots) {
		super(mainMemory, numberOfSlots);

		// Maps blocks with cache slots

		int slotIndex = 0;

		for (int i = 0; i < mainMemory.blocks.size(); i++) {
			blockSlotMap.put(i, this.getSlots().get(slotIndex));
			slotIndex++;
			if (slotIndex % numberOfSlots == 0) {
				slotIndex = 0;
			}
		}
	}

	@Override
	public Word read(int wordId) {
		int blockId = findBlockByWord(wordId, getMainMemory());
		CacheSlot slot = blockSlotMap.get(blockId);
		Block block = slot.getBlock();

		if (block == null || block.id != blockId) {
			this.totalCacheMisses++;
			block = getMainMemory().getBlock(wordId);
			slot.save(block);
		} else {
			this.totalCacheHits++;
		}

		return block.getWord(wordId);
	}

}
