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
import social.laika.app.models.Tip;
import social.laika.app.network.RequestManager;
import social.laika.app.utils.Do;

public class TipsAdapter extends ArrayAdapter<Tip> {

    private int mIdLayout = R.layout.lk_tips_adapter;
    private Context context;
    private List<Tip> tips;
    public TextView mSponsorTextView;
    public TextView mTitleTextView;
    public TextView mBodyTextView;
    public ImageView mMainImageView;
    public ImageView mFavoriteImageView;
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
        mMainImageView.setOnClickListener(new WebLinkOnClickListener(tip.mUrlTip));

        mSponsorTextView.setText(tip.mSponsorName);
        mTitleTextView.setText(tip.mTitle);
        mBodyTextView.setText(tip.mBody);

        if (!Do.isNullOrEmpty(tip.mUrlImage) && mMainImageView.getDrawable() == null) {

            RequestManager.getImage(tip.mUrlImage, mProgressBar, mMainImageView, context);

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

        return view;

    }

    public void setFavorite(Tip tip, boolean isFavorite) {

        tip.setIsFavorite(isFavorite);
        int resource = isFavorite ? R.drawable.laika_favorite_red : R.drawable.laika_favorite_white;
        mFavoriteImageView.setImageResource(resource);

    }
}
