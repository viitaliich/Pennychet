package com.example.pennychet;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import com.example.pennychet.database.AccumulatedExpense;
import com.example.pennychet.database.AppDatabase;
import com.example.pennychet.database.DataFromDB;
import com.example.pennychet.database.Expense;
import com.example.pennychet.ui.ExpandableHeightGridView;
import com.example.pennychet.ui.GridAdapter;
import com.example.pennychet.ui.GridModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MainActivity";

    // Calendar button inside category
    Button btnPickDate;
    String mDate;

    // Floating Action Button (FAB)
    FloatingActionButton mAddFab, mAddAlarmFab, mAddPersonFab;
    TextView addAlarmActionText, addPersonActionText;
    Boolean isAllFabsVisible;

    // PieChart
    PieChart pieChart;
    PieDataSet pieDataSet;

    // Categories buttons grid
    ArrayList<GridModel> gridModelArrayList;
    GridAdapter adapter;
    ExpandableHeightGridView ctgGridView;

    int[] btnIconArray = new int[] {
            R.drawable.ic_outline_shopping_cart_64,
            R.drawable.ic_outline_free_breakfast_24,
            R.drawable.ic_baseline_category_24,
            R.drawable.ic_outline_tram_24,
            R.drawable.ic_outline_takeout_dining_24,
            R.drawable.ic_baseline_favorite_border_24,
            R.drawable.ic_baseline_face_24,
            R.drawable.ic_outline_sports_esports_24,
            R.drawable.ic_outline_health_and_safety_24,
            R.drawable.ic_baseline_local_movies_24,
            R.drawable.ic_outline_shopping_cart_24,
            R.drawable.ic_outline_local_mall_24,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] ctgColorArray = new int[]{
                getResources().getColor(R.color.ctg1),
                getResources().getColor(R.color.ctg2),
                getResources().getColor(R.color.ctg3),
                getResources().getColor(R.color.ctg4),
                getResources().getColor(R.color.ctg5),
                getResources().getColor(R.color.ctg6),
                getResources().getColor(R.color.ctg7),
                getResources().getColor(R.color.ctg8),
                getResources().getColor(R.color.ctg9),
                getResources().getColor(R.color.ctg10),
                getResources().getColor(R.color.ctg11),
                getResources().getColor(R.color.ctg12),
        };

        // DB
        // allowMainThreadQueries() not recommended ???
        DataFromDB dataFromDB = new DataFromDB();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Expenses").allowMainThreadQueries().build();
        dataFromDB.setExpenseDao(db.expenseDao());
        dataFromDB.setAccumulatedExpenseDao(db.accumulatedExpenseDao());

        dataFromDB.getAllDataFromDB();

        // CategoryButtons
        String[] categories = getResources().getStringArray(R.array.categories);

        ctgGridView = findViewById(R.id.ctgGridView);
        ctgGridView.setExpanded(true);

        gridModelArrayList = new ArrayList<GridModel>();
        for(int i = 0; i < categories.length; i++)
        {
            gridModelArrayList.add(new GridModel(categories[i], btnIconArray[i], ctgColorArray[i], (int)dataFromDB.getAccumulatedExpensesByCategory()[i].sum));
        }
        adapter = new GridAdapter(this, gridModelArrayList);
        ctgGridView.setAdapter(adapter);
        ctgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBottomSheetDialog(dataFromDB, position);
            }
        });

        // PieChart
        setupPieChart(dataFromDB, ctgColorArray);
        pieChart.invalidate();

        // Drawer Layout
        DrawerLayout drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // FAB
        mAddFab = findViewById(R.id.add_fab);
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab);

        addAlarmActionText = findViewById(R.id.add_alarm_action_text);
        addPersonActionText = findViewById(R.id.add_person_action_text);

        mAddAlarmFab.setVisibility(View.GONE);
        mAddPersonFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);
        isAllFabsVisible = false;

        mAddFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                mAddAlarmFab.show();
                mAddPersonFab.show();
                addAlarmActionText.setVisibility(View.VISIBLE);
                addPersonActionText.setVisibility(View.VISIBLE);
                isAllFabsVisible = true;
            } else {
                mAddAlarmFab.hide();
                mAddPersonFab.hide();
                addAlarmActionText.setVisibility(View.GONE);
                addPersonActionText.setVisibility(View.GONE);
                isAllFabsVisible = false;
            }
        });

        mAddPersonFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT
                ).show());

        mAddAlarmFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT
                ).show());
    }

    // BottomSheet
    private void showBottomSheetDialog(DataFromDB dataFromDB, int btnIndex) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_main);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();

        // Categories drop down menu
        AutoCompleteTextView autoCompleteTextViewCategoty = bottomSheetDialog.findViewById(R.id.actvCategory);
        String[] categories = getResources().getStringArray(R.array.categories);
        List<String> categoriesList = Arrays.asList(categories);
        autoCompleteTextViewCategoty.setText(categoriesList.get(btnIndex));
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(
                this, R.layout.drop_down_item, categoriesList);
        autoCompleteTextViewCategoty.setAdapter(adapterCategory);

        // Accounts drop down menu
        AutoCompleteTextView autoCompleteTextViewAccount = bottomSheetDialog.findViewById(R.id.actvAccount);
        String[] accounts = getResources().getStringArray(R.array.accounts);
        List<String> accountsList = Arrays.asList(accounts);
        autoCompleteTextViewAccount.setText(accountsList.get(0));
        ArrayAdapter<String> adapterAccount = new ArrayAdapter<>(
                this, R.layout.drop_down_item, accountsList);
        autoCompleteTextViewAccount.setAdapter(adapterAccount);

        // Calendar
        btnPickDate = bottomSheetDialog.findViewById(R.id.btnBottomSheetPickDate);
        mDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        btnPickDate.setText(mDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.pennychet.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.example.pennychet.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        TextInputLayout tfBottomSheetSum = bottomSheetDialog.findViewById(R.id.textFieldBottomSheetSum);
        TextView tvBottomSheetSum = bottomSheetDialog.findViewById(R.id.textViewBottomSheetSum);
        TextView tvBottomSheetDescription = bottomSheetDialog.findViewById(R.id.textViewBottomSheetDescription);
        Button btnBottomSheetOk = bottomSheetDialog.findViewById(R.id.btnBottomSheetOk);
        Button btnBottomSheetCancel = bottomSheetDialog.findViewById(R.id.btnBottomSheetCancel);

        btnBottomSheetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvBottomSheetSum.getText().toString().isEmpty())
                {
                    tfBottomSheetSum.setError("Required field");
                }
                else {
                    tfBottomSheetSum.setError(null);

                    // DB
                    String category = String.valueOf(autoCompleteTextViewCategoty.getText());
                    String account = String.valueOf(autoCompleteTextViewAccount.getText());
                    String description = String.valueOf(tvBottomSheetDescription.getText());
                    double sum = Double.parseDouble(String.valueOf(tvBottomSheetSum.getText()));
                    String date = String.valueOf(mDate);

                    Expense expense = new Expense();
                    expense.category = category;
                    expense.account = account;
                    expense.description = description;
                    expense.sum = sum;
                    expense.date = date;
                    dataFromDB.getExpenseDao().insertAll(expense);

                    double accumulatedSum = 0;
                    if(dataFromDB.getAccumulatedExpensesByCategory()[btnIndex] == null) {
                        AccumulatedExpense accumulatedExpense = new AccumulatedExpense();
                        accumulatedExpense.category = category;
                        accumulatedExpense.sum = sum;
                        dataFromDB.getAccumulatedExpenseDao().insertAll(accumulatedExpense);
                        accumulatedSum += sum;
                    }
                    else
                    {
                        dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum += sum;
                        accumulatedSum = dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum;
                        dataFromDB.getAccumulatedExpenseDao().updateSum(dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum, category);
                    }

                    updatePieChart(pieChart, pieDataSet, btnIndex, accumulatedSum);

                    gridModelArrayList.get(btnIndex).setSum((int)dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum);
                    adapter.notifyDataSetChanged();
                    ctgGridView.invalidate();

                    bottomSheetDialog.dismiss();
                }
            }
        });

        btnBottomSheetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    ArrayList<PieEntry> pieChartCategories(AccumulatedExpense[] accumulatedExpensesByCategory){
        ArrayList<PieEntry> pieChartCtg = new ArrayList<>();
        for(int i = 0; i < 12; i++)     // number of categories ???
        {
            pieChartCtg.add(new PieEntry((int)accumulatedExpensesByCategory[i].sum, ""));
        }
        return pieChartCtg;
    }

    void setupPieChart(DataFromDB dataFromDB, int[] colorClassArray)
    {
        pieChart = findViewById(R.id.pieChart);
        pieDataSet = new PieDataSet(pieChartCategories(dataFromDB.getAccumulatedExpensesByCategory()), "label");
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawRoundedSlices(false);
        pieChart.setUsePercentValues(false);
        pieChart.setHoleRadius(65);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        Description description = pieChart.getDescription();
        description.setEnabled(false);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setSliceSpace(1);
        pieChart.animateX(400);
    }

    void updatePieChart(PieChart pieChart, PieDataSet pieDataSet, int entryIndex, double newData)
    {
        pieDataSet.getEntries().set(entryIndex, new PieEntry((int)newData, ""));
        pieChart.notifyDataSetChanged();
        pieChart.animateX(400);
        pieChart.invalidate();
    }

    // Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.calendar_toolbar_btn) {
            return true;
        }
        // TODO

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        mDate = String.format("%d.%d.%d", day, month+1, year);
        btnPickDate.setText(mDate);
    }
}
