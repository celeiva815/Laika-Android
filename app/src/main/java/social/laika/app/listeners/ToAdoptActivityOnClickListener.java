package social.laika.app.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import social.laika.app.activities.AdoptDogFormActivity;
import social.laika.app.activities.AdoptDogUserFormActivity;
import social.laika.app.models.AdoptDogForm;
import social.laika.app.models.Country;
import social.laika.app.responses.AdoptDogUserFormResponse;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 20-07-15.
 */
public class ToAdoptActivityOnClickListener implements View.OnClickListener {

    public static final String KEY_NEXT_ACTIVITY = AdoptDogUserFormActivity.KEY_NEXT_ACTIVITY;
    public static final int NEXT_ADOPT_DOG = AdoptDogUserFormResponse.NEXT_ADOPT_DOG;


    public ToAdoptActivityOnClickListener() {
    }

    @Override
    public void onClick(View v) {

        Context context = v.getContext();
        String iso = Do.getCountryIso(context);

        if (Country.existIso(iso)) {

            Do.showLongToast("¡Eureka! Puedes adoptar perritos en tu país", context);
            Intent intent;

            if (!AdoptDogForm.hasDogForm()) {

                intent = new Intent(v.getContext(), AdoptDogUserFormActivity.class);
                intent.putExtra(KEY_NEXT_ACTIVITY, NEXT_ADOPT_DOG);

            } else {

                intent = new Intent(v.getContext(), AdoptDogFormActivity.class);
            }

            context.startActivity(intent);

        } else {

            Do.showLongToast("¡Lo sentimos! No puedes adoptar perritos en tu país", context);
        }


    }
}
