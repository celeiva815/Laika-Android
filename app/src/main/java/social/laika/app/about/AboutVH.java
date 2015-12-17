package social.laika.app.about;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import social.laika.app.R;

public class AboutVH extends RecyclerView.ViewHolder {
    protected TextView title;
    protected TextView subtitle;
    protected View root;

    public AboutVH(View root) {
        super(root);
        this.root = root;
        title = (TextView) root.findViewById(R.id.title);
        subtitle = (TextView) root.findViewById(R.id.subtitle);
    }

    public void hideSubtitle() {
        if (subtitle != null) {
            subtitle.setVisibility(View.GONE);
        }
    }

    public void populateCard(AboutItem item) {
        if (item == null) {
            return;
        }

        if (title != null) {
            title.setText(item.getTitle());
        }

        if (subtitle != null) {
            subtitle.setText(item.getSubtitle());
        }

        switch (item.getType()) {
            case POLICIES:
                hideSubtitle();
                break;
            case TERMS:
                hideSubtitle();
                break;
        }

    }

}