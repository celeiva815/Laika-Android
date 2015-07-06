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
import cl.laikachile.laika.models.Story;

public class StoriesAdapter extends ArrayAdapter<Story> {

    private int mIdLayout = R.layout.lk_stories_adapter;
    private Context context;
    private List<Story> mStories;

    public StoriesAdapter(Context context, int resource, List<Story> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mStories = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Story story = mStories.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        TextView titleTextView = (TextView) view.findViewById(R.id.title_stories_textview);
        TextView sponsorTextView = (TextView) view.findViewById(R.id.owner_stories_textview);
        TextView dateTextView = (TextView) view.findViewById(R.id.date_stories_textview);
        TextView bodyTextView = (TextView) view.findViewById(R.id.body_stories_textview);
        ImageView mainImageView = (ImageView) view.findViewById(R.id.main_stories_imageview);

        titleTextView.setText(story.mTitle);
        sponsorTextView.setText(story.getOwnerName());
        dateTextView.setText(story.mDate);
        bodyTextView.setText(story.mBody);
        mainImageView.setImageResource(R.drawable.abuelo); //FIXME descargar la imagen y poner el bitmap

        return view;

    }
}
