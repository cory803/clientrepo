package org.chaos.map;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MapView extends EventHandler {

	private static final long serialVersionUID = 1L;

	public static final void main(String arg0[]) {
		MapView mapview1 = new MapView();
		mapview1.createFrame(765, 503);
	}

	public final void aab() {
		initializeFrame(765, 503);
	}

	/**
	 * Startup
	 */
	public final void startup() {
		Archive o1 = abb();
		drawLoadingText(100, "Please wait... Rendering Map");
		RSBuffer j1 = new RSBuffer(o1.abl("size.dat", null));
		aaj = j1.getUShort();
		aak = j1.getUShort();
		aal = j1.getUShort();
		aam = j1.getUShort();
		aen = 3200 - aaj;
		afa = (aak + aam) - 3200;
		mapYOffset = 180;
		mapXOffset = (aal * mapYOffset) / aam;
		mapX = 635 - mapXOffset - 5;
		mapY = 503 - mapYOffset - 20;
		j1 = new RSBuffer(o1.abl("labels.dat", null));
		aef = j1.getUShort();
		for (int k = 0; k < aef; k++) {
			aeh[k] = j1.ail();
			aei[k] = j1.getUShort();
			aej[k] = j1.getUShort();
			aek[k] = j1.aii();
		}

		j1 = new RSBuffer(o1.abl("floorcol.dat", null));
		int i1 = j1.getUShort();
		aan = new int[i1 + 1];
		aba = new int[i1 + 1];
		for (int k1 = 0; k1 < i1; k1++) {
			aan[k1 + 1] = j1.aih();
			aba[k1 + 1] = j1.aih();
		}

		byte abyte0[] = o1.abl("underlay.dat", null);
		byte abyte1[][] = new byte[aal][aam];
		aae(abyte0, abyte1);
		byte abyte2[] = o1.abl("overlay.dat", null);
		abc = new int[aal][aam];
		abd = new byte[aal][aam];
		aaf(abyte2, abc, abd);
		byte abyte3[] = o1.abl("loc.dat", null);
		abe = new byte[aal][aam];
		abg = new byte[aal][aam];
		abf = new byte[aal][aam];
		aad(abyte3, abe, abg, abf);
		try {
			for (int l1 = 0; l1 < 100; l1++)
				abh[l1] = new IndexedSprite(o1, "mapscene", l1);

		} catch (Exception _ex) {
		}
		try {
			for (int i2 = 0; i2 < 100; i2++)
				abi[i2] = new Sprite(o1, "mapfunction", i2);

		} catch (Exception _ex) {
		}
		abj = new TextDrawingArea(o1, "b12_full", false);
		abk = new RSFont(11, true, this);
		abl = new RSFont(12, true, this);
		abm = new RSFont(14, true, this);
		abn = new RSFont(17, true, this);
		aca = new RSFont(19, true, this);
		acb = new RSFont(22, true, this);
		acc = new RSFont(26, true, this);
		acd = new RSFont(30, true, this);
		abb = new int[aal][aam];
		aag(abyte1, abb);
		adn = new Sprite(mapXOffset, mapYOffset);
		adn.acf();
		aan(0, 0, aal, aam, 0, 0, mapXOffset, mapYOffset);
		Raster.drawRectangle(0, 0, mapXOffset, mapYOffset, 0);
		Raster.drawRectangle(1, 1, mapXOffset - 2, mapYOffset - 2, aab);
		super.fullScreen.initializeDrawingArea();
	}

	private final void aad(byte arg0[], byte arg1[][], byte arg2[][],
			byte arg3[][]) {
		for (int k = 0; k < arg0.length;) {
			int j1 = (arg0[k++] & 0xff) * 64 - aaj;
			int k1 = (arg0[k++] & 0xff) * 64 - aak;
			if (j1 > 0 && k1 > 0 && j1 + 64 < aal && k1 + 64 < aam) {
				for (int l1 = 0; l1 < 64; l1++) {
					byte abyte0[] = arg1[l1 + j1];
					byte abyte1[] = arg2[l1 + j1];
					byte abyte2[] = arg3[l1 + j1];
					int k2 = aam - k1 - 1;
					for (int l2 = -64; l2 < 0; l2++) {
						do {
							int i1 = arg0[k++] & 0xff;
							if (i1 == 0)
								break;
							if (i1 < 29)
								abyte0[k2] = (byte) i1;
							else if (i1 < 160) {
								abyte1[k2] = (byte) (i1 - 28);
							} else {
								abyte2[k2] = (byte) (i1 - 159);
								aci[ach] = l1 + j1;
								acj[ach] = k2;
								ack[ach] = i1 - 160;
								ach++;
							}
						} while (true);
						k2--;
					}

				}

			} else {
				for (int i2 = 0; i2 < 64; i2++) {
					for (int j2 = -64; j2 < 0; j2++) {
						byte byte0;
						do
							byte0 = arg0[k++];
						while (byte0 != 0);
					}

				}

			}
		}

	}

	private final void aae(byte arg0[], byte arg1[][]) {
		for (int k = 0; k < arg0.length;) {
			int i1 = (arg0[k++] & 0xff) * 64 - aaj;
			int j1 = (arg0[k++] & 0xff) * 64 - aak;
			if (i1 > 0 && j1 > 0 && i1 + 64 < aal && j1 + 64 < aam) {
				for (int k1 = 0; k1 < 64; k1++) {
					byte abyte0[] = arg1[k1 + i1];
					int l1 = aam - j1 - 1;
					for (int i2 = -64; i2 < 0; i2++)
						abyte0[l1--] = arg0[k++];

				}

			} else {
				k += 4096;
			}
		}

	}

	private final void aaf(byte arg0[], int arg1[][], byte arg2[][]) {
		for (int k = 0; k < arg0.length;) {
			int i1 = (arg0[k++] & 0xff) * 64 - aaj;
			int j1 = (arg0[k++] & 0xff) * 64 - aak;
			if (i1 > 0 && j1 > 0 && i1 + 64 < aal && j1 + 64 < aam) {
				for (int k1 = 0; k1 < 64; k1++) {
					int ai[] = arg1[k1 + i1];
					byte abyte0[] = arg2[k1 + i1];
					int i2 = aam - j1 - 1;
					for (int j2 = -64; j2 < 0; j2++) {
						byte byte0 = arg0[k++];
						if (byte0 != 0) {
							abyte0[i2] = arg0[k++];
							ai[i2--] = aba[byte0];
						} else {
							ai[i2--] = 0;
						}
					}

				}

			} else {
				for (int l1 = -4096; l1 < 0; l1++) {
					byte byte1 = arg0[k++];
					if (byte1 != 0)
						k++;
				}

			}
		}

	}

	private final void aag(byte arg0[][], int arg1[][]) {
		int k = aal;
		int i1 = aam;
		int ai[] = new int[i1];
		for (int j1 = 0; j1 < i1; j1++)
			ai[j1] = 0;

		for (int k1 = 5; k1 < k - 5; k1++) {
			byte abyte0[] = arg0[k1 + 5];
			byte abyte1[] = arg0[k1 - 5];
			for (int l1 = 0; l1 < i1; l1++)
				ai[l1] += aan[abyte0[l1] & 0xff] - aan[abyte1[l1] & 0xff];

			if (k1 > 10 && k1 < k - 10) {
				int i2 = 0;
				int j2 = 0;
				int k2 = 0;
				int ai1[] = arg1[k1];
				for (int l2 = 5; l2 < i1 - 5; l2++) {
					int i3 = ai[l2 - 5];
					int j3 = ai[l2 + 5];
					i2 += (j3 >> 20) - (i3 >> 20);
					j2 += (j3 >> 10 & 0x3ff) - (i3 >> 10 & 0x3ff);
					k2 += (j3 & 0x3ff) - (i3 & 0x3ff);
					if (k2 > 0)
						ai1[l2] = aah((double) i2 / 8533D, (double) j2 / 8533D,
								(double) k2 / 8533D);
				}

			}
		}

	}

	private final int aah(double arg0, double arg1, double arg2) {
		double d = arg2;
		double d1 = arg2;
		double d2 = arg2;
		if (arg1 != 0.0D) {
			double d3;
			if (arg2 < 0.5D)
				d3 = arg2 * (1.0D + arg1);
			else
				d3 = (arg2 + arg1) - arg2 * arg1;
			double d4 = 2D * arg2 - d3;
			double d5 = arg0 + 0.33333333333333331D;
			if (d5 > 1.0D)
				d5--;
			double d6 = arg0;
			double d7 = arg0 - 0.33333333333333331D;
			if (d7 < 0.0D)
				d7++;
			if (6D * d5 < 1.0D)
				d = d4 + (d3 - d4) * 6D * d5;
			else if (2D * d5 < 1.0D)
				d = d3;
			else if (3D * d5 < 2D)
				d = d4 + (d3 - d4) * (0.66666666666666663D - d5) * 6D;
			else
				d = d4;
			if (6D * d6 < 1.0D)
				d1 = d4 + (d3 - d4) * 6D * d6;
			else if (2D * d6 < 1.0D)
				d1 = d3;
			else if (3D * d6 < 2D)
				d1 = d4 + (d3 - d4) * (0.66666666666666663D - d6) * 6D;
			else
				d1 = d4;
			if (6D * d7 < 1.0D)
				d2 = d4 + (d3 - d4) * 6D * d7;
			else if (2D * d7 < 1.0D)
				d2 = d3;
			else if (3D * d7 < 2D)
				d2 = d4 + (d3 - d4) * (0.66666666666666663D - d7) * 6D;
			else
				d2 = d4;
		}
		int k = (int) (d * 256D);
		int i1 = (int) (d1 * 256D);
		int j1 = (int) (d2 * 256D);
		int k1 = (k << 16) + (i1 << 8) + j1;
		return k1;
	}

	public final void reset() {
		try {
			aan = null;
			aba = null;
			abb = null;
			abc = null;
			abd = null;
			abe = null;
			abf = null;
			abg = null;
			abh = null;
			abi = null;
			abj = null;
			ace = null;
			acf = null;
			acg = null;
			aci = null;
			acj = null;
			ack = null;
			adn = null;
			aeh = null;
			aei = null;
			aej = null;
			aek = null;
			shopList = null;
			System.gc();
			return;
		} catch (Throwable _ex) {
			return;
		}
	}

	public final void processLoop() {
		if (super.keyPresses[1] == 1) {
			aen = (int) ((double) aen - 16D / ael);
			keyPressed = true;
		}
		if (super.keyPresses[2] == 1) {
			aen = (int) ((double) aen + 16D / ael);
			keyPressed = true;
		}
		if (super.keyPresses[3] == 1) {
			afa = (int) ((double) afa - 16D / ael);
			keyPressed = true;
		}
		if (super.keyPresses[4] == 1) {
			afa = (int) ((double) afa + 16D / ael);
			keyPressed = true;
		}
		for (int key = 1; key > 0;) {
			key = nextClick();
			if (key == 49) {
				currentZoom = 3D;
				keyPressed = true;
			}
			if (key == 50) {
				currentZoom = 4D;
				keyPressed = true;
			}
			if (key == 51) {
				currentZoom = 6D;
				keyPressed = true;
			}
			if (key == 52) {
				currentZoom = 8D;
				keyPressed = true;
			}
			if (key == 107 || key == 75 ) {
				showKeyInterface = !showKeyInterface;
				keyPressed = true;
			}
			if (key == 111 || key == 79) {
				showMapInterface = !showMapInterface;
				keyPressed = true;
			}
			if (super.rsFrame != null && key == 101) {
				System.out.println("Starting export...");
				Sprite g1 = new Sprite(aal * 2, aam * 2);
				g1.acf();
				aan(0, 0, aal, aam, 0, 0, aal * 2, aam * 2);
				super.fullScreen.initializeDrawingArea();
				int l1 = g1.agk.length;
				byte abyte0[] = new byte[l1 * 3];
				int i3 = 0;
				for (int k3 = 0; k3 < l1; k3++) {
					int l3 = g1.agk[k3];
					abyte0[i3++] = (byte) (l3 >> 16);
					abyte0[i3++] = (byte) (l3 >> 8);
					abyte0[i3++] = (byte) l3;
				}

				System.out.println("Saving to disk");
				try {
					BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
							new FileOutputStream("map-" + aal * 2 + "-" + aam
									* 2 + "-rgb.raw"));
					bufferedoutputstream.write(abyte0);
					bufferedoutputstream.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				System.out.println("Done export: " + aal * 2 + "," + aam * 2);
			}
		}

		if (super.clickType == 1) {
			aea = super.mouseX;
			aeb = super.mouseY;
			drawCenteredString = aen;
			aed = afa;
			// zoom 37%
			if (super.mouseX > 166 && super.mouseX < 215 && super.mouseY > 449
					&& super.mouseY < 476) {				
				currentZoom = 3D;
				aea = -1;				
			}
			// zoom 50%
			if (super.mouseX > 226 && super.mouseX < 274 && super.mouseY > 449
					&& super.mouseY < 476) {
				currentZoom = 4D;
				aea = -1;
			}
			//zoom 75%
			if (super.mouseX > 286 && super.mouseX < 335 && super.mouseY > 449
					&& super.mouseY < 476) {
				currentZoom = 6D;
				aea = -1;
			}
			//zoom 100%
			if (super.mouseX > 346 && super.mouseX < 395 && super.mouseY > 449
					&& super.mouseY < 476) {
				currentZoom = 8D;
				aea = -1;
			}
			if (super.mouseX > keyX && super.mouseY > keyY + keyYOffset
					&& super.mouseX < keyX + keyRX && super.mouseY < 477) {
				showKeyInterface = !showKeyInterface;
				aea = -1;
			}
			if (super.mouseX > mapX && super.mouseY > mapY + mapYOffset
					&& super.mouseX < mapX + mapXOffset && super.mouseY < 477) {
				showMapInterface = !showMapInterface;
				aea = -1;
			}
			if (showKeyInterface) {
				if (super.mouseX > keyX && super.mouseY > keyY && super.mouseX < keyX + keyRX
						&& super.mouseY < keyY + keyYOffset)
					aea = -1;
				if (super.mouseX > keyX && super.mouseY > keyY && super.mouseX < keyX + keyRX
						&& super.mouseY < keyY + 18 && adc > 0)
					adc -= 25;
				if (super.mouseX > keyX && super.mouseY > (keyY + keyYOffset) - 18
						&& super.mouseX < keyX + keyRX && super.mouseY < keyY + keyYOffset
						&& adc < 50)
					adc += 25;
			}
			keyPressed = true;
		}
		if (showKeyInterface) {
			ade = -1;
			if (super.xDragged > keyX && super.xDragged < keyX + keyRX) {
				int i1 = keyY + 21 + 5;
				for (int shopIndex = 0; shopIndex < 25; shopIndex++)
					if (shopIndex + adb >= shopList.length || !shopList[shopIndex + adb].equals("???")) {
						if (super.yDragged >= i1 && super.yDragged < i1 + 17) {
							ade = shopIndex + adb;
							if (super.clickType == 1) {
								adg = shopIndex + adb;
								adh = 50;
							}
						}
						i1 += 17;
					}

			}
			if (ade != adf) {
				adf = ade;
				keyPressed = true;
			}
		}
		if ((super.clickMode2 == 1 || super.clickType == 1) && showMapInterface) {
			int x = super.mouseX;
			int y = super.mouseY;
			if (super.clickMode2 == 1) {
				x = super.xDragged;
				y = super.yDragged;
			}
			if (x > mapX && y > mapY && x < mapX + mapXOffset && y < mapY + mapYOffset) {
				aen = ((x - mapX) * aal) / mapXOffset;
				afa = ((y - mapY) * aam) / mapYOffset;
				aea = -1;
				keyPressed = true;
			}
		}
		if (super.clickMode2 == 1 && aea != -1) {
			aen = drawCenteredString + (int) (((double) (aea - super.xDragged) * 2D) / currentZoom);
			afa = aed + (int) (((double) (aeb - super.yDragged) * 2D) / currentZoom);
			keyPressed = true;
		}
		if (ael < currentZoom) {
			keyPressed = true;
			ael += ael / 30D;
			if (ael > currentZoom)
				ael = currentZoom;
		}
		if (ael > currentZoom) {
			keyPressed = true;
			ael -= ael / 30D;
			if (ael < currentZoom)
				ael = currentZoom;
		}
		if (adb < adc) {
			keyPressed = true;
			adb++;
		}
		if (adb > adc) {
			keyPressed = true;
			adb--;
		}
		if (adh > 0) {
			keyPressed = true;
			adh--;
		}
		int k1 = aen - (int) (635D / ael);
		int k2 = afa - (int) (503D / ael);
		int l2 = aen + (int) (635D / ael);
		int j3 = afa + (int) (503D / ael);
		if (k1 < 48)
			aen = 48 + (int) (635D / ael);
		if (k2 < 48)
			afa = 48 + (int) (503D / ael);
		if (l2 > aal - 48)
			aen = aal - 48 - (int) (635D / ael);
		if (j3 > aam - 48)
			afa = aam - 48 - (int) (503D / ael);
	}

	public final void age() {
		if (keyPressed) {
			keyPressed = false;
			aai = 0;
			Raster.clear();
			int k = aen - (int) (635D / ael);
			int i1 = afa - (int) (503D / ael);
			int j1 = aen + (int) (635D / ael);
			int k1 = afa + (int) (503D / ael);
			aan(k, i1, j1, k1, 0, 0, 765, 503);
			if (showMapInterface) {
				adn.ack(mapX, mapY);
				Raster.fillOpacRect(mapX + (mapXOffset * k) / aal, mapY + (mapYOffset * i1) / aam,
						((j1 - k) * mapXOffset) / aal, ((k1 - i1) * mapYOffset) / aam,
						0xff0000, 128);
				Raster.drawRectangle(mapX + (mapXOffset * k) / aal, mapY + (mapYOffset * i1) / aam,
						((j1 - k) * mapXOffset) / aal, ((k1 - i1) * mapYOffset) / aam,
						0xff0000);
				if (adh > 0 && adh % 10 < 5) {
					for (int l1 = 0; l1 < ach; l1++)
						if (ack[l1] == adg) {
							int j2 = mapX + (mapXOffset * aci[l1]) / aal;
							int l2 = mapY + (mapYOffset * acj[l1]) / aam;
							Raster.drawDot(j2, l2, 2, 0xffff00, 256);
						}

				}
			}
			if (showKeyInterface) {
				aam(keyX, keyY, keyRX, 18, 0x999999, 0x777777, 0x555555,
						"Prev page");
				aam(keyX, keyY + 18, keyRX, keyYOffset - 36, 0x999999, 0x777777, 0x555555,
						"");
				aam(keyX, (keyY + keyYOffset) - 18, keyRX, 18, 0x999999, 0x777777,
						0x555555, "Next page");
				int i2 = keyY + 3 + 18;
				for (int k2 = 0; k2 < 25; k2++) {
					if (k2 + adb < abi.length && k2 + adb < shopList.length) {
						if (shopList[k2 + adb].equals("???"))
							continue;
						abi[k2 + adb].acg(keyX + 3, i2);
						abj.drawString(shopList[k2 + adb], keyX + 21, i2 + 14, 0);
						int i3 = 0xffffff;
						if (ade == k2 + adb)
							i3 = 0xbbaaaa;
						if (adh > 0 && adh % 10 < 5 && adg == k2 + adb)
							i3 = 0xffff00;
						abj.drawString(shopList[k2 + adb], keyX + 20, i2 + 13, i3);
					}
					i2 += 17;
				}

			}
			aam(mapX, mapY + mapYOffset, mapXOffset, 18, aab, aac, aad, "Overview");
			aam(keyX, keyY + keyYOffset, keyRX, 18, aab, aac, aad, "Key");
			if (currentZoom == 3D)
				aam(170, 471, 50, 30, aae, aaf, aag, "37%");
			else
				aam(170, 471, 50, 30, aab, aac, aad, "37%");
			if (currentZoom == 4D)
				aam(230, 471, 50, 30, aae, aaf, aag, "50%");
			else
				aam(230, 471, 50, 30, aab, aac, aad, "50%");
			if (currentZoom == 6D)
				aam(290, 471, 50, 30, aae, aaf, aag, "75%");
			else
				aam(290, 471, 50, 30, aab, aac, aad, "75%");
			if (currentZoom == 8D)
				aam(350, 471, 50, 30, aae, aaf, aag, "100%");
			else
				aam(350, 471, 50, 30, aab, aac, aad, "100%");
		}
		aai--;
		if (aai <= 0) {
			super.fullScreen.ade(super.graphics, 0, 0);
			aai = 50;
		}
	}

	public final void afb() {
		aai = 0;
	}

	private final void aam(int arg0, int arg1, int arg2, int arg3, int arg4,
			int arg5, int arg6, String arg7) {
		Raster.drawRectangle(arg0, arg1, arg2, arg3, 0);
		arg0++;
		arg1++;
		arg2 -= 2;
		arg3 -= 2;
		Raster.fillRectangle(arg0, arg1, arg2, arg3, arg5);
		Raster.drawHorizontal(arg0, arg1, arg2, arg4);
		Raster.drawVertical(arg0, arg1, arg3, arg4);
		Raster.drawHorizontal(arg0, (arg1 + arg3) - 1, arg2, arg6);
		Raster.drawVertical((arg0 + arg2) - 1, arg1, arg3, arg6);
		abj.drawCenteredString(arg7, arg0 + arg2 / 2 + 1, arg1 + arg3 / 2 + 1 + 4, 0);
		abj.drawCenteredString(arg7, arg0 + arg2 / 2, arg1 + arg3 / 2 + 4, 0xffffff);
	}

	private final void aan(int arg0, int arg1, int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7) {
		int k = arg2 - arg0;
		int i1 = arg3 - arg1;
		int j1 = (arg6 - arg4 << 16) / k;
		int k1 = (arg7 - arg5 << 16) / i1;
		for (int l1 = 0; l1 < k; l1++) {
			int i2 = j1 * l1 >> 16;
			int k2 = j1 * (l1 + 1) >> 16;
			int i3 = k2 - i2;
			if (i3 > 0) {
				i2 += arg4;
				k2 += arg4;
				int ai[] = abb[l1 + arg0];
				int ai1[] = abc[l1 + arg0];
				byte abyte0[] = abd[l1 + arg0];
				for (int i6 = 0; i6 < i1; i6++) {
					int l6 = k1 * i6 >> 16;
					int k7 = k1 * (i6 + 1) >> 16;
					int k8 = k7 - l6;
					if (k8 > 0) {
						l6 += arg5;
						k7 += arg5;
						int k9 = ai1[i6 + arg1];
						if (k9 == 0) {
							Raster.fillRectangle(i2, l6, k2 - i2, k7 - l6, ai[i6 + arg1]);
						} else {
							byte byte0 = abyte0[i6 + arg1];
							int k10 = byte0 & 0xfc;
							if (k10 == 0 || i3 <= 1 || k8 <= 1)
								Raster.fillRectangle(i2, l6, i3, k8, k9);
							else
								aba(Raster.pixels, l6 * Raster.width + i2, ai[i6 + arg1], k9,
										i3, k8, k10 >> 2, byte0 & 3);
						}
					}
				}

			}
		}

		if (arg2 - arg0 > arg6 - arg4)
			return;
		int j2 = 0;
		for (int l2 = 0; l2 < k; l2++) {
			int j3 = j1 * l2 >> 16;
			int l3 = j1 * (l2 + 1) >> 16;
			int l4 = l3 - j3;
			if (l4 > 0) {
				byte abyte1[] = abe[l2 + arg0];
				byte abyte2[] = abg[l2 + arg0];
				byte abyte3[] = abf[l2 + arg0];
				for (int l7 = 0; l7 < i1; l7++) {
					int l8 = k1 * l7 >> 16;
					int l9 = k1 * (l7 + 1) >> 16;
					int j10 = l9 - l8;
					if (j10 > 0) {
						int l10 = abyte1[l7 + arg1] & 0xff;
						if (l10 != 0) {
							int j11;
							if (l4 == 1)
								j11 = j3;
							else
								j11 = l3 - 1;
							int i12;
							if (j10 == 1)
								i12 = l8;
							else
								i12 = l9 - 1;
							int l12 = 0xcccccc;
							if (l10 >= 5 && l10 <= 8 || l10 >= 13 && l10 <= 16
									|| l10 >= 21 && l10 <= 24 || l10 == 27
									|| l10 == 28) {
								l12 = 0xcc0000;
								l10 -= 4;
							}
							if (l10 == 1)
								Raster.drawVertical(j3, l8, j10, l12);
							else if (l10 == 2)
								Raster.drawHorizontal(j3, l8, l4, l12);
							else if (l10 == 3)
								Raster.drawVertical(j11, l8, j10, l12);
							else if (l10 == 4)
								Raster.drawHorizontal(j3, i12, l4, l12);
							else if (l10 == 9) {
								Raster.drawVertical(j3, l8, j10, 0xffffff);
								Raster.drawHorizontal(j3, l8, l4, l12);
							} else if (l10 == 10) {
								Raster.drawVertical(j11, l8, j10, 0xffffff);
								Raster.drawHorizontal(j3, l8, l4, l12);
							} else if (l10 == 11) {
								Raster.drawVertical(j11, l8, j10, 0xffffff);
								Raster.drawHorizontal(j3, i12, l4, l12);
							} else if (l10 == 12) {
								Raster.drawVertical(j3, l8, j10, 0xffffff);
								Raster.drawHorizontal(j3, i12, l4, l12);
							} else if (l10 == 17)
								Raster.drawHorizontal(j3, l8, 1, l12);
							else if (l10 == 18)
								Raster.drawHorizontal(j11, l8, 1, l12);
							else if (l10 == 19)
								Raster.drawHorizontal(j11, i12, 1, l12);
							else if (l10 == 20)
								Raster.drawHorizontal(j3, i12, 1, l12);
							else if (l10 == 25) {
								for (int i13 = 0; i13 < j10; i13++)
									Raster.drawHorizontal(j3 + i13, i12 - i13, 1, l12);

							} else if (l10 == 26) {
								for (int j13 = 0; j13 < j10; j13++)
									Raster.drawHorizontal(j3 + j13, l8 + j13, 1, l12);

							}
						}
						int k11 = abyte2[l7 + arg1] & 0xff;
						if (k11 != 0)
							abh[k11 - 1].clipSprite(j3 - l4 / 2, l8 - j10 / 2, l4 * 2,
									j10 * 2);
						int j12 = abyte3[l7 + arg1] & 0xff;
						if (j12 != 0) {
							acg[j2] = j12 - 1;
							ace[j2] = j3 + l4 / 2;
							acf[j2] = l8 + j10 / 2;
							j2++;
						}
					}
				}

			}
		}

		for (int k3 = 0; k3 < j2; k3++)
			if (abi[acg[k3]] != null)
				abi[acg[k3]].acg(ace[k3] - 7, acf[k3] - 7);

		if (adh > 0) {
			for (int i4 = 0; i4 < j2; i4++)
				if (acg[i4] == adg) {
					abi[acg[i4]].acg(ace[i4] - 7, acf[i4] - 7);
					if (adh % 10 < 5) {
						Raster.drawDot(ace[i4], acf[i4], 15, 0xffff00, 128);
						Raster.drawDot(ace[i4], acf[i4], 7, 0xffffff, 256);
					}
				}

		}
		if (ael == currentZoom && aee) {
			for (int j4 = 0; j4 < aef; j4++) {
				int i5 = aei[j4];
				int k5 = aej[j4];
				i5 -= aaj;
				k5 = (aak + aam) - k5;
				int j6 = arg4 + ((arg6 - arg4) * (i5 - arg0)) / (arg2 - arg0);
				int i7 = arg5 + ((arg7 - arg5) * (k5 - arg1)) / (arg3 - arg1);
				int i8 = aek[j4];
				int i9 = 0xffffff;
				RSFont f1 = null;
				if (i8 == 0) {
					if (ael == 3D)
						f1 = abk;
					if (ael == 4D)
						f1 = abl;
					if (ael == 6D)
						f1 = abm;
					if (ael == 8D)
						f1 = abn;
				}
				if (i8 == 1) {
					if (ael == 3D)
						f1 = abm;
					if (ael == 4D)
						f1 = abn;
					if (ael == 6D)
						f1 = aca;
					if (ael == 8D)
						f1 = acb;
				}
				if (i8 == 2) {
					i9 = 0xffaa00;
					if (ael == 3D)
						f1 = aca;
					if (ael == 4D)
						f1 = acb;
					if (ael == 6D)
						f1 = acc;
					if (ael == 8D)
						f1 = acd;
				}
				if (f1 != null) {
					String s = aeh[j4];
					int i11 = 1;
					for (int l11 = 0; l11 < s.length(); l11++)
						if (s.charAt(l11) == '/')
							i11++;

					i7 -= (f1.aib() * (i11 - 1)) / 2;
					i7 += f1.aig() / 2;
					do {
						int k12 = s.indexOf("/");
						if (k12 == -1) {
							f1.ahn(s, j6, i7, i9, true);
							break;
						}
						String s1 = s.substring(0, k12);
						f1.ahn(s1, j6, i7, i9, true);
						i7 += f1.aib();
						s = s.substring(k12 + 1);
					} while (true);
				}
			}

		}
		if (aaa) {
			for (int k4 = aaj / 64; k4 < (aaj + aal) / 64; k4++) {
				for (int j5 = aak / 64; j5 < (aak + aam) / 64; j5++) {
					int l5 = k4 * 64;
					int k6 = j5 * 64;
					l5 -= aaj;
					k6 = (aak + aam) - k6;
					int j7 = arg4 + ((arg6 - arg4) * (l5 - arg0))
							/ (arg2 - arg0);
					int j8 = arg5 + ((arg7 - arg5) * (k6 - 64 - arg1))
							/ (arg3 - arg1);
					int j9 = arg4 + ((arg6 - arg4) * ((l5 + 64) - arg0))
							/ (arg2 - arg0);
					int i10 = arg5 + ((arg7 - arg5) * (k6 - arg1))
							/ (arg3 - arg1);
					Raster.drawRectangle(j7, j8, j9 - j7, i10 - j8, 0xffffff);
					abj.drawRightAlignedString(k4 + "_" + j5, j9 - 5, i10 - 5, 0xffffff);
					if (k4 == 33 && j5 >= 71 && j5 <= 73)
						abj.drawCenteredString("u_pass", (j9 + j7) / 2, (i10 + j8) / 2,
								0xff0000);
					else if (k4 >= 32 && k4 <= 34 && j5 >= 70 && j5 <= 74)
						abj.drawCenteredString("u_pass", (j9 + j7) / 2, (i10 + j8) / 2,
								0xffff00);
				}

			}

		}
	}

	private final void aba(int arg0[], int arg1, int arg2, int arg3, int arg4,
			int arg5, int arg6, int arg7) {
		int k = Raster.width - arg4;
		if (arg6 == 9) {
			arg6 = 1;
			arg7 = arg7 + 1 & 3;
		}
		if (arg6 == 10) {
			arg6 = 1;
			arg7 = arg7 + 3 & 3;
		}
		if (arg6 == 11) {
			arg6 = 8;
			arg7 = arg7 + 3 & 3;
		}
		if (arg6 == 1) {
			if (arg7 == 0) {
				for (int i1 = 0; i1 < arg5; i1++) {
					for (int i9 = 0; i9 < arg4; i9++)
						if (i9 <= i1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j1 = arg5 - 1; j1 >= 0; j1--) {
					for (int j9 = 0; j9 < arg4; j9++)
						if (j9 <= j1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k1 = 0; k1 < arg5; k1++) {
					for (int k9 = 0; k9 < arg4; k9++)
						if (k9 >= k1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l1 = arg5 - 1; l1 >= 0; l1--) {
					for (int l9 = 0; l9 < arg4; l9++)
						if (l9 >= l1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			} else {
				return;
			}
		}
		if (arg6 == 2) {
			if (arg7 == 0) {
				for (int i2 = arg5 - 1; i2 >= 0; i2--) {
					for (int i10 = 0; i10 < arg4; i10++)
						if (i10 <= i2 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j2 = 0; j2 < arg5; j2++) {
					for (int j10 = 0; j10 < arg4; j10++)
						if (j10 >= j2 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k2 = 0; k2 < arg5; k2++) {
					for (int k10 = arg4 - 1; k10 >= 0; k10--)
						if (k10 <= k2 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l2 = arg5 - 1; l2 >= 0; l2--) {
					for (int l10 = arg4 - 1; l10 >= 0; l10--)
						if (l10 >= l2 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			} else {
				return;
			}
		}
		if (arg6 == 3) {
			if (arg7 == 0) {
				for (int i3 = arg5 - 1; i3 >= 0; i3--) {
					for (int i11 = arg4 - 1; i11 >= 0; i11--)
						if (i11 <= i3 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j3 = arg5 - 1; j3 >= 0; j3--) {
					for (int j11 = 0; j11 < arg4; j11++)
						if (j11 >= j3 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k3 = 0; k3 < arg5; k3++) {
					for (int k11 = 0; k11 < arg4; k11++)
						if (k11 <= k3 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l3 = 0; l3 < arg5; l3++) {
					for (int l11 = arg4 - 1; l11 >= 0; l11--)
						if (l11 >= l3 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			} else {
				return;
			}
		}
		if (arg6 == 4) {
			if (arg7 == 0) {
				for (int i4 = arg5 - 1; i4 >= 0; i4--) {
					for (int i12 = 0; i12 < arg4; i12++)
						if (i12 >= i4 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j4 = 0; j4 < arg5; j4++) {
					for (int j12 = 0; j12 < arg4; j12++)
						if (j12 <= j4 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k4 = 0; k4 < arg5; k4++) {
					for (int k12 = arg4 - 1; k12 >= 0; k12--)
						if (k12 >= k4 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l4 = arg5 - 1; l4 >= 0; l4--) {
					for (int l12 = arg4 - 1; l12 >= 0; l12--)
						if (l12 <= l4 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			} else {
				return;
			}
		}
		if (arg6 == 5) {
			if (arg7 == 0) {
				for (int i5 = arg5 - 1; i5 >= 0; i5--) {
					for (int i13 = arg4 - 1; i13 >= 0; i13--)
						if (i13 >= i5 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j5 = arg5 - 1; j5 >= 0; j5--) {
					for (int j13 = 0; j13 < arg4; j13++)
						if (j13 <= j5 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k5 = 0; k5 < arg5; k5++) {
					for (int k13 = 0; k13 < arg4; k13++)
						if (k13 >= k5 >> 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l5 = 0; l5 < arg5; l5++) {
					for (int l13 = arg4 - 1; l13 >= 0; l13--)
						if (l13 <= l5 << 1)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			} else {
				return;
			}
		}
		if (arg6 == 6) {
			if (arg7 == 0) {
				for (int i6 = 0; i6 < arg5; i6++) {
					for (int i14 = 0; i14 < arg4; i14++)
						if (i14 <= arg4 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j6 = 0; j6 < arg5; j6++) {
					for (int j14 = 0; j14 < arg4; j14++)
						if (j6 <= arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k6 = 0; k6 < arg5; k6++) {
					for (int k14 = 0; k14 < arg4; k14++)
						if (k14 >= arg4 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l6 = 0; l6 < arg5; l6++) {
					for (int l14 = 0; l14 < arg4; l14++)
						if (l6 >= arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
		}
		if (arg6 == 7) {
			if (arg7 == 0) {
				for (int i7 = 0; i7 < arg5; i7++) {
					for (int i15 = 0; i15 < arg4; i15++)
						if (i15 <= i7 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j7 = arg5 - 1; j7 >= 0; j7--) {
					for (int j15 = 0; j15 < arg4; j15++)
						if (j15 <= j7 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k7 = arg5 - 1; k7 >= 0; k7--) {
					for (int k15 = arg4 - 1; k15 >= 0; k15--)
						if (k15 <= k7 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l7 = 0; l7 < arg5; l7++) {
					for (int l15 = arg4 - 1; l15 >= 0; l15--)
						if (l15 <= l7 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
		}
		if (arg6 == 8) {
			if (arg7 == 0) {
				for (int i8 = 0; i8 < arg5; i8++) {
					for (int i16 = 0; i16 < arg4; i16++)
						if (i16 >= i8 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 1) {
				for (int j8 = arg5 - 1; j8 >= 0; j8--) {
					for (int j16 = 0; j16 < arg4; j16++)
						if (j16 >= j8 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 2) {
				for (int k8 = arg5 - 1; k8 >= 0; k8--) {
					for (int k16 = arg4 - 1; k16 >= 0; k16--)
						if (k16 >= k8 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

				return;
			}
			if (arg7 == 3) {
				for (int l8 = 0; l8 < arg5; l8++) {
					for (int l16 = arg4 - 1; l16 >= 0; l16--)
						if (l16 >= l8 - arg5 / 2)
							arg0[arg1++] = arg3;
						else
							arg0[arg1++] = arg2;

					arg1 += k;
				}

			}
		}
	}

	private final Archive abb() {
		byte abyte0[] = null;
		String s = null;
		try {
			s = abd();
			abyte0 = abe(s + "/worldmap.dat");
			if (!abg(abyte0))
				abyte0 = null;
			if (abyte0 != null)
				return new Archive(abyte0);
		} catch (Throwable _ex) {
		}
		abyte0 = abc();
		if (s != null && abyte0 != null)
			try {
				abf(s + "/worldmap.dat", abyte0);
			} catch (Throwable _ex) {
			}
		return new Archive(abyte0);
	}

	private final byte[] abc() {
		System.out.println("Updating");
		drawLoadingText(0, "Requesting map");
		try {
			String s = "";
			for (int k = 0; k < 10; k++)
				s = s + ArchiveHash.archiveHash[k];

			DataInputStream datainputstream;
			if (super.rsFrame != null)
				datainputstream = new DataInputStream(new FileInputStream(
						"worldmap.jag"));
			else
				datainputstream = new DataInputStream((new URL(getCodeBase(),
						"worldmap" + s + ".jag")).openStream());
			int i1 = 0;
			int j1 = 0;
			int k1 = 0x50a34;
			byte abyte0[] = new byte[k1];
			while (j1 < k1) {
				int l1 = k1 - j1;
				if (l1 > 1000)
					l1 = 1000;
				int i2 = datainputstream.read(abyte0, j1, l1);
				if (i2 < 0)
					throw new IOException("EOF");
				j1 += i2;
				int j2 = (j1 * 100) / k1;
				if (j2 != i1)
					drawLoadingText(j2, "Loading map - " + j2 + "%");
				i1 = j2;
			}
			datainputstream.close();
			return abyte0;
		} catch (IOException ioexception) {
			System.out.println("Error loading");
			ioexception.printStackTrace();
			return null;
		}
	}

	private final String abd() {
		String as[] = { "c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/",
				"e:/windows/", "e:/winnt/", "f:/windows/", "f:/winnt/", "c:/",
				"~/", "/tmp/", "" };
		String s = ".file_store_32";
		for (int k = 0; k < as.length; k++)
			try {
				String s1 = as[k];
				if (s1.length() > 0) {
					File file = new File(s1);
					if (!file.exists())
						continue;
				}
				File file1 = new File(s1 + s);
				if (file1.exists() || file1.mkdir())
					return s1 + s + "/";
			} catch (Exception _ex) {
			}

		return null;
	}

	private final byte[] abe(String arg0) throws IOException {
		File file = new File(arg0);
		if (!file.exists()) {
			return null;
		} else {
			int k = (int) file.length();
			byte abyte0[] = new byte[k];
			DataInputStream datainputstream = new DataInputStream(
					new BufferedInputStream(new FileInputStream(arg0)));
			datainputstream.readFully(abyte0, 0, k);
			datainputstream.close();
			return abyte0;
		}
	}

	private final void abf(String arg0, byte arg1[]) throws IOException {
		FileOutputStream fileoutputstream = new FileOutputStream(arg0);
		fileoutputstream.write(arg1, 0, arg1.length);
		fileoutputstream.close();
	}

	private final boolean abg(byte arg0[]) throws Exception {
		if (arg0 == null)
			return false;
		MessageDigest messagedigest = MessageDigest.getInstance("SHA");
		messagedigest.reset();
		messagedigest.update(arg0);
		byte abyte0[] = messagedigest.digest();
		for (int k = 0; k < 20; k++)
			if (abyte0[k] != ArchiveHash.archiveHash[k])
				return false;

		return true;
	}

	public MapView() {
		aab = 0x887755;
		aac = 0x776644;
		aad = 0x665533;
		aae = 0xaa0000;
		aaf = 0x990000;
		aag = 0x880000;
		keyPressed = true;
		abh = new IndexedSprite[100];
		abi = new Sprite[100];
		ace = new int[2000];
		acf = new int[2000];
		acg = new int[2000];
		aci = new int[2000];
		acj = new int[2000];
		ack = new int[2000];
		keyX = 5;
		keyY = 13;
		keyRX = 140;
		keyYOffset = 470;
		showKeyInterface = false;
		ade = -1;
		adf = -1;
		adg = -1;
		showMapInterface = false;
		aeg = 1000;
		aeh = new String[aeg];
		aei = new int[aeg];
		aej = new int[aeg];
		aek = new int[aeg];
		ael = 4D;
		currentZoom = 4D;
	}

	private static boolean aaa;
	private int aab;
	private int aac;
	private int aad;
	private int aae;
	private int aaf;
	private int aag;
	private boolean keyPressed;
	private int aai;
	private static int aaj;
	private static int aak;
	private static int aal;
	private static int aam;
	private int aan[];
	private int aba[];
	private int abb[][];
	private int abc[][];
	private byte abd[][];
	private byte abe[][];
	private byte abf[][];
	private byte abg[][];
	private IndexedSprite abh[];
	private Sprite abi[];
	private TextDrawingArea abj;
	private RSFont abk;
	private RSFont abl;
	private RSFont abm;
	private RSFont abn;
	private RSFont aca;
	private RSFont acb;
	private RSFont acc;
	private RSFont acd;
	private int ace[];
	private int acf[];
	private int acg[];
	private int ach;
	private int aci[];
	private int acj[];
	private int ack[];
	private int keyX;
	private int keyY;
	private int keyRX;
	private int keyYOffset;
	private int adb;
	private int adc;
	private boolean showKeyInterface;
	private int ade;
	private int adf;
	private int adg;
	private int adh;
	private int mapYOffset;
	private int mapXOffset;
	private int mapX;
	private int mapY;
	private boolean showMapInterface;
	private Sprite adn;
	private int aea;
	private int aeb;
	private int drawCenteredString;
	private int aed;
	private static boolean aee = true;
	private int aef;
	private int aeg;
	private String aeh[];
	private int aei[];
	private int aej[];
	private int aek[];
	private double ael;
	private double currentZoom;
	private static int aen;
	private static int afa;
	private String shopList[] = { "General Store", "Sword Shop", "Magic Shop",
			"Axe Shop", "Helmet Shop", "Bank", "Quest Start", "Amulet Shop",
			"Mining Site", "Furnace", "Anvil", "Combat Training", "Dungeon",
			"Staff Shop", "Platebody Shop", "Platelegs Shop", "Scimitar Shop",
			"Archery Shop", "Shield Shop", "Altar", "Herbalist", "Jewelery",
			"Gem Shop", "Crafting Shop", "Candle Shop", "Fishing Shop",
			"Fishing Spot", "Clothes Shop", "Apothecary", "Silk Trader",
			"Kebab Seller", "Pub/Bar", "Mace Shop", "Tannery", "Rare Trees",
			"Spinning Wheel", "Food Shop", "Cookery Shop", "Mini-Game",
			"Water Source", "Cooking Range", "Skirt Shop", "Potters Wheel",
			"Windmill", "Mining Shop", "Chainmail Shop", "Silver Shop",
			"Fur Trader", "Spice Shop", "Agility Training", "Vegetable Store",
			"Slayer Master", "Hair Dressers", "Farming patch", "Makeover Mage",
			"Guide", "Transportation", "???", "Farming shop", "Loom", "Brewery" };
}
