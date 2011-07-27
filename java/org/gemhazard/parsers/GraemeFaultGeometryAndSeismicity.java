package org.gemhazard.parsers;

import org.opensha.commons.geo.LocationList;
import org.opensha.sha.magdist.IncrementalMagFreqDist;

public class GraemeFaultGeometryAndSeismicity {
	LocationList traceLocationList; 
	String name;
	String recurrenceModel;
	double rateAtMag, magForRate;
	double gutRichBvalue;
	IncrementalMagFreqDist incrFmd;
	String occurrenceModel;
	
	// Constructor in case of GR model
	public GraemeFaultGeometryAndSeismicity(
			LocationList trace,
			double magForRate,
			double rateAtMag,
			double gutRichBvalue){
		
		this.traceLocationList = trace;
		this.magForRate = magForRate;
		this.rateAtMag = rateAtMag;
		this.gutRichBvalue = gutRichBvalue;
	 	this.occurrenceModel = "gr";
	}
	
	// Constructor in case of CHAR model
	public GraemeFaultGeometryAndSeismicity(
			LocationList trace,
			IncrementalMagFreqDist fmd){
		this.traceLocationList = trace;
		this.incrFmd = fmd;
		this.occurrenceModel = "ch";
	}
		
}
