package com.thoughtinteract.brewawebonlineapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.thoughtinteract.brewawebonlineapp.R;

/**
 * Created by AzaharSheikh on 21-09-2016.
 */
public class Product_Details_Activity extends AppCompatActivity {
    TextView txt_title,txt_p_details,txt_p_address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_layout);
        txt_title=(TextView)findViewById(R.id.txt_title);
        txt_p_details=(TextView)findViewById(R.id.txt_p_details);
        txt_p_address=(TextView)findViewById(R.id.txt_p_address);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            String title = bundle.getString("title");
            String details = bundle.getString("details");
            String address = bundle.getString("address");
            txt_title.setText(title);
            txt_p_details.setText(details);
            txt_p_address.setText(address);


        }

    }
}
