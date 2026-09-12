package com.playlist.decorator;

public interface AudioTrack {

  String getTitle();

  double[] getSamples();

  String getEffectChain();
}
