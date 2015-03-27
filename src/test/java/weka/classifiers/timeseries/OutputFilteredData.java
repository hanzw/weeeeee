package weka.classifiers.timeseries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Out put ArrayList<Data> in csv format, Data.cName is the file name.(class name)
 * @author HanzhangWang
 *
 */
public class OutputFilteredData {
	void output(ArrayList<Data> output) {
		try {
			for (int i = 0; i < output.size(); i++) {
				//each Data in ouput is a single file for the one class
				Data outputDataTemp = output.get(i);
				String file_name = new String(Main.csvFolder + File.separator
						+ outputDataTemp.cName);
				file_name = file_name.concat(".csv");

				FileWriter fw = new FileWriter(file_name);
				BufferedWriter writer = new BufferedWriter(fw);
				
				//Writre the first attirbute name: ID.
			
				//Write rest attributes name
				for (int x = 0; x < outputDataTemp.aName.length-1; x++) {
					writer.write(outputDataTemp.aName[x] + ",");
				}
				writer.write(outputDataTemp.aName[outputDataTemp.aName.length-1]);
				writer.newLine();
				
				for (int j = 0; j < outputDataTemp.data.size(); j++) {
					ArrayList<String[]> rowTemp = outputDataTemp.data;
					//write ID
					writer.write(j+1+",");
					//write other attirbutes
					for (int m = 0; m < rowTemp.get(j).length - 1; m++) {
						writer.write(rowTemp.get(j)[m] + ",");
					}
					writer.write(rowTemp.get(j)[rowTemp.get(j).length - 1]);
					writer.newLine();
				}
				writer.flush();
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}