package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.utils.DB;

@Table(name = Owner.TABLE_NAME)
public class Owner extends Model {
	
	public final static String TABLE_NAME = "owners";
    public final static String COLUMN_OWNER_ID = "owner_id";
	public final static String COLUMN_OWNER_NAME = "owner_name";
	public final static String COLUMN_FIRST_NAME = "first_name";
    public final static String COLUMN_SECOND_NAME = "second_name";
	public final static String COLUMN_FIRST_SURNAME = "first_surname";
	public final static String COLUMN_SECOND_SURNAME = "second_surname";
    public final static String COLUMN_BIRTHDAY = "birthday";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_EMAIL = "email";
    public final static String COLUMN_PHONE = "phone";
    public final static String COLUMN_ADDRESS = "address";
    public final static String COLUMN_ADDRESS_NUMBER = "address_number";
    public final static String COLUMN_APARTMENT_NUMBER = "aparment_number";
    public final static String COLUMN_TOWN = "town";
    public final static String COLUMN_CITY = "city";
    public final static String COLUMN_COUNTRY = "country";

	@Column(name = COLUMN_OWNER_ID)
	public int mOwnerId;

    @Column(name = COLUMN_OWNER_NAME) //No va a tener usuario
    public String mOwnerName;
	
	@Column(name = COLUMN_FIRST_NAME)
	public String mFirstName;
	
	@Column(name = COLUMN_SECOND_NAME)
	public String mSecondName;

    @Column(name = COLUMN_FIRST_SURNAME)
    public String mFirstSurname;

    @Column(name = COLUMN_SECOND_SURNAME)
    public String mSecondSurname;

    @Column(name = COLUMN_BIRTHDAY)
    public String mBirthday;

    @Column(name = COLUMN_GENDER)
    public int mGender;

    @Column(name = COLUMN_EMAIL)
    public String mEmail;

    @Column(name = COLUMN_PHONE)
    public String mPhone;

    @Column(name = COLUMN_ADDRESS)
    public String mAddress;

    @Column(name = COLUMN_ADDRESS_NUMBER)
    public String mAddressNumber;

    @Column(name = COLUMN_APARTMENT_NUMBER)
    public String mApartmentNumber;

    @Column(name = COLUMN_TOWN)
    public String mTown;

    @Column(name = COLUMN_CITY)
    public String mCity;

    @Column(name = COLUMN_COUNTRY)
    public String mCountry;

    //Not in the database
    public int mRole;

    public Owner(int mOwnerId, String mOwnerName, String mFirstName, String mSecondName,
                 String mFirstSurname, String mSecondSurname, String mBirthday, int mGender,
                 String mEmail, String mPhone, String mAddress, String mAddressNumber,
                 String mApartmentNumber, String mTown, String mCity, String mCountry) {

        this.mOwnerId = mOwnerId;
        this.mOwnerName = mOwnerName;
        this.mFirstName = mFirstName;
        this.mSecondName = mSecondName;
        this.mFirstSurname = mFirstSurname;
        this.mSecondSurname = mSecondSurname;
        this.mBirthday = mBirthday;
        this.mGender = mGender;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
        this.mAddressNumber = mAddressNumber;
        this.mApartmentNumber = mApartmentNumber;
        this.mTown = mTown;
        this.mCity = mCity;
        this.mCountry = mCountry;
    }

    public Owner() {}

    public String getFullName() {

        return mFirstName + " " + mFirstSurname + " " + mSecondSurname;
    }

    public void addDog(Dog dog, int role) {

        OwnerDog ownerDog = new OwnerDog(this.mOwnerId, dog.mDogId, role);
        ownerDog.save();

    }

    public static List<Owner> getOwners(List<OwnerDog> ownerDogs) {

        List<Owner> owners = new ArrayList<>();

        for (OwnerDog ownerDog : ownerDogs) {

            String condition = COLUMN_OWNER_ID + DB._EQUALS_ + ownerDog.mOwnerId;
            Owner owner = new Select().from(Owner.class).where(condition).executeSingle();

            if (owner != null) {

                owner.mRole = ownerDog.mRole;
                owners.add(owner);
            }
        }

        return owners;
    }

    public static Owner getUser(int userId) {

        String condition = COLUMN_OWNER_ID + DB._EQUALS_ + Integer.toString(userId);
        return new Select().from(Owner.class).where(condition).executeSingle();

    }
}
