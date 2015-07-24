package social.laika.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.listeners.OwnerOptionsDialogOnClickListener;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class OwnerMyDogAdapter extends ArrayAdapter<Owner> {

    private Context mContext;
    private int mIdLayout;
    private List<Owner> mOwners;
    private Dog mDog;

    public OwnerMyDogAdapter(Context context, int resource, List<Owner> objects, Dog dog) {
        super(context, resource, objects);

        mIdLayout = resource;
        mContext = context;
        mOwners = objects;
        mDog = dog;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Owner owner = mOwners.get(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.name_owner_my_dog_textview);
        TextView emailTextView = (TextView) view.findViewById(R.id.email_owner_my_dog_textview);
        TextView phoneTextView = (TextView) view.findViewById(R.id.phone_owner_my_dog_textview);
        TextView roleTextView = (TextView) view.findViewById(R.id.role_owner_my_dog_textview);
        ImageView profileImageView = (ImageView) view.findViewById(R.id.owner_my_dog_imageview);

        nameTextView.setText(owner.getFullName());
        emailTextView.setText(owner.mEmail);
        phoneTextView.setText(owner.mPhone);

        if (owner.mRole != Tag.ROLE_ADMIN) {

           roleTextView.setVisibility(View.GONE);
        }

        switch (owner.mGender) {

            case Tag.GENDER_MALE:

                profileImageView.setImageResource(R.drawable.laika_usermale_grey);

                break;

            case Tag.GENDER_FEMALE:

                profileImageView.setImageResource(R.drawable.laika_userfemale_grey);

                break;
        }

        view.setOnClickListener(new OwnerOptionsDialogOnClickListener(mDog, owner));


        return view;
    }
}
