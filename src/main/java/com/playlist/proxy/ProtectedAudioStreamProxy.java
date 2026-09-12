package com.playlist.proxy;

import com.playlist.core.AccessDeniedException;
import com.playlist.core.Subscription;
import com.playlist.core.Track;
import java.util.function.Supplier;

public class ProtectedAudioStreamProxy implements AudioStream {
    private final Track track;
    private final Subscription plan;
    private final Supplier<AudioStream> loader;

    private AudioStream realObject;
    private byte[] cacheBytes;

  public ProtectedAudioStreamProxy(Track track, Subscription plan, Supplier<AudioStream> loader) {
        if (track == null || plan == null || loader == null){
            throw new IllegalArgumentException ("Nenhum parâmetro pode ser nulo");
        }
        this.track = track;
        this.plan = plan;
        this.loader = loader;
  }

  public ProtectedAudioStreamProxy(Track track, Subscription plan) {
        this(track, plan, () -> new RemoteAudioStream(track));
  }

  public boolean isLoaded() {
        return this.realObject != null;
  }

  @Override
  public String getTrackId() {
        return this.track.id();
  }

  @Override
  public byte[] readBytes() {
    if (track.premium() && plan == Subscription.FREE){
        throw new AccessDeniedException ("Plano " + plan + " não tem acesso a faixas premium " + track.id());
    }
    if (cacheBytes == null){
        if (realObject == null){
            realObject = loader.get();
        }
        cacheBytes = realObject.readBytes();
    }
    return cacheBytes.clone();
  }
}

