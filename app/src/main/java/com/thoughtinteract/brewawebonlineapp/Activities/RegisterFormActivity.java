package com.thoughtinteract.brewawebonlineapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thoughtinteract.brewawebonlineapp.R;

/**
 * Created by AzaharSheikh on 26-09-2016.
 */
public class RegisterFormActivity extends Activity {
    Button bt_close;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_form);
        bt_close=(Button)findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                RegisterFormActivity.this.overridePendingTransition(R.anim.left_to_right,
                        R.anim.right_to_left);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right,
                R.anim.right_to_left);
        finish();
    }
}
