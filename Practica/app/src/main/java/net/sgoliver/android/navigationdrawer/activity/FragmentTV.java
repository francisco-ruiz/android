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
import net.sgoliver.android.navigationdrawer.model.TV;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentTV extends Fragment{
    Context context= null;
    TV serie;

    public FragmentTV(Context context, TV serie) {
        this.context = context;
        this.serie = serie;
        Toast.makeText(context,"Detalles de "+serie.getName(),Toast.LENGTH_SHORT).show();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contenidoTV = inflater.inflate(R.layout.detail_item_tv,container,false);
        TextView movieTitle = (TextView) contenidoTV.findViewById(R.id.title);
        movieTitle.setText(serie.getName());

        TextView data = (TextView) contenidoTV.findViewById(R.id.subtitle);
        data.setText("Estreno: "+serie.getFirstAirDate());

        TextView movieDescription = (TextView) contenidoTV.findViewById(R.id.description);
        movieDescription.setText(serie.getOverview());

        TextView rating = (TextView) contenidoTV.findViewById(R.id.rating);
        rating.setText("Valoración: "+String.valueOf(serie.getVoteAverage()));

        TextView tituloOriginal = (TextView) contenidoTV.findViewById(R.id.tituloOriginal);
        tituloOriginal.setText("Título original: "+serie.getOriginalTitle());

        ImageView imagen = (ImageView) contenidoTV.findViewById(R.id.imagenTV);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + serie.getBackdropPath()).into(imagen);

        return contenidoTV;
    }
}
