package org.chaos.map;

import java.awt.*;
import java.awt.image.PixelGrabber;

public class RSFont extends Raster {

	private void ahk(Font arg0, FontMetrics arg1, char arg2, int arg3,
			boolean arg4, EventHandler arg5) {
		int i = arg1.charWidth(arg2);
		int j = i;
		if (arg4)
			try {
				if (arg2 == '/')
					arg4 = false;
				if (arg2 == 'f' || arg2 == 't' || arg2 == 'w' || arg2 == 'v'
						|| arg2 == 'k' || arg2 == 'x' || arg2 == 'y'
						|| arg2 == 'A' || arg2 == 'V' || arg2 == 'W')
					i++;
			} catch (Exception exception) {
			}
		int k = arg1.getMaxAscent();
		int l = arg1.getMaxAscent() + arg1.getMaxDescent();
		int i1 = arg1.getHeight();
		Image image = arg5.getGameComponent().createImage(i, l);
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, i, l);
		g.setColor(Color.white);
		g.setFont(arg0);
		g.drawString(arg2 + "", 0, k);
		if (arg4)
			g.drawString(arg2 + "", 1, k);
		int ai[] = new int[i * l];
		PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, i, l, ai, 0,
				i);
		try {
			pixelgrabber.grabPixels();
		} catch (Exception exception1) {
		}
		image.flush();
		image = null;
		int j1 = 0;
		int k1 = 0;
		int l1 = i;
		int i2 = l;
		label0: for (int j2 = 0; j2 < l; j2++) {
			for (int k3 = 0; k3 < i; k3++) {
				int l4 = ai[k3 + j2 * i];
				if ((l4 & 0xffffff) == 0)
					continue;
				k1 = j2;
				break label0;
			}

		}

		label1: for (int k2 = 0; k2 < i; k2++) {
			for (int l3 = 0; l3 < l; l3++) {
				int i5 = ai[k2 + l3 * i];
				if ((i5 & 0xffffff) == 0)
					continue;
				j1 = k2;
				break label1;
			}

		}

		label2: for (int l2 = l - 1; l2 >= 0; l2--) {
			for (int i4 = 0; i4 < i; i4++) {
				int j5 = ai[i4 + l2 * i];
				if ((j5 & 0xffffff) == 0)
					continue;
				i2 = l2 + 1;
				break label2;
			}

		}

		label3: for (int i3 = i - 1; i3 >= 0; i3--) {
			for (int j4 = 0; j4 < l; j4++) {
				int k5 = ai[i3 + j4 * i];
				if ((k5 & 0xffffff) == 0)
					continue;
				l1 = i3 + 1;
				break label3;
			}

		}

		akl[arg3 * 9] = (byte) (akk / 16384);
		akl[arg3 * 9 + 1] = (byte) (akk / 128 & 0x7f);
		akl[arg3 * 9 + 2] = (byte) (akk & 0x7f);
		akl[arg3 * 9 + 3] = (byte) (l1 - j1);
		akl[arg3 * 9 + 4] = (byte) (i2 - k1);
		akl[arg3 * 9 + 5] = (byte) j1;
		akl[arg3 * 9 + 6] = (byte) (k - k1);
		akl[arg3 * 9 + 7] = (byte) j;
		akl[arg3 * 9 + 8] = (byte) i1;
		for (int j3 = k1; j3 < i2; j3++) {
			for (int k4 = j1; k4 < l1; k4++) {
				int l5 = ai[k4 + j3 * i] & 0xff;
				if (l5 > 30 && l5 < 230)
					akj = true;
				akl[akk++] = (byte) l5;
			}

		}

	}

	public void ahl(String arg0, int arg1, int arg2, int arg3, boolean arg4) {
		try {
			if (akj || arg3 == 0)
				arg4 = false;
			for (int i = 0; i < arg0.length(); i++) {
				int j = akm[arg0.charAt(i)];
				if (arg4) {
					aie(j, arg1 + 1, arg2, 0, akl, akj);
					aie(j, arg1, arg2 + 1, 0, akl, akj);
				}
				aie(j, arg1, arg2, arg3, akl, akj);
				arg1 += akl[j + 7];
			}

		} catch (Exception exception) {
			System.out.println("drawstring: " + exception);
			exception.printStackTrace();
		}
	}

	public RSFont(int arg0, boolean arg1, EventHandler arg2) {
		akj = false;
		akk = 0;
		akl = new byte[0x186a0];
		akk = 855;
		akj = false;
		Font font = new Font("Helvetica", arg1 ? 1 : 0, arg0);
		FontMetrics fontmetrics = arg2.getFontMetrics(font);
		for (int i = 0; i < 95; i++)
			ahk(font,
					fontmetrics,
					"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| "
							.charAt(i), i, false, arg2);

		if (arg1 && akj) {
			akk = 855;
			akj = false;
			Font font1 = new Font("Helvetica", 0, arg0);
			FontMetrics fontmetrics1 = arg2.getFontMetrics(font1);
			for (int j = 0; j < 95; j++)
				ahk(font1,
						fontmetrics1,
						"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| "
								.charAt(j), j, false, arg2);

			if (!akj) {
				akk = 855;
				akj = false;
				for (int k = 0; k < 95; k++)
					ahk(font1,
							fontmetrics1,
							"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| "
									.charAt(k), k, true, arg2);

			}
		}
		byte abyte0[] = new byte[akk];
		for (int l = 0; l < akk; l++)
			abyte0[l] = akl[l];

		akl = abyte0;
	}

	public void ahn(String arg0, int arg1, int arg2, int arg3, boolean arg4) {
		int i = aic(arg0) / 2;
		int j = aig();
		if (arg1 - i > Raster.bbk)
			return;
		if (arg1 + i < Raster.bbj)
			return;
		if (arg2 - j > Raster.bbi)
			return;
		if (arg2 < 0) {
			return;
		} else {
			ahl(arg0, arg1 - i, arg2, arg3, arg4);
			return;
		}
	}

	private void aia(int arg0[], byte arg1[], int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7, int arg8) {
		for (int i = -arg6; i < 0; i++) {
			for (int j = -arg5; j < 0; j++) {
				int k = arg1[arg3++] & 0xff;
				if (k > 30) {
					if (k >= 230) {
						arg0[arg4++] = arg2;
					} else {
						int l = arg0[arg4];
						arg0[arg4++] = ((arg2 & 0xff00ff) * k + (l & 0xff00ff)
								* (256 - k) & 0xff00ff00)
								+ ((arg2 & 0xff00) * k + (l & 0xff00)
										* (256 - k) & 0xff0000) >> 8;
					}
				} else {
					arg4++;
				}
			}

			arg4 += arg7;
			arg3 += arg8;
		}

	}

	public int aib() {
		return akl[8] - 1;
	}

	public int aic(String arg0) {
		int i = 0;
		for (int j = 0; j < arg0.length(); j++)
			if (arg0.charAt(j) == '@' && j + 4 < arg0.length()
					&& arg0.charAt(j + 4) == '@')
				j += 4;
			else if (arg0.charAt(j) == '~' && j + 4 < arg0.length()
					&& arg0.charAt(j + 4) == '~')
				j += 4;
			else
				i += akl[akm[arg0.charAt(j)] + 7];

		return i;
	}

	private void aie(int arg0, int arg1, int arg2, int arg3, byte arg4[],
			boolean arg5) {
		int i = arg1 + arg4[arg0 + 5];
		int j = arg2 - arg4[arg0 + 6];
		int k = arg4[arg0 + 3];
		int l = arg4[arg0 + 4];
		int i1 = arg4[arg0] * 16384 + arg4[arg0 + 1] * 128 + arg4[arg0 + 2];
		int j1 = i + j * Raster.width;
		int k1 = Raster.width - k;
		int l1 = 0;
		if (j < Raster.bbh) {
			int i2 = Raster.bbh - j;
			l -= i2;
			j = Raster.bbh;
			i1 += i2 * k;
			j1 += i2 * Raster.width;
		}
		if (j + l >= Raster.bbi)
			l -= ((j + l) - Raster.bbi) + 1;
		if (i < Raster.bbj) {
			int j2 = Raster.bbj - i;
			k -= j2;
			i = Raster.bbj;
			i1 += j2;
			j1 += j2;
			l1 += j2;
			k1 += j2;
		}
		if (i + k >= Raster.bbk) {
			int k2 = ((i + k) - Raster.bbk) + 1;
			k -= k2;
			l1 += k2;
			k1 += k2;
		}
		if (k > 0 && l > 0)
			if (arg5)
				aia(Raster.pixels, arg4, arg3, i1, j1, k, l, k1, l1);
			else
				aif(Raster.pixels, arg4, arg3, i1, j1, k, l, k1, l1);
	}

	private void aif(int arg0[], byte arg1[], int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7, int arg8) {
		try {
			int i = -(arg5 >> 2);
			arg5 = -(arg5 & 3);
			for (int j = -arg6; j < 0; j++) {
				for (int k = i; k < 0; k++) {
					if (arg1[arg3++] != 0)
						arg0[arg4++] = arg2;
					else
						arg4++;
					if (arg1[arg3++] != 0)
						arg0[arg4++] = arg2;
					else
						arg4++;
					if (arg1[arg3++] != 0)
						arg0[arg4++] = arg2;
					else
						arg4++;
					if (arg1[arg3++] != 0)
						arg0[arg4++] = arg2;
					else
						arg4++;
				}

				for (int l = arg5; l < 0; l++)
					if (arg1[arg3++] != 0)
						arg0[arg4++] = arg2;
					else
						arg4++;

				arg4 += arg7;
				arg3 += arg8;
			}

		} catch (Exception exception) {
			System.out.println("plotletter: " + exception);
			exception.printStackTrace();
		}
	}

	public int aig() {
		return akl[6];
	}

	boolean akj;
	int akk;
	byte akl[];
	static int akm[];

	static {
		akm = new int[256];
		for (int i = 0; i < 256; i++) {
			int j = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| "
					.indexOf(i);
			if (j == -1)
				j = 74;
			akm[i] = j * 9;
		}
	}
}
