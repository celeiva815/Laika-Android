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
    public ProgressBar mProgressBar;

    public TipsAdapter(Context context, int resource, List<Tip> objects) {
        super(context, resource, objects);

        this.context = context;
        this.tips = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Tip tip = tips.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        mSponsorTextView = (TextView) view.findViewById(R.id.sponsor_tip_textview);
        mTitleTextView = (TextView) view.findViewById(R.id.title_tip_textview);
        mBodyTextView = (TextView) view.findViewById(R.id.body_tip_textview);
        mMainImageView = (ImageView) view.findViewById(R.id.main_tip_imageview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);
        mMainImageView.setOnClickListener(new WebLinkOnClickListener(tip.mUrlTip));

        mSponsorTextView.setText(tip.mSponsorName);
        mTitleTextView.setText(tip.mTitle);
        mBodyTextView.setText(tip.mBody);

        if (!Do.isNullOrEmpty(tip.mUrlImage) && mMainImageView.getDrawable() == null) {

            RequestManager.getImage(tip.mUrlImage, mProgressBar, mMainImageView, context);

        } else {

            // mMainImageView.setImageResource(R.drawable.event_1); TODO definir una imagen predeterminada
        }

        return view;

    }
}
