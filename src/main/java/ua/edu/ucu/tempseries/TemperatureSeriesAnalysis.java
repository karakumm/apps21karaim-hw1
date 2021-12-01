package ua.edu.ucu.tempseries;


import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
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
            sum += Math.pow((temperatureSeries[i] - mean), 2);
        }
        double res = Math.sqrt(sum / size);
        return res;
    }

    public double min() {
        return findTempClosestToValue(-273);
    }

    public double max() {
        return findTempClosestToValue(273);
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
        int min = -273;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] < min) {
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
        int temps_num = 0;
        for (double temp : temperatureSeries) {
            temps_num += temp;
        }
        return temps_num;
    }
}