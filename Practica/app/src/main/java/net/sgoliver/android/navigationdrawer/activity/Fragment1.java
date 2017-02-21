package net.sgoliver.android.navigationdrawer.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.app.FragmentManager;
//import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.sgoliver.android.navigationdrawer.R;
import net.sgoliver.android.navigationdrawer.adapter.MoviesAdapter;
import net.sgoliver.android.navigationdrawer.model.Movie;
import net.sgoliver.android.navigationdrawer.model.MoviesResponse;
import net.sgoliver.android.navigationdrawer.rest.ApiClient;
import net.sgoliver.android.navigationdrawer.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;

import static android.R.string.no;

public class Fragment1 extends Fragment {

    public Fragment1(Context context) {
        this.context = context;
        // Required empty public constructor
    }

    private ArrayList<Movie> movies;
    @BindView(R.id.ListaEstrenos)
    protected ListView listaEstrenos;

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "46ad73f6b4a099d693d95b45308871d7";
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contenido1 = inflater.inflate(R.layout.fragment_fragment1,container,false);
        ButterKnife.bind(this,contenido1);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MoviesResponse> call = apiService.getUpcomingMovies(API_KEY, "es-ES");

        //// CUIDADO//////ONCLICK///////////////////////////
        listaEstrenos.findViewById(R.id.ListaEstrenos);
        listaEstrenos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptador, View contenido1, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragmento = new FragmentMovie(context, (Movie)adaptador.getItemAtPosition(position));
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmento).commit();
            }
        });
        /////////////////////////////////////////////

        call.enqueue(new Callback<MoviesResponse>(){
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                listaEstrenos.setAdapter(new AdaptadorEstrenos(context, movies));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return contenido1;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    }

    class AdaptadorEstrenos extends ArrayAdapter<Movie>{

        AdaptadorEstrenos(Context context, ArrayList<Movie> movies) {
            super(context, 0, movies);
            //this.context = context.getActivity();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //LayoutInflater inflater = LayoutInflater.from(getContext());
            Movie film = getItem(position);
            /*LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.list_item_movie, null);*/
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie,parent,false);

            TextView movieTitle = (TextView)convertView.findViewById(R.id.title);
            movieTitle.setText(film.getTitle());

            TextView data = (TextView)convertView.findViewById(R.id.subtitle);
            data.setText("Estreno: "+film.getReleaseDate());

            TextView movieDescription = (TextView)convertView.findViewById(R.id.description);
            movieDescription.setText(film.getOverview());

            TextView rating = (TextView)convertView.findViewById(R.id.rating);
            rating.setText("Valoración: "+String.valueOf(film.getVoteAverage()));

            ImageView poster = (ImageView)convertView.findViewById(R.id.posterMovie);
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + movies.get(position).getPosterPath()).into(poster);

            return convertView;
        }
    }
}
