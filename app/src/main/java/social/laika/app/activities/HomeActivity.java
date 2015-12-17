package social.laika.app.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.newrelic.agent.android.NewRelic;
import com.orhanobut.logger.Logger;

import de.hdodenhof.circleimageview.CircleImageView;
import social.laika.app.R;
import social.laika.app.about.AboutActivity;
import social.laika.app.fragments.PanelFragment;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.AdoptDogForm;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.City;
import social.laika.app.models.Country;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.models.OwnerDog;
import social.laika.app.models.Personality;
import social.laika.app.models.Photo;
import social.laika.app.models.Region;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.models.VetVisit;
import social.laika.app.models.publications.Event;
import social.laika.app.models.publications.Publication;
import social.laika.app.models.publications.Story;
import social.laika.app.models.publications.Tip;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.gcm.LaikaRegistrationIntentService;
import social.laika.app.network.requests.TokenRequest;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.responses.AdoptDogUserFormResponse;
import social.laika.app.responses.LoginHandler;
import social.laika.app.responses.PostulatedDogsResponse;
import social.laika.app.utils.BaseActivity;
import social.laika.app.utils.Do;
import social.laika.app.utils.Flurry;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;
import social.laika.app.utils.views.LaikaTypeFaceSpan;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, Requestable {
    protected static final String OUT_STATE_NAV_ITEM_ID = "SavedNavigationItemId";
    protected static final String TAG_FRAGMENT_PANEL = "TagFragmentPanel";
    protected static final int NAV_DRAWER_ICON_ALPHA = 150; // 0 (transparent) to 255
    protected int navItemId = R.id.nav_adopt;

    public static final String GCM_NOTIFICATION = "gcm_notification";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static boolean isActive = false;
    protected BroadcastReceiver mRegistrationBroadcastReceiver;
    protected ProgressDialog mProgressDialog;

    private CircleImageView picture;
    private TextView name;
    private DrawerLayout drawer;

    private BroadcastReceiver mGCMBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            syncFirstInformation(getApplicationContext());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FacebookSdk.sdkInitialize(getApplicationContext());
        startNewRelic();
        registerGCM();
        SyncUtils.createSyncAccount(getApplicationContext());
        Flurry.setConfigurations(this);
        FlurryAgent.onStartSession(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        updateMenuStyle(navigationView.getMenu());
        View header = getLayoutInflater().inflate(R.layout.nav_header_home, null);
        navigationView.addHeaderView(header);

        navigationView.setNavigationItemSelectedListener(this);

        picture = (CircleImageView) header.findViewById(R.id.nav_header_picture);
        name = (TextView) header.findViewById(R.id.nav_header_name);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new PanelFragment(), TAG_FRAGMENT_PANEL);
        fragmentTransaction.commit();

        populateNavHeader();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_adopt:
                adopt();
                return true;

            case R.id.nav_profile:
                Intent intentProfile = new Intent(HomeActivity.this, UserProfileActivity.class);
                intentProfile.putExtra(UserProfileActivity.KEY_OWNER_ID, PrefsManager.getUserId(HomeActivity.this));
                intentProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentProfile);
                return true;

            case R.id.nav_favorites:
                Toast.makeText(getBaseContext(), getString(R.string.feedback_option_unavailable), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nav_my_postulations:
                openPostulatedDogs();
                return true;

            case R.id.nav_sponsors:
                return true;

            case R.id.nav_close_session:
                closeSession();
                return true;

            case R.id.nav_about_us:
                Intent intentAbout = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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

    private void populateNavHeader() {
        int ownerId = PrefsManager.getUserId(getBaseContext());

        new AsyncTask<Integer, Void, Owner>() {
            @Override
            protected Owner doInBackground(Integer... params) {
                Owner owner = Owner.getSingleOwner(params[0]);
                return owner;
            }

            @Override
            protected void onPostExecute(Owner owner) {
                name.setText(owner.getFullName());
                owner.requestUserImage(getApplicationContext(), picture, Tag.IMAGE_MEDIUM);
                super.onPostExecute(owner);
            }
        }.execute(ownerId);
    }

    private void updateMenuStyle(Menu menu) {
        if (menu == null) {
            return;
        }

        for (int position = 0; position < menu.size(); position++) {
            MenuItem menuItem = menu.getItem(position);
            menuItem.getIcon().setAlpha(NAV_DRAWER_ICON_ALPHA);
            SubMenu subMenu = menuItem.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int positionSubMenu = 0; positionSubMenu < subMenu.size(); positionSubMenu++) {
                    MenuItem subMenuItem = subMenu.getItem(positionSubMenu);
                    subMenuItem.getIcon().setAlpha(NAV_DRAWER_ICON_ALPHA);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(menuItem);
        }

    }

    protected void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new LaikaTypeFaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    protected void syncFirstInformation(Context context) {
        mProgressDialog = ProgressDialog.show(HomeActivity.this, getString(R.string.feedback_wait_a_minute), getString(R.string.feedback_sync_dogs_info));
        LoginHandler.requestFirstInformation(context, this);
    }

    private void startNewRelic() {
        Log.i("new_relic", "TOKEN");
        NewRelic.withApplicationToken("AA012760242cc465209e0b156e36370869c1706bbc").start(this.getApplication());
    }

    protected void registerGCM() {

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean sentToken = PrefsManager.getSentTokenToServer(getApplicationContext());
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, LaikaRegistrationIntentService.class);
            startService(intent);
        }
    }

    protected boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Logger.d("This device is not supported");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean firstBoot = PrefsManager.isFirstBoot(getApplicationContext());
        if (firstBoot) {
            PrefsManager.finishFirstBoot(getApplicationContext());
        }
        isActive = true;
        Flurry.logTimedEvent(Flurry.SESSION_TIME);
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
        Flurry.endTimedEvent(Flurry.SESSION_TIME);
    }

    @Override
    protected void onDestroy() {
        FlurryAgent.onEndSession(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(PrefsManager.GCM_REGISTRATION_COMPLETE));
        getApplicationContext().registerReceiver(mGCMBroadcastReceiver, new IntentFilter(GCM_NOTIFICATION));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Context context = getApplicationContext();
        if (PrefsManager.needsSync(context)) {
            syncFirstInformation(getApplicationContext());
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        getApplicationContext().unregisterReceiver(mGCMBroadcastReceiver);
        super.onPause();
    }

    private void openPostulatedDogs() {
        Context context = getApplicationContext();
        if (Do.isNetworkAvailable(context)) {
            requestPostulations();
        } else {
            Do.changeActivity(context, PostulatedDogsFragmentActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
            Do.showLongToast(getString(R.string.feedback_postulations_saved), context);
        }
    }

    private void requestPostulations() {

        ProgressDialog dialog = ProgressDialog.show(HomeActivity.this, "Espere un momento", "Estamos actualizando el estado de tus postulaciones...");
        PostulatedDogsResponse response = new PostulatedDogsResponse(this, dialog);
        Request adoptDogRequest = Api.getRequest(null, Api.ADDRESS_USER_POSTULATIONS, response, response, PrefsManager.getUserToken(getApplicationContext()));
        VolleyManager.getInstance(getApplicationContext()).addToRequestQueue(adoptDogRequest, HomeActivity.class.getSimpleName());

    }

    private void closeSession() {
        clearDataBase();
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }
        unsubscribeToken();
        Do.changeActivity(getApplicationContext(), TutorialActivity.class, this, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void clearDataBase() {
        Context context = getApplicationContext();
        PrefsManager.clearPrefs(getApplicationContext());
        Dog.deleteAll();
        Owner.deleteAll();
        OwnerDog.deleteAll();
        UserAdoptDog.deleteAll();
        Photo.deleteAll();
        VetVisit.deleteAll();
        AdoptDogForm.deleteAll();
        CalendarReminder.deleteAll(context);
        AlarmReminder.deleteAll(context);
        Event.deleteAll();
        Publication.deleteAll();
        Tip.deleteAll();
        Story.deleteAll();
        Personality.deleteAll();
        City.deleteAll();
        Region.deleteAll();
        Country.deleteAll();
    }

    private void unsubscribeToken() {
        String token = PrefsManager.getGCMToken(this);
        TokenRequest tokenRequest = new TokenRequest(token, this, false);
        tokenRequest.request();
    }

    @Override
    public void request() {

    }

    @Override
    public void onSuccess() {
        mProgressDialog.dismiss();
        Do.showShortToast("Laika ha sido sincronizada con éxito", this);
    }

    @Override
    public void onFailure() {
        mProgressDialog.dismiss();
    }

    private void adopt() {
        String iso = Do.getCountryIso(HomeActivity.this);
        if (Country.existIso(iso)) {
            Intent intent;
            Owner owner = PrefsManager.getLoggedOwner(HomeActivity.this);
            AdoptDogForm adopt = AdoptDogForm.getUserAdoptDogForm(owner.mOwnerId);

            if (adopt == null || !owner.hasCity()) {
                intent = new Intent(HomeActivity.this, AdoptDogUserFormActivity.class);
                intent.putExtra(AdoptDogUserFormActivity.KEY_NEXT_ACTIVITY, AdoptDogUserFormResponse.NEXT_ADOPT_DOG);
            } else {
                intent = new Intent(HomeActivity.this, AdoptDogFormActivity.class);
            }

            startActivity(intent);
        } else {
            Do.showLongToast("¡Lo sentimos! Por ahora no puedes adoptar perritos en tu país", HomeActivity.this);
        }
    }
}
