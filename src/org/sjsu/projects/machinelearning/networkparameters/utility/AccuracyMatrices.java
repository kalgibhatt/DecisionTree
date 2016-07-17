package utility;

public class AccuracyMatrices {

	private double TP;
	private double TN;
	private double FP;
	private double FN;

	public double getTP() {
		return TP;
	}

	public void setTP(double TP) {
		this.TP = TP;
	}

	public double getTN() {
		return TN;
	}

	public void setTN(double TN) {
		this.TN = TN;
	}

	public double getFP() {
		return FP;
	}

	public void setFP(double FP) {
		this.FP = FP;
	}

	public double getFN() {
		return FN;
	}

	public void setFN(double FN) {
		this.FN = FN;
	}

	/**
	 * True Positive Rate
	 */
	public double getTPR() {
		return (TP / (TP + FN));
	}

	/**
	 * True Negative Rate
	 */
	public double getTNR() {
		return (TP / (TP + FN));
	}

	/**
	 * Positive Predictive Value
	 */
	public double getPPV() {
		return (TP / (TP + FP));
	}

	/**
	 * Negative Predictive Value
	 */
	public double getNPV() {
		return (TN / (TN + FN));
	}

	/**
	 * False Positive Rate
	 */
	public double getFPR() {
		return (FP / (FP + TN));
	}

	/**
	 * False Negative Rate
	 */
	public double getFNR() {
		return (FN / (FN + TP));
	}

	/**
	 * False Discovery Rate
	 */
	public double getFDR() {
		return (FP / (FP + TP));
	}

	/**
	 * Accuracy
	 */
	public double getACC() {
		return ((TP + TN) / (TP + FN + FP + TN));
	}

	/**
	 * F1 Score: Harmonic Mean of Precision and Recall
	 */
	public double getF1() {
		return (2 * TP / (2 * TP + FP + FN));
	}

	public String toString() {
		return "True Positive Rate: " + getTPR() + "	" + "True Negative Rate: " + getTNR() + "\n"
				+ "Positive Predictive Value: " + getPPV() + "	" + "Negative Predictive Value: " + getNPV() + "\n"
				+ "False Positive Rate: " + getFPR() + "	" + "False Negative Rate: " + getFNR() + "\n"
				+ "False Discovery Rate: " + getFDR() + "\n" + "Accuracy: " + getACC() + "\n"
				+ "F1 Score: " + getF1() + "\n";
	}

}
