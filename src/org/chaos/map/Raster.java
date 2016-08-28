package org.chaos.map;

public class Raster extends Cacheable {
	
	public static int pixels[];
	public static int width;
	public static int height;
	public static int bbh = 0;
	public static int bbi = 0;
	public static int bbj = 0;
	public static int bbk = 0;
	public static int bbl;
	public static int bbm;
	public static int bbn;
	public static int bca;

	public static void drawHorizontal(int x, int y, int length, int color) {
		if (y < bbh || y >= bbi)
			return;
		if (x < bbj) {
			length -= bbj - x;
			x = bbj;
		}
		if (x + length > bbk)
			length = bbk - x;
		int pixelIndex = x + y * width;
		for (int pixelOffset = 0; pixelOffset < length; pixelOffset++)
			pixels[pixelIndex + pixelOffset] = color;
	}

	public static void drawVertical(int arg0, int arg1, int arg2, int arg3) {
		if (arg0 < bbj || arg0 >= bbk)
			return;
		if (arg1 < bbh) {
			arg2 -= bbh - arg1;
			arg1 = bbh;
		}
		if (arg1 + arg2 > bbi)
			arg2 = bbi - arg1;
		int i = arg0 + arg1 * width;
		for (int j = 0; j < arg2; j++)
			pixels[i + j * width] = arg3;

	}

	public static void setBounds(int arg0, int arg1, int arg2, int arg3) {
		if (arg0 < 0)
			arg0 = 0;
		if (arg1 < 0)
			arg1 = 0;
		if (arg2 > width)
			arg2 = width;
		if (arg3 > height)
			arg3 = height;
		bbj = arg0;
		bbh = arg1;
		bbk = arg2;
		bbi = arg3;
		bbl = bbk - 1;
		bbm = bbk / 2;
		bbn = bbi / 2;
	}

	public static void fillOpacRect(int arg0, int arg1, int arg2, int arg3, int arg4,
			int arg5) {
		if (arg0 < bbj) {
			arg2 -= bbj - arg0;
			arg0 = bbj;
		}
		if (arg1 < bbh) {
			arg3 -= bbh - arg1;
			arg1 = bbh;
		}
		if (arg0 + arg2 > bbk)
			arg2 = bbk - arg0;
		if (arg1 + arg3 > bbi)
			arg3 = bbi - arg1;
		int i = 256 - arg5;
		int j = (arg4 >> 16 & 0xff) * arg5;
		int k = (arg4 >> 8 & 0xff) * arg5;
		int l = (arg4 & 0xff) * arg5;
		int l1 = width - arg2;
		int i2 = arg0 + arg1 * width;
		for (int j2 = 0; j2 < arg3; j2++) {
			for (int k2 = -arg2; k2 < 0; k2++) {
				int i1 = (pixels[i2] >> 16 & 0xff) * i;
				int j1 = (pixels[i2] >> 8 & 0xff) * i;
				int k1 = (pixels[i2] & 0xff) * i;
				int l2 = ((j + i1 >> 8) << 16) + ((k + j1 >> 8) << 8)
						+ (l + k1 >> 8);
				pixels[i2++] = l2;
			}

			i2 += l1;
		}

	}

	public static void drawRectangle(int x, int y, int width, int height, int color) {
		drawHorizontal(x, y, width, color);
		drawHorizontal(x, (y + height) - 1, width, color);
		drawVertical(x, y, height, color);
		drawVertical((x + width) - 1, y, height, color);
	}

	public static void setArea(int arg0[], int arg1, int arg2) {
		pixels = arg0;
		width = arg1;
		height = arg2;
		setBounds(0, 0, arg1, arg2);
	}

	public static void fillRectangle(int x, int y, int arg2, int arg3, int color) {
		if (x < bbj) {
			arg2 -= bbj - x;
			x = bbj;
		}
		if (y < bbh) {
			arg3 -= bbh - y;
			y = bbh;
		}
		if (x + arg2 > bbk)
			arg2 = bbk - x;
		if (y + arg3 > bbi)
			arg3 = bbi - y;
		int i = width - arg2;
		boolean flag = true;
		int j = x + y * width;
		for (int index = -arg3; index < 0; index++) {
			for (int l = -arg2; l < 0; l++)
				pixels[j++] = color;
			j += i;
		}
	}

	public static void clear() {
		int i = width * height;
		for (int j = 0; j < i; j++)
			pixels[j] = 0;
	}

	public static void drawDot(int arg0, int arg1, int arg2, int arg3, int arg4) {
		int i = 256 - arg4;
		int j = (arg3 >> 16 & 0xff) * arg4;
		int k = (arg3 >> 8 & 0xff) * arg4;
		int l = (arg3 & 0xff) * arg4;
		int l1 = arg1 - arg2;
		if (l1 < 0)
			l1 = 0;
		int i2 = arg1 + arg2;
		if (i2 >= height)
			i2 = height - 1;
		for (int j2 = l1; j2 <= i2; j2++) {
			int k2 = j2 - arg1;
			int l2 = (int) Math.sqrt(arg2 * arg2 - k2 * k2);
			int i3 = arg0 - l2;
			if (i3 < 0)
				i3 = 0;
			int j3 = arg0 + l2;
			if (j3 >= width)
				j3 = width - 1;
			int k3 = i3 + j2 * width;
			for (int l3 = i3; l3 <= j3; l3++) {
				int i1 = (pixels[k3] >> 16 & 0xff) * i;
				int j1 = (pixels[k3] >> 8 & 0xff) * i;
				int k1 = (pixels[k3] & 0xff) * i;
				int i4 = ((j + i1 >> 8) << 16) + ((k + j1 >> 8) << 8)
						+ (l + k1 >> 8);
				pixels[k3++] = i4;
			}
		}
	}
}
