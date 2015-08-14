package social.laika.app.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import social.laika.app.R;
import social.laika.app.utils.Do;

public class HelperDialogOnClickListener implements OnClickListener {

    public static final String TAG = HelperDialogOnClickListener.class.getSimpleName();

    private int mIdLayout = R.layout.lk_helper_dialog_layout;
    private String mBody;

    public HelperDialogOnClickListener(int res, Context mContext) {

        this.mBody = Do.getRString(mContext, res);
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        final Context context = v.getContext();
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);

        dialog.show();
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        TextView help = (TextView) view.findViewById(R.id.simple_textview);
        help.setText(mBody);

        return view;

    }
}