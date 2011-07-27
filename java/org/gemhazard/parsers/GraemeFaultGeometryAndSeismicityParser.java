package org.gemhazard.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opensha.commons.geo.Location;
import org.opensha.commons.geo.LocationList;
import org.opensha.sha.magdist.IncrementalMagFreqDist;

public class GraemeFaultGeometryAndSeismicityParser {
	HashMap<String,GraemeFaultGeometryAndSeismicity> geomSeismHash;
		
	// Parse the file
	GraemeFaultGeometryAndSeismicityParser(BufferedReader faultGeometries) 
		throws IOException {
		
		String line;
		String name = "";
		String[] stringArray,stringArrayB;
		LocationList trace = null;
		IncrementalMagFreqDist fmd;
		
		// Define patterns
		Pattern grFmdPatt = Pattern.compile("GR");
		Pattern charFmdPatt = Pattern.compile("Char Eq.");
		Pattern ratePatt = Pattern.compile("Rate \\[");
		Pattern wordPatt = Pattern.compile("\\w+");
		Pattern magminPatt = Pattern.compile("([0-9]+\\.[0-9]+)");
		Matcher mtchGrFmd;
		Matcher mtchCharFmd;
		Matcher mtchRate;
		Matcher mtchWord, mtchMagmin;
		
		this.geomSeismHash = new HashMap<String,GraemeFaultGeometryAndSeismicity>();
		
		while ((line = faultGeometries.readLine())!=null){
			// Cleaning line
			line = line.replaceAll("^\\s+","");
			
			// Splitting line
			stringArray = line.split(" ");
			// Check start of the MFD
			mtchGrFmd = grFmdPatt.matcher(line);
			mtchCharFmd = charFmdPatt.matcher(line);
			mtchRate = ratePatt.matcher(line);
			mtchWord = wordPatt.matcher(line);
			
			// Gutenberg-Richter FMD 
			if (mtchGrFmd.find()) {
				System.out.println("GR");
				// Comment line
				line = faultGeometries.readLine().
					replaceAll("(\\[|\\]|[a-zA-Z]|>|=)","").
					replaceAll("^\\s+","").
					replaceAll("\\s+$","");
				double magForRate = Double.parseDouble(line);
				// GR values
				stringArray = faultGeometries.readLine().replaceAll("^\\s+","").split("\\s+");
				double rateAtMag = Double.parseDouble(stringArray[0]);
				double gutRichBvalue = Double.parseDouble(stringArray[1]);
				// Store data
				this.geomSeismHash.put(
					name,
					new GraemeFaultGeometryAndSeismicity(
						trace,
						magForRate,
						rateAtMag,
						gutRichBvalue
					)
				);
			
			// Characteristic Earthquake Distribution
			} else if (mtchCharFmd.find()) {
				System.out.println("Char");
				// Magnitudes
				stringArray  = faultGeometries.
					readLine().replaceAll("^\\s+","").split("\\s+"); 
				// Rates
				stringArrayB = faultGeometries.readLine().replaceAll("^\\s+","").split("\\s+");
				System.out.println(stringArray[0] +"|"+stringArray[stringArray.length-1]);
				System.out.println(stringArrayB.length + " " + stringArray.length);
				// Creating the FMD
				fmd = new IncrementalMagFreqDist(
						Double.parseDouble(stringArray[0]),
						Double.parseDouble(stringArray[stringArray.length-1]),
						stringArray.length);
				// Adding rates to the fmd
				for (int i=0; i<stringArrayB.length;i++){
					System.out.printf("%5.2f %8.6f\n",
							Double.parseDouble(stringArray[i]),
							Double.parseDouble(stringArrayB[i])
					);
					fmd.add(i,Double.parseDouble(stringArrayB[i]));
				}
				
				this.geomSeismHash.put(
						name,
						new GraemeFaultGeometryAndSeismicity(
							trace,
							fmd)
				);
				
			// Processing fault name and trace coordinates
			} else if (mtchWord.find()){
				name = "";
				for (String tmp : stringArray){
					name += " " + tmp;
				}
				name = name.replaceAll("^\\s+","");
				System.out.println("name:"+name);
				
				// Reading coordinates
				stringArray = faultGeometries.readLine().
					replaceAll("^\\s+","").
					replaceAll("\\s+"," ").
					replaceAll("\\s+$","").
					split(" ");
				int cnt = 0;
				
				trace = new LocationList();
				while (cnt < stringArray.length-1){
					System.out.println("== "+stringArray[cnt]+" "+stringArray[cnt+1]);

					trace.add(new Location(
							Double.parseDouble(stringArray[cnt+1]),
							Double.parseDouble(stringArray[cnt])
							)
					);
					
					cnt += 2; 
				}
			} else {
				System.out.println("this is an error");
			}
		}
	}
}
