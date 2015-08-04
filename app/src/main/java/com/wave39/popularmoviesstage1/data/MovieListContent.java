package com.wave39.popularmoviesstage1.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bp on 8/4/15.
 */

public class MovieListContent
{
    public static List<MovieListItem> ITEMS = new ArrayList<MovieListItem>();
    public static Map<Integer, MovieListItem> ITEM_MAP = new HashMap<Integer, MovieListItem>();

    static {
        // Add 3 sample items.
        addItem(new MovieListItem(1, "Movie 1"));
        addItem(new MovieListItem(2, "Movie 2"));
        addItem(new MovieListItem(3, "Movie 3"));
        addItem(new MovieListItem(4, "Movie 4"));
        addItem(new MovieListItem(5, "Movie 5"));
    }

    private static void addItem(MovieListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class MovieListItem {
        public int id;
        public String title;

        public MovieListItem(int id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
