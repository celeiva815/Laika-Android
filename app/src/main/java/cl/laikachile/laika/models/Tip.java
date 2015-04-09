package cl.laikachile.laika.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cl.laikachile.laika.R;

@Table(name = Tip.TABLE_NAME)
public class Tip extends Model {

    public static int ID = 1;

	public static final String TABLE_NAME = "tips";
    public static final String COLUMN_TIP_ID = "tip_id";
    public static final String COLUMN_SPONSOR_ID = "sponsor_id";
    public static final String COLUMN_SPONSOR_NAME = "sponsor_name";
    public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_BODY = "body";
    public static final String COLUMN_URL_IMAGE = "url_image";
    public static final String COLUMN_URL_TIP = "url_tip";
	public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IS_PAID = "is_paid";

    @Column(name = COLUMN_TIP_ID)
    public int mTipId;

    @Column(name = COLUMN_SPONSOR_NAME)
    public String mSponsorName;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_BODY)
    public String mBody;

    @Column(name = COLUMN_URL_IMAGE)
    public int mUrlImage; //FIXME

	@Column(name = COLUMN_TYPE)
	public int mType;


	public Tip() { }

    public Tip(int mTipId, String mSponsorName, String mTitle, String mBody, int mUrlImage,
               int mType) {

        this.mTipId = mTipId;
        this.mSponsorName = mSponsorName;
        this.mTitle = mTitle;
        this.mBody = mBody;
        this.mUrlImage = mUrlImage;
        this.mType = mType;
    }
}
