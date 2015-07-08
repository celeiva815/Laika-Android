package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.utils.Do;

public class StoriesAdapter extends ArrayAdapter<Story> {

    private int mIdLayout = R.layout.lk_stories_adapter;
    private Context context;
    private List<Story> mStories;
    public TextView mTitleTextView;
    public TextView mSponsorTextView;
    public TextView mDateTextView;
    public TextView mBodyTextView;
    public ImageView mMainImageView;
    public ProgressBar mProgressBar;

    public StoriesAdapter(Context context, int resource, List<Story> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mStories = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Story story = mStories.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        mTitleTextView = (TextView) view.findViewById(R.id.title_stories_textview);
        mSponsorTextView = (TextView) view.findViewById(R.id.owner_stories_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_stories_textview);
        mBodyTextView = (TextView) view.findViewById(R.id.body_stories_textview);
        mMainImageView = (ImageView) view.findViewById(R.id.main_stories_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);

        mTitleTextView.setText(story.mTitle);
        mSponsorTextView.setText(story.getOwnerName());
        mDateTextView.setText(story.mDate);
        mBodyTextView.setText(story.mBody);

        if (!Do.isNullOrEmpty(story.mUrlImage) && mMainImageView.getDrawable() == null) {

            RequestManager.requestImage(story.mUrlImage, mProgressBar, mMainImageView, context);

        } else {

            // mMainImageView.setImageResource(R.drawable.event_1); TODO definir una imagen predeterminada
        }


        return view;

    }
}
