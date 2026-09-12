package com.playlist.proxy;

import com.playlist.core.Track;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoteAudioStream implements AudioStream {

  private static final AtomicInteger INSTANCES = new AtomicInteger();

  private final Track track;
  private int fetchCount;

  public RemoteAudioStream(Track track) {
    if (track == null) {
      throw new IllegalArgumentException("track não pode ser nula");
    }
    this.track = track;
    INSTANCES.incrementAndGet();
  }

  public static int getInstancesCreated() {
    return INSTANCES.get();
  }

  public static void resetInstanceCounter() {
    INSTANCES.set(0);
  }

  @Override
  public String getTrackId() {
    return track.id();
  }

  @Override
  public byte[] readBytes() {
    fetchCount++;
    String payload = "AUDIO::" + track.id() + "::" + track.durationSeconds();
    return payload.getBytes(StandardCharsets.UTF_8);
  }

  public int getFetchCount() {
    return fetchCount;
  }
}
