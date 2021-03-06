package arsibi_has_no_website.colorfulscreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int red=0;
    int green=0;
    int blue=0;
    TextView redText;
    TextView blueText;
    TextView greenText;
    RelativeLayout RelLayout;
    SharedPreferences sharedPref ;
        public void handleChanges(int what) {
            switch (what) {
                case 1:
                    redText.setText(String.format("%d",red));
                    break;
                case 2:
                    greenText.setText(String.format("%d",green));
                    break;
                case 3:
                    blueText.setText(String.format("%d",blue));
                    break;
            }
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        RelLayout= (RelativeLayout)findViewById(R.id.rel_layout);
        redText=(TextView)findViewById(R.id.redcount);
        greenText=(TextView)findViewById(R.id.greencount);
        blueText=(TextView)findViewById(R.id.bluecount);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        loadData();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadData();
    }
    public void redder(View v) {
        red=(red+5)<256?red+5:0;
        setBackground();
        handleChanges(1);
    }
    public void greener(View v) {
        green=(green+5)<256?green+5:0;
        setBackground();
        handleChanges(2);
    }
    public void bluer(View v){
        blue=(blue+5)<256?blue+5:0;
        setBackground();
        handleChanges(3);
    }
    public void resetColors(View v){
        blue=0;
        red=0;
        green=0;
        handleChanges(1);
        handleChanges(2);
        handleChanges(3);
        setBackground();
    }
    public void loadData(){
        red=sharedPref.getInt("red",0);
        blue=sharedPref.getInt("blue",0);
        green=sharedPref.getInt("green",0);
        handleChanges(1);
        handleChanges(2);
        handleChanges(3);
        setBackground();
    }
    public String colorString(int r,int g,int b){
        StringBuilder builder = new StringBuilder("#");
        builder.append(String.format("%2s",Integer.toHexString(r)));
        builder.append(String.format("%2s",Integer.toHexString(g)));
        builder.append(String.format("%2s",Integer.toHexString(b)));
        return builder.toString().replaceAll(" ","0");
    }
    public void setBackground(){
        int tmpr,tmpb,tmpg;
        tmpr=(128+red)%256;
        tmpg=(128+green)%256;
        tmpb=(128+blue)%256;
        RelLayout.setBackgroundColor(Color.parseColor(colorString(red,green,blue)));
        redText.setTextColor(Color.parseColor(colorString(tmpr,tmpg,tmpb)));
        blueText.setTextColor(Color.parseColor(colorString(tmpr,tmpg,tmpb)));
        greenText.setTextColor(Color.parseColor(colorString(tmpr,tmpg,tmpb)));
    }
    public void editData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("red",red);
        editor.putInt("green",green);
        editor.putInt("blue",blue);
        editor.commit();
    }
    @Override
    protected void onStop() {
        editData();
        super.onStop();

    }

}
