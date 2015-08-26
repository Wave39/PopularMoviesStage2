package com.wave39.popularmoviesstage2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * MovieDetailAdapter
 * Created by bp on 8/26/15.
 */

public class MovieDetailAdapter extends ArrayAdapter<Integer> {

    private Context mContext;

    public MovieDetailAdapter(Context context)
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
            row = inflater.inflate(R.layout.movie_detail_video_item, null);
        } else
        {
            row = convertView;
        }

        Integer idx = getItem(position);

        ViewHolder.getViewHolder(row).setValues(idx);

        return row;
    }

    private static class ViewHolder
    {
        public TextView textView;

        public static ViewHolder getViewHolder(View row)
        {
            if (row.getTag() == null)
            {
                ViewHolder holder = new ViewHolder();
                holder.textView = (TextView) row.findViewById(R.id.movie_detail_video_item_text_view);
                row.setTag(holder);
            }

            return (ViewHolder) row.getTag();
        }

        public void setValues(Integer idx)
        {
            textView.setText(Integer.toString(idx));
//            String photoUrl = Common.getPosterURL(data);
//            Picasso.with(MainActivity.getContext())
//                    .load(photoUrl)
//                    .placeholder(R.drawable.frames)
//                    .error(R.drawable.status_error)
//                    .into(Photo);
        }
    }
}
