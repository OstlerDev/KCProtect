package utilities;

import java.io.File;

/**
 * @author DarkGhoul45
 */
public class CreateUtilities extends Thread {

	public CreateUtilities() {
		super();
		File[] directories = {
				new File("plugins/KWatcher/Areas/"),
				new File("plugins/KWatcher/Logs/")
		};
		for(File f : directories) {
			if(!f.exists()) {
				f.mkdirs();
			}
		}
	}
}
