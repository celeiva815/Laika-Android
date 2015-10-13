package social.laika.app.adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lukas on 13-10-15.
 */
public class ShareIntentAdapter extends BaseAdapter {

    private final static int RESOURCE_ID = android.R.layout.select_dialog_item;
    private final static int TV_RESOURCE_ID = android.R.id.text1;

    private List<ResolveInfo> mActivities;
    private Context mContext;


    public ShareIntentAdapter(Context context, List<ResolveInfo> activities) {
        mContext = context;
        mActivities = activities;
    }

    @Override
    public int getCount() {
        return mActivities.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResolveInfo ri = mActivities.get(position);
        View row = View.inflate(mContext, RESOURCE_ID, null);
        TextView tv = (TextView) row.findViewById(TV_RESOURCE_ID);
        tv.setText(ri.activityInfo.applicationInfo.loadLabel(mContext.getPackageManager()));
        try {
            tv.setCompoundDrawablesWithIntrinsicBounds(mContext.getPackageManager()
                    .getApplicationIcon(ri.activityInfo.packageName), null, null, null);
            tv.setCompoundDrawablePadding((int) TypedValue.applyDimension(TypedValue
                    .COMPLEX_UNIT_DIP, 12, mContext.getResources().getDisplayMetrics()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return row;
    }
}
