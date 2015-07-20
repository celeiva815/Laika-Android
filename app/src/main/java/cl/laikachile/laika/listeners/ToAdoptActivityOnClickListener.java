package cl.laikachile.laika.listeners;

import android.content.Context;
import android.view.View;

import cl.laikachile.laika.models.Country;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 20-07-15.
 */
public class ToAdoptActivityOnClickListener extends ToActivityOnCLickListener {

    public ToAdoptActivityOnClickListener(Class mClass) {
        super(mClass);
    }

    @Override
    public void onClick(View v) {

        Context context = v.getContext();
        String iso = Do.getCountryIso(context);

        if (Country.existIso(iso)) {

            Do.showLongToast("¡Eureka! Puedes adoptar perritos en tu país", context);
            super.onClick(v);

        } else {

            Do.showLongToast("¡Lo sentimos! No puedes adoptar perritos en tu país", context);
        }


    }
}
