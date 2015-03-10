package cl.laikachile.laika.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.laikachile.laika.R;

/**
 * Created by Tito_Leiva on 22-01-15.
 */
public class LeftMenuAdapter extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<String> list;

    public LeftMenuAdapter(Activity activity, ArrayList<String> vector){
        this.activity = activity;
        this.list = vector;
    }

    public int getImageId(int position){

        switch(position){

            default:
                return 0;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ai_left_menu_adapter, null, false);

        //Este es el texto del men�
        TextView textView = (TextView) view.findViewById(R.id.name_item_menu);
        textView.setText(list.get(position));

        return view;
    }
}
