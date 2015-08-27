package social.laika.app.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.activities.CreateVetVisitActivity;
import social.laika.app.activities.VetVisitActivity;
import social.laika.app.fragments.VetVisitsFragment;
import social.laika.app.models.Dog;
import social.laika.app.models.VetVisit;
import social.laika.app.network.RequestManager;
import social.laika.app.utils.Do;

public class VetVisitAdapter extends ArrayAdapter<VetVisit> {

    public static final String TAG = VetVisitAdapter.class.getSimpleName();
    public static final int VIEW_DOG_HEALTH = 0;
    public static final int VIEW_VET_VISIT = 1;

    private int mIdLayout = R.layout.lk_vet_visit_adapter;
    public Context context;
    public List<VetVisit> mVetVisits;
    public Dog mDog;
    public TextView mVetNameTextView;
    public TextView mDateTextView;
    public TextView mReasonTextView;
    public ImageView mMainImageView;
    public ProgressBar mProgressBar;
    public VetVisitsFragment mFragment;


    public VetVisitAdapter(Context context, int resource, List<VetVisit> objects, Dog mDog, VetVisitsFragment fragment) {
        super(context, resource, objects);

        this.context = context;
        this.mVetVisits = objects;
        this.mDog = mDog;
        this.mFragment = fragment;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        return getVetVisitView(position, view, parent);
    }

    public View getVetVisitView(int position, View view, ViewGroup parent) {

        final VetVisit vetVisit = mVetVisits.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mIdLayout, parent, false);
        mMainImageView = (ImageView) view.findViewById(R.id.photo_vet_visits_imageview);
        mVetNameTextView = (TextView) view.findViewById(R.id.vet_name_vet_visits_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_vet_visits_textview);
        mReasonTextView = (TextView) view.findViewById(R.id.reason_vet_visits_textview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.download_image_progressbar);

        mVetNameTextView.setText(vetVisit.mVetName);
        mDateTextView.setText(vetVisit.mDate);
        mReasonTextView.setText(vetVisit.mReason);

        if (!Do.isNullOrEmpty(vetVisit.mSmallUrl) && mMainImageView.getDrawable() == null) {

            RequestManager.getImage(vetVisit.mSmallUrl, mProgressBar, mMainImageView, context);

        } else {

            // mMainImageView.setImageResource(R.drawable.vetVisit_1); DESIGN definir una imagen predeterminada
        }

        final int pos = position;
        view.setClickable(true);
        view.setFocusable(true);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, VetVisitActivity.class);
                intent.putExtra(VetVisitActivity.KEY_DOG, mDog.mDogId);
                intent.putExtra(VetVisitActivity.KEY_VET_VISIT, vetVisit.mVetVisitId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                final Context context = v.getContext();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setTitle(R.string.choose_an_option);
                dialog.setItems(new CharSequence[]{"Editar", "Eliminar"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0: // editar alarma

                                        mFragment.editVetVisit(mVetVisits.get(pos));
                                        break;

                                    case 1: // eliminar alarma

                                        mFragment.deleteVetVisit(mVetVisits.get(pos));

                                        break;
                                }

                            }
                        });

                dialog.show();

                return true;
            }
        });

        mVetNameTextView.setSelected(true);

        return view;

    }

}
