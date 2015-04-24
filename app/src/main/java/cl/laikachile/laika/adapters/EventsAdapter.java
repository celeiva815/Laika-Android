package cl.laikachile.laika.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.WebActivity;
import cl.laikachile.laika.models.Event;

public class EventsAdapter extends ArrayAdapter<Event> {

    private int mIdLayout = R.layout.lk_events_adapter;
    private Context context;
    private List<Event> mEvents;

    public EventsAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mEvents = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Event event = mEvents.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        ImageView photoImageView = (ImageView) view.findViewById(R.id.photo_events_imageview);
        TextView nameTextView = (TextView) view.findViewById(R.id.name_events_textview);
        TextView sponsorTextView = (TextView) view.findViewById(R.id.sponsor_events_textview);
        TextView locationTextView = (TextView) view.findViewById(R.id.location_events_textview);
        TextView dateTextView = (TextView) view.findViewById(R.id.date_events_textview);
        TextView timeTextView = (TextView) view.findViewById(R.id.time_events_textview);
        TextView announceTextView = (TextView) view.findViewById(R.id.announce_events_textview);

        nameTextView.setText(event.mName);
        nameTextView.setSelected(true);
        sponsorTextView.setText(event.mSponsorName);
        sponsorTextView.setSelected(true);
        locationTextView.setText(event.mLocation);
        dateTextView.setText(event.getDate());
        dateTextView.setSelected(true);
        timeTextView.setText(event.getTime());
        photoImageView.setImageResource(event.mURLImage);

        if (event.mIsAnnounce) {
            announceTextView.setVisibility(View.VISIBLE);
            sponsorTextView.setTextColor(view.getContext().getResources().getColor(R.color.laika_red));

        } else {
            announceTextView.setVisibility(View.INVISIBLE);
        }

        final int pos = position;
        view.setClickable(true);
        view.setFocusable(true);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL, mEvents.get(pos).mUrlEvent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;

    }
}
