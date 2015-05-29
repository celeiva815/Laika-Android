package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewOwnerDogOnClickListener implements OnClickListener {

    private int mIdLayout = R.layout.lk_add_owner_dialog;
    private final Dog dog;
    private EditText emailEditText;

    public AddNewOwnerDogOnClickListener(Dog dog) {

        this.dog = dog;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        final Context context = v.getContext();
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = emailEditText.getText().toString();
                String message = Do.getRString(context, R.string.sent_email_owners_my_dog) + " " +
                        email;

                Do.showLongToast(message, context);
            }
        });

        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        dialog.setTitle(v.getContext().getResources().getString(R.string.add_owners_my_dog));
        dialog.show();
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        emailEditText = (EditText) view.findViewById(R.id.email_add_owners_my_dog_editext);

        return view;

    }

}
