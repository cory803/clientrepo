package org.chaos.map;

public final class Sprite extends Raster {

	public int agk[];
	public int agl;
	public int agm;
	public int agn;
	public int aha;
	public int ahb;
	public int ahc;
	
	public void acf() {
		Raster.setArea(agk, agl, agm);
	}

	public void acg(int arg0, int arg1) {
		arg0 += agn;
		arg1 += aha;
		int i = arg0 + arg1 * Raster.width;
		int k = 0;
		int l = agm;
		int i1 = agl;
		int j1 = Raster.width - i1;
		int k1 = 0;
		if (arg1 < Raster.bbh) {
			int l1 = Raster.bbh - arg1;
			l -= l1;
			arg1 = Raster.bbh;
			k += l1 * i1;
			i += l1 * Raster.width;
		}
		if (arg1 + l > Raster.bbi)
			l -= (arg1 + l) - Raster.bbi;
		if (arg0 < Raster.bbj) {
			int i2 = Raster.bbj - arg0;
			i1 -= i2;
			arg0 = Raster.bbj;
			k += i2;
			i += i2;
			k1 += i2;
			j1 += i2;
		}
		if (arg0 + i1 > Raster.bbk) {
			int j2 = (arg0 + i1) - Raster.bbk;
			i1 -= j2;
			k1 += j2;
			j1 += j2;
		}
		if (i1 <= 0 || l <= 0) {
			return;
		} else {
			ach(Raster.pixels, agk, 0, k, i, i1, l, j1, k1);
			return;
		}
	}

	private void ach(int arg0[], int arg1[], int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7, int arg8) {
		int i = -(arg5 >> 2);
		arg5 = -(arg5 & 3);
		for (int k = -arg6; k < 0; k++) {
			for (int l = i; l < 0; l++) {
				arg2 = arg1[arg3++];
				if (arg2 != 0)
					arg0[arg4++] = arg2;
				else
					arg4++;
				arg2 = arg1[arg3++];
				if (arg2 != 0)
					arg0[arg4++] = arg2;
				else
					arg4++;
				arg2 = arg1[arg3++];
				if (arg2 != 0)
					arg0[arg4++] = arg2;
				else
					arg4++;
				arg2 = arg1[arg3++];
				if (arg2 != 0)
					arg0[arg4++] = arg2;
				else
					arg4++;
			}

			for (int i1 = arg5; i1 < 0; i1++) {
				arg2 = arg1[arg3++];
				if (arg2 != 0)
					arg0[arg4++] = arg2;
				else
					arg4++;
			}

			arg4 += arg7;
			arg3 += arg8;
		}

	}

	public Sprite(int arg0, int arg1) {
		agk = new int[arg0 * arg1];
		agl = ahb = arg0;
		agm = ahc = arg1;
		agn = aha = 0;
	}

	public Sprite(Archive arg0, String arg1, int arg2) {
		RSBuffer j1 = new RSBuffer(arg0.abl(arg1 + ".dat", null));
		RSBuffer j2 = new RSBuffer(arg0.abl("index.dat", null));
		j2.position = j1.getUShort();
		ahb = j2.getUShort();
		ahc = j2.getUShort();
		int i = j2.aii();
		int ai[] = new int[i];
		for (int k = 0; k < i - 1; k++) {
			ai[k + 1] = j2.aim();
			if (ai[k + 1] == 0)
				ai[k + 1] = 1;
		}

		for (int l = 0; l < arg2; l++) {
			j2.position += 2;
			j1.position += j2.getUShort() * j2.getUShort();
			j2.position++;
		}

		agn = j2.aii();
		aha = j2.aii();
		agl = j2.getUShort();
		agm = j2.getUShort();
		int i1 = j2.aii();
		int k1 = agl * agm;
		agk = new int[k1];
		if (i1 == 0) {
			for (int l1 = 0; l1 < k1; l1++)
				agk[l1] = ai[j1.aii()];

		} else if (i1 == 1) {
			for (int i2 = 0; i2 < agl; i2++) {
				for (int k2 = 0; k2 < agm; k2++)
					agk[i2 + k2 * agl] = ai[j1.aii()];

			}

		}
	}

	public void ack(int arg0, int arg1) {
		arg0 += agn;
		arg1 += aha;
		int i = arg0 + arg1 * Raster.width;
		int k = 0;
		int l = agm;
		int i1 = agl;
		int j1 = Raster.width - i1;
		int k1 = 0;
		if (arg1 < Raster.bbh) {
			int l1 = Raster.bbh - arg1;
			l -= l1;
			arg1 = Raster.bbh;
			k += l1 * i1;
			i += l1 * Raster.width;
		}
		if (arg1 + l > Raster.bbi)
			l -= (arg1 + l) - Raster.bbi;
		if (arg0 < Raster.bbj) {
			int i2 = Raster.bbj - arg0;
			i1 -= i2;
			arg0 = Raster.bbj;
			k += i2;
			i += i2;
			k1 += i2;
			j1 += i2;
		}
		if (arg0 + i1 > Raster.bbk) {
			int j2 = (arg0 + i1) - Raster.bbk;
			i1 -= j2;
			k1 += j2;
			j1 += j2;
		}
		if (i1 <= 0 || l <= 0) {
			return;
		} else {
			acl(Raster.pixels, agk, k, i, i1, l, j1, k1);
			return;
		}
	}

	private void acl(int arg0[], int arg1[], int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7) {
		int i = -(arg4 >> 2);
		arg4 = -(arg4 & 3);
		for (int k = -arg5; k < 0; k++) {
			for (int l = i; l < 0; l++) {
				arg0[arg3++] = arg1[arg2++];
				arg0[arg3++] = arg1[arg2++];
				arg0[arg3++] = arg1[arg2++];
				arg0[arg3++] = arg1[arg2++];
			}

			for (int i1 = arg4; i1 < 0; i1++)
				arg0[arg3++] = arg1[arg2++];

			arg3 += arg6;
			arg2 += arg7;
		}
	}
}
