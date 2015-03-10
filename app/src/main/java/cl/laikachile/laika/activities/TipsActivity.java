package cl.laikachile.laika.activities;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.TipsAdapter;
import cl.laikachile.laika.models.Tip;

public class TipsActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_tips_activity;
	
	 @Override
		public void onStart() {

	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 ListView tipsListView = (ListView) view.findViewById(R.id.tips_listview);
		 TipsAdapter adapter = new TipsAdapter(getApplicationContext(), R.layout.lk_tips_adapter, getTips(getApplicationContext()));
		 
		 tipsListView.setAdapter(adapter);
		 
	 }
	 
	 private List<Tip> getTips(Context context) {
		 
		 String[] tips = context.getResources().getStringArray(R.array.example_tips);
		 String[] types = context.getResources().getStringArray(R.array.type_tips);
		 List<Tip> tipList = new ArrayList<Tip>(tips.length);
		 
		 for (int i = 0; i < tips.length; i++) {
			 
			 Tip tip = new Tip(tips[i],Integer.parseInt(types[i])); //FIXME definir bien el tipo para que calce la imagen
			 tipList.add(tip);
			 
		 }
		 
		 return tipList;
	 }

}
