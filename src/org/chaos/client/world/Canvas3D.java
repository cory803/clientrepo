package org.chaos.client.world;

import org.chaos.Configuration;
import org.chaos.client.Client;
import org.chaos.client.cache.Archive;
import org.chaos.client.graphics.Background;
import org.chaos.client.graphics.Canvas2D;

public final class Canvas3D extends Canvas2D {
	
	public static boolean lowDetail = true;
	private static int mipMapLevel;
	public static boolean mipmapping = true;
	public static int textureAmount = 51;
	static boolean restrict_edges;
	private static boolean aBoolean1463;
	public static boolean notTextured = true;
	public static int alpha;
	public static int centerX;
	public static int centerY;
	private static int[] anIntArray1468;
	public static final int[] anIntArray1469;
	public static int SINE[];
	public static int COSINE[];
	public static int lineOffsets[];
	private static int anInt1473;
	public static Background aBackgroundArray1474s[] = new Background[textureAmount];
	private static boolean[] aBooleanArray1475 = new boolean[textureAmount];
	private static int[] anIntArray1476 = new int[textureAmount];
	private static int anInt1477;
	private static int[][][] anIntArrayArray1478;
	private static int[][][] anIntArrayArray1479 = new int[textureAmount][][];
	public static int anIntArray1480[] = new int[textureAmount];
	public static int anInt1481;
	public static int anIntArray1482[] = new int[0x10000];
	private static int[][] anIntArrayArray1483 = new int[textureAmount][];

    static {
        anIntArray1468 = new int[512];
        anIntArray1469 = new int[2048];
        SINE = new int[2048];
        COSINE = new int[2048];
        for (int i = 1; i < 512; i++) {
            anIntArray1468[i] = 32768 / i;
        }
        for (int j = 1; j < 2048; j++) {
            anIntArray1469[j] = 0x10000 / j;
        }
        for (int k = 0; k < 2048; k++) {
            SINE[k] = (int)(65536D * Math.sin((double) k * 0.0030679614999999999D));
            COSINE[k] = (int)(65536D * Math.cos((double) k * 0.0030679614999999999D));
        }
    }

	public static void nullify() {
		anIntArray1468 = null;
		anIntArray1468 = null;
		SINE = null;
		COSINE = null;
		lineOffsets = null;
		aBackgroundArray1474s = null;
		aBooleanArray1475 = null;
		anIntArray1476 = null;
		anIntArrayArray1478 = null;
		anIntArrayArray1479 = null;
		anIntArray1480 = null;
		anIntArray1482 = null;
		anIntArrayArray1483 = null;
	}
	
	private static int[] OFFSETS_512_334 = null;
	private static int[] OFFSETS_765_503 = null;
	
	public static int[] getOffsets(int j, int k) {
		if (j == 512 && k == 334 && OFFSETS_512_334 != null) {
			return OFFSETS_512_334;
		}
		if (j == 765 + 1 && k == 503 && OFFSETS_765_503 != null) {
			return OFFSETS_765_503;
		}
		int[] t = new int[k];
		for (int l = 0; l < k; l++) {
			t[l] = j * l;
		}
		if (j == 512 && k == 334) {
			OFFSETS_512_334 = t;
		}
		if (j == 765 + 1 && k == 503) {
			OFFSETS_765_503 = t;
		}
		return t;
	}

	/**
	 * Set default bounds
	 */
    public static void method364() {
        lineOffsets = new int[Canvas2D.height];
        for (int j = 0; j < Canvas2D.height; j++) {
            lineOffsets[j] = Canvas2D.width * j;
        }
        centerX = Canvas2D.width / 2;
        centerY = Canvas2D.height / 2;
    }

    public static void method365(int width, int height) {
        lineOffsets = new int[height];
        for (int l = 0; l < height; l++) {
            lineOffsets[l] = width * l;
        }
        centerX = width / 2;
        centerY = height / 2;
    }
    
