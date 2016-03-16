package edu.mobapde.convertbycornetto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditUnitActivity extends ActionBarActivity {

    EditText etUnitName, etEquivalence;
    Button buttonCancel, buttonSave, buttonDelete;
    DatabaseOpenHelper dbHelper;
    Unit currentUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unit);

        etUnitName = (EditText) findViewById(R.id.et_unitname);
        etEquivalence = (EditText) findViewById(R.id.et_equivalence);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonDelete = (Button) findViewById(R.id.button_delete);

        int dbid = getIntent().getExtras().getInt(Unit.COLUMN_ID);

        dbHelper = new DatabaseOpenHelper(getBaseContext());
        currentUnit = dbHelper.queryUnit(dbid);

        etUnitName.setText(currentUnit.getUnitName());
        etEquivalence.setText(String.valueOf(currentUnit.getCornettoEquivalence()));

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteUnit(currentUnit.getId());
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unit u = new Unit(etUnitName.getText().toString(),
                        Double.parseDouble(etEquivalence.getText().toString()));
                u.setId(currentUnit.getId());
                new DatabaseOpenHelper(getBaseContext()).updateUnit(u);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_unit, menu);
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
