package cl.laikachile.laika.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Photo;
import cl.laikachile.laika.models.Photo;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class AlbumMyDogFragment extends Fragment {

    private int mIdLayout = R.layout.lk_album_my_dog_fragment;
    public Dog mDog;
    public List<Photo> mPhotos;

    public AlbumMyDogFragment(Dog mDog) {
        this.mDog = mDog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);



        return view;
    }

    private List<Photo> getPhotos(Context context) {

        //FIXME cambiar por los dueños sacados de la API


        return mPhotos;
    }



}
