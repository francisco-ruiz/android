package net.sgoliver.android.navigationdrawer.activity;

import android.content.res.Configuration;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.sgoliver.android.navigationdrawer.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "46ad73f6b4a099d693d95b45308871d7";

    private Toolbar toolbar;
    private ScrimInsetsFrameLayout sifl;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView ndList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Fragment fragmento = new Fragment4(getApplicationContext());
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragmento)
                .commit();


        sifl = (ScrimInsetsFrameLayout)findViewById(R.id.scrimInsetsFrameLayout);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        //Menu del Navigation Drawer
        ndList = (ListView)findViewById(R.id.navdrawerlist);

        final String[] opciones = new String[]{"Estrenos", "Próximos estrenos", "Películas más valoradas", "Series más valoradas"};

        ArrayAdapter<String> ndMenuAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_activated_1, opciones);

        ndList.setAdapter(ndMenuAdapter);

        ndList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            Fragment fragment = null;

                switch (pos) {
                    case 0:
                        fragment = new Fragment4(getApplicationContext());
                        break;
                    case 1:
                        fragment = new Fragment1(getApplicationContext());
                        break;
                    case 2:
                        fragment = new Fragment2(getApplicationContext());
                        break;
                    case 3:
                        fragment = new Fragment3(getApplicationContext());
                        break;
                }

                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();

                ndList.setItemChecked(pos, true);

                getSupportActionBar().setTitle(opciones[pos]);

                drawerLayout.closeDrawer(sifl);
            }
        });

        //Drawer Layout
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorAccentDark));

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
