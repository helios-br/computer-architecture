package br.helios.architecture;

import static java.lang.String.format;

import br.helios.architecture.domain.cache.Cache;
import br.helios.architecture.domain.cache.LruFullyAssociativeCache;
import br.helios.architecture.domain.memory.MainMemory;

public class Application {

	public static void main(String[] args) {

		// Setup

		MainMemory mainMemory = new MainMemory(1024, 1);
		// Cache cacheMemory = new DirectMappedCache(mainMemory, 10);
		// Cache cacheMemory = new FifoFullyAssociativeCache(mainMemory, 10);
		Cache cacheMemory = new LruFullyAssociativeCache(mainMemory, 10);
		int[] blockReadSequence = { 21, 5, 28, 11, 18, 0, 31, 21, 28, 11, 18, 0, 31, 21, 28, 11, 18, 0, 5, 21, 28, 11,
				18, 0, 31, 38, 17, 6, 7, 11, 18, 0, 5, 31 };

		System.out.println(mainMemory.toString());
		System.out.println(cacheMemory.toString());
		System.out.println("Total words in sequence: " + blockReadSequence.length);

		// Execute

		for (int i = 0; i < blockReadSequence.length; i++) {
			String word = format("'%s'", blockReadSequence[i]);
			System.out.println("\n# Reading word " + word + "...");
			cacheMemory.read(blockReadSequence[i]);
			System.out.println(cacheMemory.getState());
		}

		// Results

		System.out.println("\nCache hits: " + cacheMemory.totalCacheHits);
		System.out.println("Cache misses: " + cacheMemory.totalCacheMisses);
	}

}
