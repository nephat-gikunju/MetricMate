package com.example.unit_convert_neph;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner conversionTypeSpinner = findViewById(R.id.conversionTypeSpinner);
        Spinner fromSpinner = findViewById(R.id.fromSpinner);
        Spinner toSpinner = findViewById(R.id.toSpinner);

        // Set adapter for conversion type selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(adapter);

        // Listen for conversion type selection to update "From" units
        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFromSpinnerUnits(position, fromSpinner, toSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateFromSpinnerUnits(int position, Spinner fromSpinner, Spinner toSpinner) {
        int unitArrayId;
        switch (position) {
            case 0: unitArrayId = R.array.distance_units; break;
            case 1: unitArrayId = R.array.weight_unit; break;
            case 2: unitArrayId = R.array.temperature_units; break;
            case 3: unitArrayId = R.array.volume_units; break;
            case 4: unitArrayId = R.array.speed_units; break;
            default: unitArrayId = R.array.distance_units;
        }

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this,
                unitArrayId, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(unitAdapter);

        // Set listener to filter units in "To" spinner based on "From" selection
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int fromPosition, long id) {
                String selectedFromUnit = parent.getItemAtPosition(fromPosition).toString();

                List<String> filteredUnits = new ArrayList<>();
                for (int i = 0; i < unitAdapter.getCount(); i++) {
                    String unit = unitAdapter.getItem(i).toString();
                    if (!unit.equals(selectedFromUnit)) {
                        filteredUnits.add(unit);
                    }
                }

                ArrayAdapter<String> toUnitAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_item, filteredUnits);
                toUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                toSpinner.setAdapter(toUnitAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            public void handleClicked(View v){





            }
        });
    }
}
