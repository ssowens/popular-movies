package com.ssowens.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 12/11/16.
 */

public class GridViewAdapter extends ArrayAdapter<MovieItem> {

    private static final String LOG_TAG = GridViewAdapter.class.getSimpleName();

    private Context context;
    private List<MovieItem> gridMovies = new ArrayList<>();

    GridViewAdapter(Context context, ArrayList<MovieItem> dataObjects) {
        super(context, 0, dataObjects);
        this.context = context;
        this.gridMovies = dataObjects;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param objects
     */
    void setGridData(List<MovieItem> objects) {
        this.gridMovies = objects;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item_layout, parent, false);
            imageView = rootView.findViewById(R.id.grid_item_image);
            rootView.setTag(imageView);

        } else {
            imageView = (ImageView) rootView.getTag();
        }

        MovieItem item = gridMovies.get(position);
        Picasso.with(context)
                .load(item.getImage())
                .error(R.drawable.errorimage)
                .into(imageView);

        return rootView;
    }
}
