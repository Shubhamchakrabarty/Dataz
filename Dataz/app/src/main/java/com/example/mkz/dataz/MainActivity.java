package com.example.mkz.dataz;

import android.graphics.Color;
import android.net.TrafficStats;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private long mStartRX = 0;
    private long mStartTX = 0;
    private String packval;


    private TextView remval;

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            TextView total= (TextView) findViewById(R.id.total);
            TextView dataval = (TextView) findViewById(R.id.dataval);
            long rxBytes = TrafficStats.getMobileRxPackets()-mStartRX;
            long txBytes = TrafficStats.getMobileTxPackets()-mStartTX;

            long totalBytes = rxBytes+txBytes;
            long sizeofpack = Long.parseLong(packval);
            long remainingdata = sizeofpack-totalBytes;


            total.setText("Total Data :"+Long.toString(totalBytes));
            dataval.setText(Long.toString(totalBytes));

            if(remainingdata>0)
            {
                remval.setText(Long.toString(remainingdata));
            }
            else
            {
                remval.setText("Limit Exceeded");
                remval.setTextColor(Color.RED);
            }
            handler.postDelayed(mRunnable,1000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView sizepack = (TextView) findViewById(R.id.sizepack);
        remval = (TextView) findViewById(R.id.remval);


        Bundle bundle = getIntent().getExtras();
        packval = bundle.getString("packval");
        sizepack.setText("Pack Size = "+packval);


        mStartRX= TrafficStats.getMobileRxPackets();
        mStartTX= TrafficStats.getMobileTxPackets();



        if(mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(" This service is unsupported ");
            alert.show();
        }
        else
        {
            handler.postDelayed(mRunnable, 1000);
        }


    }
}
