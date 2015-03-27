package weka.classifiers.timeseries;

import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.*;
import org.apache.commons.io.FilenameUtils;

/**
 * Convert CSV to Arff (last attribute is Date attribute)
 * 
 */
public class CSV2Arff {
  /**
   * @param file: input file name.
   * @param folder: output folder name.
   */
	void c2a(File file, String folder) {

		try {
			// Load CSV file
			CSVLoader loader = new CSVLoader();
System.out.println(file);
// if last one is date
		loader.setDateFormat("yyyy-mm-dd");
			loader.setDateAttributes("last");
			loader.setSource(file);
			Instances data = loader.getDataSet();

			// New File name
			String newName = FilenameUtils.removeExtension(file.getName())
					+ ".arff";
			// save
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);

			saver.setFile(new File(folder + File.separator + newName));
			// saver.setDestination(new File());
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}