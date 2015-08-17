package com.wave39.popularmoviesstage2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wave39.popularmoviesstage2.data.MovieListItem;

/**
 * PosterListAdapter
 * Created by bp on 8/8/15.
 */

public class PosterListAdapter extends ArrayAdapter<MovieListItem> {

    private Context mContext;

    public PosterListAdapter(Context context)
    {
        super(context, 0);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.poster_list_item, null);
        } else
        {
            row = convertView;
        }

        MovieListItem data = getItem(position);

        ViewHolder.getViewHolder(row).setValues(data);

        return row;
    }

    private static class ViewHolder
    {
        public ImageView Photo;

        public static ViewHolder getViewHolder(View row)
        {
            if (row.getTag() == null)
            {
                ViewHolder holder = new ViewHolder();
                holder.Photo = (ImageView) row.findViewById(R.id.poster_image_view);
                row.setTag(holder);
            }

            return (ViewHolder) row.getTag();
        }

        public void setValues(MovieListItem data)
        {
            String photoUrl = Common.getPosterURL(data);
            Picasso.with(MainActivity.getContext())
                    .load(photoUrl)
                    .placeholder(R.drawable.frames)
                    .error(R.drawable.status_error)
                    .into(Photo);
        }
    }
}
