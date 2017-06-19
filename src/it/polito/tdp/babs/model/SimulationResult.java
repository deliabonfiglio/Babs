package it.polito.tdp.babs.model;

public class SimulationResult {
	private int pickMissed;
	private int dropMissed;
	
	public SimulationResult() {
		super();
		this.pickMissed=0;
		this.dropMissed=0;
	}

	public int getPickMissed() {
		return pickMissed;
	}

	public void addPickMissed( ) {
		this.pickMissed += 1;
	}

	public int getDropMissed() {
		return dropMissed;
	}

	public void addDropMissed() {
		this.dropMissed += 1;
	}
	
	

}
