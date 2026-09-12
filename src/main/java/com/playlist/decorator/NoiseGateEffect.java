package com.playlist.decorator;

import java.util.Locale;

public final class NoiseGateEffect extends AudioEffect {
    private final double threshold;

  public NoiseGateEffect(AudioTrack wrapped, double threshold) {
    super(wrapped);
    this.threshold = threshold;
  }

  @Override
  protected String describe() {
      return String.format(Locale.ROOT, "noiseGate(%.2f)", threshold);
  }

  @Override
  public double[] getSamples() {
    double[] original = wrapped.getSamples();
    double[] result = new double[original.length];

    for(int i = 0; i < original.length; i++){
        if(Math.abs(original[i]) < threshold){
            result[i] = 0.0;
        }
        else{
            result[i] = original[i];
        }
    }
    return  result;
  }
}
