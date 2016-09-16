package org.chaos.client.world;

import org.chaos.client.Client;

/**
 * Custom object spawns
 * 
 * @author Gabbe
 */
public class CustomObjects {

	/**
	 * Spawns the objects to the map
	 */
	public static void spawn() {
		for (int i = 0; i < CUSTOM_OBJECTS.length; i++) {
			int id = CUSTOM_OBJECTS[i][0];
			int x = CUSTOM_OBJECTS[i][1];
			int y = CUSTOM_OBJECTS[i][2];
			int z = CUSTOM_OBJECTS[i][3];
			int face = CUSTOM_OBJECTS[i][4];
			Client.instance.addObject(x, y, id, face, 10, z);
		}
	}

	public static final int[][] CUSTOM_OBJECTS = {
			{ 6552, 3232, 2887, 0, 0 },
			{ 9326, 3001, 3960, 0, 0 },
			// 1 = west
			// 2 = north
			// 3 = east
			// 4 = south
	};

	public class GameObject {

		public GameObject(int id, int x, int y, int z, int face) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.z = z;
			this.face = face;
		}

		public int id;
		public int x, y, z;
		public int face;
	}

}
