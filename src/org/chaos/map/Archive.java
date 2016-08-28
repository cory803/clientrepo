package org.chaos.map;

public final class Archive {

	public Archive(byte data[]) {
		decodeData(data);
	}

	private void decodeData(byte data[]) {
		RSBuffer j1 = new RSBuffer(data);
		int i = j1.aim();
		int k = j1.aim();
		if (k != i) {
			byte abyte0[] = new byte[i];
			BZip2Decompressor.aem(abyte0, i, data, k, 6);
			afc = abyte0;
			j1 = new RSBuffer(afc);
			afi = true;
		} else {
			afc = data;
			afi = false;
		}
		afd = j1.getUShort();
		afe = new int[afd];
		aff = new int[afd];
		afg = new int[afd];
		afh = new int[afd];
		int l = j1.position + afd * 10;
		for (int i1 = 0; i1 < afd; i1++) {
			afe[i1] = j1.aih();
			aff[i1] = j1.aim();
			afg[i1] = j1.aim();
			afh[i1] = l;
			l += afg[i1];
		}

	}

	public byte[] abl(String arg0, byte arg1[]) {
		int i = 0;
		arg0 = arg0.toUpperCase();
		for (int k = 0; k < arg0.length(); k++)
			i = (i * 61 + arg0.charAt(k)) - 32;

		for (int l = 0; l < afd; l++)
			if (afe[l] == i) {
				if (arg1 == null)
					arg1 = new byte[aff[l]];
				if (!afi) {
					BZip2Decompressor.aem(arg1, aff[l], afc, afg[l], afh[l]);
				} else {
					for (int i1 = 0; i1 < aff[l]; i1++)
						arg1[i1] = afc[afh[l] + i1];

				}
				return arg1;
			}

		return null;
	}

	public byte afc[];
	public int afd;
	public int afe[];
	public int aff[];
	public int afg[];
	public int afh[];
	private boolean afi;
}
