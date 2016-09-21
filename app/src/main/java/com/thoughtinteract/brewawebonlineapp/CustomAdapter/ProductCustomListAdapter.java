package com.thoughtinteract.brewawebonlineapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.thoughtinteract.brewawebonlineapp.Activities.Product_Details_Activity;
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
        Button btn_site_visit=(Button)convertView.findViewById(R.id.btn_site_visit);
        // getting movie data for the row
        final Product m = productItems.get(position);
        btn_site_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Toast.makeText(activity,m.getTitle(),Toast.LENGTH_LONG).show();

                Intent i = new Intent(activity, Product_Details_Activity.class);
                bundle.putString("title", m.getTitle());
                bundle.putString("details", m.getP_details());
                bundle.putString("address", m.getP_address());
                i.putExtras(bundle);
                activity.startActivity(i);
            }
        });
        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());
        txt_p_details.setText(m.getP_details());
        p_address.setText(m.getP_address());

        return convertView;
    }
}
