package cl.laikachile.laika.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by lukas on 17-11-14.
 */
public class TextInputValidatorUtils {

    /**
     * Check if the input parameter is a valid email address
     * @param target The email to validate
     * @return True if is a valid email
     */
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
