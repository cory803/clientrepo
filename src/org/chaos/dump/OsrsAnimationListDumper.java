package org.chaos.dump;

import org.chaos.client.cache.definition.Animation;
import org.chaos.client.cache.definition.ItemDefinition;

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
public class OsrsAnimationListDumper {

	public static int grabFile(int frame) {
		try {
			String s = "";
			int file = 0;
			int k = 0;
			s = Integer.toHexString(frame);
			file = Integer.parseInt(s.substring(0, s.length() - 4), 16);
			k = Integer.parseInt(s.substring(s.length() - 4), 16);
			return file;
		} catch (StringIndexOutOfBoundsException e) {
			return -1;
		}
	}


    /**
     * Dumps ItemDefinitions into a .txt file.
	 * @ItemDefinitions
	 */
	public static void dump(int id, Animation definition) {
		try {
			File file = new File("osrsanims.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("id: "+id);
			bw.newLine();
			bw.write("frameIDs: ");
			for (int i = 0; i < definition.frameIDs.length; i++) {
				bw.write(definition.frameIDs[i]+",");
			}
			bw.newLine();
			bw.write("delays: ");
			for (int i = 0; i < definition.delays.length; i++) {
				bw.write(definition.delays[i]+",");
			}
			bw.newLine();
			bw.write("frame count: " + definition.frameCount);
			bw.newLine();
			bw.newLine();

			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
