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
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int red=0;
    int green=0;
    int blue=0;
    SharedPreferences sharedPref ;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ((TextView)findViewById(R.id.redcount)).setText(String.format("%d",red));
                    break;
                case 2:
                    ((TextView)findViewById(R.id.greencount)).setText(String.format("%d",green));
                    break;
                case 3:
                    ((TextView)findViewById(R.id.bluecount)).setText(String.format("%d",blue));
                    break;
                case 4:
                    int tmpr,tmpb,tmpg;
                    tmpr=(128+red)%256;
                    tmpg=(128+green)%256;
                    tmpb=(128+blue)%256;

                    findViewById(R.id.rel_layout).setBackgroundColor(Color.parseColor(colorString(red,green,blue)));
                    ((TextView)findViewById(R.id.redcount)).setTextColor(Color.parseColor(colorString(tmpr,tmpg,tmpb)));
                    ((TextView)findViewById(R.id.bluecount)).setTextColor(Color.parseColor(colorString(tmpr,tmpg,tmpb)));
                    ((TextView)findViewById(R.id.greencount)).setTextColor(Color.parseColor(colorString(tmpr,tmpg,tmpb)));
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        loadData();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SeekBar redbar = (SeekBar)findViewById(R.id.redbar);
        redbar.setProgress(red);
        redbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red=progress;
                handler.sendEmptyMessage(1);
                setBackground();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        SeekBar bluebar = (SeekBar)findViewById(R.id.bluebar);
        bluebar.setProgress(blue);
        bluebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue=progress;
                handler.sendEmptyMessage(3);
                setBackground();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBar greenbar = (SeekBar)findViewById(R.id.greenbar);
        greenbar.setProgress(green);
        greenbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = progress;
                handler.sendEmptyMessage(2);
                setBackground();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.putInt();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadData();
    }
    public void resetColors(View v){
        blue=0;
        red=0;
        green=0;
        ((SeekBar)findViewById(R.id.redbar)).setProgress(red);
        ((SeekBar)findViewById(R.id.greenbar)).setProgress(green);
        ((SeekBar)findViewById(R.id.bluebar)).setProgress(blue);
        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
        handler.sendEmptyMessage(3);
        setBackground();
    }
    public void loadData(){
        red=sharedPref.getInt("red",0);
        blue=sharedPref.getInt("blue",0);
        green=sharedPref.getInt("green",0);
        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
        handler.sendEmptyMessage(3);
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
        handler.sendEmptyMessage(4);
    }
    public void editData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("red",red);
        editor.putInt("green",green);
        editor.putInt("blue",blue);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        editData();
        super.onDestroy();

    }

}
