package cl.laikachile.laika.activities;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.TipsAdapter;
import cl.laikachile.laika.models.Tip;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

public class TipsActivity extends BaseActivity {

    private int mIdLayout = R.layout.lk_tips_activity;

    @Override
    public void onStart() {

        createFragmentView(mIdLayout);
        super.onStart();
    }

    @Override
    public void setActivityView(View view) {

        ListView tipsListView = (ListView) view.findViewById(R.id.tips_listview);
        TipsAdapter adapter = new TipsAdapter(getApplicationContext(), R.layout.lk_tips_adapter,
                getTips(getApplicationContext()));

        tipsListView.setAdapter(adapter);

    }

    private List<Tip> getTips(Context context) {

        //FIXME hacer

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
