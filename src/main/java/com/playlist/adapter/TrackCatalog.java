package com.playlist.adapter;

import com.playlist.core.Track;
import java.util.List;
import java.util.Optional;

public interface TrackCatalog {

  List<Track> findAll();

  Optional<Track> findById(String id);
}
