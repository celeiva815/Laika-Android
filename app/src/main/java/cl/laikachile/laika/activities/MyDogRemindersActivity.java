
package cl.laikachile.laika.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.RemindersAdapter;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;

public class MyDogRemindersActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_my_dog_reminders_activity;
	private Dog dog;
	
	 @Override
		public void onStart() {

		 	Bundle b = getIntent().getExtras();
			this.dog = Dog.load(Dog.class, b.getLong("DogId"));
		 
	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 ListView remindersListView = (ListView) view.findViewById(R.id.list_my_dog_reminders_listview);
		 TextView nameTextView = (TextView) view.findViewById(R.id.name_my_dog_reminders_textview);
		 RemindersAdapter adapter = new RemindersAdapter(getApplicationContext(), R.layout.lk_my_dog_reminders_adapter, getObjects());
		 
		 remindersListView.setAdapter(adapter);
		 nameTextView.setText(dog.mName);
	 }
	 
	 private List<AlarmReminder> getObjects(){
		 
		 List<AlarmReminder> reminders = AlarmReminder.getAllReminders();
		 
		 return reminders;
		
		 
	 }

}
