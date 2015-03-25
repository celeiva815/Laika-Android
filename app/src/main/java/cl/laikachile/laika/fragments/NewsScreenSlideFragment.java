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
import cl.laikachile.laika.models.News;

public class NewsScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.lk_news_screen_slide_fragment;
	News news;
	
	public NewsScreenSlideFragment(News news) {
		
		this.news = news;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(mIdLayout, container, false);
        
        TextView titleTextView = (TextView) rootView.findViewById(R.id.title_news_textview);
        TextView sponsorTextView = (TextView) rootView.findViewById(R.id.sponsor_news_textview);
        TextView dateTextView = (TextView) rootView.findViewById(R.id.date_news_textview);
		TextView bodyTextView = (TextView) rootView.findViewById(R.id.body_news_textview);
		ImageView mainImageView = (ImageView) rootView.findViewById(R.id.main_news_imageview);
        
		titleTextView.setText(news.mTitle);
        sponsorTextView.setText(news.getSponsor());
        dateTextView.setText(news.mDate);
        bodyTextView.setText(news.mBody);
        mainImageView.setImageResource(news.mImage);
        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL, news.mUrlNews);
                context.startActivity(intent);

            }
        });
        
        return rootView;
    }
	
	
}
