package com.wave39.popularmoviesstage2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wave39.popularmoviesstage2.data.MovieReview;
import com.wave39.popularmoviesstage2.data.MovieVideo;

/**
 * MovieDetailAdapter
 * Created by bp on 8/26/15.
 */

public class MovieDetailAdapter extends ArrayAdapter<Object> {

    private final int VIEW_TYPE_VIDEO = 0;
    private final int VIEW_TYPE_REVIEW = 1;

    private Context mContext;

    public MovieDetailAdapter(Context context)
    {
        super(context, 0);
        this.mContext = context;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        Object obj = getItem(position);
        if (obj instanceof MovieVideo)
        {
            return VIEW_TYPE_VIDEO;
        }

        return VIEW_TYPE_REVIEW;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int rowType = getItemViewType(position);

        if (convertView == null)
        {
            if (rowType == VIEW_TYPE_VIDEO)
            {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_detail_video_item, parent, false);
                VideoViewHolder viewHolder = new VideoViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else
            {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_detail_review_item, parent, false);
                ReviewViewHolder viewHolder = new ReviewViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
        }

        if (rowType == VIEW_TYPE_VIDEO)
        {
            MovieVideo movieVideo = (MovieVideo)getItem(position);
            VideoViewHolder holder = (VideoViewHolder)convertView.getTag();
            holder.setValues(movieVideo);
        }
        else
        {
            MovieReview movieReview = (MovieReview)getItem(position);
            ReviewViewHolder holder = (ReviewViewHolder)convertView.getTag();
            holder.setValues(movieReview);
        }

        return convertView;
    }

    private static class VideoViewHolder
    {
        public TextView textView;

        public VideoViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.video_name_text_view);
        }

        public void setValues(MovieVideo movieVideo)
        {
            textView.setText(movieVideo.name);
        }
    }

    private static class ReviewViewHolder
    {
        public TextView authorTextView;
        public TextView contentTextView;

        public ReviewViewHolder(View view) {
            authorTextView = (TextView) view.findViewById(R.id.author_text_view);
            contentTextView = (TextView)view.findViewById(R.id.content_text_view);
        }

        public void setValues(MovieReview movieReview)
        {
            authorTextView.setText("Review by: " + movieReview.author);
            contentTextView.setText(movieReview.content);
        }
    }
}
