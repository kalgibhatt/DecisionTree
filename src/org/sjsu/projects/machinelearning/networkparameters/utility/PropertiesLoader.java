package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesLoader {
	
	public static void loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./properties.properties")));
			ProjectProperties.isLocal = Boolean.parseBoolean(prop.getProperty("isLocal"));
			ProjectProperties.iterationCount = Integer.parseInt(prop.getProperty("iterationCount"));
			ProjectProperties.allLabels = prop.getProperty("allLabels").split(",");
			ProjectProperties.isLabelRandom = Boolean.parseBoolean(prop.getProperty("isLabelRandom"));
			ProjectProperties.randomSelectionPadding = Double.parseDouble(prop.getProperty("randomSelectionPadding"));
			List<String> labelsInUse = new ArrayList<String>();
			for (String label : prop.getProperty("labelsInUse").split(",")) {
				labelsInUse.add(label);
			}
			ProjectProperties.accuracyMeasureListFile = prop.getProperty("accuracyMeasureListFile"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
