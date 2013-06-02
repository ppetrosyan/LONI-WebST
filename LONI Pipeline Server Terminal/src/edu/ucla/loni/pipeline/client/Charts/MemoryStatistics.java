/*
 * This file is part of LONI Pipeline Web-based Server Terminal.
 * 
 * LONI Pipeline Web-based Server Terminal is free software: 
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * LONI Pipeline Web-based Server Terminal is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with LONI Pipeline Web-based Server Terminal.
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