    public static void drawFog(int rgb, int begin, int end) {
    	for (int depth = depthBuffer.length - 1; depth >= 0; depth--) {
    		if (depthBuffer[depth] >= end) {
    			pixels[depth] = rgb;
    		} else if (depthBuffer[depth] >= begin) {
    			int alpha = (depthBuffer[depth] - begin) / 3;
    			int src = ((rgb & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((rgb & 0xff00) * alpha >> 8 & 0xff00);
    			alpha = 256 - alpha;
    			int dst = pixels[depth];
    			dst = ((dst & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((dst & 0xff00) * alpha >> 8 & 0xff00);
    			pixels[depth] = src + dst;
    		}
    	}
    }
    
    private static int texelPos(int defaultIndex) {
		int x = (defaultIndex & 127) >> mipMapLevel;
		int y = (defaultIndex >> 7) >> mipMapLevel;
		return x + (y << (7 - mipMapLevel));
	}
    
    private static void setMipmapLevel(int y1, int y2, int y3, int x1, int x2, int x3, int tex) {
		if (!notTextured) {
			mipMapLevel = 0;
			return;
		}
		if (!mipmapping) {
			if (mipMapLevel != 0) {
				mipMapLevel = 0;
			}
			return;
		}
		int[] ids = { 17, 34, 40 };
		for (int tex2 : ids) {
			if (tex == tex2) {
				mipMapLevel = 0;
				return;
			}
		}
		int textureArea = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2) >> 1;
		if (textureArea < 0) {
			textureArea = -textureArea;
		}
		if (textureArea > 16384) {
			mipMapLevel = 0;
		} else if (textureArea > 4096) {
			mipMapLevel = 1;
		} else if (textureArea > 1024) {
			mipMapLevel = 1;
		} else if (textureArea > 256) {
			mipMapLevel = 2;
		} else if (textureArea > 64) {
			mipMapLevel = 3;
		} else if (textureArea > 16) {
			mipMapLevel = 4;
		} else if (textureArea > 4) {
			mipMapLevel = 5;
		} else if (textureArea > 1) {
			mipMapLevel = 6;
		} else {
			mipMapLevel = 7;
		}
	}
    
    public static void method366() {
        anIntArrayArray1478 = null;
        for (int j = 0; j < textureAmount; j++) {
            anIntArrayArray1479[j] = null;
        }
    }

	public static void method367(int size) {
		if (anIntArrayArray1478 == null) {
			anInt1477 = size;
			anIntArrayArray1478 = new int[anInt1477][][];
			for (int i = 0; i < anInt1477; i++) {
				anIntArrayArray1478[i] = new int[][] { new int[16384], new int[4096], new int[1024], new int[256], new int[64], new int[16], new int[4], new int[1] };
			}
			for (int k = 0; k < textureAmount; k++) {
				anIntArrayArray1479[k] = null;
			}
		}
	}

	public static void method368(Archive streamLoader) {
        anInt1473 = 0;
        for (int index = 0; index < textureAmount; index++) {
            try {
                aBackgroundArray1474s[index] = new Background(streamLoader, String.valueOf(index), 0);
                if (lowDetail && aBackgroundArray1474s[index].maxWidth == 128) {
                    aBackgroundArray1474s[index].method356();
                } else {
                    aBackgroundArray1474s[index].method357();
                }
                anInt1473++;
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
        }
    }

    public static int method369(int texture) {
        if (anIntArray1476[texture] != 0) {
            return anIntArray1476[texture];
        }
        int r = 0;
        int g = 0;
        int b = 0;
        final int textureColorCount = anIntArrayArray1483[texture].length;
        for (int index = 0; index < textureColorCount; index++) {
            r += anIntArrayArray1483[texture][index] >> 16 & 0xff;
            g += anIntArrayArray1483[texture][index] >> 8 & 0xff;
            b += anIntArrayArray1483[texture][index] & 0xff;
        }
        int color = (r / textureColorCount << 16) + (g / textureColorCount << 8) + b / textureColorCount;
        color = method373(color, 1.3999999999999999D);
        if (color == 0) {
            color = 1;
        }
        anIntArray1476[texture] = color;
        return color;
    }

    public static void method370(int texture) {
        try {
            if (anIntArrayArray1479[texture] == null) {
                return;
            }
            anIntArrayArray1478[anInt1477++] = anIntArrayArray1479[texture];
            anIntArrayArray1479[texture] = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int[][] method371(int texture) {
		anIntArray1480[texture] = anInt1481++;
		if (anIntArrayArray1479[texture] != null) {
			return anIntArrayArray1479[texture];
		}
		int[][] texels;
		if (anInt1477 > 0) {
			texels = anIntArrayArray1478[--anInt1477];
			anIntArrayArray1478[anInt1477] = null;
		} else {
			int lastUsed = 0;
			int target = -1;
			for (int l = 0; l < anInt1473; l++) {
				if (anIntArrayArray1479[l] != null && (anIntArray1480[l] < lastUsed || target == -1)) {
					lastUsed = anIntArray1480[l];
					target = l;
				}
			}
			texels = anIntArrayArray1479[target];
			anIntArrayArray1479[target] = null;
		}
		anIntArrayArray1479[texture] = texels;
		Background background = aBackgroundArray1474s[texture];
		int texturePalette[] = anIntArrayArray1483[texture];
		if (background.imgWidth == 64) {
			for (int j1 = 0; j1 < 128; j1++) {
				for (int j2 = 0; j2 < 128; j2++) {
					texels[0][j2 + (j1 << 7)] = texturePalette[background.imgPixels[(j2 >> 1) + ((j1 >> 1) << 6)]];
				}
			}
		} else {
			for (int k1 = 0; k1 < 16384; k1++) {
				texels[0][k1] = texturePalette[background.imgPixels[k1]];
			}
		}
		aBooleanArray1475[texture] = false;
		for (int l1 = 0; l1 < 16384; l1++) {
			texels[0][l1] &= 0xf8f8ff;
			int k2 = texels[0][l1];
			if (k2 == 0) {
				aBooleanArray1475[texture] = true;
			}
		}
		for (int level = 1, size = 64; level < 8; level++) {
			int[] src = texels[level - 1];
			int[] dst = texels[level];
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					double r = 0, g = 0, b = 0;
					int count = 0;
					for (int rgb : new int[] { src[x + (y * size << 1) << 1], src[(x + (y * size << 1) << 1) + 1], src[(x + (y * size << 1) << 1) + (size << 1)], src[(x + (y * size << 1) << 1) + (size << 1) + 1] }) {
						if (rgb != 0) {
							double dr = (rgb >> 16 & 0xff) / 255d;
							double dg = (rgb >> 8 & 0xff) / 255d;
							double db = (rgb & 0xff) / 255d;
							r += dr * dr;
							g += dg * dg;
							b += db * db;
							count++;
						}
					}
					if (count != 0) {
						int ri = Math.round(255 * (float) Math.sqrt(r / count));
						int gi = Math.round(255 * (float) Math.sqrt(g / count));
						int bi = Math.round(255 * (float) Math.sqrt(b / count));
						dst[x + y * size] = ri << 16 | gi << 8 | bi;
					} else {
						dst[x + y * size] = 0;
					}
				}
			}
			size >>= 1;
		}
		return texels;
	}

	public static void method372(double value) {
        int pos = 0;
        for (int index = 0; index < 512; index++) {
            final double d1 = index / 8 / 64D + 0.0078125D;
            final double d2 = (index & 7) / 8D + 0.0625D;
            for (int i = 0; i < 128; i++) {
                final double c = i / 128D;
                double r = c;
                double g = c;
                double b = c;
                if (d2 != 0.0D) {
                    double d7;
                    if (c < 0.5D) {
                        d7 = c * (1.0D + d2);
                    } else {
                        d7 = c + d2 - c * d2;
                    }
                    final double d8 = 2D * c - d7;
                    double d9 = d1 + 0.33333333333333331D;
                    if (d9 > 1.0D) {
                        d9--;
                    }
                    final double d10 = d1;
                    double d11 = d1 - 0.33333333333333331D;
                    if (d11 < 0.0D) {
                        d11++;
                    }
                    if (6D * d9 < 1.0D) {
                        r = d8 + (d7 - d8) * 6D * d9;
                    } else if (2D * d9 < 1.0D) {
                        r = d7;
                    } else if (3D * d9 < 2D) {
                        r = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
                    } else {
                        r = d8;
                    }
                    if (6D * d10 < 1.0D) {
                        g = d8 + (d7 - d8) * 6D * d10;
                    } else if (2D * d10 < 1.0D) {
                        g = d7;
                    } else if (3D * d10 < 2D) {
                        g = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
                    } else {
                        g = d8;
                    }
                    if (6D * d11 < 1.0D) {
                        b = d8 + (d7 - d8) * 6D * d11;
                    } else if (2D * d11 < 1.0D) {
                        b = d7;
                    } else if (3D * d11 < 2D) {
                        b = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
                    } else {
                        b = d8;
                    }
                }
                final int red = (int)(r * 256D);
                final int green = (int)(g * 256D);
                final int blue = (int)(b * 256D);
                int color = (red << 16) + (green << 8) + blue;
                color = method373(color, value);
                if (color == 0) {
                    color = 1;
                }
                anIntArray1482[pos++] = color;
            }
        }
        for (int index = 0; index < textureAmount; index++) {
            if (aBackgroundArray1474s[index] != null) {
                final int[] colors = aBackgroundArray1474s[index].palette;
                anIntArrayArray1483[index] = new int[colors.length];
                for (int i = 0; i < colors.length; i++) {
                    anIntArrayArray1483[index][i] = method373(colors[i], value);
                    if ((anIntArrayArray1483[index][i] & 0xf8f8ff) == 0 && i != 0) {
                        anIntArrayArray1483[index][i] = 1;
                    }
                }
            }
        }
        for (int index = 0; index < textureAmount; index++) {
            method370(index);
        }
    }

	private static int method373(int color, double amt) {
        double red = (color >> 16) / 256D;
        double green = (color >> 8 & 0xff) / 256D;
        double blue = (color & 0xff) / 256D;
        red = Math.pow(red, amt);
        green = Math.pow(green, amt);
        blue = Math.pow(blue, amt);
        final int red2 = (int)(red * 256D);
        final int green2 = (int)(green * 256D);
        final int blue2 = (int)(blue * 256D);
        return (red2 << 16) + (green2 << 8) + blue2;
	}
	
	public static void drawMaterializedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, int tz1, int tz2, int tz3, int tex, int z1, int z2, int z3) {
		if (z1 < 0.0F || z2 < 0.0F || z3 < 0.0F) {
			return;
		}
		if (!Configuration.hdTexturing || Texture.get(tex) == null) {
			method374(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3, z1, z2, z3);
			return;
		}
		
		setMipmapLevel(y1, y2, y3, x1, x2, x3, tex);
		
		int[] ai = Texture.get(tex).mipmaps[mipMapLevel];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		
		int l4 = tx3 * ty1 - ty3 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (Client.log_view_dist == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (hsl2 - hsl1 << 15) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (hsl3 - hsl2 << 15) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (hsl1 - hsl3 << 15) / (y1 - y3);
		}
		
		int x21 = x2 - x1;
		int y32 = y2 - y1;
		int x31 = x3 - x1;
		int y31 = y3 - y1;
		int z21 = z2 - z1;
		int z31 = z3 - z1;

		int div = x21 * y31 - x31 * y32;
		int depthSlope = (z21 * y31 - z31 * y32) / div;
		int depthScale = (z31 * x21 - z21 * x31) / div;
		
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Canvas2D.bottomY) {
				return;
			}
			if (y2 > Canvas2D.bottomY) {
				y2 = Canvas2D.bottomY;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			z1 = z1 - depthSlope * x1 + depthSlope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				hsl3 = hsl1 <<= 15;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					z1 -= depthScale * y1;
					hsl3 -= j8 * y1;
					hsl1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				hsl2 <<= 15;
				if (y2 < 0) {
					x2 -= k7 * y2;
					hsl2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = lineOffsets[y1];
					while (--y2 >= 0) {
						drawMaterializedScanline(Canvas2D.pixels, ai, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
						x3 += i8;
						x1 += i7;
						z1 += depthScale;
						hsl3 += j8;
						hsl1 += j7;
						y1 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawMaterializedScanline(Canvas2D.pixels, ai, y1, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
						x3 += i8;
						x2 += k7;
						z1 += depthScale;
						hsl3 += j8;
						hsl2 += l7;
						y1 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = lineOffsets[y1];
				while (--y2 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					x3 += i8;
					x1 += i7;
					z1 += depthScale;
					hsl3 += j8;
					hsl1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y1, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					x3 += i8;
					x2 += k7;
					z1 += depthScale;
					hsl3 += j8;
					hsl2 += l7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			hsl2 = hsl1 <<= 15;
			if (y1 < 0) {
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				z1 -= depthScale * y1;
				hsl2 -= j8 * y1;
				hsl1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			hsl3 <<= 15;
			if (y3 < 0) {
				x3 -= k7 * y3;
				hsl3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = lineOffsets[y1];
				while (--y3 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y1, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					x2 += i8;
					x1 += i7;
					z1 += depthScale;
					hsl2 += j8;
					hsl1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					x3 += k7;
					x1 += i7;
					z1 += depthScale;
					hsl3 += l7;
					hsl1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = lineOffsets[y1];
			while (--y3 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y1, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
				x2 += i8;
				x1 += i7;
				z1 += depthScale;
				hsl2 += j8;
				hsl1 += j7;
				y1 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z1, depthSlope);
				x3 += k7;
				x1 += i7;
				z1 += depthScale;
				hsl3 += l7;
				hsl1 += j7;
				y1 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Canvas2D.bottomY) {
				return;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if (y1 > Canvas2D.bottomY) {
				y1 = Canvas2D.bottomY;
			}
			z2 = z2 - depthSlope * x2 + depthSlope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				hsl1 = hsl2 <<= 15;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					z2 -= depthScale * y2;
					hsl1 -= j7 * y2;
					hsl2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				hsl3 <<= 15;
				if (y3 < 0) {
					x3 -= i8 * y3;
					hsl3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = lineOffsets[y2];
					while (--y3 >= 0) {
						drawMaterializedScanline(Canvas2D.pixels, ai, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
						x1 += i7;
						x2 += k7;
						z2 += depthScale;
						hsl1 += j7;
						hsl2 += l7;
						y2 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawMaterializedScanline(Canvas2D.pixels, ai, y2, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
						x1 += i7;
						x3 += i8;
						z2 += depthScale;
						hsl1 += j7;
						hsl3 += j8;
						y2 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = lineOffsets[y2];
				while (--y3 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					x1 += i7;
					x2 += k7;
					z2 += depthScale;
					hsl1 += j7;
					hsl2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y2, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					x1 += i7;
					x3 += i8;
					z2 += depthScale;
					hsl1 += j7;
					hsl3 += j8;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			hsl3 = hsl2 <<= 15;
			if (y2 < 0) {
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				z2 -= depthScale * y2;
				hsl3 -= j7 * y2;
				hsl2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= i8 * y1;
				hsl1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = lineOffsets[y2];
				while (--y1 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y2, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					x3 += i7;
					x2 += k7;
					z2 += depthScale;
					hsl3 += j7;
					hsl2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					x1 += i8;
					x2 += k7;
					z2 += depthScale;
					hsl1 += j8;
					hsl2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = lineOffsets[y2];
			while (--y1 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y2, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
				x3 += i7;
				x2 += k7;
				z2 += depthScale;
				hsl3 += j7;
				hsl2 += l7;
				y2 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z2, depthSlope);
				x1 += i8;
				x2 += k7;
				z2 += depthScale;
				hsl1 += j8;
				hsl2 += l7;
				y2 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= Canvas2D.bottomY) {
			return;
		}
		if (y1 > Canvas2D.bottomY) {
			y1 = Canvas2D.bottomY;
		}
		if (y2 > Canvas2D.bottomY) {
			y2 = Canvas2D.bottomY;
		}
		z3 = z3 - depthSlope * x3 + depthSlope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			hsl2 = hsl3 <<= 15;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				z3 -= depthScale * y3;
				hsl2 -= l7 * y3;
				hsl3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if (y1 < 0) {
				x1 -= i7 * y1;
				hsl1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = lineOffsets[y3];
				while (--y1 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
					x2 += k7;
					x3 += i8;
					z3 += depthScale;
					hsl2 += l7;
					hsl3 += j8;
					y3 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawMaterializedScanline(Canvas2D.pixels, ai, y3, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
					x2 += k7;
					x1 += i7;
					z3 += depthScale;
					hsl2 += l7;
					hsl1 += j7;
					y3 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = lineOffsets[y3];
			while (--y1 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				x2 += k7;
				x3 += i8;
				z3 += depthScale;
				hsl2 += l7;
				hsl3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y3, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				x2 += k7;
				x1 += i7;
				z3 += depthScale;
				hsl2 += l7;
				hsl1 += j7;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		hsl1 = hsl3 <<= 15;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			z3 -= depthScale * y3;
			hsl1 -= l7 * y3;
			hsl3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		hsl2 <<= 15;
		if (y2 < 0) {
			x2 -= i7 * y2;
			hsl2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = lineOffsets[y3];
			while (--y2 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y3, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				x1 += k7;
				x3 += i8;
				z3 += depthScale;
				hsl1 += l7;
				hsl3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawMaterializedScanline(Canvas2D.pixels, ai, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				x2 += i7;
				x3 += i8;
				z3 += depthScale;
				hsl2 += j7;
				hsl3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = lineOffsets[y3];
		while (--y2 >= 0) {
			drawMaterializedScanline(Canvas2D.pixels, ai, y3, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
			x1 += k7;
			x3 += i8;
			z3 += depthScale;
			hsl1 += l7;
			hsl3 += j8;
			y3 += Canvas2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawMaterializedScanline(Canvas2D.pixels, ai, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, l4, k5, j6, i5, l5, k6, z3, depthSlope);
			x2 += i7;
			x3 += i8;
			z3 += depthScale;
			hsl2 += j7;
			hsl3 += j8;
			y3 += Canvas2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static final void drawMaterializedScanline(int[] dest, int[] texels, int offset, int x1, int x2, int hsl1, int hsl2, int t1, int t2, int t3, int t4, int t5, int t6, int z1, int z2) {
		if(x2 <= x1) {
			return;
		}
		int texPos = 0;
		int rgb = 0;
		if (restrict_edges) {
			if (x2 > Canvas2D.centerX) {
				x2 = Canvas2D.centerX;
			}
			if (x1 < 0) {
				x1 = 0;
			}
		}
		if(x1 < x2) {
			offset += x1;
			z1 += z2 * (float) x1;
			int n = x2 - x1 >> 2;
			int dhsl = 0;
			if(n > 0) {
				dhsl = (hsl2 - hsl1) * anIntArray1468[n] >> 15;
			}
			int dist = x1 - centerX;
			t1 += (t4 >> 3) * dist;
			t2 += (t5 >> 3) * dist;
			t3 += (t6 >> 3) * dist;
			int i_57_ = t3 >> 14;
			int i_58_;
			int i_59_;
			if(i_57_ != 0) {
				i_58_ = t1 / i_57_;
				i_59_ = t2 / i_57_;
			} else {
				i_58_ = 0;
				i_59_ = 0;
			}
			t1 += t4;
			t2 += t5;
			t3 += t6;
			i_57_ = t3 >> 14;
			int i_60_;
			int i_61_;
			if(i_57_ != 0) {
				i_60_ = t1 / i_57_;
				i_61_ = t2 / i_57_;
			} else {
				i_60_ = 0;
				i_61_ = 0;
			}
			texPos = (i_58_ << 18) + i_59_;
			int dtexPos = (i_60_ - i_58_ >> 3 << 18) + (i_61_ - i_59_ >> 3);
			n >>= 1;
			int light;
			if(n > 0) {
				do {
					hsl1 += dhsl;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					hsl1 += dhsl;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
					i_58_ = i_60_;
					i_59_ = i_61_;
					t1 += t4;
					t2 += t5;
					t3 += t6;
					i_57_ = t3 >> 14;
					if(i_57_ != 0) {
						i_60_ = t1 / i_57_;
						i_61_ = t2 / i_57_;
					} else {
						i_60_ = 0;
						i_61_ = 0;
					}
					texPos = (i_58_ << 18) + i_59_;
					dtexPos = (i_60_ - i_58_ >> 3 << 18) + (i_61_ - i_59_ >> 3);
				} while(--n > 0);
			}
			n = x2 - x1 & 7;
			if(n > 0) {
				do {
					if((n & 3) == 0) {
						hsl1 += dhsl;
					}
					rgb = texels[texelPos((texPos & 0x3f80) + (texPos >>> 25))];
					light = ((hsl1 >> 8 & 0x7f) << 1) * (((rgb >> 16 & 0xff) + (rgb >> 8 & 0xff) + (rgb & 0xff)) / 3) / 384;
					if (light > 127) {
						light = 127;
					}
					texPos += dtexPos;
					if (true) {
						dest[offset] = anIntArray1482[(hsl1 >> 8 & 0xff80) | light];
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					offset++;
				} while(--n > 0);
			}
		}
	}
	
	static boolean smoothShading = true;
	
	public static void method374(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, int z1, int z2, int z3) {
		if (!smoothShading && notTextured) {
			drawHDGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3, z1, z2, z3);
		} else {
			drawLDGouraudTriangle(y1, y2, y3, x1, x2, x3, hsl1, hsl2, hsl3, z1, z2, z3);
		}
	}
	
	public static void drawLDGouraudTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, int z1, int z2, int z3) {
		int dx1 = 0;
		int dhsl1 = 0;
		if(y2 != y1) {
			dx1 = (x2 - x1 << 16) / (y2 - y1);
			dhsl1 = (hsl2 - hsl1 << 15) / (y2 - y1);
		}
		int dx2 = 0;
		int dhsl2 = 0;
		if(y3 != y2) {
			dx2 = (x3 - x2 << 16) / (y3 - y2);
			dhsl2 = (hsl3 - hsl2 << 15) / (y3 - y2);
		}
		int dx3 = 0;
		int dhsl3 = 0;
		if(y3 != y1) {
			dx3 = (x1 - x3 << 16) / (y1 - y3);
			dhsl3 = (hsl1 - hsl3 << 15) / (y1 - y3);
		}
		
		int x21 = x2 - x1;
		int y32 = y2 - y1;
		int x31 = x3 - x1;
		int y31 = y3 - y1;
		int z21 = z2 - z1;
		int z31 = z3 - z1;

		int div = x21 * y31 - x31 * y32;
		int depthSlope = (z21 * y31 - z31 * y32) / div;
		int depthScale = (z31 * x21 - z21 * x31) / div;
		
		if(y1 <= y2 && y1 <= y3) {
			if(y1 >= Canvas2D.bottomY) {
				return;
			}
			if(y2 > Canvas2D.bottomY) {
				y2 = Canvas2D.bottomY;
			}
			if(y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			z1 = z1 - depthSlope * x1 + depthSlope;
			if(y2 < y3) {
				x3 = x1 <<= 16;
				hsl3 = hsl1 <<= 15;
				if(y1 < 0) {
					y1 -= 0;
					x3 -= dx3 * y1;
					x1 -= dx1 * y1;
					z1 -= depthScale * y1;
					hsl3 -= dhsl3 * y1;
					hsl1 -= dhsl1 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				hsl2 <<= 15;
				if(y2 < 0) {
					y2 -= 0;
					x2 -= dx2 * y2;
					hsl2 -= dhsl2 * y2;
					y2 = 0;
				}
				if(y1 != y2 && dx3 < dx1 || y1 == y2 && dx3 > dx2) {
					y3 -= y2;
					y2 -= y1;
					for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += Canvas2D.width) {
						drawLDGouraudScanline(Canvas2D.pixels, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, z1, depthSlope);
						z1 += depthScale;
						x3 += dx3;
						x1 += dx1;
						hsl3 += dhsl3;
						hsl1 += dhsl1;
					}
					while(--y3 >= 0) {
						drawLDGouraudScanline(Canvas2D.pixels, y1, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, z1, depthSlope);
						z1 += depthScale;
						x3 += dx3;
						x2 += dx2;
						hsl3 += dhsl3;
						hsl2 += dhsl2;
						y1 += Canvas2D.width;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += Canvas2D.width) {
					drawLDGouraudScanline(Canvas2D.pixels, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, z1, depthSlope);
					z1 += depthScale;
					x3 += dx3;
					x1 += dx1;
					hsl3 += dhsl3;
					hsl1 += dhsl1;
				}
				while(--y3 >= 0) {
					drawLDGouraudScanline(Canvas2D.pixels, y1, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, z1, depthSlope);
					z1 += depthScale;
					x3 += dx3;
					x2 += dx2;
					hsl3 += dhsl3;
					hsl2 += dhsl2;
					y1 += Canvas2D.width;
				}
				return;
			}
			x2 = x1 <<= 16;
			hsl2 = hsl1 <<= 15;
			if(y1 < 0) {
				y1 -= 0;
				x2 -= dx3 * y1;
				x1 -= dx1 * y1;
				z1 -= depthScale * y1;
				hsl2 -= dhsl3 * y1;
				hsl1 -= dhsl1 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			hsl3 <<= 15;
			if(y3 < 0) {
				y3 -= 0;
				x3 -= dx2 * y3;
				hsl3 -= dhsl2 * y3;
				y3 = 0;
			}
			if(y1 != y3 && dx3 < dx1 || y1 == y3 && dx2 > dx1) {
				y2 -= y3;
				y3 -= y1;
				for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += Canvas2D.width) {
					drawLDGouraudScanline(Canvas2D.pixels, y1, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, z1, depthSlope);
					z1 += depthScale;
					x2 += dx3;
					x1 += dx1;
					hsl2 += dhsl3;
					hsl1 += dhsl1;
				}
				while(--y2 >= 0) {
					drawLDGouraudScanline(Canvas2D.pixels, y1, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, z1, depthSlope);
					z1 += depthScale;
					x3 += dx2;
					x1 += dx1;
					hsl3 += dhsl2;
					hsl1 += dhsl1;
					y1 += Canvas2D.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += Canvas2D.width) {
				drawLDGouraudScanline(Canvas2D.pixels, y1, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, z1, depthSlope);
				z1 += depthScale;
				x2 += dx3;
				x1 += dx1;
				hsl2 += dhsl3;
				hsl1 += dhsl1;
			}
			while(--y2 >= 0) {
				drawLDGouraudScanline(Canvas2D.pixels, y1, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, z1, depthSlope);
				z1 += depthScale;
				x3 += dx2;
				x1 += dx1;
				hsl3 += dhsl2;
				hsl1 += dhsl1;
				y1 += Canvas2D.width;
			}
			return;
		}
		if(y2 <= y3) {
			if(y2 >= Canvas2D.bottomY) {
				return;
			}
			if(y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if(y1 > Canvas2D.bottomY) {
				y1 = Canvas2D.bottomY;
			}
			z2 = z2 - depthSlope * x2 + depthSlope;
			if(y3 < y1) {
				x1 = x2 <<= 16;
				hsl1 = hsl2 <<= 15;
				if(y2 < 0) {
					y2 -= 0;
					x1 -= dx1 * y2;
					x2 -= dx2 * y2;
					z1 -= depthScale * y2;
					hsl1 -= dhsl1 * y2;
					hsl2 -= dhsl2 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				hsl3 <<= 15;
				if(y3 < 0) {
					y3 -= 0;
					x3 -= dx3 * y3;
					hsl3 -= dhsl3 * y3;
					y3 = 0;
				}
				if(y2 != y3 && dx1 < dx2 || y2 == y3 && dx1 > dx3) {
					y1 -= y3;
					y3 -= y2;
					for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += Canvas2D.width) {
						drawLDGouraudScanline(Canvas2D.pixels, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, z2, depthSlope);
						z2 += depthScale;
						x1 += dx1;
						x2 += dx2;
						hsl1 += dhsl1;
						hsl2 += dhsl2;
					}

					while(--y1 >= 0) {
						drawLDGouraudScanline(Canvas2D.pixels, y2, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, z2, depthSlope);
						z2 += depthScale;
						x1 += dx1;
						x3 += dx3;
						hsl1 += dhsl1;
						hsl3 += dhsl3;
						y2 += Canvas2D.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += Canvas2D.width) {
					drawLDGouraudScanline(Canvas2D.pixels, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, z2, depthSlope);
					z2 += depthScale;
					x1 += dx1;
					x2 += dx2;
					hsl1 += dhsl1;
					hsl2 += dhsl2;
				}

				while(--y1 >= 0) {
					drawLDGouraudScanline(Canvas2D.pixels, y2, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, z2, depthSlope);
					z2 += depthScale;
					x1 += dx1;
					x3 += dx3;
					hsl1 += dhsl1;
					hsl3 += dhsl3;
					y2 += Canvas2D.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			hsl3 = hsl2 <<= 15;
			if(y2 < 0) {
				y2 -= 0;
				x3 -= dx1 * y2;
				x2 -= dx2 * y2;
				z2 -= depthScale * y2;
				hsl3 -= dhsl1 * y2;
				hsl2 -= dhsl2 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if(y1 < 0) {
				y1 -= 0;
				x1 -= dx3 * y1;
				hsl1 -= dhsl3 * y1;
				y1 = 0;
			}
			if(dx1 < dx2) {
				y3 -= y1;
				y1 -= y2;
				for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += Canvas2D.width) {
					drawLDGouraudScanline(Canvas2D.pixels, y2, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, z2, depthSlope);
					z2 += depthScale;
					x3 += dx1;
					x2 += dx2;
					hsl3 += dhsl1;
					hsl2 += dhsl2;
				}
				while(--y3 >= 0) {
					drawLDGouraudScanline(Canvas2D.pixels, y2, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, z2, depthSlope);
					z2 += depthScale;
					x1 += dx3;
					x2 += dx2;
					hsl1 += dhsl3;
					hsl2 += dhsl2;
					y2 += Canvas2D.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += Canvas2D.width) {
				drawLDGouraudScanline(Canvas2D.pixels, y2, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, z2, depthSlope);
				z2 += depthScale;
				x3 += dx1;
				x2 += dx2;
				hsl3 += dhsl1;
				hsl2 += dhsl2;
			}

			while(--y3 >= 0) {
				drawLDGouraudScanline(Canvas2D.pixels, y2, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, z2, depthSlope);
				z2 += depthScale;
				x1 += dx3;
				x2 += dx2;
				hsl1 += dhsl3;
				hsl2 += dhsl2;
				y2 += Canvas2D.width;
			}
			return;
		}
		if(y3 >= Canvas2D.bottomY) {
			return;
		}
		if(y1 > Canvas2D.bottomY) {
			y1 = Canvas2D.bottomY;
		}
		if(y2 > Canvas2D.bottomY) {
			y2 = Canvas2D.bottomY;
		}
		z3 = z3 - depthSlope * x3 + depthSlope;
		if(y1 < y2) {
			x2 = x3 <<= 16;
			hsl2 = hsl3 <<= 15;
			if(y3 < 0) {
				y3 -= 0;
				x2 -= dx2 * y3;
				x3 -= dx3 * y3;
				z3 -= depthScale * y3;
				hsl2 -= dhsl2 * y3;
				hsl3 -= dhsl3 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			hsl1 <<= 15;
			if(y1 < 0) {
				y1 -= 0;
				x1 -= dx1 * y1;
				hsl1 -= dhsl1 * y1;
				y1 = 0;
			}
			if(dx2 < dx3) {
				y2 -= y1;
				y1 -= y3;
				for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += Canvas2D.width) {
					drawLDGouraudScanline(Canvas2D.pixels, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, z3, depthSlope);
					z3 += depthScale;
					x2 += dx2;
					x3 += dx3;
					hsl2 += dhsl2;
					hsl3 += dhsl3;
				}
				while(--y2 >= 0) {
					drawLDGouraudScanline(Canvas2D.pixels, y3, x2 >> 16, x1 >> 16, hsl2 >> 7, hsl1 >> 7, z3, depthSlope);
					z3 += depthScale;
					x2 += dx2;
					x1 += dx1;
					hsl2 += dhsl2;
					hsl1 += dhsl1;
					y3 += Canvas2D.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += Canvas2D.width) {
				drawLDGouraudScanline(Canvas2D.pixels, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, z3, depthSlope);
				z3 += depthScale;
				x2 += dx2;
				x3 += dx3;
				hsl2 += dhsl2;
				hsl3 += dhsl3;
			}

			while(--y2 >= 0) {
				drawLDGouraudScanline(Canvas2D.pixels, y3, x1 >> 16, x2 >> 16, hsl1 >> 7, hsl2 >> 7, z3, depthSlope);
				z3 += depthScale;
				x2 += dx2;
				x1 += dx1;
				hsl2 += dhsl2;
				hsl1 += dhsl1;
				y3 += Canvas2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		hsl1 = hsl3 <<= 15;
		if(y3 < 0) {
			y3 -= 0;
			x1 -= dx2 * y3;
			x3 -= dx3 * y3;
			z3 -= depthScale * y3;
			hsl1 -= dhsl2 * y3;
			hsl3 -= dhsl3 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		hsl2 <<= 15;
		if(y2 < 0) {
			y2 -= 0;
			x2 -= dx1 * y2;
			hsl2 -= dhsl1 * y2;
			y2 = 0;
		}
		if(dx2 < dx3) {
			y1 -= y2;
			y2 -= y3;
			for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += Canvas2D.width) {
				drawLDGouraudScanline(Canvas2D.pixels, y3, x1 >> 16, x3 >> 16, hsl1 >> 7, hsl3 >> 7, z3, depthSlope);
				z3 += depthScale;
				x1 += dx2;
				x3 += dx3;
				hsl1 += dhsl2;
				hsl3 += dhsl3;
			}
			while(--y1 >= 0) {
				drawLDGouraudScanline(Canvas2D.pixels, y3, x2 >> 16, x3 >> 16, hsl2 >> 7, hsl3 >> 7, z3, depthSlope);
				z3 += depthScale;
				x2 += dx1;
				x3 += dx3;
				hsl2 += dhsl1;
				hsl3 += dhsl3;
				y3 += Canvas2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += Canvas2D.width) {
			drawLDGouraudScanline(Canvas2D.pixels, y3, x3 >> 16, x1 >> 16, hsl3 >> 7, hsl1 >> 7, z3, depthSlope);
			z3 += depthScale;
			x1 += dx2;
			x3 += dx3;
			hsl1 += dhsl2;
			hsl3 += dhsl3;
		}
		while(--y1 >= 0) {
			drawLDGouraudScanline(Canvas2D.pixels, y3, x3 >> 16, x2 >> 16, hsl3 >> 7, hsl2 >> 7, z3, depthSlope);
			z3 += depthScale;
			x2 += dx1;
			x3 += dx3;
			hsl2 += dhsl1;
			hsl3 += dhsl3;
			y3 += Canvas2D.width;
		}
	}
	
	private static void drawLDGouraudScanline(int dest[], int offset, int x1, int x2, int hsl1, int hsl2, int z1, int z2) {
		int rgb;
		int div;
		int dhsl;
		if(notTextured) {
			if(restrict_edges) {
				if(x2 - x1 > 3) {
					dhsl = (hsl2 - hsl1) / (x2 - x1);
				} else {
					dhsl = 0;
				}
				if(x2 > Canvas2D.centerX) {
					x2 = Canvas2D.centerX;
				}
				if(x1 < 0) {
					hsl1 -= x1 * dhsl;
					x1 = 0;
				}
				if(x1 >= x2) {
					return;
				}
				offset += x1 - 1;
				div = x2 - x1 >> 2;
				z1 += z2 * x1;
				dhsl <<= 2;
			} else {
				if(x1 >= x2) {
					return;
				}	
				offset += x1 - 1;
				div = x2 - x1 >> 2;
				z1 += z2 * x1;
				if(div > 0) {
					dhsl = (hsl2 - hsl1) * anIntArray1468[div] >> 15;
				} else {
					dhsl = 0;
				}
			}
			if(alpha == 0) {
				while(--div >= 0) {
					rgb = anIntArray1482[hsl1 >> 8];
					hsl1 += dhsl;
					offset++;
                    if (true) {
                        dest[offset] = rgb;
                        depthBuffer[offset] = z1;
                    }
                    z1 += z2;
                    offset++;
                    if (true) {
                        dest[offset] = rgb;
                        depthBuffer[offset] = z1;
                    }
                    z1 += z2;
                    offset++;
                    if (true) {
                        dest[offset] = rgb;
                        depthBuffer[offset] = z1;
                    }
                    z1 += z2;
                    offset++;
                    if (true) {
                        dest[offset] = rgb;
                        depthBuffer[offset] = z1;
                    }
                    z1 += z2;
				}
				div = x2 - x1 & 3;
				if(div > 0) {
					rgb = anIntArray1482[hsl1 >> 8];
					do {
						offset++;
                        if (true) {
                            dest[offset] = rgb;
                            depthBuffer[offset] = z1;
                        }
                        z1 += z2;
					} while(--div > 0);
					return;
				}
			} else {
				int a1 = alpha;
				int a2 = 256 - alpha;
				while(--div >= 0) {
					rgb = anIntArray1482[hsl1 >> 8];
					hsl1 += dhsl;
					rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
					if (true) {
						dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
						depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
					}
					offset++;
					z1 += z2;
					if (true) {
						dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
						depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
					}
					offset++;
					z1 += z2;
					if (true) {
						dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
						depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
					}
					offset++;
					z1 += z2;
					if (true) {
						dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
						depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
					}
					offset++;
					z1 += z2;
				}
				div = x2 - x1 & 3;
				if(div > 0) {
					rgb = anIntArray1482[hsl1 >> 8];
					rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
					do {
						if (true) {
							dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
							depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
						}
						offset++;
						z1 += z2;
					} while(--div > 0);
				}
			}
			return;
		}
		if(x1 >= x2) {
			return;
		}
		int dhsl2 = (hsl2 - hsl1) / (x2 - x1);
		if(restrict_edges) {
			if(x2 > Canvas2D.centerX) {
				x2 = Canvas2D.centerX;
			}
			if(x1 < 0) {
				hsl1 -= x1 * dhsl2;
				x1 = 0;
			}
			if(x1 >= x2) {
				return;
			}
		}
		offset += x1;
		div = x2 - x1;
		if(alpha == 0) {
			do {
                if (true) {
                	final int idx = hsl1 >> 8;
                	if (notTextured && smoothShading && idx != 0xffff && (hsl1 & 0xff) != 0) {
                		final int rgb1 = anIntArray1482[idx];
                		final int rgb2 = anIntArray1482[idx + 1];
                		final int a2 = hsl1 & 0xff;
                		final int a1 = 256 - a2;
                		dest[offset] = ((rgb1 & 0xff00ff) * a1 + (rgb2 & 0xff00ff) * a2 & 0xff00ff00) + ((rgb1 & 0xff00) * a1 + (rgb2 & 0xff00) * a2 & 0xff0000) >> 8;
                	} else {
                		dest[offset] = anIntArray1482[idx];
                	}
                    depthBuffer[offset] = z1;
                }
                offset++;
                z1 += z2;
				hsl1 += dhsl2;
			} while(--div > 0);
			return;
		}
		int a1 = alpha;
		int a2 = 256 - alpha;
		do {
			rgb = anIntArray1482[hsl1 >> 8];
			hsl1 += dhsl2;
			rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
			if (true) {
				dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
				depthBuffer[offset] = z1;
			}
			offset++;
			z1 += z2;
		} while(--div > 0);
	}
	
	public static void drawHDGouraudTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int hsl1, int hsl2, int hsl3, int z1, int z2, int z3) {
		int rgb1 = anIntArray1482[hsl1];
		int rgb2 = anIntArray1482[hsl2];
		int rgb3 = anIntArray1482[hsl3];
		int r1 = rgb1 >> 16 & 0xff;
		int g1 = rgb1 >> 8 & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = rgb2 >> 16 & 0xff;
		int g2 = rgb2 >> 8 & 0xff;
		int b2 = rgb2 & 0xff;
		int r3 = rgb3 >> 16 & 0xff;
		int g3 = rgb3 >> 8 & 0xff;
		int b3 = rgb3 & 0xff;
		int dx1 = 0;
		int dr1 = 0;
		int dg1 = 0;
		int db1 = 0;
		if (y2 != y1) {
			dx1 = (x2 - x1 << 16) / (y2 - y1);
			dr1 = (r2 - r1 << 16) / (y2 - y1);
			dg1 = (g2 - g1 << 16) / (y2 - y1);
			db1 = (b2 - b1 << 16) / (y2 - y1);
		}
		int dx2 = 0;
		int dr2 = 0;
		int dg2 = 0;
		int db2 = 0;
		if (y3 != y2) {
			dx2 = (x3 - x2 << 16) / (y3 - y2);
			dr2 = (r3 - r2 << 16) / (y3 - y2);
			dg2 = (g3 - g2 << 16) / (y3 - y2);
			db2 = (b3 - b2 << 16) / (y3 - y2);
		}
		int dx3 = 0;
		int dr3 = 0;
		int dg3 = 0;
		int db3 = 0;
		if (y3 != y1) {
			dx3 = (x1 - x3 << 16) / (y1 - y3);
			dr3 = (r1 - r3 << 16) / (y1 - y3);
			dg3 = (g1 - g3 << 16) / (y1 - y3);
			db3 = (b1 - b3 << 16) / (y1 - y3);
		}
		
		int x21 = x2 - x1;
		int y32 = y2 - y1;
		int x31 = x3 - x1;
		int y31 = y3 - y1;
		int z21 = z2 - z1;
		int z31 = z3 - z1;

		int div = x21 * y31 - x31 * y32;
		int depthSlope = (z21 * y31 - z31 * y32) / div;
		int depthScale = (z31 * x21 - z21 * x31) / div;
		
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Canvas2D.bottomY) {
				return;
			}
			if (y2 > Canvas2D.bottomY) {
				y2 = Canvas2D.bottomY;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			z1 = z1 - depthSlope * x1 + depthSlope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				r3 = r1 <<= 16;
				g3 = g1 <<= 16;
				b3 = b1 <<= 16;
				if (y1 < 0) {
					x3 -= dx3 * y1;
					x1 -= dx1 * y1;
					r3 -= dr3 * y1;
					g3 -= dg3 * y1;
					b3 -= db3 * y1;
					r1 -= dr1 * y1;
					g1 -= dg1 * y1;
					b1 -= db1 * y1;
					z1 -= depthScale * y1;
					y1 = 0;
				}
				x2 <<= 16;
				r2 <<= 16;
				g2 <<= 16;
				b2 <<= 16;
				if (y2 < 0) {
					x2 -= dx2 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					y2 = 0;
				}
				if (y1 != y2 && dx3 < dx1 || y1 == y2 && dx3 > dx2) {
					y3 -= y2;
					y2 -= y1;
					for (y1 = lineOffsets[y1]; --y2 >= 0; y1 += Canvas2D.width) {
						drawHDGouraudScanline(Canvas2D.pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z1, depthSlope);
						x3 += dx3;
						x1 += dx1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						z1 += depthScale;
					}
					while (--y3 >= 0) {
						drawHDGouraudScanline(Canvas2D.pixels, y1, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z1, depthSlope);
						x3 += dx3;
						x2 += dx2;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						y1 += Canvas2D.width;
						z1 += depthScale;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for (y1 = lineOffsets[y1]; --y2 >= 0; y1 += Canvas2D.width) {
					drawHDGouraudScanline(Canvas2D.pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z1, depthSlope);
					x3 += dx3;
					x1 += dx1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					z1 += depthScale;
				}
				while (--y3 >= 0) {
					drawHDGouraudScanline(Canvas2D.pixels, y1, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z1, depthSlope);
					x3 += dx3;
					x2 += dx2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y1 += Canvas2D.width;
					z1 += depthScale;
				}
				return;
			}
			x2 = x1 <<= 16;
			r2 = r1 <<= 16;
			g2 = g1 <<= 16;
			b2 = b1 <<= 16;
			if (y1 < 0) {
				x2 -= dx3 * y1;
				x1 -= dx1 * y1;
				r2 -= dr3 * y1;
				g2 -= dg3 * y1;
				b2 -= db3 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				z1 -= depthScale * y1;
				y1 = 0;
			}
			x3 <<= 16;
			r3 <<= 16;
			g3 <<= 16;
			b3 <<= 16;
			if (y3 < 0) {
				x3 -= dx2 * y3;
				r3 -= dr2 * y3;
				g3 -= dg2 * y3;
				b3 -= db2 * y3;
				y3 = 0;
			}
			if (y1 != y3 && dx3 < dx1 || y1 == y3 && dx2 > dx1) {
				y2 -= y3;
				y3 -= y1;
				for (y1 = lineOffsets[y1]; --y3 >= 0; y1 += Canvas2D.width) {
					drawHDGouraudScanline(Canvas2D.pixels, y1, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z1, depthSlope);
					x2 += dx3;
					x1 += dx1;
					r2 += dr3;
					g2 += dg3;
					b2 += db3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					z1 += depthScale;
				}
				while (--y2 >= 0) {
					drawHDGouraudScanline(Canvas2D.pixels, y1, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z1, depthSlope);
					x3 += dx2;
					x1 += dx1;
					r3 += dr2;
					g3 += dg2;
					b3 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y1 += Canvas2D.width;
					z1 += depthScale;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for (y1 = lineOffsets[y1]; --y3 >= 0; y1 += Canvas2D.width) {
				drawHDGouraudScanline(Canvas2D.pixels, y1, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z1, depthSlope);
				x2 += dx3;
				x1 += dx1;
				r2 += dr3;
				g2 += dg3;
				b2 += db3;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				z1 += depthScale;
			}
			while (--y2 >= 0) {
				drawHDGouraudScanline(Canvas2D.pixels, y1, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z1, depthSlope);
				x3 += dx2;
				x1 += dx1;
				r3 += dr2;
				g3 += dg2;
				b3 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				y1 += Canvas2D.width;
				z1 += depthScale;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Canvas2D.bottomY) {
				return;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if (y1 > Canvas2D.bottomY) {
				y1 = Canvas2D.bottomY;
			}
			z2 = z2 - depthSlope * x2 + depthSlope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				r1 = r2 <<= 16;
				g1 = g2 <<= 16;
				b1 = b2 <<= 16;
				if (y2 < 0) {
					x1 -= dx1 * y2;
					x2 -= dx2 * y2;
					r1 -= dr1 * y2;
					g1 -= dg1 * y2;
					b1 -= db1 * y2;
					r2 -= dr2 * y2;
					g2 -= dg2 * y2;
					b2 -= db2 * y2;
					z2 -= depthScale * y2;
					y2 = 0;
				}
				x3 <<= 16;
				r3 <<= 16;
				g3 <<= 16;
				b3 <<= 16;
				if (y3 < 0) {
					x3 -= dx3 * y3;
					r3 -= dr3 * y3;
					g3 -= dg3 * y3;
					b3 -= db3 * y3;
					y3 = 0;
				}
				if (y2 != y3 && dx1 < dx2 || y2 == y3 && dx1 > dx3) {
					y1 -= y3;
					y3 -= y2;
					for (y2 = lineOffsets[y2]; --y3 >= 0; y2 += Canvas2D.width) {
						drawHDGouraudScanline(Canvas2D.pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z2, depthSlope);
						x1 += dx1;
						x2 += dx2;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r2 += dr2;
						g2 += dg2;
						b2 += db2;
						z2 += depthScale;
					}
					while (--y1 >= 0) {
						drawHDGouraudScanline(Canvas2D.pixels, y2, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z2, depthSlope);
						x1 += dx1;
						x3 += dx3;
						r1 += dr1;
						g1 += dg1;
						b1 += db1;
						r3 += dr3;
						g3 += dg3;
						b3 += db3;
						y2 += Canvas2D.width;
						z2 += depthScale;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for (y2 = lineOffsets[y2]; --y3 >= 0; y2 += Canvas2D.width) {
					drawHDGouraudScanline(Canvas2D.pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z2, depthSlope);
					x1 += dx1;
					x2 += dx2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					z2 += depthScale;
				}
				while (--y1 >= 0) {
					drawHDGouraudScanline(Canvas2D.pixels, y2, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z2, depthSlope);
					x1 += dx1;
					x3 += dx3;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					y2 += Canvas2D.width;
					z2 += depthScale;
				}
				return;
			}
			x3 = x2 <<= 16;
			r3 = r2 <<= 16;
			g3 = g2 <<= 16;
			b3 = b2 <<= 16;
			if (y2 < 0) {
				x3 -= dx1 * y2;
				x2 -= dx2 * y2;
				r3 -= dr1 * y2;
				g3 -= dg1 * y2;
				b3 -= db1 * y2;
				r2 -= dr2 * y2;
				g2 -= dg2 * y2;
				b2 -= db2 * y2;
				z2 -= depthScale * y2;
				y2 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if (y1 < 0) {
				x1 -= dx3 * y1;
				r1 -= dr3 * y1;
				g1 -= dg3 * y1;
				b1 -= db3 * y1;
				y1 = 0;
			}
			if (dx1 < dx2) {
				y3 -= y1;
				y1 -= y2;
				for (y2 = lineOffsets[y2]; --y1 >= 0; y2 += Canvas2D.width) {
					drawHDGouraudScanline(Canvas2D.pixels, y2, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z2, depthSlope);
					x3 += dx1;
					x2 += dx2;
					r3 += dr1;
					g3 += dg1;
					b3 += db1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					z2 += depthScale;
				}
				while (--y3 >= 0) {
					drawHDGouraudScanline(Canvas2D.pixels, y2, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z2, depthSlope);
					x1 += dx3;
					x2 += dx2;
					r1 += dr3;
					g1 += dg3;
					b1 += db3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					y2 += Canvas2D.width;
					z2 += depthScale;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for (y2 = lineOffsets[y2]; --y1 >= 0; y2 += Canvas2D.width) {
				drawHDGouraudScanline(Canvas2D.pixels, y2, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z2, depthSlope);
				x3 += dx1;
				x2 += dx2;
				r3 += dr1;
				g3 += dg1;
				b3 += db1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				z2 += depthScale;
			}
			while (--y3 >= 0) {
				drawHDGouraudScanline(Canvas2D.pixels, y2, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z2, depthSlope);
				x1 += dx3;
				x2 += dx2;
				r1 += dr3;
				g1 += dg3;
				b1 += db3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				y2 += Canvas2D.width;
				z2 += depthScale;
			}
			return;
		}
		if (y3 >= Canvas2D.bottomY) {
			return;
		}
		if (y1 > Canvas2D.bottomY) {
			y1 = Canvas2D.bottomY;
		}
		if (y2 > Canvas2D.bottomY) {
			y2 = Canvas2D.bottomY;
		}
		z3 = z3 - depthSlope * x3 + depthSlope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			r2 = r3 <<= 16;
			g2 = g3 <<= 16;
			b2 = b3 <<= 16;
			if (y3 < 0) {
				x2 -= dx2 * y3;
				x3 -= dx3 * y3;
				r2 -= dr2 * y3;
				g2 -= dg2 * y3;
				b2 -= db2 * y3;
				r3 -= dr3 * y3;
				g3 -= dg3 * y3;
				b3 -= db3 * y3;
				z3 -= depthScale * y3;
				y3 = 0;
			}
			x1 <<= 16;
			r1 <<= 16;
			g1 <<= 16;
			b1 <<= 16;
			if (y1 < 0) {
				x1 -= dx1 * y1;
				r1 -= dr1 * y1;
				g1 -= dg1 * y1;
				b1 -= db1 * y1;
				y1 = 0;
			}
			if (dx2 < dx3) {
				y2 -= y1;
				y1 -= y3;
				for (y3 = lineOffsets[y3]; --y1 >= 0; y3 += Canvas2D.width) {
					drawHDGouraudScanline(Canvas2D.pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z3, depthSlope);
					x2 += dx2;
					x3 += dx3;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r3 += dr3;
					g3 += dg3;
					b3 += db3;
					z3 += depthScale;
				}
				while (--y2 >= 0) {
					drawHDGouraudScanline(Canvas2D.pixels, y3, x2 >> 16, x1 >> 16, r2, g2, b2, r1, g1, b1, z3, depthSlope);
					x2 += dx2;
					x1 += dx1;
					r2 += dr2;
					g2 += dg2;
					b2 += db2;
					r1 += dr1;
					g1 += dg1;
					b1 += db1;
					y3 += Canvas2D.width;
					z3 += depthScale;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for (y3 = lineOffsets[y3]; --y1 >= 0; y3 += Canvas2D.width) {
				drawHDGouraudScanline(Canvas2D.pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z3, depthSlope);
				x2 += dx2;
				x3 += dx3;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depthScale;
			}
			while (--y2 >= 0) {
				drawHDGouraudScanline(Canvas2D.pixels, y3, x1 >> 16, x2 >> 16, r1, g1, b1, r2, g2, b2, z3, depthSlope);
				x2 += dx2;
				x1 += dx1;
				r2 += dr2;
				g2 += dg2;
				b2 += db2;
				r1 += dr1;
				g1 += dg1;
				b1 += db1;
				z3 += depthScale;
				y3 += Canvas2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		r1 = r3 <<= 16;
		g1 = g3 <<= 16;
		b1 = b3 <<= 16;
		if (y3 < 0) {
			x1 -= dx2 * y3;
			x3 -= dx3 * y3;
			r1 -= dr2 * y3;
			g1 -= dg2 * y3;
			b1 -= db2 * y3;
			r3 -= dr3 * y3;
			g3 -= dg3 * y3;
			b3 -= db3 * y3;
			z3 -= depthScale * y3;
			y3 = 0;
		}
		x2 <<= 16;
		r2 <<= 16;
		g2 <<= 16;
		b2 <<= 16;
		if (y2 < 0) {
			x2 -= dx1 * y2;
			r2 -= dr1 * y2;
			g2 -= dg1 * y2;
			b2 -= db1 * y2;
			y2 = 0;
		}
		if (dx2 < dx3) {
			y1 -= y2;
			y2 -= y3;
			for (y3 = lineOffsets[y3]; --y2 >= 0; y3 += Canvas2D.width) {
				drawHDGouraudScanline(Canvas2D.pixels, y3, x1 >> 16, x3 >> 16, r1, g1, b1, r3, g3, b3, z3, depthSlope);
				x1 += dx2;
				x3 += dx3;
				r1 += dr2;
				g1 += dg2;
				b1 += db2;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depthScale;
			}
			while (--y1 >= 0) {
				drawHDGouraudScanline(Canvas2D.pixels, y3, x2 >> 16, x3 >> 16, r2, g2, b2, r3, g3, b3, z3, depthSlope);
				x2 += dx1;
				x3 += dx3;
				r2 += dr1;
				g2 += dg1;
				b2 += db1;
				r3 += dr3;
				g3 += dg3;
				b3 += db3;
				z3 += depthScale;
				y3 += Canvas2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for (y3 = lineOffsets[y3]; --y2 >= 0; y3 += Canvas2D.width) {
			drawHDGouraudScanline(Canvas2D.pixels, y3, x3 >> 16, x1 >> 16, r3, g3, b3, r1, g1, b1, z3, depthSlope);
			x1 += dx2;
			x3 += dx3;
			r1 += dr2;
			g1 += dg2;
			b1 += db2;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			z3 += depthScale;
		}
		while (--y1 >= 0) {
			drawHDGouraudScanline(Canvas2D.pixels, y3, x3 >> 16, x2 >> 16, r3, g3, b3, r2, g2, b2, z3, depthSlope);
			x2 += dx1;
			x3 += dx3;
			r2 += dr1;
			g2 += dg1;
			b2 += db1;
			r3 += dr3;
			g3 += dg3;
			b3 += db3;
			y3 += Canvas2D.width;
			z3 += depthScale;
		}
	}
	
	public static void drawHDGouraudScanline(int[] dest, int offset, int x1, int x2, int r1, int g1, int b1, int r2, int g2, int b2, int z1, int z2) {
		int n = x2 - x1;
		if (n <= 0) {
			return;
		}
		r2 = (r2 - r1) / n;
		g2 = (g2 - g1) / n;
		b2 = (b2 - b1) / n;
		if (notTextured) {
			if (x2 > Canvas2D.centerX) {
				n -= x2 - Canvas2D.centerX;
				x2 = Canvas2D.centerX;
			}
			if (x1 < 0) {
				n = x2;
				r1 -= x1 * r2;
				g1 -= x1 * g2;
				b1 -= x1 * b2;
				x1 = 0;
			}
		}
		if (x1 < x2) {
			offset += x1;
			z1 += z2 * x1;
			if (alpha == 0) {
				while (--n >= 0) {
					if (true) {
						dest[offset] = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
						depthBuffer[offset] = z1;
					}
					z1 += z2;
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			} else {
				final int a1 = alpha;
				final int a2 = 256 - alpha;
				int rgb;
				int dst;
				while (--n >= 0) {
					rgb = (r1 & 0xff0000) | (g1 >> 8 & 0xff00) | (b1 >> 16 & 0xff);
					rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
					dst = dest[offset];
					if (true) {
						dest[offset] = rgb + ((dst & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dst & 0xff00) * a1 >> 8 & 0xff00);
						depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
					}
					z1 += z2;
					r1 += r2;
					g1 += g2;
					b1 += b2;
					offset++;
				}
			}
		}
	}
	
	public static void method376(int y1, int y2, int y3, int x1, int x2, int x3, int rgb, int z1, int z2, int z3) {
		int dx1 = 0;
		if(y2 != y1) {
			final int d = (y2 - y1);
			dx1 = (x2 - x1 << 16) / d;
		}
		int dx2 = 0;
		if(y3 != y2) {
			final int d = (y3 - y2);
			dx2 = (x3 - x2 << 16) / d;
		}
		int dx3 = 0;
		if(y3 != y1) {
			final int d = (y1 - y3);
			dx3 = (x1 - x3 << 16) / d;
		}
		
		int x21 = x2 - x1;
		int y32 = y2 - y1;
		int x31 = x3 - x1;
		int y31 = y3 - y1;
		int z21 = z2 - z1;
		int z31 = z3 - z1;

		int div = x21 * y31 - x31 * y32;
		int depthSlope = (z21 * y31 - z31 * y32) / div;
		int depthScale = (z31 * x21 - z21 * x31) / div;
		
		if(y1 <= y2 && y1 <= y3) {
			if(y1 >= Canvas2D.bottomY) {
				return;
			}
			if(y2 > Canvas2D.bottomY) {
				y2 = Canvas2D.bottomY;
			}
			if(y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			z1 = z1 - depthSlope * x1 + depthSlope;
			if(y2 < y3) {
				x3 = x1 <<= 16;
				if(y1 < 0) {
					x3 -= dx3 * y1;
					x1 -= dx1 * y1;
					z1 -= depthScale * y1;
					y1 = 0;
				}
				x2 <<= 16;
				if(y2 < 0) {
					x2 -= dx2 * y2;
					y2 = 0;
				}
				if(y1 != y2 && dx3 < dx1 || y1 == y2 && dx3 > dx2) {
					y3 -= y2;
					y2 -= y1;
					for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += Canvas2D.width) {
						drawFlatScanline(Canvas2D.pixels, y1, rgb, x3 >> 16, x1 >> 16, z1, depthSlope);
						z1 += depthScale;
						x3 += dx3;
						x1 += dx1;
					}
					while(--y3 >= 0) {
						drawFlatScanline(Canvas2D.pixels, y1, rgb, x3 >> 16, x2 >> 16, z1, depthSlope);
						z1 += depthScale;
						x3 += dx3;
						x2 += dx2;
						y1 += Canvas2D.width;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				for(y1 = lineOffsets[y1]; --y2 >= 0; y1 += Canvas2D.width) {
					drawFlatScanline(Canvas2D.pixels, y1, rgb, x1 >> 16, x3 >> 16, z1, depthSlope);
					z1 += depthScale;
					x3 += dx3;
					x1 += dx1;
				}
				while(--y3 >= 0) {
					drawFlatScanline(Canvas2D.pixels, y1, rgb, x2 >> 16, x3 >> 16, z1, depthSlope);
					z1 += depthScale;
					x3 += dx3;
					x2 += dx2;
					y1 += Canvas2D.width;
				}
				return;
			}
			x2 = x1 <<= 16;
			if(y1 < 0) {
				x2 -= dx3 * y1;
				x1 -= dx1 * y1;
				z1 -= depthScale * y1;
				y1 = 0;
			}
			x3 <<= 16;
			if(y3 < 0) {
				x3 -= dx2 * y3;
				y3 = 0;
			}
			if(y1 != y3 && dx3 < dx1 || y1 == y3 && dx2 > dx1) {
				y2 -= y3;
				y3 -= y1;
				for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += Canvas2D.width) {
					drawFlatScanline(Canvas2D.pixels, y1, rgb, x2 >> 16, x1 >> 16, z1, depthSlope);
					z1 += depthScale;
					x2 += dx3;
					x1 += dx1;
				}
				while(--y2 >= 0) {
					drawFlatScanline(Canvas2D.pixels, y1, rgb, x3 >> 16, x1 >> 16, z1, depthSlope);
					z1 += depthScale;
					x3 += dx2;
					x1 += dx1;
					y1 += Canvas2D.width;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			for(y1 = lineOffsets[y1]; --y3 >= 0; y1 += Canvas2D.width) {
				drawFlatScanline(Canvas2D.pixels, y1, rgb, x1 >> 16, x2 >> 16, z1, depthSlope);
				z1 += depthScale;
				x2 += dx3;
				x1 += dx1;
			}
			while(--y2 >= 0) {
				drawFlatScanline(Canvas2D.pixels, y1, rgb, x1 >> 16, x3 >> 16, z1, depthSlope);
				z1 += depthScale;
				x3 += dx2;
				x1 += dx1;
				y1 += Canvas2D.width;
			}
			return;
		}
		if(y2 <= y3) {
			if(y2 >= Canvas2D.bottomY) {
				return;
			}
			if(y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if(y1 > Canvas2D.bottomY) {
				y1 = Canvas2D.bottomY;
			}
			z2 = z2 - depthSlope * x2 + depthSlope;
			if(y3 < y1) {
				x1 = x2 <<= 16;
				if(y2 < 0) {
					x1 -= dx1 * y2;
					x2 -= dx2 * y2;
					z2 -= depthScale * y2;
					y2 = 0;
				}
				x3 <<= 16;
				if(y3 < 0) {
					x3 -= dx3 * y3;
					y3 = 0;
				}
				if(y2 != y3 && dx1 < dx2 || y2 == y3 && dx1 > dx3) {
					y1 -= y3;
					y3 -= y2;
					for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += Canvas2D.width) {
						drawFlatScanline(Canvas2D.pixels, y2, rgb, x1 >> 16, x2 >> 16, z2, depthSlope);
						z2 += depthScale;
						x1 += dx1;
						x2 += dx2;
					}
					while(--y1 >= 0) {
						drawFlatScanline(Canvas2D.pixels, y2, rgb, x1 >> 16, x3 >> 16, z2, depthSlope);
						z2 += depthScale;
						x1 += dx1;
						x3 += dx3;
						y2 += Canvas2D.width;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				for(y2 = lineOffsets[y2]; --y3 >= 0; y2 += Canvas2D.width) {
					drawFlatScanline(Canvas2D.pixels, y2, rgb, x2 >> 16, x1 >> 16, z2, depthSlope);
					z2 += depthScale;
					x1 += dx1;
					x2 += dx2;
				}
				while(--y1 >= 0) {
					drawFlatScanline(Canvas2D.pixels, y2, rgb, x3 >> 16, x1 >> 16, z2, depthSlope);
					z2 += depthScale;
					x1 += dx1;
					x3 += dx3;
					y2 += Canvas2D.width;
				}
				return;
			}
			x3 = x2 <<= 16;
			if(y2 < 0) {
				x3 -= dx1 * y2;
				x2 -= dx2 * y2;
				z2 -= depthScale * y2;
				y2 = 0;
			}
			x1 <<= 16;
			if(y1 < 0) {
				x1 -= dx3 * y1;
				y1 = 0;
			}
			if(dx1 < dx2) {
				y3 -= y1;
				y1 -= y2;
				for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += Canvas2D.width) {
					drawFlatScanline(Canvas2D.pixels, y2, rgb, x3 >> 16, x2 >> 16, z2, depthSlope);
					z2 += depthScale;
					x3 += dx1;
					x2 += dx2;
				}
				while(--y3 >= 0) {
					drawFlatScanline(Canvas2D.pixels, y2, rgb, x1 >> 16, x2 >> 16, z2, depthSlope);
					z2 += depthScale;
					x1 += dx3;
					x2 += dx2;
					y2 += Canvas2D.width;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			for(y2 = lineOffsets[y2]; --y1 >= 0; y2 += Canvas2D.width) {
				drawFlatScanline(Canvas2D.pixels, y2, rgb, x2 >> 16, x3 >> 16, z2, depthSlope);
				z2 += depthScale;
				x3 += dx1;
				x2 += dx2;
			}
			while(--y3 >= 0) {
				drawFlatScanline(Canvas2D.pixels, y2, rgb, x2 >> 16, x1 >> 16, z2, depthSlope);
				z2 += depthScale;
				x1 += dx3;
				x2 += dx2;
				y2 += Canvas2D.width;
			}
			return;
		}
		if(y3 >= Canvas2D.bottomY) {
			return;
		}
		if(y1 > Canvas2D.bottomY) {
			y1 = Canvas2D.bottomY;
		}
		if(y2 > Canvas2D.bottomY) {
			y2 = Canvas2D.bottomY;
		}
		z3 = z3 - depthSlope * x3 + depthSlope;
		if(y1 < y2) {
			x2 = x3 <<= 16;
			if(y3 < 0) {
				x2 -= dx2 * y3;
				x3 -= dx3 * y3;
				z3 -= depthScale * y3;
				y3 = 0;
			}
			x1 <<= 16;
			if(y1 < 0) {
				x1 -= dx1 * y1;
				y1 = 0;
			}
			if(dx2 < dx3) {
				y2 -= y1;
				y1 -= y3;
				for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += Canvas2D.width) {
					drawFlatScanline(Canvas2D.pixels, y3, rgb, x2 >> 16, x3 >> 16, z3, depthSlope);
					z3 += depthScale;
					x2 += dx2;
					x3 += dx3;
				}
				while(--y2 >= 0) {
					drawFlatScanline(Canvas2D.pixels, y3, rgb, x2 >> 16, x1 >> 16, z3, depthSlope);
					z3 += depthScale;
					x2 += dx2;
					x1 += dx1;
					y3 += Canvas2D.width;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			for(y3 = lineOffsets[y3]; --y1 >= 0; y3 += Canvas2D.width) {
				drawFlatScanline(Canvas2D.pixels, y3, rgb, x3 >> 16, x2 >> 16, z3, depthSlope);
				z3 += depthScale;
				x2 += dx2;
				x3 += dx3;
			}
			while(--y2 >= 0) {
				drawFlatScanline(Canvas2D.pixels, y3, rgb, x1 >> 16, x2 >> 16, z3, depthSlope);
				z3 += depthScale;
				x2 += dx2;
				x1 += dx1;
				y3 += Canvas2D.width;
			}
			return;
		}
		x1 = x3 <<= 16;
		if(y3 < 0) {
			x1 -= dx2 * y3;
			x3 -= dx3 * y3;
			z3 -= depthScale * y3;
			y3 = 0;
		}
		x2 <<= 16;
		if(y2 < 0) {
			x2 -= dx1 * y2;
			y2 = 0;
		}
		if(dx2 < dx3) {
			y1 -= y2;
			y2 -= y3;
			for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += Canvas2D.width) {
				drawFlatScanline(Canvas2D.pixels, y3, rgb, x1 >> 16, x3 >> 16, z3, depthSlope);
				z3 += depthScale;
				x1 += dx2;
				x3 += dx3;
			}
			while(--y1 >= 0) {
				drawFlatScanline(Canvas2D.pixels, y3, rgb, x2 >> 16, x3 >> 16, z3, depthSlope);
				z3 += depthScale;
				x2 += dx1;
				x3 += dx3;
				y3 += Canvas2D.width;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		for(y3 = lineOffsets[y3]; --y2 >= 0; y3 += Canvas2D.width) {
			drawFlatScanline(Canvas2D.pixels, y3, rgb, x3 >> 16, x1 >> 16, z3, depthSlope);
			z3 += depthScale;
			x1 += dx2;
			x3 += dx3;
		}
		while(--y1 >= 0) {
			drawFlatScanline(Canvas2D.pixels, y3, rgb, x3 >> 16, x2 >> 16, z3, depthSlope);
			z3 += depthScale;
			x2 += dx1;
			x3 += dx3;
			y3 += Canvas2D.width;
		}
	}
	
	private static void drawFlatScanline(int[] dest, int offset, int rgb, int x1, int x2, int z1, int z2) {
		if(x1 >= x2) {
			return;
		}
		if(restrict_edges) {
			if(x2 > Canvas2D.centerX) {
				x2 = Canvas2D.centerX;
			}
			if(x1 < 0) {
				x1 = 0;
			}
		}
		if(x1 >= x2) {
			return;
		}
		offset += x1;
		z1 += z2 * x1;
		int n = x2 - x1;
		if(alpha == 0) {
			while(--n >= 0) {
				if (true) {
					dest[offset] = rgb;
					depthBuffer[offset] = z1;
				}
				z1 += z2;
				offset++;
			}
		} else {
			final int a1 = alpha;
			final int a2 = 256 - alpha;
			rgb = ((rgb & 0xff00ff) * a2 >> 8 & 0xff00ff) + ((rgb & 0xff00) * a2 >> 8 & 0xff00);
			while(--n >= 0) {
				if (true) {
					dest[offset] = rgb + ((dest[offset] & 0xff00ff) * a1 >> 8 & 0xff00ff) + ((dest[offset] & 0xff00) * a1 >> 8 & 0xff00);
					depthBuffer[offset] = (z1 >> 8) * a2 + (depthBuffer[offset] >> 8) * a1;
				}
				z1 += z2;
				offset++;
			}
		}
	}

	public static void drawTexturedTriangle317(int y1, int y2, int y3, int x1, int x2, int x3, int c1, int c2, int c3, int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8, int t9, int tex) {
		c1 = 0x7f - c1;
		c2 = 0x7f - c2;
		c3 = 0x7f - c3;
		setMipmapLevel(y1, y2, y3, x1, x2, x3, tex);
		int texels[] = method371(tex)[mipMapLevel];
		aBoolean1463 = !aBooleanArray1475[tex];
		t2 = t1 - t2;
		t5 = t4 - t5;
		t8 = t7 - t8;
		t3 -= t1;
		t6 -= t4;
		t9 -= t7;
		int l4 = t3 * t4 - t6 * t1 << (Client.log_view_dist == 9 ? 14 : 15);
		int i5 = t6 * t7 - t9 * t4 << 8;
		int j5 = t9 * t1 - t3 * t7 << 5;
		int k5 = t2 * t4 - t5 * t1 << (Client.log_view_dist == 9 ? 14 : 15);
		int l5 = t5 * t7 - t8 * t4 << 8;
		int i6 = t8 * t1 - t2 * t7 << 5;
		int j6 = t5 * t3 - t2 * t6 << (Client.log_view_dist == 9 ? 14 : 15);
		int k6 = t8 * t6 - t5 * t9 << 8;
		int l6 = t2 * t9 - t8 * t3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (c2 - c1 << 16) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (c3 - c2 << 16) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (c1 - c3 << 16) / (y1 - y3);
		}
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Canvas2D.bottomY) {
				return;
			}
			if (y2 > Canvas2D.bottomY) {
				y2 = Canvas2D.bottomY;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if (y2 < y3) {
				x3 = x1 <<= 16;
				c3 = c1 <<= 16;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					c3 -= j8 * y1;
					c1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				c2 <<= 16;
				if (y2 < 0) {
					x2 -= k7 * y2;
					c2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = lineOffsets[y1];
					while (--y2 >= 0) {
						drawTexturedScanline317(Canvas2D.pixels, texels, y1, x3 >> 16, x1 >> 16, c3 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x1 += i7;
						c3 += j8;
						c1 += j7;
						y1 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawTexturedScanline317(Canvas2D.pixels, texels, y1, x3 >> 16, x2 >> 16, c3 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x2 += k7;
						c3 += j8;
						c2 += l7;
						y1 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = lineOffsets[y1];
				while (--y2 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y1, x1 >> 16, x3 >> 16, c1 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x1 += i7;
					c3 += j8;
					c1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y1, x2 >> 16, x3 >> 16, c2 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x2 += k7;
					c3 += j8;
					c2 += l7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			c2 = c1 <<= 16;
			if (y1 < 0) {
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				c2 -= j8 * y1;
				c1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			c3 <<= 16;
			if (y3 < 0) {
				x3 -= k7 * y3;
				c3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = lineOffsets[y1];
				while (--y3 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y1, x2 >> 16, x1 >> 16, c2 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
					x2 += i8;
					x1 += i7;
					c2 += j8;
					c1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y1, x3 >> 16, x1 >> 16, c3 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
					x3 += k7;
					x1 += i7;
					c3 += l7;
					c1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = lineOffsets[y1];
			while (--y3 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y1, x1 >> 16, x2 >> 16, c1 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
				x2 += i8;
				x1 += i7;
				c2 += j8;
				c1 += j7;
				y1 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y1, x1 >> 16, x3 >> 16, c1 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
				x3 += k7;
				x1 += i7;
				c3 += l7;
				c1 += j7;
				y1 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Canvas2D.bottomY) {
				return;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if (y1 > Canvas2D.bottomY) {
				y1 = Canvas2D.bottomY;
			}
			if (y3 < y1) {
				x1 = x2 <<= 16;
				c1 = c2 <<= 16;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					c1 -= j7 * y2;
					c2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				c3 <<= 16;
				if (y3 < 0) {
					x3 -= i8 * y3;
					c3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = lineOffsets[y2];
					while (--y3 >= 0) {
						drawTexturedScanline317(Canvas2D.pixels, texels, y2, x1 >> 16, x2 >> 16, c1 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x2 += k7;
						c1 += j7;
						c2 += l7;
						y2 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawTexturedScanline317(Canvas2D.pixels, texels, y2, x1 >> 16, x3 >> 16, c1 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x3 += i8;
						c1 += j7;
						c3 += j8;
						y2 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = lineOffsets[y2];
				while (--y3 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y2, x2 >> 16, x1 >> 16, c2 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x2 += k7;
					c1 += j7;
					c2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y2, x3 >> 16, x1 >> 16, c3 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x3 += i8;
					c1 += j7;
					c3 += j8;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			c3 = c2 <<= 16;
			if (y2 < 0) {
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				c3 -= j7 * y2;
				c2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			c1 <<= 16;
			if (y1 < 0) {
				x1 -= i8 * y1;
				c1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = lineOffsets[y2];
				while (--y1 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y2, x3 >> 16, x2 >> 16, c3 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
					x3 += i7;
					x2 += k7;
					c3 += j7;
					c2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y2, x1 >> 16, x2 >> 16, c1 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
					x1 += i8;
					x2 += k7;
					c1 += j8;
					c2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = lineOffsets[y2];
			while (--y1 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y2, x2 >> 16, x3 >> 16, c2 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
				x3 += i7;
				x2 += k7;
				c3 += j7;
				c2 += l7;
				y2 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y2, x2 >> 16, x1 >> 16, c2 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
				x1 += i8;
				x2 += k7;
				c1 += j8;
				c2 += l7;
				y2 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= Canvas2D. bottomY) {
			return;
		}
		if (y1 > Canvas2D.bottomY) {
			y1 = Canvas2D.bottomY;
		}
		if (y2 > Canvas2D.bottomY) {
			y2 = Canvas2D.bottomY;
		}
		if (y1 < y2) {
			x2 = x3 <<= 16;
			c2 = c3 <<= 16;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				c2 -= l7 * y3;
				c3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			c1 <<= 16;
			if (y1 < 0) {
				x1 -= i7 * y1;
				c1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = lineOffsets[y3];
				while (--y1 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y3, x2 >> 16, x3 >> 16, c2 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x3 += i8;
					c2 += l7;
					c3 += j8;
					y3 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedScanline317(Canvas2D.pixels, texels, y3, x2 >> 16, x1 >> 16, c2 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x1 += i7;
					c2 += l7;
					c1 += j7;
					y3 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = lineOffsets[y3];
			while (--y1 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y3, x3 >> 16, x2 >> 16, c3 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x3 += i8;
				c2 += l7;
				c3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y3, x1 >> 16, x2 >> 16, c1 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x1 += i7;
				c2 += l7;
				c1 += j7;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		c1 = c3 <<= 16;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			c1 -= l7 * y3;
			c3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		c2 <<= 16;
		if (y2 < 0) {
			x2 -= i7 * y2;
			c2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = lineOffsets[y3];
			while (--y2 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y3, x1 >> 16, x3 >> 16, c1 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
				x1 += k7;
				x3 += i8;
				c1 += l7;
				c3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawTexturedScanline317(Canvas2D.pixels, texels, y3, x2 >> 16, x3 >> 16, c2 >> 8, c3 >> 8, l4, k5, j6, i5, l5, k6);
				x2 += i7;
				x3 += i8;
				c2 += j7;
				c3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = lineOffsets[y3];
		while (--y2 >= 0) {
			drawTexturedScanline317(Canvas2D.pixels, texels, y3, x3 >> 16, x1 >> 16, c3 >> 8, c1 >> 8, l4, k5, j6, i5, l5, k6);
			x1 += k7;
			x3 += i8;
			c1 += l7;
			c3 += j8;
			y3 += Canvas2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawTexturedScanline317(Canvas2D.pixels, texels, y3, x3 >> 16, x2 >> 16, c3 >> 8, c2 >> 8, l4, k5, j6, i5, l5, k6);
			x2 += i7;
			x3 += i8;
			c2 += j7;
			c3 += j8;
			y3 += Canvas2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawTexturedScanline317(int dest[], int src[], int offset, int x1, int x2, int lig1, int lig2, int t1, int t2, int t3, int t4, int t5, int t6) {
		int i = 0;
		int j = 0;
		if (x1 >= x2) {
			return;
		}
		int dlig = (lig2 - lig1) / (x2 - x1);
		int k3;
		if (aBoolean1463) {
			if (x2 > Canvas2D.bottomX) {
				x2 = Canvas2D.bottomX;
			}
			if (x1 < 0) {
				lig1 -= x1 * dlig;
				x1 = 0;
			}
			if (x1 >= x2) {
				return;
			}
			k3 = x2 - x1 >> 3;
		} else {
			if (x2 - x1 > 7) {
				k3 = x2 - x1 >> 3;
			} else {
				k3 = 0;
			}
		}
		offset += x1;
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - centerX;
		t1 += (t4 >> 3) * l6;
		t2 += (t5 >> 3) * l6;
		t3 += (t6 >> 3) * l6;
		int l5 = t3 >> 14;
		if (l5 != 0) {
			i = t1 / l5;
			j = t2 / l5;
			if (i < 0) {
				i = 0;
			} else if (i > 16256) {
				i = 16256;
			}
		}
		t1 += t4;
		t2 += t5;
		t3 += t6;
		l5 = t3 >> 14;
		if (l5 != 0) {
			j4 = t1 / l5;
			l4 = t2 / l5;
			if (j4 < 7) {
				j4 = 7;
			} else if (j4 > 16256) {
				j4 = 16256;
			}
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		if (aBoolean1463) {
			while (k3-- > 0) {
				int i9;
				int l;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
				i9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
				i = j4;
				j = l4;
				lig1 += dlig;
				t1 += t4;
				t2 += t5;
				t3 += t6;
				int i6 = t3 >> 14;
				if (i6 != 0) {
					j4 = t1 / i6;
					l4 = t2 / i6;
					if (j4 < 7) {
						j4 = 7;
					} else if (j4 > 16256) {
						j4 = 16256;
					}
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
			}
			for (k3 = x2 - x1 & 7; k3-- > 0;) {
				int j9;
				int l;
				j9 = src[texelPos((j & 0x3f80) + (i >> 7))];
				l = lig1 >> 8;
				dest[offset++] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 7;
				i += j7;
				j += l7;
				lig1 += dlig;
			}
			return;
		}
		while (k3-- > 0) {
			int i9;
			int l;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
			if ((i9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i = j4;
			j = l4;
			lig1 += dlig;
			t1 += t4;
			t2 += t5;
			t3 += t6;
			int j6 = t3 >> 14;
			if (j6 != 0) {
				j4 = t1 / j6;
				l4 = t2 / j6;
				if (j4 < 7) {
					j4 = 7;
				} else if (j4 > 16256) {
					j4 = 16256;
				}
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
		}
		for (int l3 = x2 - x1 & 7; l3-- > 0;) {
			int j9;
			int l;
			if ((j9 = src[texelPos((j & 0x3f80) + (i >> 7))]) != 0) {
				l = lig1 >> 8;
				dest[offset] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 7;
			}
			offset++;
			i += j7;
			j += l7;
			lig1 += dlig;
		}
	}
	
	public static void method378(int y1, int y2, int y3, int x1, int x2, int x3, int c1, int c2, int c3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3, int tz1, int tz2, int tz3, int tex, int z1, int z2, int z3) {
		if(!notTextured) {
			drawTexturedTriangle317(y1, y2, y3, x1, x2, x3, c1, c2, c3, tx1, tx2, tx3, ty1, ty2, ty3, tz1, tz2, tz3, tex);
			return;
		}
		setMipmapLevel(y1, y2, y3, x1, x2, x3, tex);
		c1 = 0x7f - c1 << 1;
		c2 = 0x7f - c2 << 1;
		c3 = 0x7f - c3 << 1;
		int texels[] = method371(tex)[mipMapLevel];
		aBoolean1463 = !aBooleanArray1475[tex];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (Client.log_view_dist == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if (y2 != y1) {
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (c2 - c1 << 16) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if (y3 != y2) {
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (c3 - c2 << 16) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if (y3 != y1) {
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (c1 - c3 << 16) / (y1 - y3);
		}
		
		int x21 = x2 - x1;
		int y32 = y2 - y1;
		int x31 = x3 - x1;
		int y31 = y3 - y1;
		int z21 = z2 - z1;
		int z31 = z3 - z1;

		int div = x21 * y31 - x31 * y32;
		int depthSlope = (z21 * y31 - z31 * y32) / div;
		int depthScale = (z31 * x21 - z21 * x31) / div;
		
		if (y1 <= y2 && y1 <= y3) {
			if (y1 >= Canvas2D.bottomY) {
				return;
			}
			if (y2 > Canvas2D.bottomY) {
				y2 = Canvas2D.bottomY;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			z1 = z1 - depthSlope * x1 + depthSlope;
			if (y2 < y3) {
				x3 = x1 <<= 16;
				c3 = c1 <<= 16;
				if (y1 < 0) {
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					z1 -= depthScale * y1;
					c3 -= j8 * y1;
					c1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				c2 <<= 16;
				if (y2 < 0) {
					x2 -= k7 * y2;
					c2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - centerY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
					y3 -= y2;
					y2 -= y1;
					y1 = lineOffsets[y1];
					while (--y2 >= 0) {
						drawTexturedScanline(Canvas2D.pixels, texels, y1, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6, z1, depthSlope);
						z1 += depthScale;
						x3 += i8;
						x1 += i7;
						c3 += j8;
						c1 += j7;
						y1 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y3 >= 0) {
						drawTexturedScanline(Canvas2D.pixels, texels, y1, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6, z1, depthSlope);
						z1 += depthScale;
						x3 += i8;
						x2 += k7;
						c3 += j8;
						c2 += l7;
						y1 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = lineOffsets[y1];
				while (--y2 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y1, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					z1 += depthScale;
					x3 += i8;
					x1 += i7;
					c3 += j8;
					c1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y1, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					z1 += depthScale;
					x3 += i8;
					x2 += k7;
					c3 += j8;
					c2 += l7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			c2 = c1 <<= 16;
			if (y1 < 0) {
				x2 -= i8 * y1;
				z1 -= depthScale * y1;
				x1 -= i7 * y1;
				c2 -= j8 * y1;
				c1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			c3 <<= 16;
			if (y3 < 0) {
				x3 -= k7 * y3;
				c3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - centerY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
				y2 -= y3;
				y3 -= y1;
				y1 = lineOffsets[y1];
				while (--y3 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y1, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					z1 += depthScale;
					x2 += i8;
					x1 += i7;
					c2 += j8;
					c1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y1, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6, z1, depthSlope);
					z1 += depthScale;
					x3 += k7;
					x1 += i7;
					c3 += l7;
					c1 += j7;
					y1 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = lineOffsets[y1];
			while (--y3 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y1, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6, z1, depthSlope);
				z1 += depthScale;
				x2 += i8;
				x1 += i7;
				c2 += j8;
				c1 += j7;
				y1 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y1, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6, z1, depthSlope);
				z1 += depthScale;
				x3 += k7;
				x1 += i7;
				c3 += l7;
				c1 += j7;
				y1 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y2 <= y3) {
			if (y2 >= Canvas2D.bottomY) {
				return;
			}
			if (y3 > Canvas2D.bottomY) {
				y3 = Canvas2D.bottomY;
			}
			if (y1 > Canvas2D.bottomY) {
				y1 = Canvas2D.bottomY;
			}
			z2 = z2 - depthSlope * x2 + depthSlope;
			if (y3 < y1) {
				x1 = x2 <<= 16;
				c1 = c2 <<= 16;
				if (y2 < 0) {
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					z2 -= depthScale * y2;
					c1 -= j7 * y2;
					c2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				c3 <<= 16;
				if (y3 < 0) {
					x3 -= i8 * y3;
					c3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - centerY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
					y1 -= y3;
					y3 -= y2;
					y2 = lineOffsets[y2];
					while (--y3 >= 0) {
						drawTexturedScanline(Canvas2D.pixels, texels, y2, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6, z2, depthSlope);
						z2 += depthScale;
						x1 += i7;
						x2 += k7;
						c1 += j7;
						c2 += l7;
						y2 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while (--y1 >= 0) {
						drawTexturedScanline(Canvas2D.pixels, texels, y2, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6, z2, depthSlope);
						z2 += depthScale;
						x1 += i7;
						x3 += i8;
						c1 += j7;
						c3 += j8;
						y2 += Canvas2D.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = lineOffsets[y2];
				while (--y3 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y2, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					z2 += depthScale;
					x1 += i7;
					x2 += k7;
					c1 += j7;
					c2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y1 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y2, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					z2 += depthScale;
					x1 += i7;
					x3 += i8;
					c1 += j7;
					c3 += j8;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			c3 = c2 <<= 16;
			if (y2 < 0) {
				x3 -= i7 * y2;
				z2 -= depthScale * y2;
				x2 -= k7 * y2;
				c3 -= j7 * y2;
				c2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			c1 <<= 16;
			if (y1 < 0) {
				x1 -= i8 * y1;
				c1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - centerY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if (i7 < k7) {
				y3 -= y1;
				y1 -= y2;
				y2 = lineOffsets[y2];
				while (--y1 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y2, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					z2 += depthScale;
					x3 += i7;
					x2 += k7;
					c3 += j7;
					c2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y3 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y2, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6, z2, depthSlope);
					z2 += depthScale;
					x1 += i8;
					x2 += k7;
					c1 += j8;
					c2 += l7;
					y2 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = lineOffsets[y2];
			while (--y1 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y2, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6, z2, depthSlope);
				z2 += depthScale;
				x3 += i7;
				x2 += k7;
				c3 += j7;
				c2 += l7;
				y2 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y3 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y2, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6, z2, depthSlope);
				z2 += depthScale;
				x1 += i8;
				x2 += k7;
				c1 += j8;
				c2 += l7;
				y2 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if (y3 >= Canvas2D.bottomY) {
			return;
		}
		if (y1 > Canvas2D.bottomY) {
			y1 = Canvas2D.bottomY;
		}
		if (y2 > Canvas2D.bottomY) {
			y2 = Canvas2D.bottomY;
		}
		z3 = z3 - depthSlope * x3 + depthSlope;
		if (y1 < y2) {
			x2 = x3 <<= 16;
			c2 = c3 <<= 16;
			if (y3 < 0) {
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				z3 -= depthScale * y3;
				c2 -= l7 * y3;
				c3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			c1 <<= 16;
			if (y1 < 0) {
				x1 -= i7 * y1;
				c1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - centerY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if (k7 < i8) {
				y2 -= y1;
				y1 -= y3;
				y3 = lineOffsets[y3];
				while (--y1 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y3, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6, z3, depthSlope);
					z3 += depthScale;
					x2 += k7;
					x3 += i8;
					c2 += l7;
					c3 += j8;
					y3 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while (--y2 >= 0) {
					drawTexturedScanline(Canvas2D.pixels, texels, y3, x2 >> 16, x1 >> 16, c2, c1, l4, k5, j6, i5, l5, k6, z3, depthSlope);
					z3 += depthScale;
					x2 += k7;
					x1 += i7;
					c2 += l7;
					c1 += j7;
					y3 += Canvas2D.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = lineOffsets[y3];
			while (--y1 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y3, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				z3 += depthScale;
				x2 += k7;
				x3 += i8;
				c2 += l7;
				c3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y2 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y3, x1 >> 16, x2 >> 16, c1, c2, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				z3 += depthScale;
				x2 += k7;
				x1 += i7;
				c2 += l7;
				c1 += j7;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		c1 = c3 <<= 16;
		if (y3 < 0) {
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			z3 -= depthScale * y3;
			c1 -= l7 * y3;
			c3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		c2 <<= 16;
		if (y2 < 0) {
			x2 -= i7 * y2;
			c2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - centerY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if (k7 < i8) {
			y1 -= y2;
			y2 -= y3;
			y3 = lineOffsets[y3];
			while (--y2 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y3, x1 >> 16, x3 >> 16, c1, c3, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				z3 += depthScale;
				x1 += k7;
				x3 += i8;
				c1 += l7;
				c3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while (--y1 >= 0) {
				drawTexturedScanline(Canvas2D.pixels, texels, y3, x2 >> 16, x3 >> 16, c2, c3, l4, k5, j6, i5, l5, k6, z3, depthSlope);
				z3 += depthScale;
				x2 += i7;
				x3 += i8;
				c2 += j7;
				c3 += j8;
				y3 += Canvas2D.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = lineOffsets[y3];
		while (--y2 >= 0) {
			drawTexturedScanline(Canvas2D.pixels, texels, y3, x3 >> 16, x1 >> 16, c3, c1, l4, k5, j6, i5, l5, k6, z3, depthSlope);
			z3 += depthScale;
			x1 += k7;
			x3 += i8;
			c1 += l7;
			c3 += j8;
			y3 += Canvas2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while (--y1 >= 0) {
			drawTexturedScanline(Canvas2D.pixels, texels, y3, x3 >> 16, x2 >> 16, c3, c2, l4, k5, j6, i5, l5, k6, z3, depthSlope);
			z3 += depthScale;
			x2 += i7;
			x3 += i8;
			c2 += j7;
			c3 += j8;
			y3 += Canvas2D.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawTexturedScanline(int dest[], int src[], int offset, int x1, int x2, int src1, int src2, int t1, int t2, int t3, int t4, int t5, int t6, int z1, int z2) {
		int darken = 0;
		int srcPos = 0;
		if(x1 >= x2) {
			return;
		}
		int dl = (src2 - src1) / (x2 - x1);
		int n;
		if(restrict_edges) {
			if(x2 > Canvas2D.centerX) {
				x2 = Canvas2D.centerX;
			}
			if(x1 < 0) {
				src1 -= x1 * dl;
				x1 = 0;
			}
		}
		if(x1 >= x2) {
			return;
		}
		n = x2 - x1 >> 3;
		offset += x1;
		z1 += z2 * x1;
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - centerX;
		t1 += (t4 >> 3) * l6;
		t2 += (t5 >> 3) * l6;
		t3 += (t6 >> 3) * l6;
		int l5 = t3 >> 14;
		if(l5 != 0)
		{
			darken = t1 / l5;
			srcPos = t2 / l5;
			if(darken < 0)
				darken = 0;
			else
			if(darken > 16256)
				darken = 16256;
		}
		t1 += t4;
		t2 += t5;
		t3 += t6;
		l5 = t3 >> 14;
		if(l5 != 0)
		{
			j4 = t1 / l5;
			l4 = t2 / l5;
			if(j4 < 7)
				j4 = 7;
			else
			if(j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - darken >> 3;
		int l7 = l4 - srcPos >> 3;
		if(aBoolean1463)
		{
			while(n-- > 0) 
			{
				int rgb;
				int l;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
				t1 += t4;
				t2 += t5;
				t3 += t6;
				int i6 = t3 >> 14;
				if(i6 != 0)
				{
					j4 = t1 / i6;
					l4 = t2 / i6;
					if(j4 < 7)
						j4 = 7;
					else
					if(j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - darken >> 3;
				l7 = l4 - srcPos >> 3;
				src1 += dl;
			}
			for(n = x2 - x1 & 7; n-- > 0;)
			{
				int rgb;
				int l;
				rgb = src[texelPos((srcPos & 0x3f80) + (darken >> 7))];
				l = src1 >> 16;
				if (true) {
					dest[offset] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					depthBuffer[offset] = z1;
				}
				offset++;
				z1 += z2;
				darken += j7;
				srcPos += l7;
				src1 += dl;
			}

			return;
		}
		while(n-- > 0) 
		{
			int i9;
			int l;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			if((i9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
			t1 += t4;
			t2 += t5;
			t3 += t6;
			int j6 = t3 >> 14;
			if(j6 != 0) {
				j4 = t1 / j6;
				l4 = t2 / j6;
				if(j4 < 7)
					j4 = 7;
				else
				if(j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - darken >> 3;
			l7 = l4 - srcPos >> 3;
			src1 += dl;
		}
		for(int l3 = x2 - x1 & 7; l3-- > 0;) {
			int j9;
			int l;
			if((j9 = src[texelPos((srcPos & 0x3f80) + (darken >> 7))]) != 0 && true) {
				l = src1 >> 16;
				dest[offset] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 8;
				depthBuffer[offset] = z1;
			}
			z1 += z2;
			offset++;
			darken += j7;
			srcPos += l7;
			src1 += dl;
		}
	}
}