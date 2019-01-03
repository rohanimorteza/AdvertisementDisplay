package com.example.mortrza.myadvertismentdispaly;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mortrza.myadvertismentdispaly.ADV.AgahiAdapter;
import com.example.mortrza.myadvertismentdispaly.ADV.ListFragment;
import com.example.mortrza.myadvertismentdispaly.SETTING.Setting;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Dialog dialog;
    public static int DefaultTab=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("آگهی ها");

        if (SDK_INT >17){
            getWindow().peekDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

/*
        dbHandler dbh = new dbHandler(this);
        dbh.open();
        Toast.makeText(getApplicationContext(),""+dbh.Like_count(),Toast.LENGTH_SHORT).show();
        dbh.close();
*/


/*
        dbHandler dbh = new dbHandler(this);
        dbh.open();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        AgahiAdapter agahiAdapter = new AgahiAdapter(this,dbh.display());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(agahiAdapter);
        dbh.close();
*/

        dbHandler dbh = new dbHandler(this);
        dbh.open();
        DefaultTab=dbh.Cat_count()-1;
        dbh.close();

        init();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }


    private  void init(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null){
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){

        int catCount=0;
        dbHandler dbh = new dbHandler(this);
        dbh.open();
        catCount=dbh.Cat_count();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        ListFragment[] listFragments = new ListFragment[catCount];

        for( int i=0  ; i<catCount  ; i++   ){
            Bundle bundle = new Bundle();
            int m= i+1;
            bundle.putString("FRG",m+"");
            listFragments[i] = new ListFragment();
            listFragments[i].setArguments(bundle);

            fragmentAdapter.addFragment(listFragments[i],dbh.get_Cat_Name(m));

        }

        dbh.close();

        /*ListFragment car = new ListFragment();
        Bundle bundlecar = new Bundle();
        bundlecar.putString("FRG","CAR");
        car.setArguments(bundlecar);
        ListFragment home = new ListFragment();
        Bundle bundleHome = new Bundle();
        bundleHome.putString("FRG","HOME");
        home.setArguments(bundleHome);
        fragmentAdapter.addFragment(car , "خودرو");
        fragmentAdapter.addFragment(home , "املاک");*/

        viewPager.setAdapter(fragmentAdapter);
        viewPager.computeScroll();

        viewPager.setCurrentItem(DefaultTab);

    }

    static class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            fragmentList.add(fragment);
            titles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public void search(){
        View alertLayout = LayoutInflater.from(this).inflate(R.layout.alert_search,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        refreshSearch(alertLayout);


        //////////////////
        dialog = alert.create();
        dialog.show();
    }
    private void refreshSearch(View m){



        final RecyclerView recyclerView = (RecyclerView) m.findViewById(R.id.rec_alert_search);
        final EditText edt = (EditText) m.findViewById(R.id.edt_alert_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        final dbHandler dbh = new dbHandler(MainActivity.this);
        dbh.open();
        if(edt.getText().toString().equals("")){
            AgahiAdapter agahiAdapter = new AgahiAdapter(MainActivity.this,dbh.display());
            dbh.close();
            recyclerView.setAdapter(agahiAdapter);
        }

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbh.open();

                if(dbh.isDisplay(edt.getText().toString())){
                    //recyclerView.setVisibility(View.VISIBLE);

                    AgahiAdapter agahiAdapter = new AgahiAdapter(MainActivity.this,dbh.display(edt.getText().toString()));
                    recyclerView.setAdapter(agahiAdapter);
                }else {
                    //recyclerView.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(null);
                }
                dbh.close();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_search) {
                search();
            }else if (id == R.id.action_bookmark) {
                //Toast.makeText(getApplicationContext(),"Bookmark",Toast.LENGTH_LONG).show();
                DefaultTab=0;
                init();
            }

            return super.onOptionsItemSelected(item);
        }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_like_list) {
            // Handle the camera action
        } else if (id == R.id.nav_manage) {

            Setting st = new Setting(MainActivity.this);
            st.Setting();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
