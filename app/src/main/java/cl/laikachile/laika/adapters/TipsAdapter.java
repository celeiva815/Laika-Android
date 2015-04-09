package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Tip;

public class TipsAdapter extends ArrayAdapter<Tip> {

    private int mIdLayout = R.layout.lk_tips_adapter;
    private Context context;
    private List<Tip> tips;

    public TipsAdapter(Context context, int resource, List<Tip> objects) {
        super(context, resource, objects);

        this.context = context;
        this.tips = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Tip tip = tips.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(mIdLayout, parent, false);
        TextView sponsorTextView = (TextView) rowView.findViewById(R.id.sponsor_tip_textview);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.title_tip_textview);
        TextView bodyTextView = (TextView) rowView.findViewById(R.id.body_tip_textview);
        ImageView mainImageView = (ImageView) rowView.findViewById(R.id.main_tip_imageview);

        sponsorTextView.setText(tip.mSponsorName);
        titleTextView.setText(tip.mTitle);
        bodyTextView.setText(tip.mBody);
        mainImageView.setImageResource(tip.mUrlImage);

        return rowView;

    }
}
