package cl.laikachile.laika.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cl.laikachile.laika.R;

@Table(name = Tip.TABLE_NAME)
public class Tip extends Model {

	public final static String TABLE_NAME = "tips";
	public final static String COLUMN_BODY = "body";
	public final static String COLUMN_TYPE = "type";
	
	public final static int TYPE_FOOD = 1;
	public final static int TYPE_HEALTH = 2;
	public final static int TYPE_HYGIENE = 3;
	public final static int TYPE_WALK = 4;
	
	@Column(name = COLUMN_BODY)
	public String aBody;
	
	@Column(name = COLUMN_TYPE)
	public int aType;

	public Tip(String aBody, int aType) {
		
		this.aBody = aBody;
		this.aType = aType;
	}

	public Tip() { }
	
	public int getImageResource() {
		
		switch (this.aType) {
		case TYPE_FOOD:
			
			return R.drawable.lk_food_tips;
			
		case TYPE_HEALTH:
			
			return R.drawable.lk_health_tips;
			
		case TYPE_HYGIENE:
			
			return R.drawable.lk_hygiene_tips;
			
		case TYPE_WALK:
			
			return R.drawable.lk_walk_tips;
		
		}
		
		return R.drawable.lk_food_tips;
	}
	
	
}
