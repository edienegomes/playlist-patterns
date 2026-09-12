package com.playlist.decorator;


import java.util.Locale;

public final class VolumeEffect extends AudioEffect {
    private final double factor;

  public VolumeEffect(AudioTrack wrapped, double factor) {
    super(wrapped);
    this.factor = factor;
  }

  @Override
  protected String describe() {
    return String.format(Locale.ROOT, "volumw(%.1f)", factor);
  }

  @Override
  public double[] getSamples() {
   double[] original = wrapped.getSamples();
   double[] result = new double[original.length];

   for (int i = 0; i < original.length; i++){
       double val = original[i] * factor;
       if (val > 1.0){
           val = 1.0;
       }
       else if (val < -1.0){
           val = -1.0;
       }
       result[i] = val;
   }
   return result;
   }
}
