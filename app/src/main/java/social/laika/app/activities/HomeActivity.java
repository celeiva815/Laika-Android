package social.laika.app.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import social.laika.app.R;
import social.laika.app.utils.BaseActivity;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected static final String OUT_STATE_NAV_ITEM_ID = "SavedNavigationItemId";
    protected int navItemId = R.id.nav_panel;

    private CircleImageView picture;
    private TextView name;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_activity_home));
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = getLayoutInflater().inflate(R.layout.nav_header_home, null);
        navigationView.addHeaderView(header);

        navigationView.setNavigationItemSelectedListener(this);

        picture = (CircleImageView) header.findViewById(R.id.nav_header_picture);
        Button btnEditProfile = (Button) header.findViewById(R.id.nav_header_btn_edit_profile);
        name = (TextView) header.findViewById(R.id.nav_header_name);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
                // Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                // startActivity(intentProfile);
                Snackbar.make(navigationView, "Do something", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_panel:
                return true;
            case R.id.nav_add_pet:
                return true;
            case R.id.nav_my_postulations:
                return true;
            case R.id.nav_profile:
                return true;
            case R.id.nav_close_session:
                return true;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(OUT_STATE_NAV_ITEM_ID, navItemId);
        super.onSaveInstanceState(outState, outPersistentState);
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
}
