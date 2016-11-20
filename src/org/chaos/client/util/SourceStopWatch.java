package org.chaos.client.util;

public class SourceStopWatch {

	private long time = System.currentTimeMillis();

	public SourceStopWatch headStart(long startAt) {
		time = System.currentTimeMillis() - startAt;
		return this;
	}

	public SourceStopWatch reset(long i) {
		time = i;
		return this;
	}

	public SourceStopWatch reset() {
		time = System.currentTimeMillis();
		return this;
	}

	public long elapsed() {
		return System.currentTimeMillis() - time;
	}

	public boolean elapsed(long time) {
		return elapsed() >= time;
	}

	public long getTime() {
		return time;
	}

	public SourceStopWatch() {
		time = 0;
	}
}
