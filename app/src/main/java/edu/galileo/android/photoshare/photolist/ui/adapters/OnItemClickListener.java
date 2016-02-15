package edu.galileo.android.photoshare.photolist.ui.adapters;

import edu.galileo.android.photoshare.entities.Photo;

/**
 * Created by ykro.
 */
public interface OnItemClickListener {
    void onPlaceClick(Photo photo);
    void onShareClick(Photo photo);
    void onDeleteClick(Photo photo);
}
