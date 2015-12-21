package social.laika.app.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivity extends AppCompatActivity {

    /*protected MaterialDialog dialog;

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
    }*/

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
