package org.chaos.map;

public final class IndexedSprite extends Raster {
	
	public byte raster[];
	public int palette[];
	public int width;
	public int height;
	public int drawOffsetX;
	public int drawOffsetY;
	public int resizeWidth;
	public int resizeHeight;

	public IndexedSprite(Archive arg0, String name, int arg2) {
		RSBuffer j1 = new RSBuffer(arg0.abl(name + ".dat", null));
		RSBuffer j2 = new RSBuffer(arg0.abl("index.dat", null));
		j2.position = j1.getUShort();
		resizeWidth = j2.getUShort();
		resizeHeight = j2.getUShort();
		int i = j2.aii();
		palette = new int[i];
		for (int k = 0; k < i - 1; k++)
			palette[k + 1] = j2.aim();

		for (int l = 0; l < arg2; l++) {
			j2.position += 2;
			j1.position += j2.getUShort() * j2.getUShort();
			j2.position++;
		}

		drawOffsetX = j2.aii();
		drawOffsetY = j2.aii();
		width = j2.getUShort();
		height = j2.getUShort();
		int i1 = j2.aii();
		int k1 = width * height;
		raster = new byte[k1];
		if (i1 == 0) {
			for (int l1 = 0; l1 < k1; l1++)
				raster[l1] = j1.aij();

		} else if (i1 == 1) {
			for (int i2 = 0; i2 < width; i2++) {
				for (int k2 = 0; k2 < height; k2++)
					raster[i2 + k2 * width] = j1.aij();

			}
		}
	}

	public void clipSprite(int arg0, int arg1, int arg2, int arg3) {
		try {
			int i = width;
			int k = height;
			int l = 0;
			int i1 = 0;
			int j1 = (i << 16) / arg2;
			int k1 = (k << 16) / arg3;
			int l1 = resizeWidth;
			int i2 = resizeHeight;
			j1 = (l1 << 16) / arg2;
			k1 = (i2 << 16) / arg3;
			arg0 += ((drawOffsetX * arg2 + l1) - 1) / l1;
			arg1 += ((drawOffsetY * arg3 + i2) - 1) / i2;
			if ((drawOffsetX * arg2) % l1 != 0)
				l = (l1 - (drawOffsetX * arg2) % l1 << 16) / arg2;
			if ((drawOffsetY * arg3) % i2 != 0)
				i1 = (i2 - (drawOffsetY * arg3) % i2 << 16) / arg3;
			arg2 = (arg2 * (width - (l >> 16))) / l1;
			arg3 = (arg3 * (height - (i1 >> 16))) / i2;
			int j2 = arg0 + arg1 * Raster.width;
			int k2 = Raster.width - arg2;
			if (arg1 < Raster.bbh) {
				int l2 = Raster.bbh - arg1;
				arg3 -= l2;
				arg1 = 0;
				j2 += l2 * Raster.width;
				i1 += k1 * l2;
			}
			if (arg1 + arg3 > Raster.bbi)
				arg3 -= (arg1 + arg3) - Raster.bbi;
			if (arg0 < Raster.bbj) {
				int i3 = Raster.bbj - arg0;
				arg2 -= i3;
				arg0 = 0;
				j2 += i3;
				l += j1 * i3;
				k2 += i3;
			}
			if (arg0 + arg2 > Raster.bbk) {
				int j3 = (arg0 + arg2) - Raster.bbk;
				arg2 -= j3;
				k2 += j3;
			}
			plotScale(Raster.pixels, raster, palette, l, i1, j2, k2, arg2, arg3, j1, k1, i);
		} catch (Exception exception) {
			System.out.println("error in sprite clipping routine");
		}
	}

	private void plotScale(int arg0[], byte arg1[], int arg2[], int arg3, int arg4,
			int arg5, int arg6, int arg7, int arg8, int arg9, int arg10,
			int arg11) {
		try {
			int i = arg3;
			for (int k = -arg8; k < 0; k++) {
				int l = (arg4 >> 16) * arg11;
				for (int i1 = -arg7; i1 < 0; i1++) {
					byte byte0 = arg1[(arg3 >> 16) + l];
					if (byte0 != 0)
						arg0[arg5++] = arg2[byte0 & 0xff];
					else
						arg5++;
					arg3 += arg9;
				}

				arg4 += arg10;
				arg3 = i;
				arg5 += arg6;
			}
		} catch (Exception exception) {
			System.out.println("error in plot_scale");
		}
	}
}
