package com.example.mortrza.myadvertismentdispaly;

import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mortrza.myadvertismentdispaly.ADV.Agahi;
import com.example.mortrza.myadvertismentdispaly.R;
import com.example.mortrza.myadvertismentdispaly.SETTING.SettingData;

import static android.os.Build.VERSION.SDK_INT;

public class DetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    dbHandler dbh;
    Bundle b;
    Agahi agahi;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        if (SDK_INT >17){
            getWindow().peekDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        final Typeface tf_yekan = Typeface.createFromAsset(getApplicationContext().getAssets(),"byekan.ttf");
        final Typeface tf_nazanin = Typeface.createFromAsset(getApplicationContext().getAssets(),"nazanin.ttf");


        fab = (FloatingActionButton) findViewById(R.id.fab);
        b = getIntent().getExtras();
        //Toast.makeText(getApplicationContext(),b.get("ID")+"",Toast.LENGTH_SHORT).show();

        agahi = new Agahi();


        show_Like();

        ImageView img = (ImageView) findViewById(R.id.img_detail);
        byte[] pic = agahi.getAx();
        img.setImageBitmap(BitmapFactory.decodeByteArray(pic,0,pic.length));

        TextView title = (TextView) findViewById(R.id.txt_detail_title);
        TextView des = (TextView) findViewById(R.id.txt_detail_des);

        SettingData sd = new SettingData(this);

        des.setTextSize(sd.GetTextSize());
        des.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.0f,  getResources().getDisplayMetrics()),sd.GetTextSpaceLine());
        title.setText(agahi.getTitle());

        des.setText(agahi.getDescription());

        if(sd.GetFONT()==1){
            des.setTypeface(tf_yekan);
            title.setTypeface(tf_yekan);
        }else if(sd.GetFONT()==2){
            des.setTypeface(tf_nazanin);
            title.setTypeface(tf_nazanin);
        }




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(agahi.getLike()==1){
                    dbh.change_like(0,b.getString("ID"));
                    show_Like();
                    //fab.setImageResource(R.mipmap.like_empty);
                }else if(agahi.getLike()==0){
                    //like=1;
                    dbh.change_like(1,b.getString("ID"));
                    show_Like();
                    //fab.setImageResource(R.mipmap.like_selected);
                }
            }
        });

        dbh.close();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void show_Like(){

        dbh = new dbHandler(this);
        dbh.open();
        agahi = dbh.display2(b.get("ID")+"");
        dbh.close();

        if(agahi.getLike()==1){
            //Toast.makeText(getApplicationContext(),""+agahi.getLike(),Toast.LENGTH_LONG).show();
            fab.setImageResource(R.mipmap.ico_bookmark_por);
        }else if(agahi.getLike()==0) {
            //Toast.makeText(getApplicationContext(),""+agahi.getLike(),Toast.LENGTH_LONG).show();
            fab.setImageResource(R.mipmap.ico_bookmark);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
