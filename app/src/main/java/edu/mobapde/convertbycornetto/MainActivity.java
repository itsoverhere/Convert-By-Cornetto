package edu.mobapde.convertbycornetto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvLeft, rvRight;
    TextView tvRight, tvequals;
    EditText etLeft;
    UnitCursorAdapter ucaRight, ucaLeft;
    DatabaseOpenHelper dbHelper;

    private int leftUnitId, rightUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLeft = (RecyclerView) findViewById(R.id.rv_left);
        rvRight = (RecyclerView) findViewById(R.id.rv_right);
        etLeft = (EditText) findViewById(R.id.et_unit_convert_left);
        tvRight = (TextView) findViewById(R.id.tv_unit_convert_right);
        tvequals = (TextView) findViewById(R.id.tv_equals);

        ucaRight = new UnitCursorAdapter(getBaseContext(), null);
        ucaLeft = new UnitCursorAdapter(getBaseContext(), null);

        rvLeft.setAdapter(ucaLeft);
        rvRight.setAdapter(ucaRight);
        rvLeft.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvRight.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        dbHelper = new DatabaseOpenHelper(getBaseContext());

        ucaLeft.setmOnItemClickListener(new UnitCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                leftUnitId = id;
                updateTvRight();
            }

            @Override
            public void onItemLongClick(int id) {
                viewUnitConvert(id);
            }
        });

        ucaRight.setmOnItemClickListener(new UnitCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                rightUnitId = id;
                updateTvRight();
            }

            @Override
            public void onItemLongClick(int id) {
                viewUnitConvert(id);
            }
        });

        etLeft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateTvRight();
            }
        });

        tvequals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddUnitActivity.class));
            }
        });
    }


    public void viewUnitConvert(int id){
        if(id != 1) { // Can't convert Cornettos! It should always remain 1.
            Intent i = new Intent(getBaseContext(), EditUnitActivity.class);
            i.putExtra(Unit.COLUMN_ID, id);
            startActivity(i);
        }else{
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("1 Cornetto == 1 Cornetto.\n You cheeky monkey.")
                    .setPositiveButton("Boo.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    public void updateTvRight(){
        if(!etLeft.getText().toString().trim().isEmpty()) {
            double tvLeftUnit = Double.parseDouble(etLeft.getText().toString());
            DecimalFormat df = new DecimalFormat("###.##");
            double conversion = dbHelper.getConversion(leftUnitId, rightUnitId, tvLeftUnit);
            tvRight.setText(String.valueOf(df.format(conversion)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = dbHelper.queryAllUnits();
        ucaRight.swapCursor(cursor);
        ucaLeft.swapCursor(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
