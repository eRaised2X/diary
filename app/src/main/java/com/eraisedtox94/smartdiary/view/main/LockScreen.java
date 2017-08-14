package com.eraisedtox94.smartdiary.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eraisedtox94.smartdiary.R;

/**
 * Created by spraful on 04-08-2017.
 */
public class LockScreen extends Activity{

    GridView gridview ;
    Button btntmp;

    ImageView iv1;
    ImageView iv1b;
    ImageView iv2;
    ImageView iv2b;
    ImageView iv3;
    ImageView iv3b;
    ImageView iv4;
    ImageView iv4b;
    ImageView ivClearPasscode;

    ImageView [] ivArray = new ImageView[4];
    ImageView [] ivArray2 = new ImageView[4];

    int []pass =new int[4];
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
        //gridview = (GridView)findViewById(R.id.gv_num_keypad);
        btntmp =(Button)findViewById(R.id.btnGvTmp);

        iv1 = (ImageView)findViewById(R.id.ivPasscode1);
        iv1b = (ImageView)findViewById(R.id.ivPasscode1b);

        iv2 = (ImageView)findViewById(R.id.ivPasscode2);
        iv2b = (ImageView)findViewById(R.id.ivPasscode2b);

        iv3 = (ImageView)findViewById(R.id.ivPasscode3);
        iv3b = (ImageView)findViewById(R.id.ivPasscode3b);

        iv4 = (ImageView)findViewById(R.id.ivPasscode4);
        iv4b = (ImageView)findViewById(R.id.ivPasscode4b);

        ivArray = new ImageView[]{iv1,iv2,iv3,iv4};
        ivArray2 = new ImageView[]{iv1b,iv2b,iv3b,iv4b};

        ivClearPasscode =(ImageView)findViewById(R.id.iv_keypad_key_reset);

        //String[] numbers  = {"1","2","3","4","5","6","7","8","9","","0",""};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                //android.R.layout.simple_list_item_1, numbers);

        //gridview.setAdapter(adapter);

        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),(position+1)+"", Toast.LENGTH_SHORT).show();
                updatePasscodeArray(position+1,0);
            }
        });*/

        btntmp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), "You made a mess", Toast.LENGTH_LONG).show();
                //Intent myIntent = new Intent(LockScreen.this, MainActivity.class);
                //startActivity(myIntent);
                String str= pass[0] +":"+ pass[1]+":"+ pass[2]+ ":"+ pass[3];
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        });

        //clear passcode
        ivClearPasscode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                iv4.setVisibility(View.VISIBLE);

                iv1b.setVisibility(View.GONE);
                iv2b.setVisibility(View.GONE);
                iv3b.setVisibility(View.GONE);
                iv4b.setVisibility(View.GONE);
                updatePasscodeArray(-1,1);
            }
        });
    }

    public void updatePasscodeArray(int value,int flag_type){
        if(flag_type==0){
            if(index<4){
                pass[index]=value;
                ((ImageView)ivArray2[index]).setVisibility(View.VISIBLE);
                ((ImageView)ivArray[index]).setVisibility(View.GONE);
                index++;
            }
        }
        else{
            java.util.Arrays.fill(pass,-1);
            index=0;
        }
    }


    String str= "";
    public void customKeypadClick(View v){
        switch (v.getId()){
            case R.id.keypad_key0:
                str= "0";
                updatePasscodeArray(0,0);
                break;

            case R.id.keypad_key1:
                str= "1";
                updatePasscodeArray(1,0);
                break;

            case R.id.keypad_key2:
                str= "2";
                updatePasscodeArray(2,0);
                break;

            case R.id.keypad_key3:
                str= "3";
                updatePasscodeArray(3,0);
                break;

            case R.id.keypad_key4:
                str= "4";
                updatePasscodeArray(4,0);
                break;

            case R.id.keypad_key5:
                str= "5";
                updatePasscodeArray(5,0);
                break;

            case R.id.keypad_key6:
                str= "6";
                updatePasscodeArray(6,0);
                break;

            case R.id.keypad_key7:
                str= "7";
                updatePasscodeArray(7,0);
                break;

            case R.id.keypad_key8:
                str= "8";
                updatePasscodeArray(8,0);
                break;

            case R.id.keypad_key9:
                str= "9";
                updatePasscodeArray(9,0);
                break;
            default:
                break;
        }
    }
}