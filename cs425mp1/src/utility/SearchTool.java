package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SearchTool {
	BufferedReader br;
	
	public void loadFile(String filePath) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(filePath));
	}
	
	public void searchAll(int n) throws IOException {
		String query = "snapshot " + String.valueOf(n);
		String currLine;
		while ((currLine = br.readLine()) != null) {
			if (currLine.contains(query))
				System.out.println(currLine);
		}
	}
	
	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	public static void main(String[] args) throws IOException {
		
		/*SearchTool st = new SearchTool();
		st.loadFile("test.txt");
		st.searchAll(2);*/
		final File folder = new File(System.getProperty("user.dir") + "/snapshot_result");
		listFilesForFolder(folder);
	}
}
