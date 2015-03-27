package weka.classifiers.timeseries;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
//ddd
	static final String metricsFolder = "metricsInput";
	static final String csvFolder="csvData";
	static final String arffFolder="arffData";
	static final String[] metricsFileType = new String[] { "csv" };
	static final String[] originFileType = new String[] { "csv" };
	static final String[] wekaFileType = new String[] { "arff" };
	//The FISRT ATTRIBUTE MUST BE THE NAME OF THE CLASS
	//@[]attributes=[Name/ID,Attribute1..AttributeN,Timestamp]
	static final String[] dataAttributes = { "Name", "PercentLackOfCohesion","CountLineCode","SumCyclomatic","CountDeclMethodAll","Date"};
	static final String[] forecastAttributes={"PercentLackOfCohesion","CountLineCode","SumCyclomatic","CountDeclMethodAll"};
	static double[] sumErr;
	static double[] count;
	
	public static void main(String args[]) throws IOException {
		
		ArrayList<ArrayList> predictions= new ArrayList<ArrayList>();
		InputProject input = new InputProject();
		System.out.println("----------------Loading metrics files----------------");
		List<File> metricsFiles = input.readDirectory(metricsFolder,
				metricsFileType);
		
		//read all the metrics files from the metricsFolder, 
		//datas = ArrayList:(ArrayList:metricsVer1(String[]Row1, Row2...),metricsVer2()...)
		CSVLoader csvloader = new CSVLoader();
		ArrayList<ArrayList> datas = csvloader.readAll(metricsFolder);
		
		// Start the pre-process
		classFilter classfilter = new classFilter(metricsFiles, datas, dataAttributes);
		
		// Locate target attributes and Selete the useful attributes and output new datas
		
		
//		for (int i=0; i<classfilter.fAttDatas.size();i++){
//		for (int j=0; j<classfilter.fAttDatas.get(i).size(); j++){
//			String[] a= (String[]) classfilter.fAttDatas.get(i).get(j);
//			for (int k=0; k<a.length;k++){
//				System.out.println(a[k]);
//			}
//		}
//	}

		ArrayList<Data> fAttData= new ArrayList<Data>();
		fAttData=classfilter.output;
		
//		for (int i=0; i<testData.size();i++){
//		for (int j=0; j<testData.get(i).data.size(); j++){
//			String[] a= (String[]) testData.get(i).data.get(j);
//			for (int k=0; k<a.length;k++){
//				System.out.println(a[k]);
//			}
//		}
//	}
		OutputFilteredData outputfd=new OutputFilteredData();
		outputfd.output(fAttData);
		System.out.println("----------------Load pre-processed CSV files----------------");
		// getting all the files info
		List<File> fi = input.readDirectory(csvFolder, originFileType);
	
		// output all the files to Arff
		CSV2Arff c2a = new CSV2Arff(); 
		for (File file : fi) {
			//System.out.println(csvl.read(file).get(0)[2]);
			c2a.c2a(file, arffFolder);
		}
		count=new double[forecastAttributes.length];
		sumErr=new double[forecastAttributes.length];
		for (int i=0; i<Main.forecastAttributes.length;i++){
			count[i]=0;
			sumErr[i]=0;
		}
		WekaForecasterTest test = new WekaForecasterTest();
		
		List<File> wekaInput = input.readDirectory(arffFolder, wekaFileType);
		for (File file : wekaInput) {
			System.out.println("Prediting "+file);
			ArrayList<Double> resultTemp=test.forecast(file);
			predictions.add(resultTemp);
		}
		ArrayList<Double> MAE=new ArrayList<Double>();
		MAE.add(Double.NaN);
		for (int i=0;i<forecastAttributes.length;i++){
			MAE.add(sumErr[i]/count[i]);
			MAE.add(Double.NaN);
		}
		predictions.add(MAE);
		OutputProject outputproject=new OutputProject();
		outputproject.output(predictions,fi);
		
	
		
	}
}
