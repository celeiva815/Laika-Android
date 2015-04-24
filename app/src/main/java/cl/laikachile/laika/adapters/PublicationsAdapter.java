package cl.laikachile.laika.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.WebActivity;
import cl.laikachile.laika.models.Publication;

public class PublicationsAdapter extends ArrayAdapter<Publication> {

    private int mIdLayout = R.layout.lk_publication_screen_slide_fragment;
    private Context context;
    private List<Publication> mPublications;

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
        TextView titleTextView = (TextView) view.findViewById(R.id.title_news_textview);
        TextView sponsorTextView = (TextView) view.findViewById(R.id.sponsor_news_textview);
        TextView dateTextView = (TextView) view.findViewById(R.id.date_news_textview);
        TextView bodyTextView = (TextView) view.findViewById(R.id.body_news_textview);
        ImageView mainImageView = (ImageView) view.findViewById(R.id.main_news_imageview);

        titleTextView.setText(publication.mTitle);
        sponsorTextView.setText(publication.getSponsor());
        dateTextView.setText(publication.mDate);
        bodyTextView.setText(publication.mBody);
        mainImageView.setImageResource(publication.mImage);
        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL, publication.mUrlNews);
                context.startActivity(intent);

            }
        });

        return view;

    }
}
