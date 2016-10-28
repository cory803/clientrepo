package org.chaos.client.cache.definition;

import org.chaos.client.cache.Archive;
import org.chaos.client.io.ByteBuffer;

public final class VarBit {

	public static VarBit[] cache;

	public static void unpackConfig(Archive archive) {
		byte[] data = archive.get("varbit.dat");
		ByteBuffer buffer = new ByteBuffer(data);
		int size = buffer.getUnsignedShort();

		System.out.println("Varbit size: "+size);
		if (cache == null) {
			cache = new VarBit[size];
		}

		for (int i = 0; i < size; i++) {
			if (cache[i] == null) {
				cache[i] = new VarBit();
			}

			cache[i].readValues(buffer);
		}

		if (buffer.position != buffer.buffer.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public int configId;
	public int configValue;
	public int anInt650;

	private VarBit() {
	}

	private void readValues(ByteBuffer buffer) {
		configId = buffer.getUnsignedShort();
		configValue = buffer.getUnsignedByte();
		anInt650 = buffer.getUnsignedByte();

	}

}