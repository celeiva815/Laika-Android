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
import cl.laikachile.laika.utils.Do;

public class PublicationsAdapter extends ArrayAdapter<Publication> {

    private int mIdLayout = R.layout.lk_publication_adapter;
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
        final ImageView favoriteImageView = (ImageView) view.findViewById(R.id.favorite_publication_imageview);

        titleTextView.setText(publication.mTitle);
        sponsorTextView.setText(publication.getSponsor());
        dateTextView.setText(publication.mDate);
        bodyTextView.setText(publication.mBody);
        mainImageView.setImageResource(publication.mImage);
        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Do.isNullOrEmpty(publication.mUrlNews)) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra(WebActivity.URL, publication.mUrlNews);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {

                    //FIXME agregar algún mensaje de "no hay link"
                }
            }
        });

        favoriteImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!publication.mIsFavorite) {
                    publication.setIsFavorite(true);
                    favoriteImageView.setImageResource(R.drawable.star102_yellow);

                } else {
                    publication.setIsFavorite(false);
                    favoriteImageView.setImageResource(R.drawable.star102);

                }
            }
        });

        return view;

    }
}
