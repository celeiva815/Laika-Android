package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = Event.TABLE_NAME)
public class Event extends Model {

	public final static String TABLE_NAME = "events";
	public final static String COLUMN_NAME = "name";
	public final static String COLUMN_TYPE = "type";
	public final static String COLUMN_START_DATE = "start_date";
	public final static String COLUMN_FINISH_DATE = "finish_date";
	public final static String COLUMN_ASSISTANCE = "assistance";
	
	public final static int TYPE_FOOD = 1;
	public final static int TYPE_HEALTH = 2;
	public final static int TYPE_HYGIENE = 3;
	public final static int TYPE_WALK = 4;
	
	public final static int ASSISTANCE_ACCEPT = 1;
	public final static int ASSISTANCE_REFUSE = 2;
	public final static int ASSISTANCE_NOT_ANSWERED = 3;
	
	@Column(name = COLUMN_NAME)
	public String aName;
	
	@Column(name = COLUMN_TYPE)
	public int aImageType;
	
	@Column(name = COLUMN_START_DATE)
	public String aStartDate;
	
	@Column(name = COLUMN_FINISH_DATE)
	public String aFinishDate;
	
	@Column(name = COLUMN_ASSISTANCE)
	public int aAssistance;


	public Event(String aName, String aStartDate, String aFinishDate, int aImageType) {
		
		this.aName = aName;
		this.aStartDate = aStartDate;
		this.aFinishDate = aFinishDate;
		this.aAssistance = ASSISTANCE_NOT_ANSWERED;
		this.aImageType = aImageType;
	}

	public Event() { }
	
	public int getImageResource() {
		
		return aImageType;
		
	}
	
	
}
