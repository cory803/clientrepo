package org.runelive.client;
/**
 * @author Thelife/Alex
 */

public class Achievement {

	public String text;
	public int x, y, width, height, transparency, delay, achievementId, bannerColour, textColour;
	public boolean delayOver;
	public boolean started;

	public Achievement(String text, int bannerColour, int textColour) {
		this.text = text;
		this.bannerColour = bannerColour;
		this.textColour = textColour;

	}
}
