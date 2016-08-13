package edu.udacity.android.contentfinder.model;

import java.util.HashMap;
import java.util.Map;

public enum MediaItemType {
    NEWS(1L, "news"), PHOTO(2L, "photo"), VIDEO(3L, "video");

    private Long id;
    private String name;

    private static final Map<Long, MediaItemType> idToTypeMap = new HashMap<>();

    static {
        for (MediaItemType type : MediaItemType.values()) {
            idToTypeMap.put(type.getId(), type);
        }
    }

    MediaItemType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static MediaItemType fromId(Long id) {
        return idToTypeMap.get(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
