package weka.classifiers.timeseries;

import java.util.ArrayList;
/**
 * Class to store the data of the classes.
 * @author HanzhangWang
 *
 */
public class Data {
	String cName;
	ArrayList<String[]> data;
	String[] aName;
	int[] diff;
	Data(){
		this.data=new ArrayList<String[]>();	
	}

}
