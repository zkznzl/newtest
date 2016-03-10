package day5.mystopwatch;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Chronometer chrono;
    TextView timer;
    TextView txtRecord;
    Button btnstart, btnrecord;


    final int INIT= 0;
    final int RUN = 1;
    final int PAUSE = 2;

    int c_status = INIT;

    long baseTime;
    long pauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        //chrono = (Chronometer)findViewById(R.id.chrono);
        timer = (TextView)findViewById(R.id.timer);
        txtRecord = (TextView)findViewById(R.id.txtRecord);
        btnstart = (Button)findViewById(R.id.btnstart);
        btnrecord = (Button)findViewById(R.id.btnrecord);
    }

    public void myClick(View v){

              /*  if(v.getId()==R.id.btnStart){
                    chrono.start();
                }else if(v.getId() == R.id.btnStop){
                    chrono.stop();
                }else if(v.getId() == R.id.btnReset){

                    chrono.setBase(SystemClock.elapsedRealtime());
                }
                */
        if(v.getId() == R.id.btnstart){
            switch (c_status){
                case INIT:
                    baseTime = SystemClock.elapsedRealtime();
                    myHandler.sendEmptyMessage(0);
                    btnstart.setText("멈춤");
                    btnrecord.setEnabled(true);
                    c_status = RUN;
                    break;
                case RUN:
                    myHandler.removeMessages(0);
                    pauseTime = SystemClock.elapsedRealtime();
                    btnstart.setText("시작");
                    btnrecord.setText("리셋");
                    c_status = PAUSE;
                    break;
                case PAUSE:
                    long now = SystemClock.elapsedRealtime();
                    baseTime = baseTime + (now - pauseTime);
                    myHandler.sendEmptyMessage(0);
                    btnstart.setText("멈춤");
                    btnrecord.setText("기록");
                    c_status = RUN;
                    break;

            }
        }else if(v.getId()== R.id.btnrecord){
            switch (c_status){
                case RUN:
                    String str = txtRecord.getText().toString();
                    str +=String.format("%s\n", getTime());
                    txtRecord.setText(str);
                    break;
                case PAUSE:
                    myHandler.removeMessages(0);
                    btnstart.setText("시작");
                    btnrecord.setText("기록");
                    btnrecord.setEnabled(false);
                    timer.setText("00:00:00");
                    c_status = INIT;
                    break;
            }
        }
    }
    Handler myHandler = new Handler(){
        public void handleMessage(Message msg) {
            timer.setText(getTime());
            myHandler.sendEmptyMessage(0);
        }
    };

    String getTime(){
        long nowTime = SystemClock.elapsedRealtime();
        long outTime =  nowTime - baseTime;
        String outStr = String.format("%02d: %02d: %02d",
                    outTime/1000/60,
                    (outTime/1000)%60,
                    (outTime%1000)/10);
            return outStr;


    }

}
