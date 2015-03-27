package weka.classifiers.timeseries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UNFINISHED....
 * 
 * Output the preditcitions 
 * @author HanzhangWang
 *
 */
public class OutputProject {
	void output(ArrayList<ArrayList> output, List<File> wekaInput)
	{
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd'-'hh.mm.ss");

		String file_name = new String("data");
		file_name = file_name.concat(ft.format(dNow));
		file_name = file_name.concat(".csv");

		try {
			FileWriter fw = new FileWriter(file_name);
			BufferedWriter writer = new BufferedWriter( fw );
			writer.write("File"+Main.dataAttributes[0]+",");

			for(int i=1;i<Main.dataAttributes.length-1;i++){
				writer.write(Main.dataAttributes[i]+"-Real"+",");
				if (i!=Main.dataAttributes.length-2)
				writer.write(Main.dataAttributes[i]+"-Predicted"+",");
				else writer.write(Main.dataAttributes[i]+"-Predicted");
			}
	
	        writer.newLine();
			for (int j = 0; j < output.size()-1; j++) {
				ArrayList<Double> rowTemp=output.get(j);
				writer.write(wekaInput.get(j).getPath().replace("csvData\\","")+",");
				for (int i = 0; i < rowTemp.size(); i++) {
					writer.write(rowTemp.get(i)+",");
				}
				writer.newLine();
			}
			ArrayList<Double> rowTemp=output.get(output.size()-1);
			for (int i = 0; i < rowTemp.size(); i++) {
				writer.write(rowTemp.get(i)+",");
			}
			writer.newLine();
			// generate whatever data you want

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}