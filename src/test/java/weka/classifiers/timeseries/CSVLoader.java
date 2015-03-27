package weka.classifiers.timeseries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {

	/**
	 * read all the files in the folder
	 * @param folder
	 * @return
	 */
	public ArrayList<ArrayList> readAll(String folder) {

		InputProject input = new InputProject();
		List<File> metricsFiles;
		ArrayList<ArrayList> datas = new ArrayList<ArrayList>();
		try {
			metricsFiles = input.readDirectory(folder, new String[] {"csv"});
			CSVLoader csvloader = new CSVLoader();
			for (File file : metricsFiles) {
				datas.add(csvloader.read(file));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * Read a single csv file
	 * @param file
	 * @return
	 */
	public ArrayList<String[]> read(File file) {
		// File Name
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String temp[];
		ArrayList<String[]> data = new ArrayList<String[]>();
		// Loading Data
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				temp = line.split(cvsSplitBy);
				data.add(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// Test Input
		// for(int i=0; i<tupleNum;i++){
		// for (int j=0; j<attributeNum; j++){
		// .out.println(names[j]+":="+testData[i][j]);
		// }
		// }
		return data;
	}
}