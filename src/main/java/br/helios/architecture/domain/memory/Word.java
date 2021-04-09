package br.helios.architecture.domain.memory;

public class Word {

	private static int TOTAL = 0; 
	
	public final int id;
	
	public Word() {
		this.id = TOTAL;
		TOTAL++;
	}
	
	private Word(Word word) {
		this.id = word.id;
	}
	
	public Word copy() {
		return new Word(this);
	}
	
}
