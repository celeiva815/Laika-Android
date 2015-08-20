package social.laika.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.listeners.WebLinkOnClickListener;
import social.laika.app.models.Publication;
import social.laika.app.network.RequestManager;
import social.laika.app.utils.Do;

public class PublicationsAdapter extends ArrayAdapter<Publication> {

    public static final String TAG = PublicationsAdapter.class.getSimpleName();

    private int mIdLayout = R.layout.lk_publication_adapter;
    private Context context;
    private List<Publication> mPublications;

    public ImageView mFavoriteImageView;
    public TextView mTitleTextView;
    public TextView mSponsorTextView;
    public TextView mDateTextView;
    public TextView mBodyTextView;
    public ImageView mMainImageView;
    public ProgressBar mProgressBar;

    public PublicationsAdapter(Context context, int resource, List<Publication> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mPublications = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Publication publication = mPublications.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);

        mTitleTextView = (TextView) view.findViewById(R.id.title_news_textview);
        mSponsorTextView = (TextView) view.findViewById(R.id.sponsor_news_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_news_textview);
        mBodyTextView = (TextView) view.findViewById(R.id.body_news_textview);
        mMainImageView = (ImageView) view.findViewById(R.id.main_news_imageview);
        mFavoriteImageView = (ImageView) view.findViewById(R.id.favorite_publication_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);


        mTitleTextView.setText(publication.mTitle);
        mSponsorTextView.setText(publication.getSponsor());
        mDateTextView.setText(publication.mDate);
        mBodyTextView.setText(publication.mBody);
        mMainImageView.setOnClickListener(new WebLinkOnClickListener(publication.mUrlPublication));

        if (!Do.isNullOrEmpty(publication.mUrlImage)) {

            RequestManager.getImage(publication.mUrlImage, mProgressBar, mMainImageView, context);

        } else {

            mMainImageView.setImageResource(R.drawable.event_1); //DESIGN definir una imagen predeterminada
        }

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setFavorite(publication, !publication.mIsFavorite);
            }
        });

        //setFavorite(publication, publication.mIsFavorite);

        return view;

    }

    public void setFavorite(Publication publication, boolean isFavorite) {

        if (isFavorite) {
            publication.setIsFavorite(true);
            mFavoriteImageView.setImageResource(R.drawable.star102_yellow);

        } else {
            publication.setIsFavorite(false);
            mFavoriteImageView.setImageResource(R.drawable.star102);

        }

    }

}
