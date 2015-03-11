package cl.laikachile.laika.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class RemindersMyDogFragment extends Fragment {

    private int mIdLayout = R.layout.lk_reminders_my_dog_fragment;
    public Dog mDog;
    public Fragment mFragment;

    public ImageView mFoodImageView;
    public ImageView mPooImageView;
    public ImageView mWalkImageView;
    public ImageView mMedicineImageView;
    public ImageView mHygieneImageView;
    public ImageView mVetImageView;
    public ImageView mVaccineImageView;

    public RemindersMyDogFragment(Dog mDog) {
        this.mDog = mDog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(mIdLayout, container, false);

        RelativeLayout foodLayout = (RelativeLayout) view.findViewById(R.id.food_reminders_my_dog_layout);
        RelativeLayout pooLayout = (RelativeLayout) view.findViewById(R.id.poo_reminders_my_dog_layout);
        RelativeLayout walkLayout = (RelativeLayout) view.findViewById(R.id.walk_reminders_my_dog_layout);
        RelativeLayout medicineLayout = (RelativeLayout) view.findViewById(R.id.medicine_reminders_my_dog_layout);
        RelativeLayout hygieneLayout = (RelativeLayout) view.findViewById(R.id.hygiene_reminders_my_dog_layout);
        RelativeLayout vetLayout = (RelativeLayout) view.findViewById(R.id.vet_reminders_my_dog_layout);
        RelativeLayout vaccineLayout = (RelativeLayout) view.findViewById(R.id.vaccine_reminders_my_dog_layout);

        mFoodImageView = (ImageView) view.findViewById(R.id.food_reminders_my_dog_imageview);
        mPooImageView = (ImageView) view.findViewById(R.id.poo_reminders_my_dog_imageview);
        mWalkImageView = (ImageView) view.findViewById(R.id.walk_reminders_my_dog_imageview);
        mMedicineImageView = (ImageView) view.findViewById(R.id.medicine_reminders_my_dog_imageview);
        mHygieneImageView = (ImageView) view.findViewById(R.id.hygiene_reminders_my_dog_imageview);
        mVetImageView = (ImageView) view.findViewById(R.id.vet_reminders_my_dog_imageview);
        mVaccineImageView = (ImageView) view.findViewById(R.id.vaccine_reminders_my_dog_imageview);

        foodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_FOOD);
                setCheckedImage(true, false, false, false, false, false, false);
            }
        });

        pooLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_POO);
                setCheckedImage(false, true, false, false, false, false, false);
            }
        });

        walkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_WALK);
                setCheckedImage(false, false, true, false, false, false, false);
            }
        });

        medicineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmReminder(Tag.CATEGORY_MEDICINE);
                setCheckedImage(false, false, false, true, false, false, false);
            }
        });

        hygieneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarReminder(Tag.CATEGORY_HYGIENE);
                setCheckedImage(false, false, false, false, true, false, false);
            }
        });

        vetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarReminder(Tag.CATEGORY_VET);
                setCheckedImage(false, false, false, false, false, true, false);
            }
        });

        vaccineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarReminder(Tag.CATEGORY_VACCINE);
                setCheckedImage(false, false, false, false, false, false, true);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    public void openAlarmReminder(int reminderType) {

        if (mFragment != null) {
            getChildFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new AlarmReminderMyDogFragment(mDog, reminderType);
        getChildFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void openCalendarReminder(int reminderCategory) {

        if (mFragment != null) {
            getChildFragmentManager().beginTransaction().detach(mFragment).commit();
        }

        mFragment = new CalendarReminderMyDogFragment(mDog, reminderCategory);
        getChildFragmentManager().beginTransaction().add(R.id.container_reminder_my_dog_framelayout, mFragment).commit();

    }

    public void setCheckedImage(boolean food, boolean poo, boolean walk, boolean medicine,
                                boolean hygiene, boolean vet, boolean vaccine) {

        mFoodImageView.setImageDrawable(getResources().getDrawable(R.drawable.dog51_trans));
        mPooImageView.setImageDrawable(getResources().getDrawable(R.drawable.poo_trans));
        mWalkImageView.setImageDrawable(getResources().getDrawable(R.drawable.walk_trans));
        mMedicineImageView.setImageDrawable(getResources().getDrawable(R.drawable.pill_trans));
        mHygieneImageView.setImageDrawable(getResources().getDrawable(R.drawable.hygiene_trans));
        mVetImageView.setImageDrawable(getResources().getDrawable(R.drawable.cross_trans));
        mVaccineImageView.setImageDrawable(getResources().getDrawable(R.drawable.medicine_trans));

        if (food) {
            mFoodImageView.setImageDrawable(getResources().getDrawable(R.drawable.dog51_white));
        }

        if (poo) {
            mPooImageView.setImageDrawable(getResources().getDrawable(R.drawable.poo_white));
        }

        if (walk) {
            mWalkImageView.setImageDrawable(getResources().getDrawable(R.drawable.walk_white));
        }

        if (medicine) {
            mMedicineImageView.setImageDrawable(getResources().getDrawable(R.drawable.pill_white));
        }

        if (hygiene) {
            mHygieneImageView.setImageDrawable(getResources().getDrawable(R.drawable.hygiene_white));
        }

        if (vet) {
            mVetImageView.setImageDrawable(getResources().getDrawable(R.drawable.cross_white));
        }

        if (vaccine) {
            mVaccineImageView.setImageDrawable(getResources().getDrawable(R.drawable.medicine_white));
        }
    }
}
