package org.chaos.client;

/**
 * Handles Chaos Map Themes
 * @author Jonny, relex
 */
public enum MapTheme {
	
	DEFAULT(-1, -1),
	
	HALLOWEEN(16, 1),
	
	CHRISTMAS(-1, 16, true),
	
	DARK(11, 23),
	
	SKY(87, 73),
	
	AUTUMN(65, 35),

	SUMMER(25, 25),
	
	DESERT(24, 61),
	
	SWAMP(27, 87),
	
	CANDY(41, 3),
	;
	
	private MapTheme(int overlay, int underlay, boolean special) {
		this.overlay = (byte) overlay;
		this.underlay = (byte) underlay;
		this.special = special;
		this.name = "theme"; //TODO: Fix this
		// TextUtils.capitalize(toString().toLowerCase().replaceAll("_", " "));
	}
	
	private MapTheme(int overlay, int underlay) {
		this(overlay, underlay, false);
	}
	
	/**
	 * This is the overlay for the theme. The overlay
	 * is the path maps. Used for house floors and path areas.
	 */
	private final byte overlay;
	
	/**
	 * This is the underlay for the theme. The underlay
	 * is the non-path maps. Mostly used to covers grass.
	 */
	private final byte underlay;
	
	/**
	 * This flag checks if this is map theme is considered
	 * 'special', such as season themed.
	 */
	private final boolean special;
	
	/**
	 * The name used in the 'Map Theme' menu bar.
	 */
	private final String name;
	
	public byte getOverlay() {
		return overlay;
	}
	
	public byte getUnderlay() {
		return underlay;
	}
	
	public boolean isSpecial() {
		return special;
	}
	
	public String getName() {
		return name;
	}
	
	public static MapTheme forName(String name) {
		for (MapTheme mapTheme : MapTheme.values()) {
			if (mapTheme.getName().equals(name)) {
				return mapTheme;
			}
		}
		return null;
	}
}
