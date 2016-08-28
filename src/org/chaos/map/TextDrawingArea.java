package org.chaos.map;

import java.util.Random;

public final class TextDrawingArea extends Raster {
	
	byte indexedPixels[][];
	int widths[];
	int heights[];
	int ahn[];
	int aia[];
	public int charWidths[];
	public int lastKnownHeight;
	Random aid;
	boolean aie;

	public int getTextWidth(String text) {
		if (text == null)
			return 0;
		int width = 0;
		for (int character = 0; character < text.length(); character++)
			width += charWidths[text.charAt(character)];
		return width;
	}

	public void drawString(String text, int width, int height, int arg3) {
		if (text == null)
			return;
		height -= lastKnownHeight;
		for (int k = 0; k < text.length(); k++) {
			char c1 = text.charAt(k);
			if (c1 != ' ')
				aed(indexedPixels[c1], width + ahn[c1], height + aia[c1], widths[c1], heights[c1],
						arg3);
			width += charWidths[c1];
		}
	}

	public TextDrawingArea(Archive archive, String file, boolean arg2) {
		indexedPixels = new byte[256][];
		widths = new int[256];
		heights = new int[256];
		ahn = new int[256];
		aia = new int[256];
		charWidths = new int[256];
		lastKnownHeight = 0;
		aid = new Random();
		aie = false;
		RSBuffer buffer = new RSBuffer(archive.abl(file + ".dat", null));
		RSBuffer index = new RSBuffer(archive.abl("index.dat", null));
		byte byte0 = -1;
		index.position = buffer.getUShort() + 4;
		int l = index.aii();
		if (l > 0)
			index.position += 3 * (l - 1);
		for (int i1 = 0; i1 < 256; i1++) {
			int k = i1;
			ahn[i1] = index.aii();
			aia[i1] = index.aii();
			int k1 = widths[i1] = index.getUShort();
			int l1 = heights[i1] = index.getUShort();
			int i2 = index.aii();
			int k2 = k1 * l1;
			indexedPixels[i1] = new byte[k2];
			if (i2 == 0) {
				for (int l2 = 0; l2 < k2; l2++)
					indexedPixels[i1][l2] = buffer.aij();

			} else if (i2 == 1) {
				for (int i3 = 0; i3 < k1; i3++) {
					for (int k3 = 0; k3 < l1; k3++)
						indexedPixels[i1][i3 + k3 * k1] = buffer.aij();

				}

			}
			if (l1 > lastKnownHeight && i1 < 128)
				lastKnownHeight = l1;
			ahn[i1] = 1;
			charWidths[i1] = k1 + 2;
			int j3 = 0;
			for (int l3 = l1 / 7; l3 < l1; l3++)
				j3 += indexedPixels[i1][l3 * k1];

			if (j3 <= l1 / 7) {
				charWidths[i1]--;
				ahn[i1] = 0;
			}
			j3 = 0;
			for (int i4 = l1 / 7; i4 < l1; i4++)
				j3 += indexedPixels[i1][(k1 - 1) + i4 * k1];

			if (j3 <= l1 / 7)
				charWidths[i1]--;
		}

		if (arg2)
			charWidths[32] = charWidths[73];
		else
			charWidths[32] = charWidths[105];
	}

	public void drawRightAlignedString(String text, int width, int height, int arg3) {
		drawString(text, width - getTextWidth(text), height, arg3);
	}

	public void drawCenteredString(String text, int width, int height, int arg3) {
		drawString(text, width - getTextWidth(text) / 2, height, arg3);
	}

	private void aed(byte arg0[], int arg1, int arg2, int arg3, int arg4,
			int arg5) {
		int k = arg1 + arg2 * Raster.width;
		int l = Raster.width - arg3;
		int i1 = 0;
		int j1 = 0;
		if (arg2 < Raster.bbh) {
			int k1 = Raster.bbh - arg2;
			arg4 -= k1;
			arg2 = Raster.bbh;
			j1 += k1 * arg3;
			k += k1 * Raster.width;
		}
		if (arg2 + arg4 >= Raster.bbi)
			arg4 -= ((arg2 + arg4) - Raster.bbi) + 1;
		if (arg1 < Raster.bbj) {
			int l1 = Raster.bbj - arg1;
			arg3 -= l1;
			arg1 = Raster.bbj;
			j1 += l1;
			k += l1;
			i1 += l1;
			l += l1;
		}
		if (arg1 + arg3 >= Raster.bbk) {
			int i2 = ((arg1 + arg3) - Raster.bbk) + 1;
			arg3 -= i2;
			i1 += i2;
			l += i2;
		}
		if (arg3 <= 0 || arg4 <= 0) {
			return;
		} else {
			aee(Raster.pixels, arg0, arg5, j1, k, arg3, arg4, l, i1);
			return;
		}
	}

	private void aee(int arg0[], byte arg1[], int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7, int arg8) {
		int k = -(arg5 >> 2);
		arg5 = -(arg5 & 3);
		for (int l = -arg6; l < 0; l++) {
			for (int i1 = k; i1 < 0; i1++) {
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

			for (int j1 = arg5; j1 < 0; j1++)
				if (arg1[arg3++] != 0)
					arg0[arg4++] = arg2;
				else
					arg4++;

			arg4 += arg7;
			arg3 += arg8;
		}
	}
}
