package main;

import static decisiontree.feature.P.betweenD;
import static decisiontree.feature.P.lessThanD;
import static decisiontree.feature.P.moreThan;
import static decisiontree.feature.P.moreThanD;
import static decisiontree.feature.PredicateFeature.newFeature;
import static logger.Logger.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

import decisiontree.DecisionTree;
import decisiontree.data.DataSample;
import decisiontree.data.SimpleDataSample;
import decisiontree.feature.Feature;
import decisiontree.label.BooleanLabel;
import decisiontree.label.Label;
import decisiontree.label.StringLabel;
import utility.AccuracyMatrices;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		List<AccuracyMatrices> accuracyList = new ArrayList<AccuracyMatrices>();
		for (int i = 0; i < 10; i++) {
			log("Randomly deciding the features to use...");
			List<String> labels = randomlySelectFeatures();
			accuracyList.add(calculateAccuracyForLabels(labels));
		}
		
		log("Dumping accuracy matrices to a file...");
		
		FileWriter writer = new FileWriter("./AccuracyMatrices.csv");
		writer.append("LabelsCount");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getLabels().size());
		}
		writer.append("\n");
		writer.append("TPR");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getTPR());
		}
		writer.append("\n");
		writer.append("TNR");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getTNR());
		}
		writer.append("\n");
		writer.append("PPV");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getPPV());
		}
		writer.append("\n");
		writer.append("NPV");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getNPV());
		}
		writer.append("\n");
		writer.append("FPR");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getFPR());
		}
		writer.append("\n");
		writer.append("FNR");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getFNR());
		}
		writer.append("\n");
		writer.append("FDR");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getFDR());
		}
		writer.append("\n");
		writer.append("ACC");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getACC());
		}
		writer.append("\n");
		writer.append("F1");
		for (AccuracyMatrices accuracyMatrix : accuracyList) {
			writer.append(",");
			writer.append(""+accuracyMatrix.getF1());
		}
		writer.flush();
		writer.close();

		// classify all test data
		// for (DataSample dataSample : testingData) {
		// predictions.add(dataSample.getValue("duration").get() + "," +
		// dataSample.getValue("protocol_type").get()
		// + "," + dataSample.getValue("service").get() + "," +
		// dataSample.getValue("flag").get() + ","
		// + dataSample.getValue("source_bytes").get() + "," +
		// dataSample.getValue("destination_bytes").get()
		// + "," + dataSample.getValue("land").get() + "," +
		// dataSample.getValue("wrong_fragment").get() + ","
		// + dataSample.getValue("urgent").get() + "," +
		// dataSample.getValue("hot").get() + ","
		// + dataSample.getValue("failed_logins").get() + "," +
		// dataSample.getValue("logged_in").get() + ","
		// + dataSample.getValue("compromised").get() + "," +
		// dataSample.getValue("root_shell").get() + ","
		// + dataSample.getValue("su_attempted").get() + "," +
		// dataSample.getValue("root").get() + ","
		// + dataSample.getValue("file_creations").get() + "," +
		// dataSample.getValue("shells").get() + ","
		// + dataSample.getValue("access_files").get() + "," +
		// dataSample.getValue("outbound_cmds").get() + ","
		// + dataSample.getValue("is_hot_logins").get() + "," +
		// dataSample.getValue("is_guest_login").get()
		// + "," + dataSample.getValue("connection_count").get() + ","
		// + dataSample.getValue("service_count").get() + "," +
		// dataSample.getValue("serror_rate").get() + ","
		// + dataSample.getValue("service_serror_rate").get() + "," +
		// dataSample.getValue("rerror_rate").get()
		// + "," + dataSample.getValue("service_rerror_rate").get() + ","
		// + dataSample.getValue("same_service_rate").get() + ","
		// + dataSample.getValue("diff_service_rate").get() + ","
		// + dataSample.getValue("service_diff_host_rate").get() + ","
		// + dataSample.getValue("destination_host_count").get() + ","
		// + dataSample.getValue("destination_host_service_count").get() + ","
		// + dataSample.getValue("destination_host_same_service_rate").get() +
		// ","
		// + dataSample.getValue("destination_host_diff_service_rate").get() +
		// ","
		// + dataSample.getValue("destination_host_same_source_port_rate").get()
		// + ","
		// +
		// dataSample.getValue("destination_host_service_diff_host_rate").get()
		// + ","
		// + dataSample.getValue("destination_host_serror_rate").get() + ","
		// + dataSample.getValue("destination_host_service_serror_rate").get() +
		// ","
		// + dataSample.getValue("destination_host_rerror_rate").get() + ","
		// + dataSample.getValue("destination_host_service_rerror_rate").get() +
		// ","
		// + tree.classify(dataSample).getPrintValue());
		// }
		// log("Finished predicting results...");
		// //
		// // // write predictions to file
		// log("Writing predictions to file...");
		// FileWriter fileWriter = new FileWriter(new File("predictions.csv"));
		// fileWriter
		// .append("duration,protocol_type,service,flag,source_bytes,destination_bytes,land,wrong_fragment,urgent,hot,failed_logins,logged_in,compromised,root_shell,su_attempted,root,file_creations,shells,access_files,outbound_cmds,is_hot_logins,is_guest_login,connection_count,service_count,serror_rate,service_serror_rate,rerror_rate,service_rerror_rate,same_service_rate,diff_service_rate,service_diff_host_rate,destination_host_count,destination_host_service_count,destination_host_same_service_rate,destination_host_diff_service_rate,destination_host_same_source_port_rate,destination_host_service_diff_host_rate,destination_host_serror_rate,destination_host_service_serror_rate,destination_host_rerror_rate,destination_host_service_rerror_rate,class")
		// .append("\n");
		// for (String prediction : predictions) {
		// fileWriter.append(prediction).append("\n");
		// }
		// fileWriter.flush();
		// fileWriter.close();
		// log("Finished writing predictions to file...");

	}

	private static AccuracyMatrices calculateAccuracyForLabels(List<String> labels) throws IOException {
		log("Reading data...");
		List<DataSample> trainingData = readData(true);
		log("Training Data Count: " + trainingData.size());
		log("Finished reading data...");

		DecisionTree tree = new DecisionTree();

		List<Feature> features = getFeatures(labels);

		log("Training model...");
		tree.train(trainingData, features);
		log("Finished training model...");

		// print tree after training
//		tree.printTree();

		// read test data
		log("Predicting results...");

		List<DataSample> testingData = readData(false);
		// List<String> predictions = Lists.newArrayList();
		log("Testing Data Count: " + testingData.size());

		return calculateAccuracy(testingData, tree, labels);
		
	}

	private static List<String> randomlySelectFeatures() {
		String[] labels = { "duration", "protocol_type", "service", "flag", "source_bytes", "destination_bytes", "land",
				"wrong_fragment", "urgent", "hot", "failed_logins", "logged_in", "compromised", "root_shell",
				"su_attempted", "root", "file_creations", "shells", "access_files", "outbound_cmds", "is_hot_logins",
				"is_guest_login", "connection_count", "service_count", "serror_rate", "service_serror_rate",
				"rerror_rate", "service_rerror_rate", "same_service_rate", "diff_service_rate",
				"service_diff_host_rate", "destination_host_count", "destination_host_service_count",
				"destination_host_same_service_rate", "destination_host_diff_service_rate",
				"destination_host_same_source_port_rate", "destination_host_service_diff_host_rate",
				"destination_host_serror_rate", "destination_host_service_serror_rate", "destination_host_rerror_rate",
				"destination_host_service_rerror_rate", "class" };
		int[] labelInclusionArray = new int[labels.length];
		double trigger = Math.random() + 0.25;
		List<String> labelsInUse = new ArrayList<String>();
		for (int i = 0; i < labelInclusionArray.length; i++) {
			if (Math.random() <= trigger) {
				labelsInUse.add(labels[i]);
			}
		}
		return labelsInUse;
	}

	private static AccuracyMatrices calculateAccuracy(List<DataSample> testingData, DecisionTree tree, List<String> labels) {
		double TP = 0;
		double TN = 0;
		double FP = 0;
		double FN = 0;
		AccuracyMatrices accuracy = new AccuracyMatrices();
		for (DataSample dataSample : testingData) {
			int actualClass = Integer.parseInt(dataSample.getValue("class").get().toString());
			int predictedClass = Integer.parseInt(tree.classify(dataSample).getPrintValue());
			if (actualClass == 1) {
				if (predictedClass == 1) {
					TP++;
				} else {
					FN++;
				}
			} else {
				if (predictedClass == 1) {
					FP++;
				} else {
					TN++;
				}
			}
		}
		accuracy.setFN(FN);
		accuracy.setFP(FP);
		accuracy.setTN(TN);
		accuracy.setTP(TP);
		accuracy.setLabels(labels);
		
		return accuracy;
	}

	private static List<Feature> getFeatures(List<String> labels) {

		List<Feature> features = new ArrayList<Feature>();

		Feature shortDuration = newFeature("duration", lessThanD(14000.0), "less than 14000.0");
		Feature mediumDuration = newFeature("duration", betweenD(14000.0, 44329.0), "between 14000.0 and 44329.0");
		Feature longDuration = newFeature("duration", moreThanD(44329.0), "more than 44329.0");

		if (labels.contains("duration")) {
			features.add(shortDuration);
			features.add(mediumDuration);
			features.add(longDuration);
		}

		Feature isICMP = newFeature("protocol_type", "icmp");
		Feature isTCP = newFeature("protocol_type", "tcp");
		Feature isUDP = newFeature("protocol_type", "udp");

		if (labels.contains("protocol_type")) {
			features.add(isICMP);
			features.add(isTCP);
			features.add(isUDP);
		}

		Feature isServiceAuth = newFeature("service", "auth");
		Feature isServiceBGP = newFeature("service", "bgp");
		Feature isServiceCourier = newFeature("service", "courier");
		Feature isServiceCsnet_ns = newFeature("service", "csnet_ns");
		Feature isServiceCTF = newFeature("service", "ctf");
		Feature isServiceDaytime = newFeature("service", "daytime");
		Feature isServiceDiscard = newFeature("service", "discard");
		Feature isServiceDomain = newFeature("service", "domain");
		Feature isServiceDomain_u = newFeature("service", "domain_u");
		Feature isServiceEcho = newFeature("service", "echo");
		Feature isServiceEco_i = newFeature("service", "eco_i");
		Feature isServiceEcr_i = newFeature("service", "ecr_i");
		Feature isServiceEfs = newFeature("service", "efs");
		Feature isServiceExec = newFeature("service", "exec");
		Feature isServiceFinger = newFeature("service", "finger");
		Feature isServiceFTP = newFeature("service", "ftp");
		Feature isServiceFTP_data = newFeature("service", "ftp_data");
		Feature isServiceGopher = newFeature("service", "gopher");
		Feature isServiceHostnames = newFeature("service", "hostnames");
		Feature isServiceHTTP = newFeature("service", "http");
		Feature isServiceHTTP_443 = newFeature("service", "http_443");
		Feature isServiceIMAP4 = newFeature("service", "imap4");
		Feature isServiceIRC = newFeature("service", "IRC");
		Feature isServiceISO_tsap = newFeature("service", "iso_tsap");
		Feature isServiceKLogin = newFeature("service", "klogin");
		Feature isServiceKShell = newFeature("service", "kshell");
		Feature isServiceLDAP = newFeature("service", "ldap");
		Feature isServiceLink = newFeature("service", "link");
		Feature isServiceLogin = newFeature("service", "login");
		Feature isServiceMTP = newFeature("service", "mtp");
		Feature isServiceName = newFeature("service", "name");
		Feature isServiceNetbios_dgm = newFeature("service", "netbios_dgm");
		Feature isServiceNetbios_ns = newFeature("service", "netbios_ns");
		Feature isServiceNetbios_ssn = newFeature("service", "netbios_ssn");
		Feature isServiceNetstat = newFeature("service", "netstat");
		Feature isServiceNnsp = newFeature("service", "nnsp");
		Feature isServiceNntp = newFeature("service", "nntp");
		Feature isServiceNtp_u = newFeature("service", "ntp_u");
		Feature isServiceOther = newFeature("service", "other");
		Feature isServicePm_dump = newFeature("service", "pm_dump");
		Feature isServicePop_2 = newFeature("service", "pop_2");
		Feature isServicePop_3 = newFeature("service", "pop_3");
		Feature isServicePrinter = newFeature("service", "printer");
		Feature isServicePrivate = newFeature("service", "private");
		Feature isServiceRed_i = newFeature("service", "red_i");
		Feature isServiceRemote_job = newFeature("service", "remote_job");
		Feature isServiceRJE = newFeature("service", "rje");
		Feature isServiceShell = newFeature("service", "shell");
		Feature isServiceSMTP = newFeature("service", "smtp");
		Feature isServiceSql_net = newFeature("service", "sql_net");
		Feature isServiceSSH = newFeature("service", "ssh");
		Feature isServiceSunrpc = newFeature("service", "sunrpc");
		Feature isServiceSundup = newFeature("service", "sundup");
		Feature isServiceSystat = newFeature("service", "systat");
		Feature isServiceTelnet = newFeature("service", "telnet");
		Feature isServiceTftp_u = newFeature("service", "tftp_u");
		Feature isServiceTime = newFeature("service", "time");
		Feature isServiceTim_i = newFeature("service", "tim_i");
		Feature isServiceUrh_i = newFeature("service", "urh_i");
		Feature isServiceUucp = newFeature("service", "uucp");
		Feature isServiceUucp_path = newFeature("service", "uucp_path");
		Feature isServiceVmnet = newFeature("service", "vmnet");
		Feature isServiceWhois = newFeature("service", "whois");
		Feature isServiceX11 = newFeature("service", "X11");
		Feature isServiceZ39_50 = newFeature("service", "Z39_50");

		if (labels.contains("service")) {
			features.add(isServiceAuth);
			features.add(isServiceBGP);
			features.add(isServiceCourier);
			features.add(isServiceCsnet_ns);
			features.add(isServiceCTF);
			features.add(isServiceDaytime);
			features.add(isServiceDiscard);
			features.add(isServiceDomain);
			features.add(isServiceDomain_u);
			features.add(isServiceEcho);
			features.add(isServiceEco_i);
			features.add(isServiceEcr_i);
			features.add(isServiceEfs);
			features.add(isServiceExec);
			features.add(isServiceFinger);
			features.add(isServiceFTP);
			features.add(isServiceFTP_data);
			features.add(isServiceGopher);
			features.add(isServiceHostnames);
			features.add(isServiceHTTP);
			features.add(isServiceHTTP_443);
			features.add(isServiceIMAP4);
			features.add(isServiceIRC);
			features.add(isServiceISO_tsap);
			features.add(isServiceKLogin);
			features.add(isServiceKShell);
			features.add(isServiceLDAP);
			features.add(isServiceLink);
			features.add(isServiceLogin);
			features.add(isServiceMTP);
			features.add(isServiceName);
			features.add(isServiceNetbios_dgm);
			features.add(isServiceNetbios_ns);
			features.add(isServiceNetbios_ssn);
			features.add(isServiceNetstat);
			features.add(isServiceNnsp);
			features.add(isServiceNntp);
			features.add(isServiceNtp_u);
			features.add(isServiceOther);
			features.add(isServicePm_dump);
			features.add(isServicePop_2);
			features.add(isServicePop_3);
			features.add(isServicePrinter);
			features.add(isServicePrivate);
			features.add(isServiceRed_i);
			features.add(isServiceRemote_job);
			features.add(isServiceRJE);
			features.add(isServiceShell);
			features.add(isServiceSMTP);
			features.add(isServiceSql_net);
			features.add(isServiceSSH);
			features.add(isServiceSunrpc);
			features.add(isServiceSundup);
			features.add(isServiceSystat);
			features.add(isServiceTelnet);
			features.add(isServiceTftp_u);
			features.add(isServiceTime);
			features.add(isServiceTim_i);
			features.add(isServiceUrh_i);
			features.add(isServiceUucp);
			features.add(isServiceUucp_path);
			features.add(isServiceVmnet);
			features.add(isServiceWhois);
			features.add(isServiceX11);
			features.add(isServiceZ39_50);
		}

		Feature isFlagOTH = newFeature("flag", "OTH");
		Feature isFlagREJ = newFeature("flag", "REJ");
		Feature isFlagRSTO = newFeature("flag", "RSTO");
		Feature isFlagRSTOS0 = newFeature("flag", "RSTOS0");
		Feature isFlagRSTR = newFeature("flag", "RSTR");
		Feature isFlagS0 = newFeature("flag", "S0");
		Feature isFlagS1 = newFeature("flag", "S1");
		Feature isFlagS2 = newFeature("flag", "S2");
		Feature isFlagS3 = newFeature("flag", "S3");
		Feature isFlagSF = newFeature("flag", "SF");
		Feature isFlagSH = newFeature("flag", "SH");

		if (labels.contains("flag")) {
			features.add(isFlagOTH);
			features.add(isFlagREJ);
			features.add(isFlagRSTO);
			features.add(isFlagRSTOS0);
			features.add(isFlagRSTR);
			features.add(isFlagS0);
			features.add(isFlagS1);
			features.add(isFlagS2);
			features.add(isFlagS3);
			features.add(isFlagSF);
			features.add(isFlagSH);
		}

		Feature sourceBytesSmall = newFeature("source_bytes", lessThanD(2.0), "less than 2.0");
		Feature sourceBytesMedium = newFeature("source_bytes", betweenD(2.0, 5.0), "between 2.0 and 5.0");
		Feature sourceBytesLarge = newFeature("source_bytes", moreThanD(5.0), "more than 5.0");

		if (labels.contains("source_bytes")) {
			features.add(sourceBytesSmall);
			features.add(sourceBytesMedium);
			features.add(sourceBytesLarge);
		}

		Feature destinationBytesSmall = newFeature("destination_bytes", lessThanD(1000000.0), "less than 1000000.0");
		Feature destinationBytesMedium = newFeature("destination_bytes", betweenD(1000000.0, 4155468.0),
				"between 1000000.0 and 4155468.0");
		Feature destinationBytesLarge = newFeature("destination_bytes", moreThanD(4155468.0), "more than 4155468.0");

		if (labels.contains("destination_bytes")) {
			features.add(destinationBytesSmall);
			features.add(destinationBytesMedium);
			features.add(destinationBytesLarge);
		}

		Feature land = newFeature("land", 1);
		Feature notLand = newFeature("land", 0);

		if (labels.contains("land")) {
			features.add(land);
			features.add(notLand);
		}

		Feature noWrongFragment = newFeature("wrong_fragment", 0);
		Feature oneWrongFragment = newFeature("wrong_fragment", 1);
		Feature moreWrongFragments = newFeature("wrong_fragment", moreThan(1), "more than 1");

		if (labels.contains("wrong_fragment")) {
			features.add(noWrongFragment);
			features.add(oneWrongFragment);
			features.add(moreWrongFragments);
		}

		Feature noUrgentPacket = newFeature("urgent", 0);
		Feature oneUrgentPacket = newFeature("urgent", 1);
		Feature moreUrgentPackets = newFeature("urgent", moreThan(1), "more than 1");

		if (labels.contains("urgent")) {
			features.add(noUrgentPacket);
			features.add(oneUrgentPacket);
			features.add(moreUrgentPackets);
		}

		Feature lessHotIndicators = newFeature("hot", lessThanD(1.0), "less than 10.0");
		Feature mediumHotIndicators = newFeature("hot", betweenD(1.0, 5.0), "between 10.0 and 20.0");
		Feature moreHotIndicators = newFeature("hot", moreThanD(5.0), "more than 20.0");

		if (labels.contains("hot")) {
			features.add(lessHotIndicators);
			features.add(mediumHotIndicators);
			features.add(moreHotIndicators);
		}

		Feature noFailedLoginAttempt = newFeature("failed_logins", 0);
		Feature oneFailedLoginAttempt = newFeature("failed_logins", 1);
		Feature twoFailedLoginAttempt = newFeature("failed_logins", 2);
		Feature moreFailedLoginAttempt = newFeature("failed_logins", moreThan(2), "more than 2");

		if (labels.contains("failed_logins")) {
			features.add(noFailedLoginAttempt);
			features.add(oneFailedLoginAttempt);
			features.add(twoFailedLoginAttempt);
			features.add(moreFailedLoginAttempt);
		}

		Feature isLoggedIn = newFeature("logged_in", 1);
		Feature notLoggedIn = newFeature("logged_in", 0);

		if (labels.contains("logged_in")) {
			features.add(isLoggedIn);
			features.add(notLoggedIn);
		}

		Feature lessCompromised = newFeature("compromised", lessThanD(10.0), "less than 200.0");
		Feature mediumCompromised = newFeature("compromised", betweenD(10.0, 200.0), "between 200.0 and 600.0");
		Feature moreCompromised = newFeature("compromised", moreThanD(200.0), "more than 600.0");

		if (labels.contains("compromised")) {
			features.add(lessCompromised);
			features.add(mediumCompromised);
			features.add(moreCompromised);
		}

		Feature isRootShell = newFeature("root_shell", 1);
		Feature notRootShell = newFeature("root_shell", 0);

		if (labels.contains("root_shell")) {
			features.add(isRootShell);
			features.add(notRootShell);
		}

		Feature noSuAttempts = newFeature("su_attempted", 0);
		Feature oneSuAttempt = newFeature("su_attempted", 1);
		Feature moreSuAttempts = newFeature("su_attempted", moreThan(1), "more than 1");

		if (labels.contains("su_attempted")) {
			features.add(noSuAttempts);
			features.add(oneSuAttempt);
			features.add(moreSuAttempts);
		}

		Feature lessRootAccess = newFeature("root", lessThanD(200.0), "less than 200.0");
		Feature mediumRootAccess = newFeature("root", betweenD(200.0, 700.0), "between 200.0 and 700.0");
		Feature moreRootAccess = newFeature("root", moreThanD(700.0), "more than 700.0");

		if (labels.contains("root")) {
			features.add(lessRootAccess);
			features.add(mediumRootAccess);
			features.add(moreRootAccess);
		}

		Feature lessFileCreations = newFeature("file_creations", lessThanD(8.0), "less than 8.0");
		Feature mediumFileCreations = newFeature("file_creations", betweenD(8.0, 20.0), "between 8.0 and 20.0");
		Feature moreFileCreations = newFeature("file_creations", moreThanD(20.0), "more than 20.0");

		if (labels.contains("file_creations")) {
			features.add(lessFileCreations);
			features.add(mediumFileCreations);
			features.add(moreFileCreations);
		}

		Feature noShellPrompt = newFeature("shells", 0);
		Feature oneShellPrompt = newFeature("shells", 1);
		Feature moreShellPrompts = newFeature("shells", moreThan(1), "more than 1");

		if (labels.contains("shells")) {
			features.add(noShellPrompt);
			features.add(oneShellPrompt);
			features.add(moreShellPrompts);
		}

		Feature noAccessFiles = newFeature("access_files", 0);
		Feature oneAccessFiles = newFeature("access_files", 1);
		Feature twoAccessFiles = newFeature("access_files", 2);
		Feature threeAccessFiles = newFeature("access_files", 3);
		Feature moreAccessFiles = newFeature("access_files", moreThan(3), "more than 3");

		if (labels.contains("access_files")) {
			features.add(noAccessFiles);
			features.add(oneAccessFiles);
			features.add(twoAccessFiles);
			features.add(threeAccessFiles);
			features.add(moreAccessFiles);
		}

		Feature noOutboundCmd = newFeature("outbound_cmds", 0);
		Feature moreOutboundCmd = newFeature("outbound_cmds", moreThan(0), "more than 0");

		if (labels.contains("outbound_cmds")) {
			features.add(noOutboundCmd);
			features.add(moreOutboundCmd);
		}

		Feature noHotLogins = newFeature("is_hot_logins", 0);
		Feature moreHotLogins = newFeature("is_hot_logins", moreThan(0), "more than 0");

		if (labels.contains("is_hot_logins")) {
			features.add(noHotLogins);
			features.add(moreHotLogins);
		}

		Feature isGuestLogin = newFeature("is_guest_login", 1);
		Feature notGuestLogin = newFeature("is_guest_login", 0);

		if (labels.contains("is_guest_login")) {
			features.add(isGuestLogin);
			features.add(notGuestLogin);
		}

		Feature lessConnections = newFeature("connection_count", lessThanD(10.0), "less than 10.0");
		Feature mediumConnections = newFeature("connection_count", betweenD(10.0, 50.0), "between 10.0 and 50.0");
		Feature moreConnections = newFeature("connection_count", moreThanD(50.0), "more than 50.0");

		if (labels.contains("connection_count")) {
			features.add(lessConnections);
			features.add(mediumConnections);
			features.add(moreConnections);
		}

		Feature lessServices = newFeature("service_count", lessThanD(150.0), "less than 150.0");
		Feature mediumServices = newFeature("service_count", betweenD(150.0, 350.0), "between 150.0 and 350.0");
		Feature moreServices = newFeature("service_count", moreThanD(350.0), "more than 350.0");

		if (labels.contains("service_count")) {
			features.add(lessServices);
			features.add(mediumServices);
			features.add(moreServices);
		}

		Feature lessSErrorRate = newFeature("serror_rate", lessThanD(0.1), "less than 0.1");
		Feature mediumSErrorRate = newFeature("serror_rate", betweenD(0.1, 0.2), "between 0.1 and 0.2");
		Feature moreSErrorRate = newFeature("serror_rate", moreThanD(0.2), "more than 0.2");

		if (labels.contains("serror_rate")) {
			features.add(lessSErrorRate);
			features.add(mediumSErrorRate);
			features.add(moreSErrorRate);
		}

		Feature lessServSErrorRate = newFeature("service_serror_rate", lessThanD(0.3), "less than 0.3");
		Feature mediumServSErrorRate = newFeature("service_serror_rate", betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreServSErrorRate = newFeature("service_serror_rate", moreThanD(0.6), "more than 0.6");

		if (labels.contains("service_serror_rate")) {
			features.add(lessServSErrorRate);
			features.add(mediumServSErrorRate);
			features.add(moreServSErrorRate);
		}

		Feature lessRErrorRate = newFeature("rerror_rate", lessThanD(0.3), "less than 0.3");
		Feature mediumRErrorRate = newFeature("rerror_rate", betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreRErrorRate = newFeature("rerror_rate", moreThanD(0.6), "more than 0.6");

		if (labels.contains("rerror_rate")) {
			features.add(lessRErrorRate);
			features.add(mediumRErrorRate);
			features.add(moreRErrorRate);
		}

		Feature lessServRErrorRate = newFeature("service_rerror_rate", lessThanD(0.3), "less than 0.3");
		Feature mediumServRErrorRate = newFeature("service_rerror_rate", betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreServRErrorRate = newFeature("service_rerror_rate", moreThanD(0.6), "more than 0.6");

		if (labels.contains("service_rerror_rate")) {
			features.add(lessServRErrorRate);
			features.add(mediumServRErrorRate);
			features.add(moreServRErrorRate);
		}

		Feature lessSameServiceRate = newFeature("same_service_rate", lessThanD(0.3), "less than 0.3");
		Feature mediumSameServiceRate = newFeature("same_service_rate", betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreSameServiceRate = newFeature("same_service_rate", moreThanD(0.6), "more than 0.6");

		if (labels.contains("same_service_rate")) {
			features.add(lessSameServiceRate);
			features.add(mediumSameServiceRate);
			features.add(moreSameServiceRate);
		}

		Feature lessDiffServiceRate = newFeature("diff_service_rate", lessThanD(0.3), "less than 0.3");
		Feature mediumDiffServiceRate = newFeature("diff_service_rate", betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDiffServiceRate = newFeature("diff_service_rate", moreThanD(0.6), "more than 0.6");

		if (labels.contains("diff_service_rate")) {
			features.add(lessDiffServiceRate);
			features.add(mediumDiffServiceRate);
			features.add(moreDiffServiceRate);
		}

		Feature lessServDiffHostRate = newFeature("service_diff_host_rate", lessThanD(0.3), "less than 0.3");
		Feature mediumServDiffHostRate = newFeature("service_diff_host_rate", betweenD(0.3, 0.6),
				"between 0.3 and 0.6");
		Feature moreServDiffHostRate = newFeature("service_diff_host_rate", moreThanD(0.6), "more than 0.6");

		if (labels.contains("service_diff_host_rate")) {
			features.add(lessServDiffHostRate);
			features.add(mediumServDiffHostRate);
			features.add(moreServDiffHostRate);
		}

		Feature lessDestinationHosts = newFeature("destination_host_count", lessThanD(64.0), "less than 64.0");
		Feature mediumDestinationHosts = newFeature("destination_host_count", betweenD(64.0, 192.0),
				"between 64.0 and 192.0");
		Feature moreDestinationHosts = newFeature("destination_host_count", moreThanD(192.0), "more than 192.0");

		if (labels.contains("destination_host_count")) {
			features.add(lessDestinationHosts);
			features.add(mediumDestinationHosts);
			features.add(moreDestinationHosts);
		}

		Feature lessDestinationHostServCount = newFeature("destination_host_service_count", lessThanD(64.0),
				"less than 64.0");
		Feature mediumDestinationHostServCount = newFeature("destination_host_service_count", betweenD(64.0, 192.0),
				"between 64.0 and 192.0");
		Feature moreDestinationHostServCount = newFeature("destination_host_service_count", moreThanD(192.0),
				"more than 192.0");

		if (labels.contains("destination_host_service_count")) {
			features.add(lessDestinationHostServCount);
			features.add(mediumDestinationHostServCount);
			features.add(moreDestinationHostServCount);
		}

		Feature lessDestinationHostSameServiceRate = newFeature("destination_host_same_service_rate", lessThanD(0.3),
				"less than 0.3");
		Feature mediumDestinationHostSameServiceRate = newFeature("destination_host_same_service_rate",
				betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDestinationHostSameServiceRate = newFeature("destination_host_same_service_rate", moreThanD(0.6),
				"more than 0.6");

		if (labels.contains("destination_host_same_service_rate")) {
			features.add(lessDestinationHostSameServiceRate);
			features.add(mediumDestinationHostSameServiceRate);
			features.add(moreDestinationHostSameServiceRate);
		}

		Feature lessDestinationHostDiffServiceRate = newFeature("destination_host_diff_service_rate", lessThanD(0.3),
				"less than 0.3");
		Feature mediumDestinationHostDiffServiceRate = newFeature("destination_host_diff_service_rate",
				betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDestinationHostDiffServiceRate = newFeature("destination_host_diff_service_rate", moreThanD(0.6),
				"more than 0.6");

		if (labels.contains("destination_host_diff_service_rate")) {
			features.add(lessDestinationHostDiffServiceRate);
			features.add(mediumDestinationHostDiffServiceRate);
			features.add(moreDestinationHostDiffServiceRate);
		}

		Feature lessDestinationHostSameServicePortRate = newFeature("destination_host_same_source_port_rate",
				lessThanD(0.3), "less than 0.3");
		Feature mediumDestinationHostSameServicePortRate = newFeature("destination_host_same_source_port_rate",
				betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDestinationHostSameServicePortRate = newFeature("destination_host_same_source_port_rate",
				moreThanD(0.6), "more than 0.6");

		if (labels.contains("destination_host_same_source_port_rate")) {
			features.add(lessDestinationHostSameServicePortRate);
			features.add(mediumDestinationHostSameServicePortRate);
			features.add(moreDestinationHostSameServicePortRate);
		}

		Feature lessDestinationHostDiffServicePortRate = newFeature("destination_host_service_diff_host_rate",
				lessThanD(0.3), "less than 0.3");
		Feature mediumDestinationHostDiffServicePortRate = newFeature("destination_host_service_diff_host_rate",
				betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDestinationHostDiffServicePortRate = newFeature("destination_host_service_diff_host_rate",
				moreThanD(0.6), "more than 0.6");

		if (labels.contains("destination_host_service_diff_host_rate")) {
			features.add(lessDestinationHostDiffServicePortRate);
			features.add(mediumDestinationHostDiffServicePortRate);
			features.add(moreDestinationHostDiffServicePortRate);
		}

		Feature lessDestinationHostSErrorRate = newFeature("destination_host_serror_rate", lessThanD(0.3),
				"less than 0.3");
		Feature mediumDestinationHostSErrorRate = newFeature("destination_host_serror_rate", betweenD(0.3, 0.6),
				"between 0.3 and 0.6");
		Feature moreDestinationHostSErrorRate = newFeature("destination_host_serror_rate", moreThanD(0.6),
				"more than 0.6");

		if (labels.contains("destination_host_serror_rate")) {
			features.add(lessDestinationHostSErrorRate);
			features.add(mediumDestinationHostSErrorRate);
			features.add(moreDestinationHostSErrorRate);
		}

		Feature lessDestinationHostServiceSErrorRate = newFeature("destination_host_service_serror_rate",
				lessThanD(0.3), "less than 0.3");
		Feature mediumDestinationHostServiceSErrorRate = newFeature("destination_host_service_serror_rate",
				betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDestinationHostServiceSErrorRate = newFeature("destination_host_service_serror_rate",
				moreThanD(0.6), "more than 0.6");

		if (labels.contains("destination_host_service_serror_rate")) {
			features.add(lessDestinationHostServiceSErrorRate);
			features.add(mediumDestinationHostServiceSErrorRate);
			features.add(moreDestinationHostServiceSErrorRate);
		}

		Feature lessDestinationHostRErrorRate = newFeature("destination_host_rerror_rate", lessThanD(0.3),
				"less than 0.3");
		Feature mediumDestinationHostRErrorRate = newFeature("destination_host_rerror_rate", betweenD(0.3, 0.6),
				"between 0.3 and 0.6");
		Feature moreDestinationHostRErrorRate = newFeature("destination_host_rerror_rate", moreThanD(0.6),
				"more than 0.6");

		if (labels.contains("destination_host_rerror_rate")) {
			features.add(lessDestinationHostRErrorRate);
			features.add(mediumDestinationHostRErrorRate);
			features.add(moreDestinationHostRErrorRate);
		}

		Feature lessDestinationHostServiceRErrorRate = newFeature("destination_host_service_rerror_rate",
				lessThanD(0.3), "less than 0.3");
		Feature mediumDestinationHostServiceRErrorRate = newFeature("destination_host_service_rerror_rate",
				betweenD(0.3, 0.6), "between 0.3 and 0.6");
		Feature moreDestinationHostServiceRErrorRate = newFeature("destination_host_service_rerror_rate",
				moreThanD(0.6), "more than 0.6");

		if (labels.contains("destination_host_service_rerror_rate")) {
			features.add(lessDestinationHostServiceRErrorRate);
			features.add(mediumDestinationHostServiceRErrorRate);
			features.add(moreDestinationHostServiceRErrorRate);
		}

		return features;

	}

	private static List<DataSample> readData(boolean training) throws IOException {
		List<DataSample> data = new ArrayList<DataSample>();
		String filename = training ? "./train.csv" : "./test.csv";
		InputStreamReader stream = new InputStreamReader(new FileInputStream(filename));
		try (ICsvListReader listReader = new CsvListReader(stream, CsvPreference.STANDARD_PREFERENCE);) {

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = listReader.getHeader(true);

			List<Object> values;
			while ((values = listReader.read(getProcessors(training))) != null) {
				// log(String.format("lineNo=%s,
				// rowNo=%s,data=%s", listReader.getLineNumber(),
				// listReader.getRowNumber(), values));
				data.add(SimpleDataSample.newSimpleDataSample("class", header, values.toArray()));
			}
		}
		List<DataSample> finalData = new ArrayList<DataSample>(data);
		return finalData;
	}

	private static CellProcessor[] getProcessors(boolean training) {
		// TODO fix this is ugly
		if (training) {
			final CellProcessor[] processors = new CellProcessor[] { new Optional(new ParseDouble()), new Optional(),
					new Optional(), new Optional(), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseBool()), new Optional(new ParseInt()), new Optional(new ParseInt()),
					new Optional(new ParseDouble()), new Optional(new ParseInt()), new Optional(new ParseBool()),
					new Optional(new ParseDouble()), new Optional(new ParseBool()), new Optional(new ParseInt()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseInt()),
					new Optional(new ParseInt()), new Optional(new ParseInt()), new Optional(new ParseInt()),
					new Optional(new ParseBool()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseStringLabel()) };
			return processors;
		} else {
			final CellProcessor[] processors = new CellProcessor[] { new Optional(new ParseDouble()), new Optional(),
					new Optional(), new Optional(), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseBool()), new Optional(new ParseInt()), new Optional(new ParseInt()),
					new Optional(new ParseDouble()), new Optional(new ParseInt()), new Optional(new ParseBool()),
					new Optional(new ParseDouble()), new Optional(new ParseBool()), new Optional(new ParseInt()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseInt()),
					new Optional(new ParseInt()), new Optional(new ParseInt()), new Optional(new ParseInt()),
					new Optional(new ParseBool()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseDouble()),
					new Optional(new ParseDouble()), new Optional(new ParseDouble()), new Optional(new ParseInt()) };
			return processors;
		}
	}

	@SuppressWarnings("unused")
	private static class ParseBooleanLabel extends ParseBool {
		public Object execute(final Object value, final CsvContext context) {
			Boolean parsed = (Boolean) super.execute(value, context);
			return parsed ? BooleanLabel.TRUE_LABEL : BooleanLabel.FALSE_LABEL;
		}

	}

	private static class ParseStringLabel extends ParseBool {
		public Object execute(final Object value, final CsvContext context) {
			Label parsed = StringLabel.newLabel(value.toString());
			return parsed;
		}

	}

}
