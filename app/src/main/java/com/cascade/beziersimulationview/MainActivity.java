package com.cascade.beziersimulationview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private BezierSimulationView bezierSimulationViewShowCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bezierSimulationViewShowCase = (BezierSimulationView) findViewById(R.id.bsv_show_case);

        SeekBar seekBarSetRatio = (SeekBar) findViewById(R.id.sb_set_ratio);
        seekBarSetRatio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bezierSimulationViewShowCase.setRatio(i / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
