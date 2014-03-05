package utility;

import java.io.File;

public class SearchTool {
	File file;
	
	public void loadFile(String filePath) {
		file = new File(filePath);
	}
	
	public void searchAll(int n) {
		String query = "snapshot" + String.valueOf(n);
		
	}
}
