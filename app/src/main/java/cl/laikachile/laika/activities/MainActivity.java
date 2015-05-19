package cl.laikachile.laika.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ToActivityOnCLickListener;
import cl.laikachile.laika.listeners.ToMyDogOnCLickListener;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;


public class MainActivity extends BaseActivity {

    private int mIdLayout = R.layout.lk_main_activity;

    @Override
    public void onStart() {
		/* FIXME
		SessionManager session = new SessionManager(getApplicationContext());
		if (session.isLoggedIn()) {

			Intent i = new Intent(this, TeacherMainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			finish();

		} else {

			createFragmentView(mIdLayout);
		}
		*/
        setTitle(mTitle);
        createFragmentView(mIdLayout);

        boolean firstboot = PrefsManager.isFirstBoot(getApplicationContext());

        if (firstboot) {
            // 1) Launch the authentication activity
            Do.showToast("Bienvenido " + PrefsManager.getUserName(this.getApplicationContext()) +
                    ", ¿Cómo están tus perritos?", this.getApplicationContext(), Toast.LENGTH_LONG);

            // 2) Then save the state
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
        adoptImageView.setOnClickListener(new ToActivityOnCLickListener(AdoptDogFormActivity.class));
        newsImageView.setOnClickListener(new ToActivityOnCLickListener(PublicationsActivity.class));
        tipsImageView.setOnClickListener(new ToActivityOnCLickListener(TipsActivity.class));
        eventsImageView.setOnClickListener(new ToActivityOnCLickListener(EventsActivity.class));
        storiesImageView.setOnClickListener(new ToActivityOnCLickListener(StoriesActivity.class));

    }
}