package org.chaos.client;

import org.apache.commons.codec.binary.Base64;
import org.chaos.Configuration;
import org.chaos.client.net.HttpDownloadUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public final class GameWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static GameRenderer applet;
    private static GameWindow instance;
    private static Dimension minimumSize;
    private JToolBar variable1;

    public static JFrame frame;
    public static JLabel label1;
    public static JTextArea text;
    public static String currentImage = "none";
    private static java.util.List<Image> icons2;

    public static GameWindow getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        icons = new ArrayList<>();
        try {
            icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/16x16.png")));
            icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/32x32.png")));
            icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/64x64.png")));
            icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/128x128.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        instance = new GameWindow(new Client(), 765, 503 + 30, false, false);
        minimumSize = instance.getSize();
        instance.applet.init();
        instance.setAlwaysOnTop(true);
        instance.toFront();
        instance.requestFocus();
        instance.setAlwaysOnTop(false);
    }

    public static void setFixed() {
        instance.dispose();
        instance = new GameWindow(applet, 765, 503 + 30, false, false);
        instance.setLocationRelativeTo(null);
    }

    public static void setResizable() {
        if (instance.isUndecorated()) {
            instance.dispose();
            instance = new GameWindow(applet, minimumSize.width + 20, minimumSize.height + 30, false, true);
        } else {
            instance.setMinimumSize(new Dimension(minimumSize.width + 20, minimumSize.height + 30));
            instance.setSize(new Dimension(minimumSize.width + 20, minimumSize.height + 30));
            instance.setResizable(true);
        }
        instance.setLocationRelativeTo(null);
    }

    public static void setFullscreen() {
        instance.dispose();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        instance = new GameWindow(applet, size.width, size.height, true, false);
    }

    private static java.util.List<Image> icons;

    private int hoverIndex = -1;

    public void createImgurUploadFrame() {
        currentImage = "none";
        frame = new JFrame();
        icons2 = new ArrayList<>();
        try {
            icons2.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/imgur-16x16.png.png")));
            icons2.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/imgur-32x32.png.png")));
            icons2.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/imgur-96x96.png.png")));
            icons2.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/chaos/client/resources/imgur-152x152.png.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        frame.setIconImages(icons2);
        frame.setBounds(GameWindow.getInstance().getX() + 193, GameWindow.getInstance().getY() + 200, 400, 200);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Screenshot");
        label1 = new JLabel("Uploading to imgur...");
        label1.setBackground(Color.BLACK);
        label1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        label1.setBounds(105, 10, 400, 35);
        text = new JTextArea("Uploading please wait...");
        text.setBackground(new Color(57,196,66));
        text.setBounds(65, 45, 250, 20);
        frame.getContentPane().add(text);
        frame.getContentPane().add(label1);
        JButton localJButton2 = new JButton("Open");
        localJButton2.setBorderPainted(false);
        localJButton2.addActionListener(this);
        localJButton2.setActionCommand("open_imgur");
        localJButton2.setToolTipText("Opens the imgur link in your browser.");
        localJButton2.setBounds(87, 80, 64, 64);
        localJButton2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/open_web.png"))));
        frame.getContentPane().add(localJButton2);
        JButton localJButton3 = new JButton("Copy");
        localJButton3.setBorderPainted(false);
        localJButton3.addActionListener(this);
        localJButton3.setActionCommand("copy_imgur");
        localJButton3.setToolTipText("Copys the imgur link to your clipboard.");
        localJButton3.setBounds(157, 80, 64, 64);
        localJButton3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/copy_url.png"))));
        JButton localJButton4 = new JButton("Copy");
        localJButton4.setBorderPainted(false);
        localJButton4.addActionListener(this);
        localJButton4.setActionCommand("save_imgur");
        localJButton4.setToolTipText("Saves the image to your computer.");
        localJButton4.setBounds(227, 80, 64, 64);
        localJButton4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/save_image.png"))));
        frame.getContentPane().add(localJButton3);
        frame.getContentPane().add(localJButton4);
        frame.setVisible(true);
    }

    public void upload(BufferedImage image){
        createImgurUploadFrame();
        String IMGUR_POST_URI = "https://api.imgur.com/3/upload";
        String IMGUR_API_KEY = "c016d93d6cff409";

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.out.println("Writing image...");
            ImageIO.write(image, "png", baos);
            URL url = new URL(IMGUR_POST_URI);

            System.out.println("Encoding...");
            String data = URLEncoder.encode("image", "UTF-8")
                    + "="
                    + URLEncoder.encode(
                    Base64.encodeBase64String(baos.toByteArray())
                            .toString(), "UTF-8");
            data += "&" + URLEncoder.encode("key", "UTF-8") + "="
                    + URLEncoder.encode(IMGUR_API_KEY, "UTF-8");

            System.out.println("Connecting...");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Authorization", "Client-ID "
                    + IMGUR_API_KEY);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());

            System.out.println("Sending data...");
            wr.write(data);
            wr.flush();

            System.out.println("Finished.");

            // just display the raw response


            InputStream stream = conn.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(stream));

            String line;

            while ((line = in.readLine()) != null) {
                String[] args = line.split(",\"");
                String link = args[20].replaceAll("\"", "").replaceAll("\\\\", "");
                //System.out.println(link.substring(5));
                text.setText(link.substring(5));
                currentImage = link.substring(5);
                //SCREENSHOTS.add(link.substring(5));
                //System.out.println(line);
                //JsonObject reader = new JsonParser().parse(line).getAsJsonObject();

                // if (reader.has("data")) {
                // System.out.println("link: "+reader.get("data").getAsString());
                //  } else {
                //    System.out.println("Not found");
                // }

            }
            in.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public GameWindow(GameRenderer applet, int width, int height, boolean undecorative, boolean resizable) {
        this.applet = applet;
        this.applet.setFocusTraversalKeysEnabled(false);
        this.setTitle("" + Configuration.CLIENT_NAME + "");
        this.setFocusTraversalKeysEnabled(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(undecorative);
        this.setBackground(Color.BLACK);
        //this.setLayout(new BorderLayout());
        this.setIconImages(icons);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        /*
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				int x = (this.getWidth() / 2 - (765 / 2));
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), 41);
			}
		};

		NavListener navListener = new NavListener(panel);
		panel.addMouseMotionListener(navListener);
		panel.addMouseListener(navListener);
		panel.setSize(new Dimension(765, 41));
		panel.setPreferredSize(new Dimension(765, 41));
		panel.setMinimumSize(new Dimension(765, 41));
		this.add(panel, BorderLayout.NORTH);
*/

        getContentPane().setBackground(Color.BLACK);

        GridBagLayout localGridBagLayout = new GridBagLayout();
        localGridBagLayout.columnWidths = new int[2];
        localGridBagLayout.rowHeights = new int[3];
        localGridBagLayout.columnWeights = new double[]{1.0D, 4.9E-324D};
        localGridBagLayout.rowWeights = new double[]{0.0D, 1.0D, 4.9E-324D};
        getContentPane().setLayout(localGridBagLayout);

        drawMenuBar();
        GridBagConstraints localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.insets = new Insets(0, 0, 0, 0);
        localGridBagConstraints1.fill = 2;
        localGridBagConstraints1.gridx = 0;
        localGridBagConstraints1.gridy = 0;
        getContentPane().add(this.variable1, localGridBagConstraints1);
        GridBagConstraints localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.gridx = 0;
        localGridBagConstraints2.gridy = 1;
        getContentPane().add(this.applet, localGridBagConstraints2);
        //this.add(this.applet, BorderLayout.CENTER);

        this.setResizable(resizable);
        this.setVisible(true);


        if (undecorative) {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {

            if (!this.isResizable()) {
                Insets insets = getInsets();
                this.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
                if (minimumSize == null) {
                    this.setMinimumSize(this.getSize());
                } else {
                    this.setMinimumSize(new Dimension(minimumSize.width, minimumSize.height));
                }
            } else {
                this.setSize(new Dimension(minimumSize.width + 20, minimumSize.height));
                this.setMinimumSize(new Dimension(minimumSize.width + 20, minimumSize.height));
            }
            setLocationRelativeTo(null);
        }

        drawMenuBar();
        this.requestFocus();
        this.toFront();
    }

    private void drawMenuBar() {
        variable1 = new JToolBar();
        variable1.setFloatable(false);
        variable1.setLayout(new FlowLayout(1, 0, 0));
        Dimension localDimension = new Dimension(10, 20);
        JButton localJButton1 = new JButton("Home");
        localJButton1.setBorderPainted(false);
        localJButton1.addActionListener(this);
        localJButton1.setActionCommand("home");
        localJButton1.setToolTipText("Opens the Chaos homepage in your web browser.");
        localJButton1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/home_icon.png"))));
        variable1.add(localJButton1);
        JButton localJButton2 = new JButton("Forum");
        localJButton2.setBorderPainted(false);
        localJButton2.addActionListener(this);
        localJButton2.setActionCommand("forum");
        localJButton2.setToolTipText("Opens the Chaos forum in your web browser.");
        localJButton2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/forum_icon.png"))));
        variable1.add(localJButton2);
        JButton localJButton3 = new JButton("Store");
        localJButton3.setBorderPainted(false);
        localJButton3.addActionListener(this);
        localJButton3.setActionCommand("store");
        localJButton3.setToolTipText("Opens the Chaos store in your web browser.");
        localJButton3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/store_icon.png"))));
        variable1.add(localJButton3);
        JButton localJButton4 = new JButton("Vote");
        localJButton4.setBorderPainted(false);
        localJButton4.addActionListener(this);
        localJButton4.setActionCommand("vote");
        localJButton4.setToolTipText("Opens the Chaos vote in your web browser.");
        localJButton4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/vote_icon.png"))));
        variable1.add(localJButton4);
        JButton localJButton5 = new JButton("Hiscores");
        localJButton5.setBorderPainted(false);
        localJButton5.addActionListener(this);
        localJButton5.setActionCommand("hiscore");
        localJButton5.setToolTipText("Opens the Chaos hiscores in your web browser.");
        localJButton5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/hiscore_icon.png"))));
        variable1.add(localJButton5);
        variable1.addSeparator(localDimension);
        JButton localJButton6 = new JButton();
        localJButton6.setBorderPainted(false);
        localJButton6.addActionListener(this);
        localJButton6.setActionCommand("facebook");
        localJButton6.setToolTipText("Opens the Chaos Facebook in your web browser.");
        localJButton6.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/facebook_icon.png"))));
        variable1.add(localJButton6);
        JButton localJButton7 = new JButton();
        localJButton7.setBorderPainted(false);
        localJButton7.addActionListener(this);
        localJButton7.setActionCommand("youtube");
        localJButton7.setToolTipText("Opens the Chaos YouTube page in your web browser.");
        localJButton7.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/youtube_icon.png"))));
        variable1.add(localJButton7);
        variable1.addSeparator(localDimension);
        JButton jbutton = new JButton("Screenshot");
        jbutton.setEnabled(true);
        jbutton.setBorderPainted(false);
        jbutton.addActionListener(this);
        jbutton.setActionCommand("screenshot");
        jbutton.setToolTipText("Screenshots your client window and uploads it to Imgur.");
        jbutton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/monitor_icon.png"))));
        variable1.add(jbutton);
        JButton variable6 = new JButton("History");
        variable6.setEnabled(false);
        variable6.setBorderPainted(false);
        variable6.addActionListener(this);
        variable6.setActionCommand("history");
        variable6.setToolTipText("Opens your screenshot history.");
        variable6.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/chaos/client/resources/history_icon.png"))));
        variable1.add(variable6);
        variable1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent paramActionEvent) {
        String str = paramActionEvent.getActionCommand();
        if (str == null) {
            return;
        }
        if (str.equalsIgnoreCase("home")) {
            Client.launchURL("www.chaosps.com");
        } else if (str.equalsIgnoreCase("forum")) {
            Client.launchURL("www.chaosps.com/forum");
        } else if (str.equalsIgnoreCase("store")) {
            Client.launchURL("www.chaosps.com/store/");
        } else if (str.equalsIgnoreCase("vote")) {
            Client.launchURL("www.chaosps.com/vote/");
        } else if (str.equalsIgnoreCase("hiscore")) {
            Client.launchURL("www.chaosps.com/hiscores/");
        } else if (str.equalsIgnoreCase("facebook")) {
            Client.launchURL("www.facebook.com/chaosps");
        } else if (str.equalsIgnoreCase("twitter")) {
            Client.launchURL("www.twitter.com/chaosps");
        } else if (str.equalsIgnoreCase("youtube")) {
            Client.launchURL("www.youtube.com/user/chaosps");
        } else if (str.equalsIgnoreCase("screenshot")) {
            ScreenshotThread screenshot = new ScreenshotThread();
            Thread thread = new Thread(screenshot);
            thread.start();
        } else if (str.equalsIgnoreCase("open_imgur")) {
            if(!currentImage.equals("none")) {
                Client.launchURL(currentImage);
            } else {
                text.setText("Still uploading, please wait.");
            }
        } else if (str.equalsIgnoreCase("copy_imgur")) {
            if(!currentImage.equals("none")) {
                StringSelection stringSelection = new StringSelection(currentImage);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            } else {
                text.setText("Still uploading, please wait.");
            }
        } else if (str.equalsIgnoreCase("save_imgur")) {
            if(!currentImage.equals("none")) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose where you want to save the screenshot");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    text.setText("Downloading image...");
                    boolean directory = chooser.getSelectedFile().toString().endsWith(".png") || chooser.getSelectedFile().toString().endsWith(".jpg");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    HttpDownloadUtility.downloadFile(currentImage, chooser.getSelectedFile().toString(), directory);
                }
            } else {
                text.setText("Still uploading, please wait.");
            }
        } else if (str.equalsIgnoreCase("history")) {
            //TODO: Add history option to see a list of all screenshots with open features
        }
    }

    private final class NavListener extends MouseAdapter {

        private final JPanel panel;

        public NavListener(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            int x = (panel.getWidth() / 2 - (765 / 2));
            int hover = (e.getX() - x) / 109;
            if (hover != hoverIndex) {
                hoverIndex = hover;
                panel.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = (panel.getWidth() / 2 - (765 / 2));
            int hover = (e.getX() - x) / 109;
            if (hover != hoverIndex) {
                hoverIndex = hover;
                panel.repaint();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            int hover = hoverIndex;
            hoverIndex = -1;
            if (hover != hoverIndex) {
                panel.repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                int x = (panel.getWidth() / 2 - (765 / 2));
                int tab = (e.getX() - x) / 109;
                if (tab > -1 && tab < Configuration.NAV_LINKS.length) {
                    Client.launchURL(Configuration.NAV_LINKS[tab]);
                }
            }
        }
    }

}