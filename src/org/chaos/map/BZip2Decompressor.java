package org.chaos.map;

public final class BZip2Decompressor {

	private static byte aef(BZip2DecompressionState arg0) {
		return (byte) aen(8, arg0);
	}

	private static void aeg(BZip2DecompressionState arg0) {
		int l8 = 0;
		int ai[] = null;
		int ai1[] = null;
		int ai2[] = null;
		arg0.ani = 1;
		if (BZip2DecompressionState.bad == null)
			BZip2DecompressionState.bad = new int[arg0.ani * 0x186a0];
		boolean flag20 = true;
		while (flag20) {
			byte data = aef(arg0);
			if (data == 23)
				return;
			data = aef(arg0);
			data = aef(arg0);
			data = aef(arg0);
			data = aef(arg0);
			data = aef(arg0);
			arg0.anj++;
			data = aef(arg0);
			data = aef(arg0);
			data = aef(arg0);
			data = aef(arg0);
			data = aej(arg0);
			if (data != 0)
				arg0.anf = true;
			else
				arg0.anf = false;
			if (arg0.anf)
				System.out.println("PANIC! RANDOMISED BLOCK!");
			arg0.ank = 0;
			data = aef(arg0);
			arg0.ank = arg0.ank << 8 | data & 0xff;
			data = aef(arg0);
			arg0.ank = arg0.ank << 8 | data & 0xff;
			data = aef(arg0);
			arg0.ank = arg0.ank << 8 | data & 0xff;
			for (int k = 0; k < 16; k++) {
				byte byte1 = aej(arg0);
				if (byte1 == 1)
					arg0.bag[k] = true;
				else
					arg0.bag[k] = false;
			}

			for (int l = 0; l < 256; l++)
				arg0.baf[l] = false;

			for (int i1 = 0; i1 < 16; i1++)
				if (arg0.bag[i1]) {
					for (int j3 = 0; j3 < 16; j3++) {
						byte byte2 = aej(arg0);
						if (byte2 == 1)
							arg0.baf[i1 * 16 + j3] = true;
					}

				}

			aei(arg0);
			int j4 = arg0.bae + 2;
			int k4 = aen(3, arg0);
			int l4 = aen(15, arg0);
			for (int j1 = 0; j1 < l4; j1++) {
				int k3 = 0;
				do {
					byte byte3 = aej(arg0);
					if (byte3 == 0)
						break;
					k3++;
				} while (true);
				arg0.bal[j1] = (byte) k3;
			}

			byte abyte0[] = new byte[6];
			for (byte byte16 = 0; byte16 < k4; byte16++)
				abyte0[byte16] = byte16;

			for (int k1 = 0; k1 < l4; k1++) {
				byte byte17 = arg0.bal[k1];
				byte byte15 = abyte0[byte17];
				for (; byte17 > 0; byte17--)
					abyte0[byte17] = abyte0[byte17 - 1];

				abyte0[0] = byte15;
				arg0.bak[k1] = byte15;
			}

			for (int l3 = 0; l3 < k4; l3++) {
				int i7 = aen(5, arg0);
				for (int l1 = 0; l1 < j4; l1++) {
					do {
						byte byte4 = aej(arg0);
						if (byte4 == 0)
							break;
						byte4 = aej(arg0);
						if (byte4 == 0)
							i7++;
						else
							i7--;
					} while (true);
					arg0.bam[l3][l1] = (byte) i7;
				}

			}

			for (int i4 = 0; i4 < k4; i4++) {
				byte byte8 = 32;
				int i = 0;
				for (int i2 = 0; i2 < j4; i2++) {
					if (arg0.bam[i4][i2] > i)
						i = arg0.bam[i4][i2];
					if (arg0.bam[i4][i2] < byte8)
						byte8 = arg0.bam[i4][i2];
				}

				ael(arg0.ban[i4], arg0.bba[i4], arg0.bbb[i4], arg0.bam[i4],
						byte8, i, j4);
				arg0.bbc[i4] = byte8;
			}

			int i5 = arg0.bae + 1;
			int j5 = -1;
			int k5 = 0;
			for (int j2 = 0; j2 <= 255; j2++)
				arg0.ann[j2] = 0;

			int i10 = 4095;
			for (int i9 = 15; i9 >= 0; i9--) {
				for (int k9 = 15; k9 >= 0; k9--) {
					arg0.bai[i10] = (byte) (i9 * 16 + k9);
					i10--;
				}

				arg0.baj[i9] = i10 + 1;
			}

			int j6 = 0;
			if (k5 == 0) {
				j5++;
				k5 = 50;
				byte byte12 = arg0.bak[j5];
				l8 = arg0.bbc[byte12];
				ai = arg0.ban[byte12];
				ai2 = arg0.bbb[byte12];
				ai1 = arg0.bba[byte12];
			}
			k5--;
			int j7 = l8;
			int i8;
			byte byte9;
			for (i8 = aen(j7, arg0); i8 > ai[j7]; i8 = i8 << 1 | byte9) {
				j7++;
				byte9 = aej(arg0);
			}

			for (int l5 = ai2[i8 - ai1[j7]]; l5 != i5;)
				if (l5 == 0 || l5 == 1) {
					int k6 = -1;
					int l6 = 1;
					do {
						if (l5 == 0)
							k6 += 1 * l6;
						else if (l5 == 1)
							k6 += 2 * l6;
						l6 *= 2;
						if (k5 == 0) {
							j5++;
							k5 = 50;
							byte byte13 = arg0.bak[j5];
							l8 = arg0.bbc[byte13];
							ai = arg0.ban[byte13];
							ai2 = arg0.bbb[byte13];
							ai1 = arg0.bba[byte13];
						}
						k5--;
						int k7 = l8;
						int j8;
						byte byte10;
						for (j8 = aen(k7, arg0); j8 > ai[k7]; j8 = j8 << 1
								| byte10) {
							k7++;
							byte10 = aej(arg0);
						}

						l5 = ai2[j8 - ai1[k7]];
					} while (l5 == 0 || l5 == 1);
					k6++;
					byte byte5 = arg0.bah[arg0.bai[arg0.baj[0]] & 0xff];
					arg0.ann[byte5 & 0xff] += k6;
					for (; k6 > 0; k6--) {
						BZip2DecompressionState.bad[j6] = byte5 & 0xff;
						j6++;
					}

				} else {
					int k11 = l5 - 1;
					byte byte6;
					if (k11 < 16) {
						int k10 = arg0.baj[0];
						byte6 = arg0.bai[k10 + k11];
						for (; k11 > 3; k11 -= 4) {
							int l11 = k10 + k11;
							arg0.bai[l11] = arg0.bai[l11 - 1];
							arg0.bai[l11 - 1] = arg0.bai[l11 - 2];
							arg0.bai[l11 - 2] = arg0.bai[l11 - 3];
							arg0.bai[l11 - 3] = arg0.bai[l11 - 4];
						}

						for (; k11 > 0; k11--)
							arg0.bai[k10 + k11] = arg0.bai[(k10 + k11) - 1];

						arg0.bai[k10] = byte6;
					} else {
						int i11 = k11 / 16;
						int j11 = k11 % 16;
						int l10 = arg0.baj[i11] + j11;
						byte6 = arg0.bai[l10];
						for (; l10 > arg0.baj[i11]; l10--)
							arg0.bai[l10] = arg0.bai[l10 - 1];

						arg0.baj[i11]++;
						for (; i11 > 0; i11--) {
							arg0.baj[i11]--;
							arg0.bai[arg0.baj[i11]] = arg0.bai[(arg0.baj[i11 - 1] + 16) - 1];
						}

						arg0.baj[0]--;
						arg0.bai[arg0.baj[0]] = byte6;
						if (arg0.baj[0] == 0) {
							int j10 = 4095;
							for (int j9 = 15; j9 >= 0; j9--) {
								for (int l9 = 15; l9 >= 0; l9--) {
									arg0.bai[j10] = arg0.bai[arg0.baj[j9] + l9];
									j10--;
								}

								arg0.baj[j9] = j10 + 1;
							}

						}
					}
					arg0.ann[arg0.bah[byte6 & 0xff] & 0xff]++;
					BZip2DecompressionState.bad[j6] = arg0.bah[byte6 & 0xff] & 0xff;
					j6++;
					if (k5 == 0) {
						j5++;
						k5 = 50;
						byte byte14 = arg0.bak[j5];
						l8 = arg0.bbc[byte14];
						ai = arg0.ban[byte14];
						ai2 = arg0.bbb[byte14];
						ai1 = arg0.bba[byte14];
					}
					k5--;
					int l7 = l8;
					int k8;
					byte byte11;
					for (k8 = aen(l7, arg0); k8 > ai[l7]; k8 = k8 << 1 | byte11) {
						l7++;
						byte11 = aej(arg0);
					}

					l5 = ai2[k8 - ai1[l7]];
				}

			arg0.ane = 0;
			arg0.and = 0;
			arg0.bab[0] = 0;
			for (int k2 = 1; k2 <= 256; k2++)
				arg0.bab[k2] = arg0.ann[k2 - 1];

			for (int l2 = 1; l2 <= 256; l2++)
				arg0.bab[l2] += arg0.bab[l2 - 1];

			for (int i3 = 0; i3 < j6; i3++) {
				byte byte7 = (byte) (BZip2DecompressionState.bad[i3] & 0xff);
				BZip2DecompressionState.bad[arg0.bab[byte7 & 0xff]] |= i3 << 8;
				arg0.bab[byte7 & 0xff]++;
			}

			arg0.anl = BZip2DecompressionState.bad[arg0.ank] >> 8;
			arg0.baa = 0;
			arg0.anl = BZip2DecompressionState.bad[arg0.anl];
			arg0.anm = (byte) (arg0.anl & 0xff);
			arg0.anl >>= 8;
			arg0.baa++;
			arg0.bbd = j6;
			aeh(arg0);
			if (arg0.baa == arg0.bbd + 1 && arg0.ane == 0)
				flag20 = true;
			else
				flag20 = false;
		}
	}

