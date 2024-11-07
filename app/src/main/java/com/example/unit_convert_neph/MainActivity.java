package com.example.unit_convert_neph;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner conversionTypeSpinner;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private EditText editTextText;
    private TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionTypeSpinner = findViewById(R.id.conversionTypeSpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(adapter);

        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFromSpinnerUnits(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateFromSpinnerUnits(int position) {
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
        });
    }

    public void handleClicked(View v) {
        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();
        String conversionType = conversionTypeSpinner.getSelectedItem().toString();
        EditText editText = findViewById(R.id.editTextText);
        String input = editText.getText().toString();

        double intInput;
        try {
            intInput = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        double converted = 0;
        boolean conversionFound = true;

        switch (conversionType) {
            case "Distance":
                switch (fromUnit) {
                    case "Kilometers":
                        if (toUnit.equals("Meters")) {
                            converted = intInput * 1000;
                        } else if (toUnit.equals("Miles")) {
                            converted = intInput / 1.60934;
                        } else if (toUnit.equals("Feet")) {
                            converted = intInput * 3280.84;
                        } else if (toUnit.equals("Inches")) {
                            converted = intInput * 39370.1;
                        }
                        break;
                    case "Miles":
                        if (toUnit.equals("Kilometers")) {
                            converted = intInput * 1.60934;
                        } else if (toUnit.equals("Meters")) {
                            converted = intInput * 1609.34;
                        } else if (toUnit.equals("Feet")) {
                            converted = intInput * 5280;
                        } else if (toUnit.equals("Inches")) {
                            converted = intInput * 63360;
                        }
                        break;
                    case "Meters":
                        if (toUnit.equals("Kilometers")) {
                            converted = intInput / 1000;
                        } else if (toUnit.equals("Miles")) {
                            converted = intInput / 1609.34;
                        } else if (toUnit.equals("Feet")) {
                            converted = intInput * 3.28084;
                        } else if (toUnit.equals("Inches")) {
                            converted = intInput * 39.3701;
                        }
                        break;
                    case "Feet":
                        if (toUnit.equals("Kilometers")) {
                            converted = intInput / 3280.84;
                        } else if (toUnit.equals("Meters")) {
                            converted = intInput / 3.28084;
                        } else if (toUnit.equals("Miles")) {
                            converted = intInput / 5280;
                        } else if (toUnit.equals("Inches")) {
                            converted = intInput * 12;
                        }
                        break;
                    case "Inches":
                        if (toUnit.equals("Kilometers")) {
                            converted = intInput / 39370.1;
                        } else if (toUnit.equals("Meters")) {
                            converted = intInput / 39.3701;
                        } else if (toUnit.equals("Feet")) {
                            converted = intInput / 12;
                        } else if (toUnit.equals("Miles")) {
                            converted = intInput / 63360;
                        }
                        break;
                    default:
                        conversionFound = false;
                        break;
                }
                break;

            // Add cases for other conversion types...

            default:
                conversionFound = false;
        }

        output = findViewById(R.id.output);
        output.setText(String.valueOf(converted));

    }
}
