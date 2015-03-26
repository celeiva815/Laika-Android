package cl.laikachile.laika.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import cl.laikachile.laika.models.Photo;

public class AlbumAdapter extends BaseAdapter {

    private Context mContext;
    private List<Photo> mPhotos;

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

        SquareImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new SquareImageView(mContext);
            imageView.setAdjustViewBounds(false);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (SquareImageView) view;
        }

        imageView.setImageResource(mPhotos.get(position).mResource);

        return imageView;
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
