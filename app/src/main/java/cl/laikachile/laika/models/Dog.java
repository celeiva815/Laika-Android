package cl.laikachile.laika.models;

import java.util.Calendar;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;

import cl.laikachile.laika.utils.Do;


@Table(name = Dog.TABLE_NAME)
public class Dog extends Model {

	public final static String TABLE_NAME = "dogs";
    public final static String COLUMN_DOG_ID = "dog_id";
	public final static String COLUMN_NAME = "name";
	public final static String COLUMN_BIRTH = "birth";
	public final static String COLUMN_TYPE = "type";
	public final static String COLUMN_BREED = "breed";
	public final static String COLUMN_SPACE = "space";
	public final static String COLUMN_FREE_TIME = "free_time";
	public final static String COLUMN_PARTNER = "partner";
	public final static String COLUMN_GENDER = "gender";
	public final static String COLUMN_SIZE = "size";
	public final static String COLUMN_PERSONALITY = "personality";
	public final static String COLUMN_STATUS = "status";
	public final static String COLUMN_USER_ID = "user_id";
	
	public final static int STATUS_OWN = 1;
	public final static int STATUS_ADOPTED = 2;
	public final static int STATUS_PUBLISH = 3;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

	@Column(name = COLUMN_NAME)
	public String mName;

	@Column(name = COLUMN_BIRTH)
	public String aBirth;

	@Column(name = COLUMN_TYPE)
	public int aType;

	@Column(name = COLUMN_BREED)
	public String aBreed;

	@Column(name = COLUMN_FREE_TIME)
	public int aFreeTime;

	@Column(name = COLUMN_PARTNER)
	public int aPartner;

	@Column(name = COLUMN_GENDER)
	public String aGender;

    @Column(name = COLUMN_SPACE)
    public int aSpace;

	@Column(name = COLUMN_SIZE)
	public String aSize;
	
	@Column(name = COLUMN_PERSONALITY)
	public String aPersonality;
	
	@Column(name = COLUMN_STATUS)
	public int aStatus;
	
	@Column(name = COLUMN_USER_ID)
	public int mOwnerId;
	
	//FIXME
	public String aStory;
	
	public int percentage;

	public Dog() { }

	public Dog(int mDogId, int space, String name, String birth, int type, String breed, int freeTime,
               int partner, String gender, String size,	String personality, int status, int userId, int percentage) {

        this.mDogId = mDogId;
		this.aSpace = space;
		this.mName = name;
		this.aBirth = birth;
		this.aType = type;
		this.aBreed = breed;
		this.aFreeTime = freeTime;
		this.aPartner = partner;
		this.aGender = gender;
		this.aSize = size;
		this.aPersonality = personality;
		this.aStatus = status;
		this.mOwnerId = userId;
		this.percentage = percentage;
	}

 	public void setaStory(String aStory) {
		this.aStory = aStory;
	}

	public String getaStory() {
		return aStory;
	}

	public String getAge() {
		
		Calendar dateOfBirth = Calendar.getInstance();  
		dateOfBirth.setTime(Do.stringToDate(aBirth, Do.DAY_FIRST));
		
		Calendar today = Calendar.getInstance();
		
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		
		if (today.get(Calendar.MONTH) < dateOfBirth.get(Calendar.MONTH)) {
			
			age--;  
		
		} else if (today.get(Calendar.MONTH) == dateOfBirth.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dateOfBirth.get(Calendar.DAY_OF_MONTH)) {
			age--;  
		}
		
		return Integer.toString(age) + " a�os";
	}
	
	public String getNameAndStatus() {
		
		switch (aStatus) {
		case Dog.STATUS_OWN:
			
			return this.mName;
			
		case Dog.STATUS_ADOPTED:
			
			return this.mName + " - (Adoptado)";
			
		case Dog.STATUS_PUBLISH:
	
			return this.mName + " - (En adopci�n)";

		default:
			
			return this.mName;
		}
		
	}

    public void addOwner(Owner owner, int role) {

        OwnerDog ownerDog = new OwnerDog(owner.mOwnerId, this.mDogId, role);
        ownerDog.save();

    }
}
