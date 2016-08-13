package edu.udacity.android.contentfinder;

import java.util.List;

import edu.udacity.android.contentfinder.model.MediaItem;

public interface MediaItemListContainer {
    void loadMediaItems(List<MediaItem> mediaItemList);
}
