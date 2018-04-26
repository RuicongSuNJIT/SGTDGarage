package com.sg.tdgarage.configure;

public class DistanceMatrix {
    double[][] distances = new double[11][];

    public DistanceMatrix() {
        distances[0] = new double[]{};
        distances[1] = new double[]{9.052};
        distances[2] = new double[]{5.552, 3.802};
        distances[3] = new double[]{5.552, 15.208, 11.406};
        distances[4] = new double[]{9.052, 19.01, 15.208, 3.802};
        distances[5] = new double[]{9.052, 19.01, 15.208, 3.802, 0};
        distances[6] = new double[]{5.552, 15.208, 11.406, 0, 3.802, 3.802};
        distances[7] = new double[]{1.75, 11.406, 7.604, 3.802, 7.604, 7.604, 3.802};
        distances[8] = new double[]{1.75, 7.604, 3.802, 7.604, 11.406, 11.406, 7.604, 3.802};
        distances[9] = new double[]{5.552, 3.802, 0, 11.406, 15.208, 15.208, 11.406, 7.604, 3.802};
        distances[10] = new double[]{9.052, 0, 3.802, 15.208, 19.01, 19.01, 15.208, 11.406, 7.604, 3.802};
    }

    public double getDistance(int from, int to) {
        if (from < to) {
            return distances[to][from];
        }
        return distances[from][to];
    }
}
