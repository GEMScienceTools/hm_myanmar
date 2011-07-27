package org.gemhazard.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.opensha.commons.geo.LocationList;
import org.opensha.sha.earthquake.rupForecastImpl.GEM1.SourceData.GEMFaultSourceData;
import org.opensha.sha.earthquake.rupForecastImpl.GEM1.SourceData.GEMSourceData;
import org.opensha.sha.magdist.IncrementalMagFreqDist;
import org.opensha.sha.util.TectonicRegionType;

public class MyanmarFaultParser {
	final static double MMIN = 5.0; 
	final static double MWDT = 0.1; 
	final static double DEFAULTRAKE = 90.0;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		ArrayList<GEMSourceData> sourceList = new ArrayList<GEMSourceData>();
		
		String line;
		GEMFaultSourceData faultSourceData; 
		IncrementalMagFreqDist fmd = null;
		
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
		
		// Skip two header lines
		line = faultData.readLine();
		line = faultData.readLine();
		
		// Reading fault data
		String tmpID = "0001";
		while ((line=faultData.readLine())!=null){
			// Splitting the line string
			GraemeFaultParameters param = new GraemeFaultParameters(line);
			// Getting the corresponding geometry and seismicity data
			HashMap<String,GraemeFaultGeometryAndSeismicity> geomHash = geoSei.geomSeismHash;
			// Set or create the FMD 
			if (geomHash.get(param.name).occurrenceModel.matches("gr")){
				fmd = createFMD(
					geomHash.get(param.name).magForRate,
					geomHash.get(param.name).rateAtMag,
					geomHash.get(param.name).gutRichBvalue,
					param.maxMagnitude);
			} else if (geomHash.get(param.name).occurrenceModel.matches("ch")){
				fmd = geomHash.get(param.name).incrFmd;
			}
			
			// Fixing the tectonic region type (deduced from the shear modulus)
			TectonicRegionType tecReg = null;
			if (param.shearModulus > 48.0){
				tecReg = TectonicRegionType.SUBDUCTION_INTERFACE;
			} else {
				tecReg = TectonicRegionType.ACTIVE_SHALLOW;
			}
			
			// Creating fault source data
			faultSourceData = new GEMFaultSourceData(
					tmpID, 
					param.name,
					tecReg, 
		            fmd,
		            geomHash.get(param.name).traceLocationList, 
		            param.dipPref, 
		            getRakeAngle(param.slipType), 
		            param.lowSeismDepthPref,
		            param.uppSeismDepthPref, 
		            true);
			sourceList.add(faultSourceData);
		}
		
		// Close Readers
		faultData.close();
		faultGeometries.close();
	}
	
	/**
	 * This creates an incremental magnitude frequency distribution.
	 * 
	 * @param magForRate
	 * @param rateAtMag
	 * @param bGR
	 * @param mMax
	 * @return fmd
	 */
	public static IncrementalMagFreqDist createFMD(
			double magForRate,
			double rateAtMag,
			double bGR,
			double mMax){
		IncrementalMagFreqDist fmd;
		double aGR;
		double mag;
		double rate;
		
		// Instantiate FMD
		double mMaxRound = Math.ceil(mMax/MWDT)*MWDT;
		int num = (int) ((mMaxRound-MMIN-MWDT)/MWDT);
		fmd = new IncrementalMagFreqDist(MMIN+MWDT/2.0, num, MWDT);
		// Computing Gutenberg-Richter a-value
		aGR = Math.log10(rateAtMag)+bGR*magForRate;
		System.out.printf("mmin %5.2f mmax %5.2f num: %d bGR:%+5.2f aGR:%8.5f\n",
				MMIN,mMaxRound,num,bGR,aGR);
		// Initialize bin central magnitude 
		mag = MMIN + MWDT/2.0;
		for (int i=0; i < fmd.getNum(); i++){
			// Computing the seismicity occurrence rate for the magnitude interval comprised 
			// between mag-MWDT/2 and mag+MWDT/2  
			rate = Math.pow(10,aGR-bGR*(mag-MWDT/2.0)) - 
				Math.pow(10,aGR-bGR*(mag+MWDT/2.0));
			// Updating the FMD
			System.out.printf(" %5.2f %10.8f \n",mag,rate);
			fmd.add(mag,rate);
			// Update bin central magnitude 
			mag = mag + MWDT;
		}
		return fmd;
	}
	
	/**
	 * This defines the rake angle following the Aki and Richards (2002) convention
	 * 
	 * @param slipType
	 * @return rakeAngle
	 */
	public static double getRakeAngle(String slipType){
		double rakeAngle = Double.NaN;
		// Reverse-dextral
		if (slipType.matches("(N|n)ormal-dextral")){
			rakeAngle = 240;
		} else if (slipType.matches("(D|d)extral-normal")){
			rakeAngle = 210;	
		} else if (slipType.matches("(N|n)ormal-sinistral")){
			rakeAngle = 300;
		} else if (slipType.matches("(S|s)inistral-normal")){
			rakeAngle = 330;
		} else if (slipType.matches("(R|r)everse-dextral")){
			rakeAngle = 120;
		} else if (slipType.matches("(D|d)extral-reverse")){
			rakeAngle = 150;
		} else if (slipType.matches("(T|t)hrust") || slipType.matches("(R|r)everse")){
			rakeAngle = 90;
		} else if (slipType.matches("(R|r)ight") || slipType.matches("(D|d)extral")){
			rakeAngle = 180;
		} else if (slipType.matches("(L|l)eft") || slipType.matches("(S|s)inistral")){
			rakeAngle = 180;
		} else if (slipType.matches("(N|n)ormal") || slipType.matches("(D|d)extral")){
			rakeAngle = -90;
		} else {
			System.out.println("unknown option: "+slipType);
			rakeAngle = DEFAULTRAKE;
		}
		return rakeAngle;
	}
	

}
