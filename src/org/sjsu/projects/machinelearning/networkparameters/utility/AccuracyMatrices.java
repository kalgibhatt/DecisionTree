package utility;

import java.util.ArrayList;
import java.util.List;

public class AccuracyMatrices implements Comparable<AccuracyMatrices> {

	private double TP;
	private double TN;
	private double FP;
	private double FN;

	private double TPR;
	private double TNR;
	private double PPV;
	private double NPV;
	private double FPR;
	private double FNR;
	private double FDR;
	private double ACC;
	private double F1;
	
	private List<String> labels = new ArrayList<String>();

	public AccuracyMatrices(double TP, double TN, double FP, double FN) {
		TPR = (TP / (TP + FN));
		TNR = (TP / (TP + FN));
		PPV = (TP / (TP + FP));
		NPV = (TN / (TN + FN));
		FPR = (FP / (FP + TN));
		FNR = (FN / (FN + TP));
		FDR = (FP / (FP + TP));
		ACC = ((TP + TN) / (TP + FN + FP + TN));
		F1 = (2 * TP / (2 * TP + FP + FN));
	}

	public AccuracyMatrices() {

	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public double getTP() {
		return TP;
	}

	public void setTP(double tP) {
		TP = tP;
	}

	public double getTN() {
		return TN;
	}

	public void setTN(double tN) {
		TN = tN;
	}

	public double getFP() {
		return FP;
	}

	public void setFP(double fP) {
		FP = fP;
	}

	public double getFN() {
		return FN;
	}

	public void setFN(double fN) {
		FN = fN;
	}

	public double getTPR() {
		return TPR;
	}

	public void setTPR(double tPR) {
		TPR = tPR;
	}

	public double getTNR() {
		return TNR;
	}

	public void setTNR(double tNR) {
		TNR = tNR;
	}

	public double getPPV() {
		return PPV;
	}

	public void setPPV(double pPV) {
		PPV = pPV;
	}

	public double getNPV() {
		return NPV;
	}

	public void setNPV(double nPV) {
		NPV = nPV;
	}

	public double getFPR() {
		return FPR;
	}

	public void setFPR(double fPR) {
		FPR = fPR;
	}

	public double getFNR() {
		return FNR;
	}

	public void setFNR(double fNR) {
		FNR = fNR;
	}

	public double getFDR() {
		return FDR;
	}

	public void setFDR(double fDR) {
		FDR = fDR;
	}

	public double getACC() {
		return ACC;
	}

	public void setACC(double aCC) {
		ACC = aCC;
	}

	public double getF1() {
		return F1;
	}

	public void setF1(double f1) {
		F1 = f1;
	}

	public String toString() {
		String objectString = "";
		objectString = objectString + "LabelsCount: " + getLabels().size() + "\n" + "Labels: ";
		for (String label : getLabels()) {
			objectString = objectString + label + ",";
		}
		objectString = objectString + "\n" + "True Positive Rate: " + "\t" + getTPR() + "\n" + "True Negative Rate: " + "\t" + getTNR() + "\n" + "Positive Predictive Value: " + "\t" + getPPV() + "\n" + "Negative Predictive Value: " + "\t" + getNPV() + "\n" + "False Positive Rate: " + "\t" + getFPR() + "\n" + "False Negative Rate: " + "\t" + getFNR() + "\n" + "False Discovery Rate: " + "\t" + getFDR() + "\n" + "Accuracy: " + "\t" + getACC() + "\n" + "F1 Score: " + "\t" + getF1() + "\n";
		return  objectString;
	}

	@Override
	public int compareTo(AccuracyMatrices compareMatrix) {
		return this.getLabels().size() - compareMatrix.getLabels().size();
	}

}
