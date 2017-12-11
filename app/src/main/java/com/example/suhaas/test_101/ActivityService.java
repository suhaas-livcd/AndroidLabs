package com.example.suhaas.test_101;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class ActivityService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ToggleButton service_state_toggle = findViewById(R.id.toggleButton_servicestate);
        service_state_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Intent intent = new Intent(ActivityService.this,MyService.class);
                if (isChecked){
                    startService(intent);
                }else{
                    stopService(intent);
                }
            }
        });

        ToggleButton intent_service_state = findViewById(R.id.toggleButton_intentservice);
        intent_service_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent(ActivityService.this,MyIntentService.class);
                if (b){
                    startService(intent);
                }else{
                    stopService(intent);
                }
            }
        });

        Button button_list = findViewById(R.id.button_list_Services);
        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isrunning = isMyServiceRunning(ActivityService.class);
                List list = getMyServiceRunningList(ActivityService.class);
                Toast.makeText(getApplicationContext(),"ActivityService.class "+isrunning,Toast.LENGTH_SHORT).show();
                System.out.println(list.get(0));
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private List getMyServiceRunningList(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        return manager.getRunningServices(Integer.MAX_VALUE);
    }

}
