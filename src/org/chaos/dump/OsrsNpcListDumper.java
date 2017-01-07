package org.chaos.dump;

import org.chaos.client.cache.definition.ItemDefinition;
import org.chaos.client.cache.definition.MobDefinition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Dumps a list of all items in Chaos.
 * Used for the DataSuite.
 *
 * @Author Jonny
 */
public class OsrsNpcListDumper {

    /**
     * Dumps ItemDefinitions into a .txt file.
	 * @ItemDefinitions
	 */
	public static void dump(MobDefinition definition) {
		try {
			File file = new File("osrsnpcs.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("id: "+definition.id);
			bw.newLine();
			bw.write("name: "+definition.name);
			bw.newLine();
			bw.write("standAnimation: "+definition.standAnimation);
			bw.newLine();
			bw.write("walkAnimation: "+definition.walkAnimation);
			bw.newLine();
			bw.write("combat: "+definition.combatLevel);
			bw.newLine();
			if(definition.npcModels != null) {
				bw.write("npcModels: ");
				for (int i = 0; i < definition.npcModels.length; i++) {
					bw.write(definition.npcModels[i] + ",");
				}
				bw.newLine();
			}
			bw.newLine();

			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
