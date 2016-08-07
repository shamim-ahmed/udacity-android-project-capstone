package edu.udacity.android.contentfinder;

import java.util.List;

import edu.udacity.android.contentfinder.model.MediaItem;

/**
 * Created by shamim on 8/7/16.
 */
public interface MediaItemListContainer {
    void loadMediaItems(List<MediaItem> mediaItemList);
}
