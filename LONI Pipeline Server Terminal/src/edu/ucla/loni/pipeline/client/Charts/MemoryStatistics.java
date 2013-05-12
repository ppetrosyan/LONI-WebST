package edu.ucla.loni.pipeline.client.Charts;

public class MemoryStatistics {

	private int initMemMB;
	private int usedMemMB;
	private int commMemMB;
	private int maxMemMB;
	
	private int usedCommMemPercent;
	private int usedMaxMemPercent;
	private int commMaxMemPercent;
	
	public MemoryStatistics() {
		setInitMemMB(0);
		setUsedMemMB(0);
		setCommMemMB(0);
		setMaxMemMB(0);
		
		setUsedCommMemPercent(0);
		setUsedMaxMemPercent(0);
		setCommMaxMemPercent(0);
	}

	public int getInitMemMB() {
		return initMemMB;
	}

	public void setInitMemMB(int initMemMB) {
		this.initMemMB = initMemMB;
	}

	public int getUsedMemMB() {
		return usedMemMB;
	}

	public void setUsedMemMB(int usedMemMB) {
		this.usedMemMB = usedMemMB;
	}

	public int getCommMemMB() {
		return commMemMB;
	}

	public void setCommMemMB(int commMemMB) {
		this.commMemMB = commMemMB;
	}

	public int getMaxMemMB() {
		return maxMemMB;
	}

	public void setMaxMemMB(int maxMemMB) {
		this.maxMemMB = maxMemMB;
	}

	public int getUsedCommMemPercent() {
		return usedCommMemPercent;
	}

	public void setUsedCommMemPercent(int usedCommMemPercent) {
		this.usedCommMemPercent = usedCommMemPercent;
	}

	public int getUsedMaxMemPercent() {
		return usedMaxMemPercent;
	}

	public void setUsedMaxMemPercent(int usedMaxMemPercent) {
		this.usedMaxMemPercent = usedMaxMemPercent;
	}

	public int getCommMaxMemPercent() {
		return commMaxMemPercent;
	}

	public void setCommMaxMemPercent(int commMaxMemPercent) {
		this.commMaxMemPercent = commMaxMemPercent;
	}
	
}
