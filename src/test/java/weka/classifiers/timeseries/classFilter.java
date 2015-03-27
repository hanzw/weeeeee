package weka.classifiers.timeseries;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Preprocess the input files, output only the classes esist more than a certain
 * amount with the needed attributes.
 * 
 */
public class classFilter {

	static String[] dataAttributes;
	List<File> files;
	ArrayList<ArrayList> datas;
	ArrayList<Integer[]> locators;
	ArrayList<ArrayList> fAttDatas;
	ArrayList<Data> output;
	
	classFilter(List<File> files, ArrayList<ArrayList> datas,
			String[] attributes) {
		this.files = files;
		this.datas = datas;
		this.dataAttributes = Main.dataAttributes;
		this.fAttDatas = new ArrayList<ArrayList>();
		this.output = new ArrayList<Data>();
		attributeFilter();
		formatTrans();
	}

	/**
	 * check each class in the first file with all the others (the names should
	 * be exsistet in all the files) The FISRT ATTRIBUTE MUST BE THE NAME OF THE
	 * CLASS
	 * 
	 * @param fAttDatas
	 *            the data with needed attributes
	 * @return 
	 * @return the output with the classes fits the exsitance requirements.
	 */
	void formatTrans() {

		
		ArrayList<String[]> data1 = fAttDatas.get(0);
		data1.remove(0);
		for (String[] s : data1) {
			Data dataTemp = new Data();
			dataTemp.cName = s[0];
			if(s[0].equals("io.realm.examples.concurrency.model.Dog")){
				System.out.println();
			}
			dataTemp.data.add(deleteClassName(s));
			int allExist = 0;
			Boolean exsit = false;
			// No need to check first file
			for (int i = 1; i < fAttDatas.size(); i++) {
				ArrayList<String[]> data = fAttDatas.get(i);

				// No need to check the first row(attribute names)
				for (int j = 1; j < data.size(); j++) {
					if (data.get(j)[0].equals(s[0])) {
						dataTemp.data.add(deleteClassName(data.get(j)));
						allExist++;
						break;
					}
				}
			}
			if (allExist >= fAttDatas.size() - 3) {
				dataTemp.aName = new String[Main.dataAttributes.length];
				dataTemp.aName[0] = "ID";
				for (int i = 1; i < Main.dataAttributes.length; i++) {
					dataTemp.aName[i] = Main.dataAttributes[i];
				}

				output.add(dataTemp);
			}
		}
	
	}

	/**
	 * Delete class name attribute (the name goes to the file name)
	 * 
	 * @param s
	 *            attributes
	 * @return new attributes without class name
	 */
	String[] deleteClassName(String[] s) {
		String[] o = new String[s.length - 1];
		for (int i = 1; i < s.length; i++) {
			o[i - 1] = s[i];
		}
		return o;
	}

	//
	/**
	 * Preprocess the input, take only the needed attributes
	 * 
	 * @param locators
	 *            : {Name/ID of class, Atrribute1, Attribute2 .. Attribute N锛�
	 *            (Class)}
	 * @return 
	 * @return data with the needed attributes
	 */
	void attributeFilter() {
		locateAttributes();
		
		Boolean empty = false;
		for (int i = 0; i < datas.size(); i++) {
			ArrayList<String[]> data = datas.get(i);
			ArrayList<String[]> fAttData = new ArrayList<String[]>();

			for (int j = 0; j < data.size(); j++) {
				Integer[] locator = locators.get(i);
				String[] dat = data.get(j);
				String[] fAttDat = new String[locator.length];

				for (int n = 0; n < locator.length; n++) {
			

					if (locator[n] >= dat.length || 
							dat[locator[n]].equals("")) {
						empty = true;
						break;
					}
					fAttDat[n] = dat[locator[n]];

				}
				// check if there is missing data
				for (int n = 0; n < fAttDat.length; n++) {

				}
				if (!empty) {
					fAttData.add(fAttDat);
				}
				empty = false;
			}
			fAttDatas.add(fAttData);
		}
	
	}

	/**
	 * Locate the atributes
	 * 
	 * @return the attributes location (locators)
	 */
	void locateAttributes() {

		ArrayList<String[]> data = null;
		ArrayList<Integer[]> locators = new ArrayList<Integer[]>();
		for (int i = 0; i < datas.size(); i++) {
			data = datas.get(i);
			if (i == 42) {
				System.out.println();
			}
			String[] firstLine = data.get(0);
			Integer[] locator = new Integer[dataAttributes.length];
			for (int j = 0; j < dataAttributes.length; j++) {
				for (int n = 0; n < firstLine.length; n++) {
					if (dataAttributes[j].equals(firstLine[n])) {
						locator[j] = n;
					}
				}
				if (locator[j] == null) {
					System.out.println("***Can't find attibute:"
							+ dataAttributes[j] + " at " + files.get(i)
							+ "File in the List.");
				}
				// System.out.println(locator[j]);
			}
			// System.out.println("d"+locator[1]);
			locators.add(locator);
		}
		this.locators = locators;
	}
}
