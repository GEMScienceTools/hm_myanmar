package org.gemhazard.parsers;

import org.opensha.sha.faultSurface.FaultTrace;
import org.opensha.sha.magdist.IncrementalMagFreqDist;

public class GraemeFaultGeometryAndSeismicity {
	FaultTrace traceLocationList; 
	String name;
	String recurrenceModel;
	double rateAtMag, magForRate;
	double gutRichBvalue;
	IncrementalMagFreqDist incrFmd;
	String occurrenceModel;
	
	// Constructor in case of GR model
	public GraemeFaultGeometryAndSeismicity(
			FaultTrace trace,
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
			FaultTrace trace,
			IncrementalMagFreqDist fmd){
		this.traceLocationList = trace;
		this.incrFmd = fmd;
		this.occurrenceModel = "ch";
	}
		
}
