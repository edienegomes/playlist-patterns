package com.playlist.decorator;

public final class FadeInEffect extends AudioEffect {
    private final int sampleCount;

  public FadeInEffect(AudioTrack wrapped, int sampleCount) {
    super(wrapped);
    this.sampleCount = sampleCount;
  }

  @Override
  protected String describe() {
   return "fadeIn(" + sampleCount + ")";
  }

  @Override
  public double[] getSamples() {
    double[] original = wrapped.getSamples();
    double[] result = original.clone();

    if (sampleCount <= 0){
        return result;
    }

    int limit = Math.min(sampleCount, result.length);
    for (int i = 0; i < limit; i ++){
        result[i] = result[i] * ((double) i - sampleCount);
    }
    return result;
  }
}
