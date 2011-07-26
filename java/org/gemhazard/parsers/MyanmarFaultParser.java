package org.gemhazard.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.opensha.sha.earthquake.rupForecastImpl.GEM1.SourceData.GEMFaultSourceData;
import org.opensha.sha.faultSurface.FaultTrace;
import org.opensha.sha.imr.param.OtherParams.TectonicRegionTypeParam;
import org.opensha.sha.magdist.IncrementalMagFreqDist;
import org.opensha.sha.util.TectonicRegionType;


public class MyanmarFaultParser {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String line;
		GEMFaultSourceData faultSourceData; 
		
		String mainDirectory = "/Users/marcop/Dropbox/MyanmarDropbox/";
		String inputFilenameFaultProperties = mainDirectory + 
			"MyanmarActiveFaultProperties.csv";
		String inputFilenameFaultGeometry = mainDirectory + 
			"MyanmarActiveFaultProperties.csv";
		
		// Instantiate File Readers 
		BufferedReader faultData = new 
			BufferedReader(new FileReader(inputFilenameFaultProperties));
		BufferedReader faultGeometries = new 
			BufferedReader(new FileReader(inputFilenameFaultGeometry));
		
		// Reading fault data
		String[] stringArray;
		String tmpID = "0001";
		
		while ((line=faultData.readLine())!=null){
			
			// Splitting the line string
			stringArray = line.split(",");
			System.out.println(stringArray[0]);
			
			// Creating the GEMFaultSourceData object
			// 2 - Upp Seism Depth - preferred
			// 3 - Upp Seism Depth - min
			// 4 - Upp Seism Depth - max
			// 
			// 6 - Low Seism Depth - preferred
			// 7 - Low Seism Depth - min
			// 8 - Low Seism Depth - max
			faultSourceData = new GEMFaultSourceData(
					tmpID, 
					stringArray[0],
					TectonicRegionType.ACTIVE_SHALLOW, 
		            IncrementalMagFreqDist mfd,
		            FaultTrace trc, 
		            double dip, 
		            double rake, 
		            double seismDepthLow,
		            double seismDepthUpp, 
		            boolean floatRuptureFlag);

		}
		
		// Close Readers
		faultData.close();
		faultGeometries.close();
		

	}

}