	private static void aeh(BZip2DecompressionState arg0) {
		byte byte4 = arg0.and;
		int i = arg0.ane;
		int k = arg0.baa;
		int l = arg0.anm;
		int ai[] = BZip2DecompressionState.bad;
		int i1 = arg0.anl;
		byte abyte0[] = arg0.amm;
		int j1 = arg0.amn;
		int k1 = arg0.ana;
		int l1 = k1;
		int i2 = arg0.bbd + 1;
		label0: do {
			if (i > 0) {
				do {
					if (k1 == 0)
						break label0;
					if (i == 1)
						break;
					abyte0[j1] = byte4;
					i--;
					j1++;
					k1--;
				} while (true);
				if (k1 == 0) {
					i = 1;
					break;
				}
				abyte0[j1] = byte4;
				j1++;
				k1--;
			}
			boolean flag = true;
			while (flag) {
				flag = false;
				if (k == i2) {
					i = 0;
					break label0;
				}
				byte4 = (byte) l;
				i1 = ai[i1];
				byte byte0 = (byte) (i1 & 0xff);
				i1 >>= 8;
				k++;
				if (byte0 != l) {
					l = byte0;
					if (k1 == 0) {
						i = 1;
					} else {
						abyte0[j1] = byte4;
						j1++;
						k1--;
						flag = true;
						continue;
					}
					break label0;
				}
				if (k != i2)
					continue;
				if (k1 == 0) {
					i = 1;
					break label0;
				}
				abyte0[j1] = byte4;
				j1++;
				k1--;
				flag = true;
			}
			i = 2;
			i1 = ai[i1];
			byte byte1 = (byte) (i1 & 0xff);
			i1 >>= 8;
			if (++k != i2)
				if (byte1 != l) {
					l = byte1;
				} else {
					i = 3;
					i1 = ai[i1];
					byte byte2 = (byte) (i1 & 0xff);
					i1 >>= 8;
					if (++k != i2)
						if (byte2 != l) {
							l = byte2;
						} else {
							i1 = ai[i1];
							byte byte3 = (byte) (i1 & 0xff);
							i1 >>= 8;
							k++;
							i = (byte3 & 0xff) + 4;
							i1 = ai[i1];
							l = (byte) (i1 & 0xff);
							i1 >>= 8;
							k++;
						}
				}
		} while (true);
		int j2 = arg0.anb;
		arg0.anb += l1 - k1;
		if (arg0.anb < j2)
			arg0.anc++;
		arg0.and = byte4;
		arg0.ane = i;
		arg0.baa = k;
		arg0.anm = l;
		BZip2DecompressionState.bad = ai;
		arg0.anl = i1;
		arg0.amm = abyte0;
		arg0.amn = j1;
		arg0.ana = k1;
	}

