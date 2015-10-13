package social.laika.app.adapters;

import android.app.Activity;
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
import social.laika.app.models.publications.Tip;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.LocalImageSaverResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.ShareHelper;

public class TipsAdapter extends ArrayAdapter<Tip> {

    private int mIdLayout = R.layout.lk_tips_adapter;
    private Context context;
    private List<Tip> tips;
    public TextView mSponsorTextView;
    public TextView mTitleTextView;
    public TextView mBodyTextView;
    public ImageView mMainImageView;
    public ImageView mFavoriteImageView;
    private ImageView mShareImageView;
    public ProgressBar mProgressBar;

    public TipsAdapter(Context context, int resource, List<Tip> objects) {
        super(context, resource, objects);

        this.context = context;
        this.tips = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Tip tip = tips.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        mSponsorTextView = (TextView) view.findViewById(R.id.sponsor_tip_textview);
        mTitleTextView = (TextView) view.findViewById(R.id.title_tip_textview);
        mBodyTextView = (TextView) view.findViewById(R.id.body_tip_textview);
        mMainImageView = (ImageView) view.findViewById(R.id.main_tip_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);
        mFavoriteImageView = (ImageView) view.findViewById(R.id.favorite_tip_imageview);
        mShareImageView = (ImageView) view.findViewById(R.id.share_tip_imageview);
        mMainImageView.setOnClickListener(new WebLinkOnClickListener(tip.mUrl));

        mSponsorTextView.setText(tip.mSponsorName);
        mTitleTextView.setText(tip.mTitle);
        mBodyTextView.setText(tip.mBody);

        if (!Do.isNullOrEmpty(tip.mUriLocal)) {

            File file = new File(Uri.parse(tip.mUriLocal).getPath());

            if (file.exists()) {
                mMainImageView.setImageURI(Uri.parse(tip.mUriLocal));

            } else if (!Do.isNullOrEmpty(tip.mUrlImage) && mMainImageView.getDrawable() == null) {

                LocalImageSaverResponse response = new LocalImageSaverResponse(context,
                        mMainImageView, tip, Tip.TABLE_NAME);
                Request request = Api.imageRequest(tip.mUrlImage, mMainImageView, response,
                        response);
                VolleyManager.getInstance(context).addToRequestQueue(request);
            }

        } else if (!Do.isNullOrEmpty(tip.mUrlImage) && mMainImageView.getDrawable() == null) {

            LocalImageSaverResponse response = new LocalImageSaverResponse(context,
                    mMainImageView, tip, Tip.TABLE_NAME);
            Request request = Api.imageRequest(tip.mUrlImage, mMainImageView, response,
                    response);
            VolleyManager.getInstance(context).addToRequestQueue(request);

        } else {

            // mMainImageView.setImageResource(R.drawable.event_1); DESIGN definir una imagen predeterminada
        }

        setFavorite(tip, tip.mIsFavorite);

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setFavorite(tip, !tip.mIsFavorite);
            }
        });

        mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareHelper helper = new ShareHelper((Activity) context, tip);
                helper.share();
            }
        });

        return view;

    }

    public void setFavorite(Tip tip, boolean isFavorite) {

        tip.setIsFavorite(isFavorite);
        int resource = isFavorite ? R.drawable.laika_favorite_red : R.drawable.laika_favorite_white;
        mFavoriteImageView.setImageResource(resource);
        notifyDataSetChanged();

    }
}
