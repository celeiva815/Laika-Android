package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Tag;

public class DogsAdapter extends ArrayAdapter<Dog> {

    private int mIdLayout;
    private Context context;
    private List<Dog> dogs;

    public DogsAdapter(Context context, int resource, List<Dog> objects) {
        super(context, resource, objects);

        this.context = context;
        this.dogs = objects;
        this.mIdLayout = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        Dog dog = dogs.get(position);

        switch (mIdLayout) {

            case R.layout.lk_dog_user_profile_row:
                ImageView dogImageView = (ImageView) view.findViewById(R.id.dog_user_profile_imageview);
                ImageResponse response = new ImageResponse(dogImageView, null);
                Request request = RequestManager.imageRequest(dog.getImage(Tag.IMAGE_THUMB),
                        dogImageView, response, response);
                VolleyManager.getInstance(context).addToRequestQueue(request);


            case R.layout.lk_dog_my_dog_row:
                TextView nameTextView = (TextView) view.findViewById(R.id.name_dog_my_dog_textview);
                nameTextView.setText(dog.mName);
            break;

        }

        return view;

    }
}
