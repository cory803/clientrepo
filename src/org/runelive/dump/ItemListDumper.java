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
public class ItemListDumper {

    /**
     * Dumps ItemDefinitions into a .txt file.
	 * @ItemDefinitions
	 */
	public static void dump() {
		try {
			File file = new File("items.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < ItemDefinition.totalItems; i++) {
				String content = i+"\t"+ItemDefinition.get(i).name;
				bw.write(content);
				bw.newLine();
			}
			bw.close();
			System.out.println("Successfully dumped "+ItemDefinition.totalItems+" items into items.txt");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
