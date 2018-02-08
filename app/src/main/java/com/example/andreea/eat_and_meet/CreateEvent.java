package com.example.andreea.eat_and_meet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by Riccardo on 08/02/2018.
 */

public class CreateEvent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pranzo:
                if (checked)

                    break;
            case R.id.radio_cena:
                if (checked)

                    break;
        }
    }
}
