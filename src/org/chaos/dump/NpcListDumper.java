package org.chaos.dump;

import org.chaos.client.cache.definition.MobDefinition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Dumps a list of all npcs in Chaos.
 * Used for the DataSuite.
 *
 * @Author Jonny
 */
public class NpcListDumper {

    /**
     * Dumps MobDefinition into a .txt file.
	 * @MobDefinition
	 */
	public static void dump() {
		try {
			File file = new File("npcs.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String content = "invalid";
			for (int i = 0; i < MobDefinition.totalNpcs; i++) {
				if(MobDefinition.get(i) != null) {
					content = i + "\t" + MobDefinition.get(i).name;
				} else {
					content = "invalid";
				}
				bw.write(content);
				bw.newLine();
			}
			bw.close();
			System.out.println("Successfully dumped "+MobDefinition.totalNpcs+" items into npcs.txt");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
