package cl.laikachile.laika.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.List;

import cl.laikachile.laika.fragments.RemindersMyDogFragment;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.requests.RemindersRequest;
import cl.laikachile.laika.responses.CreateAlarmReminderResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class RemindersService extends IntentService {

    private static final String NAME = "RemindersService";
    private static final String ACTION_SYNC = "cl.laikachile.laika.network";

    public static void startSync(Context context) {
        Intent intent = new Intent(context, RemindersService.class);
        intent.setAction(ACTION_SYNC);
        context.startService(intent);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RemindersService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (Do.isNetworkAvailable(getApplicationContext())) {

            List<Dog> dogs = Dog.getDogs(Tag.DOG_OWNED);
            RemindersRequest request = new RemindersRequest();

            for (Dog dog : dogs) {

                /** FIXME ver como se puede mejorar esto, quizas enviando una fecha de la ultima
                 *  sincronización o alguna forma.
                 */

                request.requestAlarmReminders(dog, getApplicationContext());
                //request.requestCalendarReminders(dog, getApplicationContext());

            }
        }
    }

}
