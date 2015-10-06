package social.laika.app.adapters;

import android.content.Context;
import android.net.Uri;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
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
import social.laika.app.models.publications.Publication;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.LocalImageSaverResponse;
import social.laika.app.utils.Do;

public class PublicationsAdapter extends ArrayAdapter<Publication> {

    public static final String TAG = PublicationsAdapter.class.getSimpleName();

    private int mIdLayout = R.layout.lk_publication_adapter;
    private Context mContext;
    private List<Publication> mPublications;

    public ImageView mFavoriteImageView;
    public TextView mTitleTextView;
    public TextView mSponsorTextView;
    public TextView mDateTextView;
    public TextView mBodyTextView;
    public ImageView mMainImageView;
    public ProgressBar mProgressBar;
    private ImageView mShareImageView;

    public PublicationsAdapter(Context context, int resource, List<Publication> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mPublications = objects;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final Publication publication = mPublications.get(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);

        mTitleTextView = (TextView) view.findViewById(R.id.title_news_textview);
        mSponsorTextView = (TextView) view.findViewById(R.id.sponsor_news_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_news_textview);
        mBodyTextView = (TextView) view.findViewById(R.id.body_news_textview);
        mMainImageView = (ImageView) view.findViewById(R.id.main_news_imageview);
        mFavoriteImageView = (ImageView) view.findViewById(R.id.favorite_publication_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);
        mShareImageView = (ImageView) view.findViewById(R.id.share_news_imageview);

        mTitleTextView.setText(publication.mTitle);
        mSponsorTextView.setText(publication.getSponsor());
        mDateTextView.setText(publication.mDate);
        mBodyTextView.setText(publication.mBody);
        mMainImageView.setOnClickListener(new WebLinkOnClickListener(publication.mUrl));

        if (!Do.isNullOrEmpty(publication.mUriLocal)) {

            File file = new File(Uri.parse(publication.mUriLocal).getPath());

            if (file.exists()) {
                mMainImageView.setImageURI(Uri.parse(publication.mUriLocal));

            } else if (!Do.isNullOrEmpty(publication.mUrlImage)) {

                LocalImageSaverResponse response = new LocalImageSaverResponse(context,
                        mMainImageView, publication, Publication.TABLE_NAME);
                Request request = Api.imageRequest(publication.mUrlImage, mMainImageView, response,
                        response);
                VolleyManager.getInstance(context).addToRequestQueue(request);


            }
        } else if (!Do.isNullOrEmpty(publication.mUrlImage)) {

            LocalImageSaverResponse response = new LocalImageSaverResponse(context,
                    mMainImageView, publication, Publication.TABLE_NAME);
            Request request = Api.imageRequest(publication.mUrlImage, mMainImageView, response,
                    response);
            VolleyManager.getInstance(context).addToRequestQueue(request);

            Api.getImage(publication.mUrlImage, mProgressBar, mMainImageView, mContext);

        } else {

            mMainImageView.setImageResource(R.drawable.event_1); //DESIGN definir una imagen predeterminada
        }

        setFavorite(publication, publication.mIsFavorite);

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Publication publication = mPublications.get(position);
                setFavorite(publication, !publication.mIsFavorite);
            }
        });

        mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = mContext.getResources();
                String shareStr = publication.mTitle + "\n\n" + publication.mBody +
                        "\n\n Ver más en: " + publication.mUrlPublication;
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareStr);
                shareIntent.setType("text/plain");
                //shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent.createChooser()
                mContext.startActivity(Intent.createChooser(shareIntent, res.getString(R.string
                        .share_string)));
            }
        });

        //setFavorite(publication, publication.mIsFavorite);

        return view;

    }

    public void setFavorite(Publication publication, boolean isFavorite) {

        publication.setIsFavorite(isFavorite);
        int resource = publication.mIsFavorite ? R.drawable.laika_favorite_red :
                R.drawable.laika_favorite_white;

        mFavoriteImageView.setImageResource(resource);
        notifyDataSetChanged();

    }

}
