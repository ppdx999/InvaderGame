package com.example.fujis.invadergame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningActivity extends AppCompatActivity implements View.OnClickListener{

    private Button LEVEL1,LEVEL2,LEVEL3,LEVEL4;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        LEVEL1=findViewById(R.id.LEVEL_1);
        LEVEL2=findViewById(R.id.LEVEL_2);
        LEVEL3=findViewById(R.id.LEVEL_3);
        LEVEL4=findViewById(R.id.LEVEL_4);
        LEVEL1.setOnClickListener(this);
        LEVEL2.setOnClickListener(this);
        LEVEL3.setOnClickListener(this);
        LEVEL4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent=new Intent(this,MainActivity.class);
        bundle=new Bundle();
        switch (v.getId()){
            case R.id.LEVEL_1:
                bundle.putInt("LEVEL",1);
                break;
            case R.id.LEVEL_2:
                bundle.putInt("LEVEL",2);
                break;
            case R.id.LEVEL_3:
                bundle.putInt("LEVEL",3);
                break;
            case R.id.LEVEL_4:
                bundle.putInt("LEVEL",4);
                break;
        }
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
