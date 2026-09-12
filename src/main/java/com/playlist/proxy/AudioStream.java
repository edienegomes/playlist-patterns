package com.playlist.proxy;

public interface AudioStream {

  String getTrackId();
  byte[] readBytes();
}
