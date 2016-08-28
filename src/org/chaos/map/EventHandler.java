package org.chaos.map;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class EventHandler extends Applet implements Runnable, MouseListener,
		MouseMotionListener, KeyListener, FocusListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final void stop() {
		if (stopCounter >= 0)
			stopCounter = 4000 / delayTime;
	}

	public void drawLoadingText(int progression, String text) {
		while (graphics == null) {
			graphics = getGameComponent().getGraphics();
			try {
				getGameComponent().repaint();
			} catch (Exception exception) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception exception1) {
			}
		}
		Font font = new Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = getGameComponent().getFontMetrics(font);
		Font font1 = new Font("Helvetica", 0, 13);
		FontMetrics fontmetrics1 = getGameComponent().getFontMetrics(font1);
		if (clearScreen) {
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, myWidth, myHeight);
			clearScreen = false;
		}
		Color color = new Color(140, 17, 17);
		int i = myHeight / 2 - 18;
		graphics.setColor(color);
		graphics.drawRect(myWidth / 2 - 152, i, 304, 34);
		graphics.fillRect(myWidth / 2 - 150, i + 2, progression * 3, 30);
		graphics.setColor(Color.black);
		graphics.fillRect((myWidth / 2 - 150) + progression * 3, i + 2, 300 - progression * 3, 30);
		graphics.setFont(font);
		graphics.setColor(Color.white);
		graphics.drawString(text, (myWidth - fontmetrics.stringWidth(text)) / 2, i + 22);
	}

	public final void mouseReleased(MouseEvent event) {
		idleTime = 0;
		clickMode2 = 0;
	}

	public final void keyPressed(KeyEvent keyEvent) {
		idleTime = 0;
		int keyCode = keyEvent.getKeyCode();
		int keyIndex = keyEvent.getKeyChar();
		if (keyIndex < 30)
			keyIndex = 0;
		if (keyCode == 37) {
			System.out.println("37");
			keyIndex = 1;
		}
		if (keyCode == 39) {
			System.out.println("39");
			keyIndex = 2;
		}
		if (keyCode == 38) {
			System.out.println("38");
			keyIndex = 3;
		}
		if (keyCode == 40) {
			System.out.println("40");
			keyIndex = 4;
		}
		if (keyCode == 17)
			keyIndex = 5;
		if (keyCode == 8)
			keyIndex = 8;
		if (keyCode == 127)
			keyIndex = 8;
		if (keyCode == 9)
			keyIndex = 9;
		if (keyCode == 10)
			keyIndex = 10;
		if (keyCode >= 112 && keyCode <= 123)
			keyIndex = (1008 + keyCode) - 112;
		if (keyCode == 36)
			keyIndex = 1000;
		if (keyCode == 35)
			keyIndex = 1001;
		if (keyCode == 33)
			keyIndex = 1002;
		if (keyCode == 34)
			keyIndex = 1003;
		if (keyIndex > 0 && keyIndex < 128)
			keyPresses[keyIndex] = 1;
		if (keyIndex > 4) {
			charQueue[writeIndex] = keyIndex;
			writeIndex = writeIndex + 1 & 0x7f;
		}
	}

	public void startRunnable(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(priority);
	}

	public final void windowClosing(WindowEvent event) {
		destroy();
	}

	public final void exit() {
		stopCounter = -2;
		cleanUpForQuit();
		if (rsFrame != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
			try {
				System.exit(0);
			} catch (Throwable throwable) {
			}
		}
	}

	public final void update(Graphics g) {
		if (graphics == null)
			graphics = g;
		clearScreen = true;
	}

	public final void mouseEntered(MouseEvent mouseevent) {
	}

	public final void mouseExited(MouseEvent arg0) {
		idleTime = 0;
		xDragged = -1;
		yDragged = -1;
	}

	public final void windowOpened(WindowEvent windowevent) {
	}

	public final void windowDeiconified(WindowEvent windowevent) {
	}

	public final void windowActivated(WindowEvent windowevent) {
	}

	public void startup() {
	}

	public final void start() {
		if (stopCounter >= 0)
			stopCounter = 0;
	}

	public final void createFrame(int width, int height) {
		myWidth = width;
		myHeight = height;
		rsFrame = new Window(this, myWidth, myHeight);
		graphics = getGameComponent().getGraphics();
		fullScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
		startRunnable(this, 1);
	}

	public final int nextClick() {
		int click = -1;
		if (writeIndex != readIndex) {
			click = charQueue[readIndex];
			readIndex = readIndex + 1 & 0x7f;
		}
		return click;
	}

	public void age() {
	}

	public Component getGameComponent() {
		if (rsFrame != null)
			return rsFrame;
		else
			return this;
	}

	public final void mouseClicked(MouseEvent mouseevent) {
	}

	public final void mousePressed(MouseEvent mouse) {
		int x = mouse.getX();
		int y = mouse.getY();
		if (rsFrame != null) {
			x -= 4;
			y -= 22;
		}
		
		System.out.println("Mouse X: " + x);
		System.out.println("Mouse Y: " + y);
		
		idleTime = 0;
		localMouseX = x;
		localMouseY = y;
		clickTime = System.currentTimeMillis();
		if (mouse.isMetaDown()) {
			clickMode1 = 2;
			clickMode2 = 2;
		} else {
			clickMode1 = 1;
			clickMode2 = 1;
		}
	}

	public final void mouseDragged(MouseEvent mouse) {
		int x = mouse.getX();
		int y = mouse.getY();
		if (rsFrame != null) {
			x -= 4;
			y -= 22;
		}
		idleTime = 0;
		xDragged = x;
		yDragged = y;
	}

	public final void initializeFrame(int width, int height) {
		myWidth = width;
		myHeight = height;
		graphics = getGameComponent().getGraphics();
		fullScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
		startRunnable(this, 1);
	}

	public final void mouseMoved(MouseEvent mouseEvent) {
		int xDragged = mouseEvent.getX();
		int yDragged = mouseEvent.getY();
		if (rsFrame != null) {
			xDragged -= 4;
			yDragged -= 22;
		}
		idleTime = 0;
		this.xDragged = xDragged;
		this.yDragged = yDragged;
	}

	public final void keyTyped(KeyEvent keyEvent) {
	}

	public final void windowDeactivated(WindowEvent windowevent) {
	}

	public final void agn(Graphics g) {
		if (graphics == null)
			graphics = g;
		clearScreen = true;
	}

	public final void destroy() {
		stopCounter = -1;
		try {
			Thread.sleep(5000L);
		} catch (Exception exception) {
		}
		if (stopCounter == -1)
			exit();
	}

	public void cleanUpForQuit() {
	}

	public EventHandler() {
		stopCounter = 0;
		delayTime = 20;
		minDelay = 1;
		aLong1 = new long[10];
		framesPerSecond = 0;
		ail = false;
		ajc = new Sprite[6];
		clearScreen = true;
		awtFocus = true;
		idleTime = 0;
		clickMode2 = 0;
		xDragged = 0;
		yDragged = 0;
		clickMode1 = 0;
		localMouseX = 0;
		localMouseY = 0;
		clickTime = 0L;
		clickType = 0;
		mouseX = 0;
		mouseY = 0;
		clickHold = 0L;
		keyPresses = new int[128];
		charQueue = new int[128];
		readIndex = 0;
		writeIndex = 0;
	}

	
	public void processLoop() {
		
	}

	public final void focusLost(FocusEvent event) {
		awtFocus = false;
		for (int index = 0; index < 128; index++)
			keyPresses[index] = 0;
	}

	public final void keyReleased(KeyEvent keyEvent) {
		idleTime = 0;
		int i = keyEvent.getKeyCode();
		char c = keyEvent.getKeyChar();
		if (c < '\036')
			c = '\0';
		if (i == 37)
			c = '\001';
		if (i == 39)
			c = '\002';
		if (i == 38)
			c = '\003';
		if (i == 40)
			c = '\004';
		if (i == 17)
			c = '\005';
		if (i == 8)
			c = '\b';
		if (i == 127)
			c = '\b';
		if (i == 9)
			c = '\t';
		if (i == 10)
			c = '\n';
		if (c > 0 && c < '\200')
			keyPresses[c] = 0;
	}

	public final void windowClosed(WindowEvent windowevent) {
	}

	public void run() {
		getGameComponent().addMouseListener(this);
		getGameComponent().addMouseMotionListener(this);
		getGameComponent().addKeyListener(this);
		getGameComponent().addFocusListener(this);
		if (rsFrame != null)
			rsFrame.addWindowListener(this);
		drawLoadingText(0, "Loading...");
		startup();
		int i = 0;
		int fpsRatio = 256;
		int k = 1;
		int fpsCount = 0;
		int j1 = 0;
		for (int index = 0; index < 10; index++)
			aLong1[index] = System.currentTimeMillis();

		long l1 = System.currentTimeMillis();
		while (stopCounter >= 0) {
			if (stopCounter > 0) {
				stopCounter--;
				if (stopCounter == 0) {
					exit();
					return;
				}
			}
			int i2 = fpsRatio;
			int j2 = k;
			fpsRatio = 300;
			k = 1;
			long l2 = System.currentTimeMillis();
			if (aLong1[i] == 0L) {
				fpsRatio = i2;
				k = j2;
			} else if (l2 > aLong1[i])
				fpsRatio = (int) ((long) (2560 * delayTime) / (l2 - aLong1[i]));
			if (fpsRatio < 25)
				fpsRatio = 25;
			if (fpsRatio > 256) {
				fpsRatio = 256;
				k = (int) ((long) delayTime - (l2 - aLong1[i]) / 10L);
			}
			if (k > delayTime)
				k = delayTime;
			aLong1[i] = l2;
			i = (i + 1) % 10;
			if (k > 1) {
				for (int k2 = 0; k2 < 10; k2++)
					if (aLong1[k2] != 0L)
						aLong1[k2] += k;

			}
			if (k < minDelay)
				k = minDelay;
			try {
				Thread.sleep(k);
			} catch (InterruptedException interruptedexception) {
				j1++;
			}
			for (; fpsCount < 256; fpsCount += fpsRatio) {
				clickType = clickMode1;
				mouseX = localMouseX;
				mouseY = localMouseY;
				clickHold = clickTime;
				clickMode1 = 0;
				processLoop();
				readIndex = writeIndex;
			}

			fpsCount &= 0xff;
			if (delayTime > 0)
				framesPerSecond = (1000 * fpsRatio) / (delayTime * 256);
			age();
			if (ail) {
				System.out.println("ntime:" + l2);
				for (int i3 = 0; i3 < 10; i3++) {
					int j3 = ((i - i3 - 1) + 20) % 10;
					System.out.println("otim" + j3 + ":" + aLong1[j3]);
				}

				System.out.println("fps:" + framesPerSecond + " ratio:" + fpsRatio + " count:"
						+ fpsCount);
				System.out.println("del:" + k + " deltime:" + delayTime + " mindel:"
						+ minDelay);
				System.out.println("intex:" + j1 + " opos:" + i);
				ail = false;
				j1 = 0;
			}
		}
		if (stopCounter == -1)
			exit();
	}

	public final void focusGained(FocusEvent event) {
		awtFocus = true;
		clearScreen = true;		
	}

	public final void windowIconified(WindowEvent windowevent) {
	}

	private int stopCounter;
	private int delayTime;
	public int minDelay;
	private long aLong1[];
	public int framesPerSecond;
	public boolean ail;
	public int myWidth;
	public int myHeight;
	public Graphics graphics;
	public RSImageProducer fullScreen;
	public Sprite ajc[];
	public Window rsFrame;
	public boolean clearScreen;
	public boolean awtFocus;
	public int idleTime;
	public int clickMode2;
	public int xDragged;
	public int yDragged;
	public int clickMode1;
	public int localMouseX;
	public int localMouseY;
	public long clickTime;
	public int clickType;
	public int mouseX;
	public int mouseY;
	public long clickHold;
	public int keyPresses[];
	private int charQueue[];
	private int readIndex;
	private int writeIndex;
	public static boolean aki;
}
