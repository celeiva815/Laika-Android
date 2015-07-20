package cl.laikachile.laika.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.WebActivity;
import cl.laikachile.laika.models.Event;
import cl.laikachile.laika.models.Publication;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Do;

public class EventsAdapter extends ArrayAdapter<Event> {

    public static final String TAG = EventsAdapter.class.getSimpleName();

    private int mIdLayout = R.layout.lk_events_adapter;
    private Context context;
    private List<Event> mEvents;
    public TextView mNameTextView;
    public TextView mSponsorTextView;
    public TextView mLocationTextView;
    public TextView mDateTextView;
    public TextView mTimeTextView;
    public TextView mAnnounceTextView;
    public ImageView mMainImageView;
    public ProgressBar mProgressBar;


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
        mMainImageView = (ImageView) view.findViewById(R.id.photo_events_imageview);
        mNameTextView = (TextView) view.findViewById(R.id.name_events_textview);
        mSponsorTextView = (TextView) view.findViewById(R.id.sponsor_events_textview);
        mLocationTextView = (TextView) view.findViewById(R.id.location_events_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_events_textview);
        mTimeTextView = (TextView) view.findViewById(R.id.time_events_textview);
        mAnnounceTextView = (TextView) view.findViewById(R.id.announce_events_textview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);

        mNameTextView.setText(Integer.toString(event.mEventId) + " - " + event.mName); //FIXME
        mNameTextView.setSelected(true);
        mSponsorTextView.setText(event.mSponsorName);
        mSponsorTextView.setSelected(true);
        mLocationTextView.setText(Integer.toString(event.mCityId));
        mDateTextView.setText(event.getDate());
        mDateTextView.setSelected(true);
        mTimeTextView.setText(event.getTime());

        if (!Do.isNullOrEmpty(event.mUrlImage) && mMainImageView.getDrawable() == null) {

            RequestManager.requestImage(event.mUrlImage, mProgressBar, mMainImageView, context);

        } else {

            // mMainImageView.setImageResource(R.drawable.event_1); TODO definir una imagen predeterminada
        }

        if (event.mIsPaid) {
            mAnnounceTextView.setVisibility(View.VISIBLE);
            mSponsorTextView.setTextColor(view.getContext().getResources().getColor(R.color.laika_red));

        } else {
            mAnnounceTextView.setVisibility(View.INVISIBLE);
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
