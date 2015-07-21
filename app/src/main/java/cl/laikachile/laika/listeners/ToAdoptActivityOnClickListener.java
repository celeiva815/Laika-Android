package cl.laikachile.laika.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cl.laikachile.laika.activities.AdoptDogFormActivity;
import cl.laikachile.laika.activities.AdoptDogUserFormActivity;
import cl.laikachile.laika.models.AdoptDogForm;
import cl.laikachile.laika.models.Country;
import cl.laikachile.laika.responses.AdoptDogUserFormResponse;
import cl.laikachile.laika.utils.Do;

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
