package cl.laikachile.laika.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.TipsAdapter;
import cl.laikachile.laika.models.Tip;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class TipsActivity extends ActionBarActivity {

    private int mIdLayout = R.layout.lk_tips_activity;

    public List<Tip> mTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void setActivityView() {

        mTips = getTips(getApplicationContext());

        ListView tipsListView = (ListView) findViewById(R.id.tips_listview);
        TipsAdapter adapter = new TipsAdapter(getApplicationContext(), R.layout.lk_tips_adapter,
                mTips);

        tipsListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private List<Tip> getTips(Context context) {

        //FIXME hacer la conexión con la API

        List<Tip> tipList = new ArrayList<>();
        Tip tip = new Tip(Tip.ID++, "Pach News", Do.getRString(context, R.string.title_tip_activity),
                Do.getRString(context,R.string.body_tip_activity), R.drawable.lk_news_picture_three,
                Tag.TIP_HYGIENE);

        Tip tip2 = new Tip(Tip.ID++, "Pet Vet", "Parovirus: 5 cosas que debes saber", "El parovirus" +
                "es una enfermedad grave que necesita atención médica urgente, ya que sin un " +
                "tratamiento adecuado puede provocar su muerte en pocos días",
                R.drawable.lk_news_picture_two, Tag.TIP_HEALTH);

        tipList.add(tip);
        tipList.add(tip2);

        return tipList;
    }

}
