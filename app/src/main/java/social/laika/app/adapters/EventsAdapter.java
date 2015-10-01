package social.laika.app.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import java.io.File;
import java.util.List;

import social.laika.app.R;
import social.laika.app.listeners.WebLinkOnClickListener;
import social.laika.app.models.publications.Event;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.PublicationImageResponse;
import social.laika.app.utils.Do;

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

        mNameTextView.setText(event.mName);
        mNameTextView.setSelected(true);
        mSponsorTextView.setText(event.mSponsorName);
        mSponsorTextView.setSelected(true);
        mLocationTextView.setText(event.mAddress);
        mDateTextView.setText(event.getDate());
        mDateTextView.setSelected(true);
        mTimeTextView.setText(event.getTime());

        if (!Do.isNullOrEmpty(event.mUriLocal)) {

            File file = new File(Uri.parse(event.mUriLocal).getPath());

            if (file.exists()) {
                mMainImageView.setImageURI(Uri.parse(event.mUriLocal));

            } else if (!Do.isNullOrEmpty(event.mUrlImage) && mMainImageView.getDrawable() == null) {

                PublicationImageResponse response = new PublicationImageResponse(context,
                        mMainImageView, event, Event.TABLE_NAME);
                Request request = Api.imageRequest(event.mUrlImage, mMainImageView, response,
                        response);
                VolleyManager.getInstance(context).addToRequestQueue(request);

            }
        } else if (!Do.isNullOrEmpty(event.mUrlImage) && mMainImageView.getDrawable() == null) {

            PublicationImageResponse response = new PublicationImageResponse(context,
                    mMainImageView, event, Event.TABLE_NAME);
            Request request = Api.imageRequest(event.mUrlImage, mMainImageView, response,
                    response);
            VolleyManager.getInstance(context).addToRequestQueue(request);

        } else {

            // mMainImageView.setImageResource(R.drawable.event_1); DESIGN definir una imagen predeterminada
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
        view.setOnClickListener(new WebLinkOnClickListener(event.mUrlEvent));

        return view;

    }

}
