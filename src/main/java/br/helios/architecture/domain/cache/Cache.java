package br.helios.architecture.domain.cache;

import java.util.ArrayList;
import java.util.List;

import br.helios.architecture.domain.memory.Block;
import br.helios.architecture.domain.memory.MainMemory;
import br.helios.architecture.domain.memory.Word;

public abstract class Cache {

	public int totalCacheHits = 0;
	public int totalCacheMisses = 0;

	private MainMemory mainMemory;
	private List<CacheSlot> slots = new ArrayList<>();

	public Cache(MainMemory mainMemory, int numberOfSlots) {
		this.mainMemory = mainMemory;
		for (int i = 0; i < numberOfSlots; i++) {
			slots.add(new CacheSlot());
		}
	}

	public String getState() {
		StringBuilder builder = new StringBuilder();
		builder.append("-- New cache state --\n");
		for (int i = 0; i < this.getSlots().size(); i++) {
			CacheSlot slot = this.getSlots().get(i);
			Block block = slot.getBlock();
			builder.append(String.format(" [slot-%s] %s", slot.id, block == null ? "null" : block.id));
		}
		return builder.toString();
	}

	public abstract Word read(int wordId);

	protected List<CacheSlot> getSlots() {
		return slots;
	}

	public MainMemory getMainMemory() {
		return mainMemory;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("-- Direct Mapped Cache --\n");
		builder.append("Total cache slots: " + this.getSlots().size() + "\n");
		return builder.toString();
	}

}
