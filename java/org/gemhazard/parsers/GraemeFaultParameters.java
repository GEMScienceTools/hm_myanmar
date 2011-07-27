package org.gemhazard.parsers;

public class GraemeFaultParameters {
	String name; // idx:0 
	double faultLength; // idx: 1
	double uppSeismDepthPref,uppSeismDepthMin,uppSeismDepthMax; // idx: 2,3,4
	int uppSeismDepthQuality; // idx: 5
	double lowSeismDepthPref,lowSeismDepthMin,lowSeismDepthMax; // idx: 6,7,8
	int lowSeismDepthQuality; // idx: 9
	double strike, dipPref, dipMin, dipMax; // idx: 10, 11, 12, 13
	int dipQuality; // idx: 14
	double dipDirection; // idx: 15
	int dipDirectionQuality; // idx: 16
	String slipType; // idx: 17
	int slipTypeQuality; // idx: 18 
	double slipPref, slipMin, slipMax; // (mm/yr) - idx: 19, 20, 21
	int slipQuality; // idx: 22
	double aseismicFactor; // idx: 23
	int aseismicFactorQuality; // idx: 24
	double displacementPref, displacementMin, displacementMax; // idx: 25, 26, 27
	double width; // (km) - idx: 28
	double area; // (km^2) - idx: 29
	String recurrenceModel; // idx: 30
	double maxMagnitude; // idx: 31
	double shearModulus; // idx: 32 
	double aspectRatio; // idx: 33
	double slipToLengthRatio; // idx: 34
	String megazone; // idx: 35
	
	private String[] stringsArray;
	
	public GraemeFaultParameters(String line){
		stringsArray = line.split(",");
		this.name = stringsArray[0];
		
		this.faultLength = Double.parseDouble(stringsArray[1]);
		this.uppSeismDepthPref = Double.parseDouble(stringsArray[2]);
		this.uppSeismDepthMin = Double.parseDouble(stringsArray[3]);
		this.uppSeismDepthMax = Double.parseDouble(stringsArray[4]);
		this.uppSeismDepthQuality = Integer.parseInt(stringsArray[5]);
		
		this.lowSeismDepthPref = Double.parseDouble(stringsArray[6]);
		this.lowSeismDepthMin = Double.parseDouble(stringsArray[7]);
		this.lowSeismDepthMax = Double.parseDouble(stringsArray[8]);
		this.lowSeismDepthQuality = Integer.parseInt(stringsArray[9]);
		
		this.strike = Double.parseDouble(stringsArray[10]);
		this.dipPref = Double.parseDouble(stringsArray[11]);
		this.dipMin = Double.parseDouble(stringsArray[12]);
		this.dipMax = Double.parseDouble(stringsArray[13]);
		this.dipQuality = Integer.parseInt(stringsArray[14]);
		
		this.dipDirection = Double.parseDouble(stringsArray[15]);
		this.dipDirectionQuality = Integer.parseInt(stringsArray[16]);
		this.slipType = stringsArray[17];
		
		this.slipTypeQuality =  Integer.parseInt(stringsArray[18]);
		
		this.slipPref = Double.NaN;
		this.slipMin = Double.NaN;
		this.slipMax = Double.NaN;
		if (stringsArray[19].length() > 0) 
			this.slipPref = Double.parseDouble(stringsArray[19]);
		if (stringsArray[20].length() > 0) 
			this.slipMin = Double.parseDouble(stringsArray[20]);
		if (stringsArray[21].length() > 0)
			this.slipMax = Double.parseDouble(stringsArray[21]);
		
		this.slipQuality = Integer.parseInt(stringsArray[22]);
		this.aseismicFactor = Double.parseDouble(stringsArray[23]);
		this.aseismicFactorQuality = Integer.parseInt(stringsArray[24]);
		this.displacementPref = Double.parseDouble(stringsArray[25]);
		this.displacementMin = Double.parseDouble(stringsArray[26]);
		this.displacementMax = Double.parseDouble(stringsArray[27]);
		
		this.width = Double.parseDouble(stringsArray[28]); // (km) - idx: 28
		this.area = Double.parseDouble(stringsArray[29]); // (km^2) - idx: 29
		this.recurrenceModel = stringsArray[30]; // idx: 30
		this.maxMagnitude = Double.parseDouble(stringsArray[31]); // idx: 31
		this.shearModulus = Double.parseDouble(stringsArray[32]); // idx: 32 
		this.aspectRatio = Double.parseDouble(stringsArray[33]); // idx: 33
		this.slipToLengthRatio = Double.parseDouble(stringsArray[34]); // idx: 34
		String megazone = stringsArray[35];
		
		
	}
}
