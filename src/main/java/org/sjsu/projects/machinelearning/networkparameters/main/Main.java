package org.sjsu.projects.machinelearning.networkparameters.main;

import static org.sjsu.projects.machinelearning.networkparameters.decisiontree.feature.PredicateFeature.*;
import static org.sjsu.projects.machinelearning.networkparameters.decisiontree.feature.P.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sjsu.projects.machinelearning.networkparameters.decisiontree.DecisionTree;
import org.sjsu.projects.machinelearning.networkparameters.decisiontree.data.DataSample;
import org.sjsu.projects.machinelearning.networkparameters.decisiontree.data.SimpleDataSample;
import org.sjsu.projects.machinelearning.networkparameters.decisiontree.feature.Feature;
import org.sjsu.projects.machinelearning.networkparameters.decisiontree.label.BooleanLabel;
import org.sjsu.projects.machinelearning.networkparameters.decisiontree.label.Label;
import org.sjsu.projects.machinelearning.networkparameters.decisiontree.label.StringLabel;
import org.sjsu.projects.machinelearning.networkparameters.utility.AccuracyMatrices;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
//		System.out.println("Randomly deciding the features to use...");
		
//		List<String> labels = randomlySelectFeatures();
		
		System.out.println("Reading data...");
		List<DataSample> trainingData = readData(true);
		System.out.println("Finished reading data...");

		DecisionTree tree = new DecisionTree();

		List<Feature> features = getFeatures();

		System.out.println("Training model...");
		tree.train(trainingData, features);
		System.out.println("Finished training model...");

		// print tree after training
		tree.printTree();

		// read test data
		System.out.println("Predicting results...");

		List<DataSample> testingData = readData(false);
