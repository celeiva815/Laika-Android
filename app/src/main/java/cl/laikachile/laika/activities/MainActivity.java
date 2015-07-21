package cl.laikachile.laika.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ToActivityOnCLickListener;
import cl.laikachile.laika.listeners.ToAdoptActivityOnClickListener;
import cl.laikachile.laika.listeners.ToMyDogOnCLickListener;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;


public class MainActivity extends BaseActivity {

    private int mIdLayout = R.layout.lk_main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Do.showLongToast("Bienvenido " + PrefsManager.getUserName(this.getApplicationContext()) +
                ", ¿Cómo están tus perritos?", this.getApplicationContext());
    }

    @Override
    public void onStart() {

        setTitle(mTitle);
        createFragmentView(mIdLayout);

        boolean firstboot = PrefsManager.isFirstBoot(getApplicationContext());

        if (firstboot) {
            PrefsManager.finishFirstBoot(getApplicationContext());
        }

        super.onStart();
    }

    @Override
    public void setActivityView(View view) {

        ImageView adoptImageView = (ImageView) view.findViewById(R.id.gender_new_dog_register_textview);
        ImageView myDogImageView = (ImageView) view.findViewById(R.id.my_dog_main_imageview);
        ImageView newsImageView = (ImageView) view.findViewById(R.id.news_main_imageview);
        ImageView tipsImageView = (ImageView) view.findViewById(R.id.tips_main_imageview);
        ImageView eventsImageView = (ImageView) view.findViewById(R.id.events_main_imageview);
        ImageView storiesImageView = (ImageView) view.findViewById(R.id.stories_main_imageview);

        myDogImageView.setOnClickListener(new ToMyDogOnCLickListener());
        adoptImageView.setOnClickListener(new ToAdoptActivityOnClickListener());
        newsImageView.setOnClickListener(new ToActivityOnCLickListener(PublicationsActivity.class));
        tipsImageView.setOnClickListener(new ToActivityOnCLickListener(TipsActivity.class));
        eventsImageView.setOnClickListener(new ToActivityOnCLickListener(EventsActivity.class));
        storiesImageView.setOnClickListener(new ToActivityOnCLickListener(StoriesActivity.class));

    }
}