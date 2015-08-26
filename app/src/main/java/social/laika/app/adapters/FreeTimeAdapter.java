package social.laika.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.models.indexes.FreeTime;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class FreeTimeAdapter extends BaseAdapter {

    public List<FreeTime> mFreeTimes;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public FreeTimeAdapter(Context context, int resource, int textViewResourceId, List<FreeTime> objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mFreeTimes = objects;

    }

    public int getPosition(int homeType) {

        for (FreeTime freeTime : mFreeTimes) {
            if (freeTime.mIndex == homeType) {
                return mFreeTimes.indexOf(freeTime);
            }
        }

        return 0;
    }

    @Override
    public int getCount() {
        return mFreeTimes.size();
    }

    @Override
    public Object getItem(int position) {
        return mFreeTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mFreeTimes.get(position).mIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mFreeTimes.get(position).mName);
        textView.setTextColor(mContext.getResources().getColor(R.color.light_black_font));
        textView.setTextColor(mContext.getResources().getColor(R.color.light_black_font));
        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mIdLayout, null);
        }

        ((TextView) convertView).setText(mFreeTimes.get(position).mName);
        return convertView;


    }

}
