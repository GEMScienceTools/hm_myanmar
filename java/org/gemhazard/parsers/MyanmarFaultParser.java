package org.gemhazard.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opensha.commons.geo.LocationList;
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
		final double MMIN = 5.0; 
		
		String[] stringArray, stringArrayB;
		String line;
		GEMFaultSourceData faultSourceData; 
		HashMap<String,LocationList> surfaceTraceMap;
		IncrementalMagFreqDist fmd = null;
		double grBvalue,grRateAtMagMlow,grMlow;
		
		String mainDirectory = "/Users/marcop/Dropbox/MyanmarDropbox/";
		String inputFilenameFaultProperties = mainDirectory + 
			"MyanmarActiveFaultProperties.csv";
		String inputFilenameFaultGeometry = mainDirectory + 
			"MyanmarActiveFaultVectorData2.txt";
		
		// Instantiate File Readers 
		BufferedReader faultData = new 
			BufferedReader(new FileReader(inputFilenameFaultProperties));
		BufferedReader faultGeometries = new 
			BufferedReader(new FileReader(inputFilenameFaultGeometry));
		
		// Reading fault geometry and seismicity
		GraemeFaultGeometryAndSeismicityParser geoSei = 
			new GraemeFaultGeometryAndSeismicityParser(faultGeometries);
		
		System.out.println("==================");
		
		// Skip two header lines
		line = faultData.readLine();
		line = faultData.readLine();
		
		System.out.println(geoSei.geomSeismHash.keySet());
		
		// Reading fault data
		String tmpID = "0001";
		while ((line=faultData.readLine())!=null){
			// Splitting the line string
			GraemeFaultParameters param = new GraemeFaultParameters(line);
			// Getting the corresponding geometry and seismicity data
			HashMap<String,GraemeFaultGeometryAndSeismicity> geomHash = geoSei.geomSeismHash;
			//
//			System.out.println("name:"+param.name+"<--");
//			System.out.println(geoSei.geomSeismHash.get(param.name).gutRichBvalue);
//			System.out.println(geoSei.geomSeismHash.get(param.name).incrFmd);
//			System.out.println(geoSei.geomSeismHash.get(param.name).occurrenceModel);
			// Creating fault source data
//			faultSourceData = new GEMFaultSourceData(
//					tmpID, 
//					stringArray[0],
//					TectonicRegionType.ACTIVE_SHALLOW, 
//		            IncrementalMagFreqDist mfd,
//		            FaultTrace trc, 
//		            double dip, 
//		            double rake, 
//		            double seismDepthLow,
//		            double seismDepthUpp, 
//		            boolean floatRuptureFlag);

		}
		
		// Close Readers
		faultData.close();
		faultGeometries.close();
	}

}
