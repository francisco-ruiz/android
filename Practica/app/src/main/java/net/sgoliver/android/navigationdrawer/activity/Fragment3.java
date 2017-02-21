package net.sgoliver.android.navigationdrawer.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
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
import net.sgoliver.android.navigationdrawer.model.TV;
import net.sgoliver.android.navigationdrawer.model.TVResponse;
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

public class Fragment3 extends Fragment {

    public Fragment3(Context context) {
        this.context = context;
        // Required empty public constructor
    }

    private ArrayList<TV> series;
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
        Call<TVResponse> call = apiService.getTopRatedTV(API_KEY, "es-ES");

        //// CUIDADO//////ONCLICK///////////////////////////
        listaEstrenos.findViewById(R.id.ListaEstrenos);
        listaEstrenos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptador, View contenido1, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragmento = new FragmentTV(context, (TV)adaptador.getItemAtPosition(position));
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmento).commit();
            }
        });
        /////////////////////////////////////////////

        call.enqueue(new Callback<TVResponse>(){
            @Override
            public void onResponse(Call<TVResponse>call, Response<TVResponse> response) {
                series = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + series.size());
                listaEstrenos.setAdapter(new AdaptadorTV(context, series));
            }

            @Override
            public void onFailure(Call<TVResponse>call, Throwable t) {
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

    class AdaptadorTV extends ArrayAdapter<TV>{

        AdaptadorTV(Context context, ArrayList<TV> movies) {
            super(context, 0, movies);
            //this.context = context.getActivity();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //LayoutInflater inflater = LayoutInflater.from(getContext());
            TV serie = getItem(position);
                /*LayoutInflater inflater = context.getLayoutInflater();
                View item = inflater.inflate(R.layout.list_item_movie, null);*/
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_top_movie,parent,false);

            TextView movieTitle = (TextView)convertView.findViewById(R.id.title);
            movieTitle.setText(serie.getName()+String.valueOf(" ("+serie.getVoteAverage())+") ");

            TextView data = (TextView)convertView.findViewById(R.id.subtitle);
            if (!serie.getName().equals(serie.getOriginalTitle())){
                data.setText("("+serie.getOriginalTitle()+") "+(serie.getOriginCountry())+": "+serie.getFirstAirDate());
            }else{
                data.setText(serie.getOriginCountry()+": "+serie.getFirstAirDate());
            }

            TextView movieDescription = (TextView)convertView.findViewById(R.id.description);
            movieDescription.setText(serie.getOverview());

            ImageView poster = (ImageView)convertView.findViewById(R.id.posterMovie);
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + series.get(position).getPosterPath()).into(poster);

            return convertView;
        }
    }
}
