package cl.laikachile.laika.utils;

import java.util.Comparator;

import cl.laikachile.laika.models.Dog;

/**
 * Created by Tito_Leiva on 15-06-15.
 */
public class DogSort implements Comparator<Dog> {

    public static final int TYPE_STATUS = 1;
    public static final int TYPE_DOG_ID = 2;

    private int mSortType;

    public DogSort(int sortType) {

        mSortType = sortType;
    }

    @Override
    public int compare(Dog dog1, Dog dog2) {

        switch (mSortType) {

            case TYPE_STATUS:
                return dog1.mStatus - dog2.mStatus;

            case TYPE_DOG_ID:
                return dog1.mDogId - dog2.mDogId;

        }

        return 0;
    }
}
