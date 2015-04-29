package cl.laikachile.laika.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.WebActivity;
import cl.laikachile.laika.models.Publication;

public class PublicationScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.lk_publication_adapter;
	Publication publication;
	
	public PublicationScreenSlideFragment(Publication publication) {
		
		this.publication = publication;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);
        
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
