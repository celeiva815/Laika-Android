package cl.laikachile.laika.activities;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.OwnersAdapter;
import cl.laikachile.laika.listeners.AddNewOwnerDogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Owner;

public class MyDogOwnersActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.lk_my_dog_owners_activity;
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
		 
		 ListView ownersListView = (ListView) view.findViewById(R.id.owners_my_dog_listview);
	     Button addButton = (Button) view.findViewById(R.id.add_owners_my_dog_owners_button);
		 
	     OwnersAdapter ownersAdapter = new OwnersAdapter(view.getContext(), R.layout.lk_my_dog_owners_adapter, getOwners(view.getContext()));
	     ownersListView.setAdapter(ownersAdapter);
	     
	     AddNewOwnerDogOnClickListener addListener = new AddNewOwnerDogOnClickListener(dog);
	     addButton.setOnClickListener(addListener);
	     
		 
	 }
	 
	 public List<Owner> getOwners(Context context) {
			
			List<Owner> owners = new Select().from(Owner.class).execute();
			
			return owners;
			
		}

}
