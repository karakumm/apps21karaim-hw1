package ua.edu.ucu.tempseries;


import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final int MIN_T = -273;
    static final int MAX_T = 273;
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        this.temperatureSeries = Arrays.copyOf(temperatureSeries,
                temperatureSeries.length);
        this.size = temperatureSeries.length;
    }

    public double average() {
        double sum = 0;
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            sum += temperatureSeries[i];
        }
        return sum / size;
    }

    public double deviation() {
        double sum = 0;
        double mean = average();
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            sum += (temperatureSeries[i] - mean) * (temperatureSeries[i] - mean);
        }
        double res = Math.sqrt(sum / size);
        return res;
    }

    public double min() {
        return findTempClosestToValue(MIN_T);
    }

    public double max() {
        return findTempClosestToValue(MAX_T);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double closestTemp = 0;
        double diff = Math.abs(temperatureSeries[0] - tempValue);
        for (double temp : temperatureSeries) {
            if (Math.abs(temp - tempValue) < diff) {
                diff = temp - closestTemp;
                closestTemp = temp;
            } else if (Math.abs(temp - closestTemp) == diff) {
                if (temp > closestTemp) {
                    closestTemp = temp;
                }
            }
        }
        return closestTemp;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] less = new double[size];
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] < tempValue) {
                less[counter] = temperatureSeries[i];
                counter++;
            }
        }
        return Arrays.copyOf(less, counter);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] greater = new double[size];
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] >= tempValue) {
                greater[counter] = temperatureSeries[i];
                counter++;
            }
        }
        return Arrays.copyOf(greater, counter);
    }

    public TempSummaryStatistics summaryStatistics() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] < MIN_T) {
                throw new InputMismatchException();
            }
        }
        int len = size;
        if (len < temps.length + size) {
            len = len * 2;
            temperatureSeries = Arrays.copyOf(temperatureSeries, len);
        }
        for (double temp : temps) {
            temperatureSeries[size] = temp;
            size++;
        }
        int tempsNum = 0;
        for (double temp : temperatureSeries) {
            tempsNum += temp;
        }
        return tempsNum;
    }
}