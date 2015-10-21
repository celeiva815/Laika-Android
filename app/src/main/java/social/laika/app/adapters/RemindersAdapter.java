package social.laika.app.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.models.Reminder;
import social.laika.app.utils.Do;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class RemindersAdapter extends ArrayAdapter<Reminder> {

    private Context mContext;
    private int mIdLayout;
    private List<Reminder> mReminders;

    public RemindersAdapter(Context context, int resource, List<Reminder> objects) {
        super(context, resource, objects);

        mIdLayout = resource;
        mContext = context;
        mReminders = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Reminder reminder = mReminders.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        TextView titleTextView = (TextView) view.findViewById(R.id.title_history_my_dog_textview);
        TextView detailTextView = (TextView) view.findViewById(R.id.detail_history_my_dog_textview);
        TextView dateTextView = (TextView) view.findViewById(R.id.date_history_my_dog_textview);
        ImageView typeImageView = (ImageView) view.findViewById(R.id.category_history_my_dog_imageview);
        Switch activateSwitch = (Switch) view.findViewById(R.id.activate_history_my_dog_switch);

        titleTextView.setText(reminder.mTitle);
        detailTextView.setText(reminder.mDetail);
        dateTextView.setText(reminder.getDateTime(view.getContext()));

        int alarmStatus = reminder.mReminder.checkStatusAlarm(mContext);
        activateSwitch.setChecked(alarmStatus == Tag.STATUS_ACTIVATED);

        activateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Resources res = mContext.getResources();
                String s = isChecked ? res.getString(R.string.reminder_activated) : res.
                        getString(R.string.reminder_desactivated);
                Do.showShortToast(s, mContext);

                if (isChecked) {
                    reminder.mReminder.setAlarm(mContext);
                } else {
                    reminder.mReminder.cancelAlarm(mContext);
                }
            }
        });

        final int imageId = reminder.mCategory;

        switch (imageId) {
            case Tag.CATEGORY_FOOD:
                typeImageView.setImageResource(R.drawable.laika_food_grey);
                break;
            case Tag.CATEGORY_POO:
                typeImageView.setImageResource(R.drawable.laika_poop_grey);
                break;
            case Tag.CATEGORY_WALK:
                typeImageView.setImageResource(R.drawable.laika_walk_grey);
                break;
            case Tag.CATEGORY_MEDICINE:
                typeImageView.setImageResource(R.drawable.laika_pill_grey);
                break;
            case Tag.CATEGORY_VACCINE:
                typeImageView.setImageResource(R.drawable.laika_vaccine_grey);
                break;
            case Tag.CATEGORY_HYGIENE:
                typeImageView.setImageResource(R.drawable.laika_hygiene_grey);
                break;
            case Tag.CATEGORY_VET:
                typeImageView.setImageResource(R.drawable.laika_vetalarm_grey);
                break;
        }

        return view;
    }
}
