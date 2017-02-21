package net.sgoliver.android.navigationdrawer.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.sgoliver.android.navigationdrawer.R;
import net.sgoliver.android.navigationdrawer.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentMovie extends Fragment{
    Context context= null;
    Movie film;

    public FragmentMovie(Context context, Movie film) {
        this.context = context;
        this.film = film;
        Toast.makeText(context,"Detalles de "+film.getTitle(),Toast.LENGTH_SHORT).show();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contenidoMovie = inflater.inflate(R.layout.detail_item_movie,container,false);
        TextView movieTitle = (TextView) contenidoMovie.findViewById(R.id.title);
        movieTitle.setText(film.getTitle());

        TextView data = (TextView) contenidoMovie.findViewById(R.id.subtitle);
        data.setText("Estreno: "+film.getReleaseDate());

        TextView movieDescription = (TextView) contenidoMovie.findViewById(R.id.description);
        movieDescription.setText(film.getOverview());

        TextView rating = (TextView) contenidoMovie.findViewById(R.id.rating);
        rating.setText("Valoración: "+String.valueOf(film.getVoteAverage()));

        TextView tituloOriginal = (TextView) contenidoMovie.findViewById(R.id.tituloOriginal);
        tituloOriginal.setText("Título original: "+film.getOriginalTitle());

        ImageView poster = (ImageView) contenidoMovie.findViewById(R.id.posterMovie);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + film.getPosterPath()).into(poster);

        ImageView imagen = (ImageView) contenidoMovie.findViewById(R.id.imagenMovie);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + film.getBackdropPath()).into(imagen);

        return contenidoMovie;
    }
}
