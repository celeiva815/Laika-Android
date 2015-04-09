package cl.laikachile.laika.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.PhotosFragmentActivity;
import cl.laikachile.laika.adapters.AlbumAdapter;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Photo;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class AlbumMyDogFragment extends Fragment {

    private int mIdLayout = R.layout.lk_album_my_dog_fragment;
    public Dog mDog;
    public List<Photo> mPhotos;
    public GridView mGridView;
    public AlbumAdapter mAlbumAdapter;

    public AlbumMyDogFragment(Dog mDog) {
        this.mDog = mDog;
        this.mPhotos = getPhotos();
    }

    @Override
    public void onResume() {
        super.onResume();

        mPhotos = getPhotos();
        mAlbumAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        mGridView= (GridView) view.findViewById(R.id.album_my_dog_gridview);
        mAlbumAdapter = new AlbumAdapter(view.getContext(), mPhotos);
        mGridView.setAdapter(mAlbumAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(v.getContext(), PhotosFragmentActivity.class);
                intent.putExtra(PhotosFragmentActivity.KEY_CURRENT_ITEM, position);
                intent.putExtra(PhotosFragmentActivity.KEY_DOG_ID, mDog.mDogId);
                v.getContext().startActivity(intent);
            }
        });


        return view;
    }

    private List<Photo> getPhotos() {

        return Photo.getPhotos(mDog.mDogId);
    }
}
