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
import cl.laikachile.laika.models.Publication;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Do;

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
        mMainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Do.isNullOrEmpty(publication.mUrlPublication)) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra(WebActivity.URL, publication.mUrlPublication);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {

                }
            }
        });

        requestImage(publication, context);

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

    public void requestImage(Publication publication, Context context) {

        mProgressBar.setVisibility(View.VISIBLE);

        ImageResponse response = new ImageResponse(mMainImageView, mProgressBar);

        Request request = RequestManager.imageRequest(publication.mUrlImage, mMainImageView,
                response, response);

        VolleyManager.getInstance(context).addToRequestQueue(request, TAG);


    }
}
