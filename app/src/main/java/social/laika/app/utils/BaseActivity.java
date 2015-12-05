package social.laika.app.utils;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import social.laika.app.R;

/**
 * Created by GustavoAdolfo on 05-12-2015.
 */
public class BaseActivity extends AppCompatActivity {

    protected MaterialDialog dialog;

    /**
     * Create a dialog, by default is a loading dialog.
     *
     * @return Return a dialog.
     */
    private MaterialDialog createDialog() {
        return new MaterialDialog.Builder(this)
                .content(R.string.feedback_loading)
                .contentColor(ContextCompat.getColor(this, android.R.color.black))
                .progress(true, 0)
                .backgroundColor(ContextCompat.getColor(this, android.R.color.white))
                .widgetColor(ContextCompat.getColor(this, android.R.color.white))
                .build();
    }

    protected void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void showDialog() {
        if (dialog != null) {
            dialog.show();
        } else {
            dialog = createDialog();
            dialog.show();
        }
    }
}
