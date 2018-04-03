package ui.dom.com.tez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ui.dom.com.Button.OnStateChangeListener;
import ui.dom.com.Button.Swipe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Swipe swipe=(Swipe) findViewById(R.id.swipe);
        swipe.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(int active) {
                if(active==0){
                    Toast.makeText(getApplicationContext(),"CENTER",Toast.LENGTH_SHORT).show();

                }else if(active==1){
                    Toast.makeText(getApplicationContext(),"TOP",Toast.LENGTH_SHORT).show();
                }else if(active==2){
                    Toast.makeText(getApplicationContext(),"BOTTOM",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
