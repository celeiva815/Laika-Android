package social.laika.app.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Request;

import java.util.List;

import social.laika.app.R;
import social.laika.app.models.Photo;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.ImageResponse;
import social.laika.app.utils.Do;

public class AlbumAdapter extends BaseAdapter {

    private Context mContext;
    private List<Photo> mPhotos;
    public SquareImageView mImageView;

    public AlbumAdapter(Context context, List<Photo> photos) {

        this.mContext = context;
        this.mPhotos = photos;

    }

    @Override
    public int getCount() {

        return mPhotos.size();
    }

    @Override
    public Object getItem(int position) {

        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {

        return mPhotos.get(position).mPhotoId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Photo photo = mPhotos.get(position);

        if (view == null) {
            // if it's not recycled, initialize some attributes
            mImageView = new SquareImageView(mContext);
            mImageView.setAdjustViewBounds(false);
            mImageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(0, 0, 0, 0);
            mImageView.setBackgroundColor(Do.getColor(mContext, R.color.laika_picture_background));

        } else {

            mImageView = (SquareImageView) view;
        }

        if (!Do.isNullOrEmpty(photo.mUrlThumbnail) && mImageView.getDrawable() == null &&
                Do.isNullOrEmpty(photo.mUriLocal)) {

            ImageResponse response = new ImageResponse(mImageView, photo, mContext);
            Request request = Api.imageRequest(photo.mUrlThumbnail, mImageView, response,
                    response);

            VolleyManager.getInstance(mContext).addToRequestQueue(request);

        } else if (!Do.isNullOrEmpty(photo.mUriLocal)){

            mImageView.setImageURI(Uri.parse(photo.mUriLocal));
        }

        return mImageView;
    }

    private class SquareImageView extends ImageView {

        public SquareImageView(Context context) {
            super(context);
        }

        public SquareImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
        {
            final int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
            setMeasuredDimension(width, width);
        }

        @Override
        protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh)
        {
            super.onSizeChanged(w, w, oldw, oldh);
        }
    }
}
