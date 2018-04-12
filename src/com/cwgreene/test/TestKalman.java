package com.cwgreene.test;

import org.apache.commons.math4.filter.DefaultMeasurementModel;
import org.apache.commons.math4.filter.DefaultProcessModel;
import org.apache.commons.math4.filter.KalmanFilter;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.random.GaussianRandomGenerator;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.math4.distribution.MultivariateNormalDistribution;
import org.apache.commons.math4.distribution.MultivariateRealDistribution.Sampler;

public class TestKalman {
    public static void main(String[] args) {
        MultivariateNormalDistribution rng =
            new MultivariateNormalDistribution(new double[] {0}, new double[][] {{2}});
        Sampler sampler = rng.createSampler(RandomSource.create(RandomSource.MT));
        DefaultProcessModel processModel = new DefaultProcessModel(
                new double[][]{{1}},
                new double[][]{{.1}},
                new double[][]{{0.0}},
                new double[] {1},
                new double[][] {{5}});
        KalmanFilter kalmanFilter = new KalmanFilter(
            processModel,
            new DefaultMeasurementModel(
                new double[][]{{1}},
                new double[][]{{2}}));
        for (int i = 0; i < 1000; i++) {
            kalmanFilter.predict();
            kalmanFilter.correct(new double[]{1 + sampler.sample()[0]});
        }
        RealVector x = kalmanFilter.getStateEstimationVector();
        System.out.println(x);
        System.out.println(kalmanFilter.getErrorCovarianceMatrix());
        System.out.println("Hello World!");
    }
}
