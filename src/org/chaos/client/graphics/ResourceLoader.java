package org.chaos.client.graphics;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

import org.chaos.client.Signlink;

public class ResourceLoader {

	private static final HashMap<String, Image> loadedImages = new HashMap<String, Image>();

	public Image getImage(String imageName) {
		if (loadedImages.containsKey(imageName))
			return loadedImages.get(imageName);
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(Signlink.getCacheDirectory() + "" + imageName + ".png");
		} catch (Exception e) {
			e.printStackTrace();
			img = null;
		}
		if (img != null)
			loadedImages.put(imageName, img);
		return img;
	}

	public static Image getIcon(String imageName) {
		if (loadedImages.containsKey(imageName))
			return loadedImages.get(imageName);
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(imageName);
		} catch (Exception e) {
			e.printStackTrace();
			img = null;
		}
		if (img != null)
			loadedImages.put(imageName, img);
		return img;
	}

}
