package com.playlist.adapter;

import com.playlist.adapter.external.LegacyVinylCatalog;
import com.playlist.core.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VinylCatalogAdapter implements TrackCatalog {

    private final LegacyVinylCatalog legacyCatalog;

    public VinylCatalogAdapter(LegacyVinylCatalog legacyCatalog) {
        if (legacyCatalog == null) {
            throw new IllegalArgumentException("legacyCatalog não pode ser nulo");
        }
        this.legacyCatalog = legacyCatalog;
    }

    @Override
    public List<Track> findAll() {
        String[] records = legacyCatalog.fetchAllRecords();
        List<Track> tracks = new ArrayList<>();
        if (records == null) {
            return tracks;
        }
        for (String record : records) {
            Optional<Track> trackOpt = parseRecord(record);
            if (trackOpt.isPresent()) {
                tracks.add(trackOpt.get());
            }
        }
        return tracks;
    }

    @Override
    public Optional<Track> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        String record = legacyCatalog.findRecordByCatalogNumber(id.trim());
        return parseRecord(record);
    }

    private Optional<Track> parseRecord(String record) {
        if (record == null) {
            return Optional.empty();
        }

        String[] parts = record.split("\\|", -1);
        if (parts.length != 5) {
            return Optional.empty();
        }

        String id = parts[0].trim();
        if (id.isEmpty()) {
            return Optional.empty();
        }

        String title = formatTitleCase(parts[1].trim());
        if (title.isEmpty()) {
            return Optional.empty();
        }

        String artist = formatArtist(parts[2].trim());

        int durationSeconds;
        try {
            long durationsMs = Long.parseLong(parts[3].trim());
            if (durationsMs < 0) {
                return Optional.empty();
            }
            durationSeconds = (int) (durationsMs / 1000);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        boolean premium = "Y".equalsIgnoreCase(parts[4].trim());

        return Optional.of(new Track(id, title, artist, durationSeconds, premium));
    }

    private String formatTitleCase(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

    private String formatArtist(String rawArtist) {
        if (rawArtist == null || rawArtist.isBlank()) {
            return "";
        }
        if (rawArtist.contains(",")) {
            String[] parts = rawArtist.split(",", 2);
            String lastName = formatTitleCase(parts[0]);
            String firstName = formatTitleCase(parts[1]);

            if (firstName.isEmpty()) {
                return lastName;
            }
            if (lastName.isEmpty()) {
                return firstName;
            }
            return firstName + " " + lastName;
        }
        return formatTitleCase(rawArtist);
    }
}