package com.playlist.composite;


import com.playlist.core.Track;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;


public class PlaylistNode implements MediaItem {
    private final String name;
    private final List<MediaItem> children = new ArrayList<>();

  public PlaylistNode(String name) {
    if (name == null || name.isBlank()){
        throw new IllegalArgumentException("Nome da playlist não pode ser nulo ou em branco");
    }
    this.name = name;
  }

  public PlaylistNode add(MediaItem item) {
      if (item == null){
          throw new IllegalArgumentException("Item não pode ser nulo o");
      }
      if (item == this){
          throw new IllegalArgumentException("Não é possível adicionar a playlist a si mesma");
      }
      if (item instanceof PlaylistNode childNode && childNode.contains(this)){
          throw new IllegalArgumentException("Adicionar este item criaria um ciclo");
      }
      this.children.add(item);
      return this;
  }

  public boolean remove(MediaItem item) {
      return this.children.remove(item);
  }

  public List<MediaItem> getChildren() {
      return List.copyOf(this.children);
  }

  public boolean contains(MediaItem item) {
   if (item == null){
       return false;
   }
   for (MediaItem child: children){
       if (child.equals(item)){
           return true;
       }
   }
   return false;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getDurationSeconds() {
    int total = 0;
    for (MediaItem child : children){
        total += child.getDurationSeconds();
    }
    return total;
  }

  @Override
  public int getTrackCount() {
      int total = 0;
      for (MediaItem child : children) {
          total += child.getTrackCount();
      }
      return total;
  }
  @Override
  public List<Track> flatten() {
    List<Track> result = new ArrayList<>();
    for (MediaItem child : children){
        result.addAll(child.flatten());
    }
    return result;
  }
}
