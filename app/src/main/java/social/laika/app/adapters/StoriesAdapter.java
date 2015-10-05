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
import social.laika.app.models.publications.Story;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.LocalImageSaverResponse;
import social.laika.app.utils.Do;

public class StoriesAdapter extends ArrayAdapter<Story> {

    private int mIdLayout = R.layout.lk_stories_adapter;
    private Context context;
    private List<Story> mStories;
    public TextView mTitleTextView;
    public TextView mSponsorTextView;
    public TextView mDateTextView;
    public TextView mBodyTextView;
    public ImageView mMainImageView;
    public ImageView mFavoriteImageView;
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
        mFavoriteImageView = (ImageView) view.findViewById(R.id.favorite_stories_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);

        mTitleTextView.setText(story.mTitle);
        mSponsorTextView.setText(story.getOwnerName());
        mDateTextView.setText(story.mDate);
        mBodyTextView.setText(story.mBody);

        if (!Do.isNullOrEmpty(story.mUriLocal)) {

            File file = new File(Uri.parse(story.mUriLocal).getPath());

            if (file.exists()) {
                mMainImageView.setImageURI(Uri.parse(story.mUriLocal));

            } else if (!Do.isNullOrEmpty(story.mUrlImage) && mMainImageView.getDrawable() == null) {

                LocalImageSaverResponse response = new LocalImageSaverResponse(context,
                        mMainImageView, story, Story.TABLE_NAME);
                Request request = Api.imageRequest(story.mUrlImage, mMainImageView, response,
                        response);
                VolleyManager.getInstance(context).addToRequestQueue(request);
            }
        } else if (!Do.isNullOrEmpty(story.mUrlImage) && mMainImageView.getDrawable() == null) {

            LocalImageSaverResponse response = new LocalImageSaverResponse(context,
                    mMainImageView, story, Story.TABLE_NAME);
            Request request = Api.imageRequest(story.mUrlImage, mMainImageView, response,
                    response);
            VolleyManager.getInstance(context).addToRequestQueue(request);

        } else {

            // mMainImageView.setImageResource(R.drawable.event_1); DESIGN definir una imagen predeterminada
        }

        setFavorite(story, story.mIsFavorite);

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setFavorite(story, !story.mIsFavorite);
            }
        });

        return view;

    }

    public void setFavorite(Story story, boolean isFavorite) {

        story.setIsFavorite(isFavorite);
        int resource = isFavorite ? R.drawable.laika_favorite_red : R.drawable.laika_favorite_white;
        mFavoriteImageView.setImageResource(resource);

    }
}
