package edu.udacity.android.contentfinder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shamim on 5/4/16.
 */
public enum MediaItemType {
    NEWS(1, "news"), PHOTO(2, "photo"), VIDEO(3, "video");

    private Integer id;
    private String name;

    private static final Map<Integer, MediaItemType> idToTypeMap = new HashMap<>();

    static {
        for (MediaItemType type : MediaItemType.values()) {
            idToTypeMap.put(type.getId(), type);
        }
    }

    MediaItemType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MediaItemType fromId(Integer id) {
        return idToTypeMap.get(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
