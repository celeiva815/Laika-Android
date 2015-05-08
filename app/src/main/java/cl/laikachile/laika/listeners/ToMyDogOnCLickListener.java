package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MyDogsActivity;
import cl.laikachile.laika.activities.NewDogActivity;
import cl.laikachile.laika.adapters.DogsAdapter;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ToMyDogOnCLickListener implements OnClickListener {

    List<Dog> mDogs;
    Dialog mDialog;

    @Override
    public void onClick(View v) {

        Context context = v.getContext();
        mDogs = Dog.getDogs(Tag.DOG_OWNED);

        if (mDogs == null || mDogs.isEmpty()) {
            Do.changeActivity(context, NewDogActivity.class);

        } else if (mDogs.size() == 1) {
            goToMyDogsActivity(context, mDogs.get(0).mDogId);

        } else {

            mDialog = new Dialog(context);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(getDialogView(context, R.layout.simple_listview));
            mDialog.show();

        }
    }

    private View getDialogView(final Context context, int layoutId) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null, false);

        ListView listView = (ListView) view.findViewById(R.id.simple_listview);
        DogsAdapter dogsAdapter = new DogsAdapter(context, R.layout.lk_dog_my_dog_row, mDogs);
        listView.setAdapter(dogsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mDialog != null && mDialog.isShowing()) {

                    mDialog.dismiss();
                }

                goToMyDogsActivity(context, mDogs.get(position).mDogId);
            }
        });

        return view;
    }

    public void goToMyDogsActivity(Context context, int dogId) {

        Intent intent = new Intent(context, MyDogsActivity.class);
        intent.putExtra(MyDogsActivity.DOG_ID, dogId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
