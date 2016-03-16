package edu.mobapde.convertbycornetto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddUnitActivity extends AppCompatActivity {

    EditText etUnitName, etEquivalence;
    Button buttonCancel, buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        etUnitName = (EditText) findViewById(R.id.et_unitname);
        etEquivalence = (EditText) findViewById(R.id.et_equivalence);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonAdd = (Button) findViewById(R.id.button_add);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unit u = new Unit(etUnitName.getText().toString(),
                                  Double.parseDouble(etEquivalence.getText().toString()));
                new DatabaseOpenHelper(getBaseContext()).insertUnit(u);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_unit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
