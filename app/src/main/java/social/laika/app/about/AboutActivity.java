package social.laika.app.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.psdev.licensesdialog.LicensesDialog;
import social.laika.app.BuildConfig;
import social.laika.app.R;
import social.laika.app.utils.BaseSwipeBackActivity;
import social.laika.app.utils.DividerItemDecoration;
import social.laika.app.utils.ItemClickSupport;
import social.laika.app.utils.WebActivity;

public class AboutActivity extends BaseSwipeBackActivity {
    private static final int RES_TITLE = R.string.title_activity_about;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar();

        intiViews();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLicences() {
        LicensesDialog.Builder builder = new LicensesDialog.Builder(this);
        builder.setTitle(R.string.dialog_licenses_title);
        builder.setNotices(R.raw.noticies);
        builder.build().show();
    }

    private void intiViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // populate view
        final List<AboutItem> itemList = new ArrayList<>();
        itemList.add(new AboutItem(getString(R.string.about_item_version_title), BuildConfig.VERSION_NAME, AboutItemType.VERSION));
        itemList.add(new AboutItem(getString(R.string.about_item_licenses_title), getString(R.string.about_item_licences_subtitle), AboutItemType.LICENSES));
        itemList.add(new AboutItem(getString(R.string.about_item_policies_title), AboutItemType.POLICIES));
        itemList.add(new AboutItem(getString(R.string.about_item_terms_title), AboutItemType.TERMS));
        AboutAdapter adapter = new AboutAdapter(itemList);
        recyclerView.setAdapter(adapter);


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // Logger.d("Click on #" + position + " item");
                switch (itemList.get(position).getType()) {

                    case VERSION:
                        Toast.makeText(AboutActivity.this, "VERSION " + BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
                        break;

                    case LICENSES:
                        showLicences();
                        break;

                    case POLICIES:
                        Intent intentPrivacy = new Intent(AboutActivity.this, WebActivity.class);
                        intentPrivacy.putExtra(WebActivity.ARG_WEB_TITLE, getString(R.string.privacy_title));
                        intentPrivacy.putExtra(WebActivity.ARG_WEB_URL, getString(R.string.privacy_url));
                        startActivity(intentPrivacy);
                        break;

                    case TERMS:
                        Toast.makeText(AboutActivity.this, "Próximamente", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(RES_TITLE);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
