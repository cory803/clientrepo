package org.runelive.client;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jonathan on 8/26/2016.
 */

public class ScreenshotThread implements Runnable {
    @Override
    public void run() {
        try {
            Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .getFocusedWindow();
            Point point = window.getLocationOnScreen();
            Robot robot = new Robot(window.getGraphicsConfiguration().getDevice());
            Rectangle rectangle = new Rectangle((int) point.getX(), (int) point.getY(),
                    window.getWidth(), window.getHeight());
            BufferedImage img = robot.createScreenCapture(rectangle);
            GameWindow.getInstance().upload(img);
        } catch (AWTException reason) {
            throw new RuntimeException("Fatal error whilst capturing screenshot", reason);
        }
    }
}
