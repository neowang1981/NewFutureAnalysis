package org.ezool.iqx.events;

public class MainActivitySlidingEnableChangedEvent extends BasicEvent{
	private boolean isSlidingEnabled;

	public boolean isSlidingEnabled() {
		return isSlidingEnabled;
	}

	public void setSlidingEnabled(boolean isSlidingEnabled) {
		this.isSlidingEnabled = isSlidingEnabled;
	}
}
