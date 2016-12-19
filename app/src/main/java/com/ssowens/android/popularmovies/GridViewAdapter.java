package com.ssowens.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sheila Owens on 12/11/16.
 */

public class GridViewAdapter extends ArrayAdapter<MovieItem> {

    private static final String LOG_TAG = GridViewAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<MovieItem> mGridMovies = new ArrayList<>();


    public GridViewAdapter(Context context, ArrayList<MovieItem> dataObjects) {
        super(context, 0, dataObjects);
        this.mContext = context;
        this.mGridMovies = dataObjects;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param objects
     */
    public void setGridData(ArrayList<MovieItem> objects) {
        this.mGridMovies = objects;
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
            imageView = (ImageView) rootView.findViewById(R.id.grid_item_image);
            rootView.setTag(imageView);

        } else {
            imageView = (ImageView) rootView.getTag();
        }

        MovieItem item = mGridMovies.get(position);
        Picasso.with(mContext).load(item.getImage()).into(imageView);

        return rootView;
    }

}
