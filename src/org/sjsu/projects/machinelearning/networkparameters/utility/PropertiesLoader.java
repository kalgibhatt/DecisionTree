package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class PropertiesLoader {

	public static void loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./properties.properties")));
			ProjectProperties.isLocal = Boolean.parseBoolean(prop.getProperty("isLocal"));
			ProjectProperties.iterationCount = Integer.parseInt(prop.getProperty("iterationCount"));
			for (String label : prop.getProperty("allLabels").split(",")) {
				ProjectProperties.allLabels.add(label);
			}
			ProjectProperties.isLabelRandom = Boolean.parseBoolean(prop.getProperty("isLabelRandom"));
			ProjectProperties.randomSelectionPadding = Double.parseDouble(prop.getProperty("randomSelectionPadding"));
			ProjectProperties.labelsInUse = new ArrayList<String>();
			for (String label : prop.getProperty("labelsInUse").split(",")) {
				ProjectProperties.labelsInUse.add(label);
			}
			ProjectProperties.accuracyMeasureListFile = prop.getProperty("accuracyMeasureListFile");
			ProjectProperties.trainingFile = prop.getProperty("trainingFile");
			ProjectProperties.testingFile = prop.getProperty("testingFile");
			ProjectProperties.printTree = Boolean.parseBoolean(prop.getProperty("printTree"));
			ProjectProperties.aggregationCount = Integer.parseInt(prop.getProperty("aggregationCount"));
			ProjectProperties.treeObjectFileName = prop.getProperty("treeObjectFileName");
			ProjectProperties.labelSelectionCount = Integer.parseInt(prop.getProperty("labelSelectionCount"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
