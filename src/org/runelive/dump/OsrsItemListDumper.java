package org.runelive.dump;

import org.runelive.client.cache.definition.ItemDefinition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Dumps a list of all items in RuneLive.
 * Used for the DataSuite.
 *
 * @Author Jonny
 */
public class OsrsItemListDumper {

    /**
     * Dumps ItemDefinitions into a .txt file.
	 * @ItemDefinitions
	 */
	public static void dump(ItemDefinition definition) {
		try {
			File file = new File("osrsitems.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("id: "+definition.id);
			bw.newLine();
			bw.write("name: "+definition.name);
			bw.newLine();
			bw.write("inventory model: "+definition.modelID);
			bw.newLine();
			bw.write("male model: "+definition.maleWearId);
			bw.newLine();
			bw.write("female model: "+definition.femaleWearId);
			bw.newLine();
			bw.write("modelZoom: "+definition.modelZoom);
			bw.newLine();
			bw.write("modelRotation1: "+definition.modelRotation1);
			bw.newLine();
			bw.write("modelRotation2: "+definition.modelRotation2);
			bw.newLine();
			bw.write("modelOffsetX: "+definition.modelOffset1);
			bw.newLine();
			bw.write("modelOffsetY: "+definition.modelOffsetY);
			bw.newLine();
			bw.newLine();

			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
