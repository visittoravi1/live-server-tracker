package org.opengraph.lst.core.beans;

/**
 * App Analysis
 */
public class AppAnalysis {
    private String app;
    private double sum;
    private double average;
    private long count;
    private long min;
    private long max;
    private Range range;

    public AppAnalysis() {
        this.range = new Range();
    }

    public AppAnalysis(String app) {
        this.app = app;
        range = new Range();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
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

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
