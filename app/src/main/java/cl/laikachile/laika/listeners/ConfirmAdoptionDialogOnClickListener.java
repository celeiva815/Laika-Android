package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.AdoptDogSuccessActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.responses.ConfirmAdoptionResponse;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmAdoptionDialogOnClickListener implements OnClickListener {

    private int mIdLayout = R.layout.ai_simple_textview_for_dialog;
    private final Dog mDog;
    private final Activity mActivity;
    public ProgressDialog mProgressDialog;

    public ConfirmAdoptionDialogOnClickListener(Dog mDog, Activity activity,
                                                ProgressDialog mProgressDialog) {

        this.mDog = mDog;
        this.mActivity = activity;
        this.mProgressDialog = mProgressDialog;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        final Context context = v.getContext();
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                mProgressDialog = ProgressDialog.show(context, "Postulación Enviada",
                        "Enviando notificación de adopción");

                requestPostulation();
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        TextView question = (TextView) view.findViewById(R.id.simple_textview);
        question.setText("¿Desea realmente adoptar a " + mDog.mName + "?");

        return view;

    }

    public void requestPostulation() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Dog.COLUMN_DOG_ID, mDog.mDogId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConfirmAdoptionResponse response = new ConfirmAdoptionResponse(mActivity,
                mProgressDialog, mDog);
        RequestManager.postRequest(jsonObject, RequestManager.ADDRESS_CONFIRM_POSTULATION, response,
                response, PrefsManager.getUserToken(mActivity.getApplicationContext()));

    }

}
