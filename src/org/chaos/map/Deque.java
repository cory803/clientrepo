package org.chaos.map;

public final class Deque {

	public Deque() {
		afk = new Linkable();
		afk.next = afk;
		afk.previous = afk;
	}

	public Linkable afk;
}
