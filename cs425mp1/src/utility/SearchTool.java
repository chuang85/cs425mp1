package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SearchTool {
	final File folder;
	Set<String> fileSet;
	static final String txtDirectory = System.getProperty("user.dir") + "/snapshot_result/";

	public SearchTool(String folderPath) {
		folder = new File(folderPath);
		fileSet = new HashSet<String>();
	}

	public void listFilesForFolder() {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder();
			} else {
				//System.out.println(fileEntry.getName());
				fileSet.add(fileEntry.getName());
			}
		}
	}

	public void searchAll(int n) throws IOException {
		String query = "snapshot " + String.valueOf(n);
		String fullPath;
		String currLine;
		BufferedReader br = null;
		for (String currFile : fileSet) {
			fullPath = txtDirectory + currFile;
			//System.out.println(fullPath);
			br = new BufferedReader(new FileReader(fullPath));
			while ((currLine = br.readLine()) != null) {
				if (currLine.contains(query))
					System.out.println(currLine);
			}
		}
		br.close();
	}

	public static void main(String[] args) throws IOException {
		SearchTool st = new SearchTool(txtDirectory);
		st.listFilesForFolder();
		st.searchAll(1);
	}
}
