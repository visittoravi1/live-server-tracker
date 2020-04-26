package org.opengraph.lst.core.beans;

/**
 * App Analysis
 */
public class AppAnalysis {
    private String app;
    private long sum;
    private double average;
    private long count;
    private Range range;

    public AppAnalysis() {
        this.range = new Range();
    }

    public AppAnalysis(String app) {
        this.app = app;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