//		 List<String> predictions = Lists.newArrayList();

		System.out.println(calculateAccuracy(testingData, tree).toString());

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
		// System.out.println("Finished predicting results...");
		// //
		// // // write predictions to file
		// System.out.println("Writing predictions to file...");
		// FileWriter fileWriter = new FileWriter(new File("predictions.csv"));
		// fileWriter
		// .append("duration,protocol_type,service,flag,source_bytes,destination_bytes,land,wrong_fragment,urgent,hot,failed_logins,logged_in,compromised,root_shell,su_attempted,root,file_creations,shells,access_files,outbound_cmds,is_hot_logins,is_guest_login,connection_count,service_count,serror_rate,service_serror_rate,rerror_rate,service_rerror_rate,same_service_rate,diff_service_rate,service_diff_host_rate,destination_host_count,destination_host_service_count,destination_host_same_service_rate,destination_host_diff_service_rate,destination_host_same_source_port_rate,destination_host_service_diff_host_rate,destination_host_serror_rate,destination_host_service_serror_rate,destination_host_rerror_rate,destination_host_service_rerror_rate,class")
		// .append("\n");
		// for (String prediction : predictions) {
		// fileWriter.append(prediction).append("\n");
		// }
		// fileWriter.flush();
		// fileWriter.close();
		// System.out.println("Finished writing predictions to file...");

	}

	private static List<String> randomlySelectFeatures() {
		String[] labels = {"duration", "protocol_type", "service", "flag", "source_bytes", "destination_bytes", "land", "wrong_fragment", "urgent", "hot", "failed_logins", "logged_in", "compromised", "root_shell", "su_attempted", "root", "file_creations", "shells", "access_files", "outbound_cmds", "is_hot_logins", "is_guest_login", "connection_count", "service_count", "serror_rate", "service_serror_rate", "rerror_rate", "service_rerror_rate", "same_service_rate", "diff_service_rate", "service_diff_host_rate", "destination_host_count", "destination_host_service_count", "destination_host_same_service_rate", "destination_host_diff_service_rate", "destination_host_same_source_port_rate", "destination_host_service_diff_host_rate", "destination_host_serror_rate", "destination_host_service_serror_rate", "destination_host_rerror_rate", "destination_host_service_rerror_rate", "class"};
		int[] labelInclusionArray = new int[labels.length];
		double trigger = Math.random() + 0.25;
		List<String> labelsInUse = new ArrayList<String>();
		for(int i = 0; i < labelInclusionArray.length; i++) {
			if(Math.random() <= trigger) {
				labelsInUse.add(labels[i]);
			}
		}
		return labelsInUse;
	}

	private static AccuracyMatrices calculateAccuracy(List<DataSample> testingData, DecisionTree tree) {
		double TP = 0;
		double TN = 0;
		double FP = 0;
		double FN = 0;
		AccuracyMatrices accuracy = new AccuracyMatrices();
		for (DataSample dataSample : testingData) {
			int actualClass = Integer.parseInt(dataSample.getValue("class").get().toString());
			int predictedClass = Integer.parseInt(tree.classify(dataSample).getPrintValue());
			if(actualClass == 1) {
				if(predictedClass == 1) {
					TP++;
				} else {
					FN++;
				}
			} else {
				if(predictedClass == 1) {
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
		return accuracy;
	}

	private static List<Feature> getFeatures() {
		 Feature shortDuration = newFeature("duration", lessThanD(14000.0),
		 "less than 14000.0");
		 Feature mediumDuration = newFeature("duration", betweenD(14000.0,
		 44329.0), "between 14000.0 and 44329.0");
		 Feature longDuration = newFeature("duration", moreThanD(44329.0),
		 "more than 44329.0");

		 Feature isICMP = newFeature("protocol_type", "icmp");
		 Feature isTCP = newFeature("protocol_type", "tcp");
		 Feature isUDP = newFeature("protocol_type", "udp");

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

		 Feature sourceBytesSmall = newFeature("source_bytes", lessThanD(2.0),
		 "less than 2.0");
		 Feature sourceBytesMedium = newFeature("source_bytes", betweenD(2.0,
		 5.0), "between 2.0 and 5.0");
		 Feature sourceBytesLarge = newFeature("source_bytes", moreThanD(5.0),
		 "more than 5.0");

		 Feature destinationBytesSmall = newFeature("destination_bytes",
		 lessThanD(1000000.0), "less than 1000000.0");
		 Feature destinationBytesMedium = newFeature("destination_bytes",
		 betweenD(1000000.0, 4155468.0),
		 "between 1000000.0 and 4155468.0");
		 Feature destinationBytesLarge = newFeature("destination_bytes",
		 moreThanD(4155468.0), "more than 4155468.0");

		 Feature land = newFeature("land", 1);
		 Feature notLand = newFeature("land", 0);

		 Feature noWrongFragment = newFeature("wrong_fragment", 0);
		 Feature oneWrongFragment = newFeature("wrong_fragment", 1);
		 Feature moreWrongFragments = newFeature("wrong_fragment",
		 moreThan(1), "more than 1");

		 Feature noUrgentPacket = newFeature("urgent", 0);
		 Feature oneUrgentPacket = newFeature("urgent", 1);
		 Feature moreUrgentPackets = newFeature("urgent", moreThan(1), "more than 1");

//		 Feature lessHotIndicators = newFeature("hot", lessThanD(1.0), "less than 10.0");
//		 Feature mediumHotIndicators = newFeature("hot", betweenD(1.0, 5.0),
//		 "between 10.0 and 20.0");
//		 Feature moreHotIndicators = newFeature("hot", moreThanD(5.0), "more than 20.0");
//		
//		 Feature noFailedLoginAttempt = newFeature("failed_logins", 0);
//		 Feature oneFailedLoginAttempt = newFeature("failed_logins", 1);
//		 Feature twoFailedLoginAttempt = newFeature("failed_logins", 2);
//		 Feature moreFailedLoginAttempt = newFeature("failed_logins",
//		 moreThan(2), "more than 2");
//
//		 Feature isLoggedIn = newFeature("logged_in", 1);
//		 Feature notLoggedIn = newFeature("logged_in", 0);
//		
//		 Feature lessCompromised = newFeature("compromised", lessThanD(10.0),
//		 "less than 200.0");
//		 Feature mediumCompromised = newFeature("compromised", betweenD(10.0,
//		 200.0), "between 200.0 and 600.0");
//		 Feature moreCompromised = newFeature("compromised", moreThanD(200.0),
//		 "more than 600.0");
//
//		 Feature isRootShell = newFeature("root_shell", 1);
//		 Feature notRootShell = newFeature("root_shell", 0);
//
//		 Feature noSuAttempts = newFeature("su_attempted", 0);
//		 Feature oneSuAttempt = newFeature("su_attempted", 1);
//		 Feature moreSuAttempts = newFeature("su_attempted", moreThan(1),
//		 "more than 1");
//
//		 Feature lessRootAccess = newFeature("root", lessThanD(200.0), "less than 200.0");
//		 Feature mediumRootAccess = newFeature("root", betweenD(200.0, 700.0),
//		 "between 200.0 and 700.0");
//		 Feature moreRootAccess = newFeature("root", moreThanD(700.0), "more than 700.0");
//
//		 Feature lessFileCreations = newFeature("file_creations",
//		 lessThanD(8.0), "less than 8.0");
//		 Feature mediumFileCreations = newFeature("file_creations",
//		 betweenD(8.0, 20.0), "between 8.0 and 20.0");
//		 Feature moreFileCreations = newFeature("file_creations",
//		 moreThanD(20.0), "more than 20.0");
//
//		 Feature noShellPrompt = newFeature("shells", 0);
//		 Feature oneShellPrompt = newFeature("shells", 1);
//		 Feature moreShellPrompts = newFeature("shells", moreThan(1), "more than 1");
//
//		 Feature noAccessFiles = newFeature("access_files", 0);
//		 Feature oneAccessFiles = newFeature("access_files", 1);
//		 Feature twoAccessFiles = newFeature("access_files", 2);
//		 Feature threeAccessFiles = newFeature("access_files", 3);
//		 Feature moreAccessFiles = newFeature("access_files", moreThan(3),
//		 "more than 3");
//
//		 Feature noOutboundCmd = newFeature("outbound_cmds", 0);
//		 Feature moreOutboundCmd = newFeature("outbound_cmds", moreThan(0),
//		 "more than 0");
//
//		 Feature noHotLogins = newFeature("is_hot_logins", 0);
//		 Feature moreHotLogins = newFeature("is_hot_logins", moreThan(0),
//		 "more than 0");
//
//		 Feature isGuestLogin = newFeature("is_guest_login", 1);
//		 Feature notGuestLogin = newFeature("is_guest_login", 0);
//		
//		 Feature lessConnections = newFeature("connection_count",
//		 lessThanD(10.0), "less than 10.0");
//		 Feature mediumConnections = newFeature("connection_count",
//		 betweenD(10.0, 50.0), "between 10.0 and 50.0");
//		 Feature moreConnections = newFeature("connection_count",
//		 moreThanD(50.0), "more than 50.0");
//		
//		 Feature lessServices = newFeature("service_count", lessThanD(150.0),
//		 "less than 150.0");
//		 Feature mediumServices = newFeature("service_count", betweenD(150.0,
//		 350.0), "between 150.0 and 350.0");
//		 Feature moreServices = newFeature("service_count", moreThanD(350.0),
//		 "more than 350.0");
		
		 Feature lessSErrorRate = newFeature("serror_rate", lessThanD(0.1),
		 "less than 0.1");
		 Feature mediumSErrorRate = newFeature("serror_rate", betweenD(0.1,
		 0.2), "between 0.1 and 0.2");
		 Feature moreSErrorRate = newFeature("serror_rate", moreThanD(0.2),
		 "more than 0.2");
		
//		 Feature lessServSErrorRate = newFeature("service_serror_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumServSErrorRate = newFeature("service_serror_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreServSErrorRate = newFeature("service_serror_rate",
//		 moreThanD(0.6), "more than 0.6");
		
		 Feature lessRErrorRate = newFeature("rerror_rate", lessThanD(0.3),
		 "less than 0.3");
		 Feature mediumRErrorRate = newFeature("rerror_rate", betweenD(0.3,
		 0.6), "between 0.3 and 0.6");
		 Feature moreRErrorRate = newFeature("rerror_rate", moreThanD(0.6),
		 "more than 0.6");
		
//		 Feature lessServRErrorRate = newFeature("service_rerror_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumServRErrorRate = newFeature("service_rerror_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreServRErrorRate = newFeature("service_rerror_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessSameServiceRate = newFeature("same_service_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumSameServiceRate = newFeature("same_service_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreSameServiceRate = newFeature("same_service_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessDiffServiceRate = newFeature("diff_service_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumDiffServiceRate = newFeature("diff_service_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDiffServiceRate = newFeature("diff_service_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessServDiffHostRate = newFeature("service_diff_host_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumServDiffHostRate = newFeature("service_diff_host_rate",
//		 betweenD(0.3, 0.6),
//		 "between 0.3 and 0.6");
//		 Feature moreServDiffHostRate = newFeature("service_diff_host_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessDestinationHosts = newFeature("destination_host_count",
//		 lessThanD(64.0), "less than 64.0");
//		 Feature mediumDestinationHosts = newFeature("destination_host_count",
//		 betweenD(64.0, 192.0),
//		 "between 64.0 and 192.0");
//		 Feature moreDestinationHosts = newFeature("destination_host_count",
//		 moreThanD(192.0), "more than 192.0");
//		
//		 Feature lessDestinationHostServCount =
//		 newFeature("destination_host_service_count", lessThanD(64.0),
//		 "less than 64.0");
//		 Feature mediumDestinationHostServCount =
//		 newFeature("destination_host_service_count", betweenD(64.0, 192.0),
//		 "between 64.0 and 192.0");
//		 Feature moreDestinationHostServCount =
//		 newFeature("destination_host_service_count", moreThanD(192.0),
//		 "more than 192.0");
//		
//		 Feature lessDestinationHostSameServiceRate =
//		 newFeature("destination_host_same_service_rate", lessThanD(0.3),
//		 "less than 0.3");
//		 Feature mediumDestinationHostSameServiceRate =
//		 newFeature("destination_host_same_service_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDestinationHostSameServiceRate =
//		 newFeature("destination_host_same_service_rate", moreThanD(0.6),
//		 "more than 0.6");
//		
//		 Feature lessDestinationHostDiffServiceRate =
//		 newFeature("destination_host_diff_service_rate", lessThanD(0.3),
//		 "less than 0.3");
//		 Feature mediumDestinationHostDiffServiceRate =
//		 newFeature("destination_host_diff_service_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDestinationHostDiffServiceRate =
//		 newFeature("destination_host_diff_service_rate", moreThanD(0.6),
//		 "more than 0.6");
//		
//		 Feature lessDestinationHostSameServicePortRate =
//		 newFeature("destination_host_same_source_port_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumDestinationHostSameServicePortRate =
//		 newFeature("destination_host_same_source_port_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDestinationHostSameServicePortRate =
//		 newFeature("destination_host_same_source_port_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessDestinationHostDiffServicePortRate =
//		 newFeature("destination_host_service_diff_host_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumDestinationHostDiffServicePortRate =
//		 newFeature("destination_host_service_diff_host_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDestinationHostDiffServicePortRate =
//		 newFeature("destination_host_service_diff_host_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessDestinationHostSErrorRate =
//		 newFeature("destination_host_serror_rate", lessThanD(0.3),
//		 "less than 0.3");
//		 Feature mediumDestinationHostSErrorRate =
//		 newFeature("destination_host_serror_rate", betweenD(0.3, 0.6),
//		 "between 0.3 and 0.6");
//		 Feature moreDestinationHostSErrorRate =
//		 newFeature("destination_host_serror_rate", moreThanD(0.6),
//		 "more than 0.6");
//		
//		 Feature lessDestinationHostServiceSErrorRate =
//		 newFeature("destination_host_service_serror_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumDestinationHostServiceSErrorRate =
//		 newFeature("destination_host_service_serror_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDestinationHostServiceSErrorRate =
//		 newFeature("destination_host_service_serror_rate",
//		 moreThanD(0.6), "more than 0.6");
//		
//		 Feature lessDestinationHostRErrorRate =
//		 newFeature("destination_host_rerror_rate", lessThanD(0.3),
//		 "less than 0.3");
//		 Feature mediumDestinationHostRErrorRate =
//		 newFeature("destination_host_rerror_rate", betweenD(0.3, 0.6),
//		 "between 0.3 and 0.6");
//		 Feature moreDestinationHostRErrorRate =
//		 newFeature("destination_host_rerror_rate", moreThanD(0.6),
//		 "more than 0.6");
//		
//		 Feature lessDestinationHostServiceRErrorRate =
//		 newFeature("destination_host_service_rerror_rate",
//		 lessThanD(0.3), "less than 0.3");
//		 Feature mediumDestinationHostServiceRErrorRate =
//		 newFeature("destination_host_service_rerror_rate",
//		 betweenD(0.3, 0.6), "between 0.3 and 0.6");
//		 Feature moreDestinationHostServiceRErrorRate =
//		 newFeature("destination_host_service_rerror_rate",
//		 moreThanD(0.6), "more than 0.6");

		return Arrays.asList(
		 shortDuration, mediumDuration, longDuration,

		 isICMP, isTCP, isUDP,

		 isServiceAuth, isServiceBGP, isServiceCourier, isServiceCsnet_ns,
		 isServiceCTF,
		 isServiceDaytime, isServiceDiscard, isServiceDomain,
		 isServiceDomain_u, isServiceEcho, isServiceEco_i,
		 isServiceEcr_i, isServiceEfs, isServiceExec, isServiceFinger,
		 isServiceFTP, isServiceFTP_data, isServiceGopher,
		 isServiceHostnames, isServiceHTTP, isServiceHTTP_443, isServiceIMAP4,
		 isServiceIRC, isServiceISO_tsap,
		 isServiceKLogin, isServiceKShell, isServiceLDAP, isServiceLink,
		 isServiceLogin, isServiceMTP, isServiceName,
		 isServiceNetbios_dgm, isServiceNetbios_ns, isServiceNetbios_ssn,
		 isServiceNetstat, isServiceNnsp,
		 isServiceNntp, isServiceNtp_u, isServiceOther, isServicePm_dump,
		 isServicePop_2, isServicePop_3,
		 isServicePrinter, isServicePrivate, isServiceRed_i,
		 isServiceRemote_job, isServiceRJE, isServiceShell,
		 isServiceSMTP, isServiceSql_net, isServiceSSH, isServiceSunrpc,
		 isServiceSundup, isServiceSystat,
		 isServiceTelnet, isServiceTftp_u, isServiceTime, isServiceTim_i,
		 isServiceUrh_i, isServiceUucp,
		 isServiceUucp_path, isServiceVmnet, isServiceWhois, isServiceX11,
		 isServiceZ39_50,
		
		 isFlagOTH, isFlagREJ, isFlagRSTO, isFlagRSTOS0, isFlagRSTR, isFlagS0,
		 isFlagS1, isFlagS2, isFlagS3, isFlagSF, isFlagSH,
		
		 sourceBytesSmall, sourceBytesMedium, sourceBytesLarge,
		
		 destinationBytesSmall, destinationBytesMedium, destinationBytesLarge,
		
		 land, notLand,
		
		 noWrongFragment, oneWrongFragment, moreWrongFragments,
		
		 noUrgentPacket, oneUrgentPacket, moreUrgentPackets,
		
//		 lessHotIndicators, mediumHotIndicators, moreHotIndicators,
//		
//		 noFailedLoginAttempt, oneFailedLoginAttempt, twoFailedLoginAttempt,
//		 moreFailedLoginAttempt,
//		
//		 isLoggedIn, notLoggedIn,
//		
//		 lessCompromised, mediumCompromised, moreCompromised,
//		
//		 isRootShell, notRootShell,
//		
//		 noSuAttempts, oneSuAttempt, moreSuAttempts,
//		
//		 lessRootAccess, mediumRootAccess, moreRootAccess,
//		
//		 lessFileCreations, mediumFileCreations, moreFileCreations,
//		
//		 noShellPrompt, oneShellPrompt, moreShellPrompts,
//		
//		 noAccessFiles, oneAccessFiles, twoAccessFiles, threeAccessFiles,
//		 moreAccessFiles,
//		
//		 noOutboundCmd, moreOutboundCmd,
//		
//		 noHotLogins, moreHotLogins,
//		
//		 isGuestLogin, notGuestLogin,
//		
//		 lessConnections, mediumConnections, moreConnections,
//		
//		 lessServices, mediumServices, moreServices,
		
		 lessSErrorRate, mediumSErrorRate, moreSErrorRate,
		
//		 lessServSErrorRate, mediumServSErrorRate, moreServSErrorRate,
		
		 lessRErrorRate, mediumRErrorRate, moreRErrorRate
//		 ,
		
//		 lessServRErrorRate, mediumServRErrorRate, moreServRErrorRate,
//		
//		 lessSameServiceRate, mediumSameServiceRate, moreSameServiceRate,
//		
//		 lessDiffServiceRate, mediumDiffServiceRate, moreDiffServiceRate,
//		
//		 lessServDiffHostRate, mediumServDiffHostRate, moreServDiffHostRate,
//		
//		 lessDestinationHosts, mediumDestinationHosts, moreDestinationHosts,
//		
//		 lessDestinationHostServCount, mediumDestinationHostServCount,
//		 moreDestinationHostServCount,
//		
//		 lessDestinationHostSameServiceRate,
//		 mediumDestinationHostSameServiceRate,
//		 moreDestinationHostSameServiceRate,
//		
//		 lessDestinationHostDiffServiceRate,
//		 mediumDestinationHostDiffServiceRate,
//		 moreDestinationHostDiffServiceRate,
//		
//		 lessDestinationHostSameServicePortRate,
//		 mediumDestinationHostSameServicePortRate,
//		 moreDestinationHostSameServicePortRate,
//		
//		 lessDestinationHostDiffServicePortRate,
//		 mediumDestinationHostDiffServicePortRate,
//		 moreDestinationHostDiffServicePortRate,
//		
//		 lessDestinationHostSErrorRate, mediumDestinationHostSErrorRate,
//		 moreDestinationHostSErrorRate,
//		
//		 lessDestinationHostServiceSErrorRate,
//		 mediumDestinationHostServiceSErrorRate,
//		 moreDestinationHostServiceSErrorRate,
//		
//		 lessDestinationHostRErrorRate, mediumDestinationHostRErrorRate,
//		 moreDestinationHostRErrorRate,
//		
//		 lessDestinationHostServiceRErrorRate,
//		 mediumDestinationHostServiceRErrorRate,
//		 moreDestinationHostServiceRErrorRate
		);

	}

	private static List<DataSample> readData(boolean training) throws IOException {
		List<DataSample> data = new ArrayList<DataSample>();
		String filename = training ? "train.csv" : "test.csv";
		InputStreamReader stream = new InputStreamReader(Main.class.getResourceAsStream(filename));
		try (ICsvListReader listReader = new CsvListReader(stream, CsvPreference.STANDARD_PREFERENCE);) {

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = listReader.getHeader(true);

			List<Object> values;
			while ((values = listReader.read(getProcessors(training))) != null) {
				// System.out.println(String.format("lineNo=%s,
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
