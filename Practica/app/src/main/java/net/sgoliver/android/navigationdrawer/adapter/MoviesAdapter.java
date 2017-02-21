package net.sgoliver.android.navigationdrawer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.sgoliver.android.navigationdrawer.model.Movie;
import net.sgoliver.android.navigationdrawer.R;
import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movie> {
    LinearLayout moviesLayout;
    TextView movieTitle;
    TextView data;
    TextView movieDescription;
    TextView rating;
    public List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.list_item_movie, movies);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_item_movie, null);

        TextView movieTitle = (TextView)item.findViewById(R.id.title);
        movieTitle.setText(movies.get(position).getOriginalTitle());

        TextView data= (TextView)item.findViewById(R.id.subtitle);
        data.setText(movies.get(position).getReleaseDate());

        TextView movieDescription= (TextView)item.findViewById(R.id.description);
        movieDescription.setText(movies.get(position).getOverview());

        TextView rating= (TextView)item.findViewById(R.id.rating);
        rating.setText(movies.get(position).getOverview());

        return(item);

    }



}
