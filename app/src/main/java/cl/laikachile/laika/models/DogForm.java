package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;

@Table(name = DogForm.TABLE_NAME)
public class DogForm extends Model {

	public final static String TABLE_NAME = "dog_forms";
	public final static String COLUMN_SPACE = "space";
	public final static String COLUMN_FREE_TIME = "free_time";
	public final static String COLUMN_PARTNER = "partner";
	public final static String COLUMN_GENDER = "gender";
	public final static String COLUMN_SIZE = "size";
	public final static String COLUMN_PERSONALITY = "personality";
	public final static String COLUMN_USER_ID = "user_id";
	
	@Column(name = COLUMN_SPACE)
	public int aSpace;
	
	@Column(name = COLUMN_FREE_TIME)
	public int aFreeTime;
	
	@Column(name = COLUMN_PARTNER)
	public int aPartner;
	
	@Column(name = COLUMN_GENDER)
	public int aGender;
	
	@Column(name = COLUMN_SIZE)
	public int aSize;
	
	@Column(name = COLUMN_PERSONALITY)
	public String aPersonality;
	
	@Column(name = COLUMN_USER_ID)
	public int aUserId;

	public DogForm() { }

	public DogForm(int aSpace, int aFreeTime, int aPartner, int aGender,
			int aSize, String aPersonality, int aUserId) {

		this.aSpace = aSpace;
		this.aFreeTime = aFreeTime;
		this.aPartner = aPartner;
		this.aGender = aGender;
		this.aSize = aSize;
		this.aPersonality = aPersonality;
		this.aUserId = aUserId;
	}
	
	
	
	
	
	
}
