package org.runelive.client.cache.node;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import java.util.ArrayList;
import java.util.List;

public final class NodeSubList {

	public NodeSubList() {
		head = new NodeSub();
		head.prevNodeSub = head;
		head.nextNodeSub = head;
	}

	public void insertHead(NodeSub nodeSub) {
		if (nodeSub.nextNodeSub != null) {
			nodeSub.unlinkCacheable();
		}
		nodeSub.nextNodeSub = head.nextNodeSub;
		nodeSub.prevNodeSub = head;
		nodeSub.nextNodeSub.prevNodeSub = nodeSub;
		nodeSub.prevNodeSub.nextNodeSub = nodeSub;
	}

	public NodeSub popTail() {
		NodeSub nodeSub = head.prevNodeSub;
		if (nodeSub == head) {
			return null;
		} else {
			nodeSub.unlinkCacheable();
			return nodeSub;
		}
	}

	public NodeSub reverseGetFirst() {
		NodeSub nodeSub = head.prevNodeSub;
		if (nodeSub == head) {
			current = null;
			return null;
		} else {
			current = nodeSub.prevNodeSub;
			return nodeSub;
		}
	}

	public NodeSub reverseGetNext() {
		NodeSub nodeSub = current;
		if (nodeSub == head) {
			current = null;
			return null;
		} else {
			current = nodeSub.prevNodeSub;
			return nodeSub;
		}
	}

	public int getSize() {
		int i = 0;
		for (NodeSub nodeSub = head.prevNodeSub; nodeSub != head; nodeSub = nodeSub.prevNodeSub) {
			i++;
		}

		return i;
	}

	public List<NodeSub> getList() {
		List<NodeSub> list = new ArrayList<>();
		for (NodeSub nodeSub = head.prevNodeSub; nodeSub != head; nodeSub = nodeSub.prevNodeSub) {
			list.add(nodeSub);
		}
		return list;
	}

	private final NodeSub head;
	private NodeSub current;
}
