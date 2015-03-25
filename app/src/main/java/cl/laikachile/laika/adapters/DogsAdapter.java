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
import cl.laikachile.laika.models.Dog;

public class DogsAdapter extends ArrayAdapter<Dog> {

    private int mIdLayout = R.layout.lk_dog_my_dog_row;
    private Context context;
    private List<Dog> dogs;

    public DogsAdapter(Context context, int resource, List<Dog> objects) {
        super(context, resource, objects);

        this.context = context;
        this.dogs = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(mIdLayout, parent, false);
        Dog dog = dogs.get(position);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.name_dog_my_dog_textview);
        ImageView typeImageView = (ImageView) rowView.findViewById(R.id.dog_my_dog_imageview);

        nameTextView.setText(dog.mName);
        typeImageView.setImageResource(dog.mImage);

        return rowView;

    }
}
