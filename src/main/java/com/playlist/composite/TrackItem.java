package com.playlist.composite;

import com.playlist.core.Track;
import java.util.List;

public class TrackItem implements MediaItem {
    private final Track track;

  public TrackItem(Track track) {
    if (track == null){
        throw new IllegalArgumentException("Track não pode ser nula");
    }
    this.track = track;
  }

  public Track getTrack() {
    return this.track;
  }

  @Override
  public String getName() {
   return this.track.title();
  }

  @Override
  public int getDurationSeconds() {
   return this.track.durationSeconds();
  }

  @Override
  public int getTrackCount() {
    return 1;
  }

  @Override
  public List<Track> flatten() {
    return List.of(this.track);
  }
}
