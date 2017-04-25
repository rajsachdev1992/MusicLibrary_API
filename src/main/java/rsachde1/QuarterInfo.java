package rsachde1;

/**
 *
 * @author Raj.Sachdev
 * 
 * This class stores the percentage of daily requests during four different
 * segments of the day/
 */
public class QuarterInfo {

    private double q1Percent; //12am-5:59am
    private double q2Percent; //6am-11:59am
    private double q3Percent; //12pm-5:59pm
    private double q4Percent; //6pm-11:59pm

    /**
     * Constructor which accepts number of requests during different parts 
     * of the day and calculates the percentages.
     * @param q1Count
     * @param q2Count
     * @param q3Count
     * @param q4Count 
     */
    public QuarterInfo(double q1Count, double q2Count, double q3Count, double q4Count) {
        double total = q1Count + q2Count + q3Count + q4Count;
        this.q1Percent = (q1Count / total) * 100.0;
        this.q2Percent = (q2Count / total) * 100.0;
        this.q3Percent = (q3Count / total) * 100.0;
        this.q4Percent = (q4Count / total) * 100.0;
    }

    public double getQ1Percent() {
        return q1Percent;
    }

    public void setQ1Percent(double q1Percent) {
        this.q1Percent = q1Percent;
    }

    public double getQ2Percent() {
        return q2Percent;
    }

    public void setQ2Percent(double q2Percent) {
        this.q2Percent = q2Percent;
    }

    public double getQ3Percent() {
        return q3Percent;
    }

    public void setQ3Percent(double q3Percent) {
        this.q3Percent = q3Percent;
    }

    public double getQ4Percent() {
        return q4Percent;
    }

    public void setQ4Percent(double q4Percent) {
        this.q4Percent = q4Percent;
    }

}
