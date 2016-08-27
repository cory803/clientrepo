package org.chaos.client;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