	private static void aei(BZip2DecompressionState arg0) {
		arg0.bae = 0;
		for (int i = 0; i < 256; i++)
			if (arg0.baf[i]) {
				arg0.bah[arg0.bae] = (byte) i;
				arg0.bae++;
			}

	}

	private static byte aej(BZip2DecompressionState arg0) {
		return (byte) aen(1, arg0);
	}

	private static void ael(int arg0[], int arg1[], int arg2[], byte arg3[],
			int arg4, int arg5, int arg6) {
		int i = 0;
		for (int k = arg4; k <= arg5; k++) {
			for (int j2 = 0; j2 < arg6; j2++)
				if (arg3[j2] == k) {
					arg2[i] = j2;
					i++;
				}

		}

		for (int l = 0; l < 23; l++)
			arg1[l] = 0;

		for (int i1 = 0; i1 < arg6; i1++)
			arg1[arg3[i1] + 1]++;

		for (int j1 = 1; j1 < 23; j1++)
			arg1[j1] += arg1[j1 - 1];

		for (int k1 = 0; k1 < 23; k1++)
			arg0[k1] = 0;

		int k2 = 0;
		for (int l1 = arg4; l1 <= arg5; l1++) {
			k2 += arg1[l1 + 1] - arg1[l1];
			arg0[l1] = k2 - 1;
			k2 <<= 1;
		}

		for (int i2 = arg4 + 1; i2 <= arg5; i2++)
			arg1[i2] = (arg0[i2 - 1] + 1 << 1) - arg1[i2];

	}

	public static int aem(byte arg0[], int arg1, byte arg2[], int arg3, int arg4) {
		synchronized (aif) {
			aif.amh = arg2;
			aif.ami = arg4;
			aif.amm = arg0;
			aif.amn = 0;
			aif.amj = arg3;
			aif.ana = arg1;
			aif.anh = 0;
			aif.ang = 0;
			aif.amk = 0;
			aif.aml = 0;
			aif.anb = 0;
			aif.anc = 0;
			aif.anj = 0;
			aeg(aif);
			arg1 -= aif.ana;
			int i = arg1;
			return i;
		}
	}

	private static int aen(int arg0, BZip2DecompressionState arg1) {
		int i;
		do {
			if (arg1.anh >= arg0) {
				int k = arg1.ang >> arg1.anh - arg0 & (1 << arg0) - 1;
				arg1.anh -= arg0;
				i = k;
				break;
			}
			arg1.ang = arg1.ang << 8 | arg1.amh[arg1.ami] & 0xff;
			arg1.anh += 8;
			arg1.ami++;
			arg1.amj--;
			arg1.amk++;
			if (arg1.amk == 0)
				arg1.aml++;
		} while (true);
		return i;
	}

	private static BZip2DecompressionState aif = new BZip2DecompressionState();

}
