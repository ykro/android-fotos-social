package edu.galileo.android.photoshare.photolist.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.photoshare.R;
import edu.galileo.android.photoshare.entities.Photo;
import edu.galileo.android.photoshare.lib.base.BaseUtil;
import edu.galileo.android.photoshare.lib.base.ImageLoader;

/**
 * Created by ykro.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private BaseUtil utils;
    private List<Photo> photoList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public PhotoListAdapter(BaseUtil utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.utils = utils;
        this.photoList = photoList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo currentPhoto = photoList.get(position);
        holder.setOnItemClickListener(currentPhoto, onItemClickListener);

        imageLoader.load(holder.imgMain, currentPhoto.getUrl());
        imageLoader.load(holder.imgAvatar, utils.getAvatarUrl(currentPhoto.getEmail()));
        holder.txtUser.setText(currentPhoto.getEmail());
        holder.txtPlace.setText(utils.getFromLocation(currentPhoto.getLatitutde(), currentPhoto.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhoto(Photo photo) {
        photoList.add(photo);
        notifyDataSetChanged();
    }

    public void removePhoto(Photo photo) {
        photoList.remove(photo);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtUser)
        TextView txtUser;
        @Bind(R.id.imgMain)
        ImageView imgMain;
        @Bind(R.id.txtPlace)
        TextView txtPlace;
        @Bind(R.id.imgShare)
        ImageButton btnShare;
        @Bind(R.id.imgDelete)
        ImageButton btnDelete;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final Photo photo,
                                           final OnItemClickListener listener) {
            txtPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPlaceClick(photo);
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onShareClick(photo);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(photo);
                }
            });
        }
    }
}
