package social.laika.app.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import social.laika.app.R;
import social.laika.app.interfaces.Photographable;
import social.laika.app.utils.Photographer;

/**
 * Created by Tito_Leiva on 23-07-15.
 */
public class PhotographerListener implements View.OnClickListener, View.OnLongClickListener {

    Photographer mPhotographer;
    Photographable mActivity;

    public PhotographerListener(Photographer mPhotographer, Photographable mActivity) {
        this.mPhotographer = mPhotographer;
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {

        final Context context = v.getContext();
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(R.string.choose_an_option);
        dialog.setItems(mPhotographer.getOptions(),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {

                            case 0:

                                mActivity.takePhoto();

                                break;

                            case 1:

                                mActivity.pickPhoto();

                                break;

                            case 2:

                                mActivity.cropPhoto(mPhotographer.mSourceImage);

                                break;
                        }
                    }
                });

        dialog.show();

    }

    @Override
    public boolean onLongClick(View v) {

        if (mPhotographer.mSourceImage != null) {

            mActivity.cropPhoto(mPhotographer.mSourceImage);
            return true;

        } else {

            return false;
        }
    }
}
