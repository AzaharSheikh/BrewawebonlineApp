package com.thoughtinteract.brewawebonlineapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.thoughtinteract.brewawebonlineapp.ApplicationClass.AppController;
import com.thoughtinteract.brewawebonlineapp.Model.Product;
import com.thoughtinteract.brewawebonlineapp.R;

import java.util.List;

/**
 * Created by AzaharSheikh on 20-09-2016.
 */
public class ProductCustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Product> productItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ProductCustomListAdapter(Activity activity, List<Product> productItems) {
        this.activity = activity;
        this.productItems = productItems;

    }

    @Override
    public int getCount() {
        return productItems.size();
    }

    @Override
    public Object getItem(int position) {
        return productItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.main_page, null);
        imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView txt_p_details = (TextView) convertView.findViewById(R.id.txt_p_details);
        TextView p_address = (TextView) convertView.findViewById(R.id.p_address);
        // getting movie data for the row
        Product m = productItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());
        txt_p_details.setText(m.getP_details());
        p_address.setText(m.getP_details());

        return convertView;
    }
}
