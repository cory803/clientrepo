package org.chaos.map;

public final class RSBuffer extends Cacheable {

	public int aih() {
		position += 4;
		return ((akn[position - 4] & 0xff) << 24) + ((akn[position - 3] & 0xff) << 16)
				+ ((akn[position - 2] & 0xff) << 8) + (akn[position - 1] & 0xff);
	}

	public int aii() {
		return akn[position++] & 0xff;
	}

	public byte aij() {
		return akn[position++];
	}

	public int getUShort() {
		position += 2;
		return ((akn[position - 2] & 0xff) << 8) + (akn[position - 1] & 0xff);
	}

	public String ail() {
		int i = position;
		while (akn[position++] != 10)
			;
		return new String(akn, i, position - i - 1);
	}

	public int aim() {
		position += 3;
		return ((akn[position - 3] & 0xff) << 16) + ((akn[position - 2] & 0xff) << 8)
				+ (akn[position - 1] & 0xff);
	}

	public RSBuffer(byte data[]) {
		akn = data;
		position = 0;
	}

	public byte akn[];
	public int position;
	private static int alb[];
	private static final int alc[] = { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511,
			1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff,
			0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
			0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff,
			0x7fffffff, -1 };
	private static int ald = 0;
	private static int ale = 0;
	private static int alf = 0;
	private static Deque alg = new Deque();
	private static Deque alh = new Deque();
	private static Deque ali = new Deque();
	private static char alj[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', '+', '/' };
	public static boolean alk;

	static {
		alb = new int[256];
		for (int l = 0; l < 256; l++) {
			int i = l;
			for (int i1 = 0; i1 < 8; i1++)
				if ((i & 1) == 1)
					i = i >>> 1 ^ 0xedb88320;
				else
					i >>>= 1;

			alb[l] = i;
		}
	}
}
