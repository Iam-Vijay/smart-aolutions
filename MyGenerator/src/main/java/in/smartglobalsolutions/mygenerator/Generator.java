package in.smartglobalsolutions.mygenerator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.smartglobalsolutions.mygenerator.Pojo.RecylerPasser;
import in.smartglobalsolutions.mygenerator.Pojo.SeachPasser;
import in.smartglobalsolutions.mygenerator.Pojo.View_Receipt_Passer;
import in.smartglobalsolutions.mygenerator.Searchspinner.SearchableSpinner;

import static com.android.volley.VolleyLog.TAG;
import static in.smartglobalsolutions.mygenerator.LegacyTableView.BOLD;
import static in.smartglobalsolutions.mygenerator.LegacyTableView.CENTER;
import static in.smartglobalsolutions.mygenerator.LegacyTableView.OCEAN;
import static in.smartglobalsolutions.mygenerator.LegacyTableView.ODD;


public class Generator  implements RecyclerViewClickListener, SearchView.OnQueryTextListener{
    LinearLayout relativelayout;
    Context context;
    ProgressDialog progressDialog;
    Activity activity;

    HashMap<String, String> alldatatmap = new HashMap<String, String>();


    ArrayList<SearchableSpinner> totalsearchablespinner=new ArrayList<>();
    ArrayList<ArrayList<String>> totalsearchspinnerid=new ArrayList<>();
    ArrayList<String> total_search_spinner_field_names=new ArrayList<>();


    ArrayList<EditText> editTextArrayList=new ArrayList<>();
    ArrayList<ArrayList<String>> totalspinnerid=new ArrayList<>();
    ArrayList<Spinner> totalspinner=new ArrayList<>();
    ArrayList<TextView> totalfulldate=new ArrayList<>();
    ArrayList<TextView> totaltextview=new ArrayList<>();
    final static String BASE="https://www.chitsoft.com/wapp/api/mobile/";
    RequestQueue requestQueue;
    ArrayList<String> Field_Edittext=new ArrayList<String>();
    ArrayList<String> Field_Date =new ArrayList<String>();
    ArrayList<String> Field_Textview =new ArrayList<String>();
    ArrayList<String> Field_Spinner=new ArrayList<String>();
    private int mYear, mMonth, mDay;
    String CUS_NAME="";
    String CUS_ADDRESS="";
    ArrayList<ArrayList<SeachPasser>> totalseachPasserList=new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date datet = new Date();
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    String selectddate;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    LinearLayout tablelinear;
    int dHeight;
     int dWidth;
    LinearLayout.LayoutParams LayoutParamsview;
    LinearLayout.LayoutParams semiview;

    ArrayList<String> tablehead=new ArrayList<>();
    ArrayList<String> tablecontent =new ArrayList<>();
    TableRefresh tableRefresh;



    AlertDialog.Builder builder1;
    AlertDialog alertDialog1;

    RecyclerView recyclerView;
    SearchView searchView;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    CustomSeachAdapter customSeachAdapter;
    String searchidlist=null;


    int finalI = -1;


    Sessionmanager sessionmanager;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public Generator(LinearLayout linearLayout, Context context, TableRefresh tableRefresh ,Activity activity) {
        this.relativelayout = linearLayout;
        this.context = context;
        this.tableRefresh=tableRefresh;
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        requestQueue=Volley.newRequestQueue(context);
        context.getDisplay().getRealMetrics(displayMetrics);

        dHeight = displayMetrics.heightPixels;
        sessionmanager=new Sessionmanager(context);
        dWidth = displayMetrics.widthPixels;
        this.activity=activity;

        LayoutParamsview = new LinearLayout.LayoutParams(dWidth/3, LinearLayout.LayoutParams.WRAP_CONTENT);
         semiview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        customSeachAdapter=new CustomSeachAdapter(this);
    }
    public void doEdittext(String name,String fieldname,int Inputtype){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //  linearlayoutlayoutparams.setMargins(50,50,50,50);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 0);
        TextView myEditText = new TextView(context);
        linearLayout.addView(myEditText);
        myEditText.setTextColor(Color.BLACK);
        myEditText.setTextSize(18);
        myEditText.setText(name);
        myEditText.setLayoutParams(LayoutParamsview);

        LinearLayout.LayoutParams semiview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView semicolon=new TextView(context);
        semicolon.setLayoutParams(semiview);
        semicolon.setText(" : ");
        semicolon.setTextColor(Color.BLACK);
        semicolon.setTextSize(18);
        linearLayout.addView(semicolon);



        EditText ditText = new EditText(context);
        ditText.setInputType(Inputtype);
        LinearLayout.LayoutParams editextLayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ditText.setLayoutParams(editextLayoutParamsview);
        linearLayout.addView(ditText);


        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        relativelayout.addView(linearLayout);
        editTextArrayList.add(ditText);
        Field_Edittext.add(fieldname);
    }
    public void doDate(String name,String fieldname,boolean isclick){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 0);
        TextView mytextview = new TextView(context);
        mytextview.setTextSize(18);
        mytextview.setTextColor(Color.BLACK);
        mytextview.setLayoutParams(LayoutParamsview);
        linearLayout.addView(mytextview);
        mytextview.setText(name);


        TextView semicolon=new TextView(context);
        semicolon.setLayoutParams(semiview);
        semicolon.setText(" : ");
        semicolon.setTextColor(Color.BLACK);
        semicolon.setTextSize(18);
        linearLayout.addView(semicolon);



        final TextView date = new TextView(context);
        date.setTextSize(20);
        date.setTextColor(Color.BLACK);
        date.setLayoutParams(LayoutParamsview);
        date.setText(formatter.format(datet));
        date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(date);
        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        relativelayout.addView(linearLayout);
        if (isclick) {
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,

                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
            if (monthOfYear+1 < 10){
                date.setText(new StringBuilder().append(dayOfMonth).append("/").append(0)
                        .append(monthOfYear+1).append("/").append(year));
            }else {
                date.setText(new StringBuilder().append(dayOfMonth).append("/")
                        .append(monthOfYear+1).append("/").append(year));
            }


                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
            totalfulldate.add(date);
            Field_Date.add(fieldname);
        }
    }
    public void doTextview(String name,String fieldname,String value,boolean issearch) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 0);
        TextView mytextview = new TextView(context);
        mytextview.setTextSize(18);
        mytextview.setTextColor(Color.BLACK);
        mytextview.setLayoutParams(LayoutParamsview);
        linearLayout.addView(mytextview);
        mytextview.setText(name);


        TextView semicolon = new TextView(context);
        semicolon.setLayoutParams(semiview);
        semicolon.setText(" : ");
        semicolon.setTextColor(Color.BLACK);
        semicolon.setTextSize(18);
        linearLayout.addView(semicolon);


        final TextView date = new TextView(context);
        date.setTextSize(20);
        date.setTextColor(Color.BLACK);
        date.setLayoutParams(LayoutParamsview);

        date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(date);
        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        if (issearch) {
            date.setText("Choose a Name");
            ArrayList<SeachPasser> seachPasserList=new ArrayList<>();
            relativelayout.addView(linearLayout);
            totaltextview.add(date);
            Field_Textview.add(fieldname);
            try {
                JSONArray array=new JSONArray(value);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    SeachPasser passer=new SeachPasser();
                    passer.setId(object.getInt("id"));
                    passer.setAddress(object.getString("address"));
                    passer.setName(object.getString("name"));
                    seachPasserList.add(passer);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

                totalseachPasserList.add(seachPasserList);
            for (int i=0;i<totaltextview.size();i++){

                final int finalI1 = i;
                totaltextview.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCustomDialog(view);
                        finalI= finalI1;
                        recyclerView.setAdapter(customSeachAdapter);
                        customSeachAdapter.setdata(totalseachPasserList.get(finalI1));
                        customSeachAdapter.notifyDataSetChanged();
                    }
                });


            }

        }else {
            //do nothing....
            totaltextview.add(date);
            relativelayout.addView(linearLayout);
        }

    }

    public void doSearchableeSpinner(String label,String field ,String array)throws JSONException {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 0);
        TextView mytextview = new TextView(context);
        mytextview.setTextSize(18);
        mytextview.setTextColor(Color.BLACK);
        mytextview.setLayoutParams(LayoutParamsview);
        mytextview.setText(label);
        linearLayout.addView(mytextview);

        TextView semicolon=new TextView(context);
        semicolon.setLayoutParams(semiview);
        semicolon.setText(" : ");
        semicolon.setTextColor(Color.BLACK);
        semicolon.setTextSize(18);
        linearLayout.addView(semicolon);

        SearchableSpinner searchableSpinner=new SearchableSpinner(context);

        JSONArray spinnervalues=new JSONArray(array);
        ArrayList<String> personNames=new ArrayList<>();
        ArrayList<String> personid=new ArrayList<>();
        for (int m=0;m<spinnervalues.length();m++){
            JSONObject spobj=spinnervalues.getJSONObject(m);
            personNames.add(spobj.getString("name"));
            personid.add(spobj.getString("id"));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, personNames);
        searchableSpinner.setAdapter(arrayAdapter);
        searchableSpinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        linearLayout.addView(searchableSpinner);
        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        relativelayout.addView(linearLayout);
        totalsearchspinnerid.add(personid);
        totalsearchablespinner.add(searchableSpinner);
        total_search_spinner_field_names.add(field);
        for (int a=0;a<totalsearchablespinner.size();a++){
            Spinner s =totalsearchablespinner.get(a);
            final int finalA = a;
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String id=totalsearchspinnerid.get(finalA).get(i);
                    Log.i(TAG, total_search_spinner_field_names.get(finalA)+": "+id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
    public void doSpinner(String label,String field,String jsonarray) throws JSONException {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 0);
        TextView mytextview = new TextView(context);
        mytextview.setTextSize(18);
        mytextview.setTextColor(Color.BLACK);
        mytextview.setLayoutParams(LayoutParamsview);
        mytextview.setText(label);
        linearLayout.addView(mytextview);


        TextView semicolon=new TextView(context);
        semicolon.setLayoutParams(semiview);
        semicolon.setText(" : ");
        semicolon.setTextColor(Color.BLACK);
        semicolon.setTextSize(18);
        linearLayout.addView(semicolon);


        final Spinner spinner = new Spinner(context);
        JSONArray spinnervalues=new JSONArray(jsonarray);
        ArrayList<String> personNames=new ArrayList<>();
        ArrayList<String> personid=new ArrayList<>();
        for (int m=0;m<spinnervalues.length();m++){
            JSONObject spobj=spinnervalues.getJSONObject(m);
            personNames.add(spobj.getString("name"));
            personid.add(spobj.getString("id"));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, personNames);
        spinner.setAdapter(arrayAdapter);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        linearLayout.addView(spinner);
        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        relativelayout.addView(linearLayout);
        totalspinnerid.add(personid);
        totalspinner.add(spinner);
        Field_Spinner.add(field);
        for (int a=0;a<totalspinner.size();a++){
            Spinner s =totalspinner.get(a);
            final int finalA = a;
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String id=totalspinnerid.get(finalA).get(i);
                    Log.i(TAG, Field_Spinner.get(finalA)+": "+id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }
    public void GenerateSaveButton(String label, String apiname){

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams LayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //  linearlayoutlayoutparams.setMargins(50,50,50,50);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 50);
        final Button button = new Button(context);
        linearLayout.addView(button);
        button.setBackgroundResource(R.drawable.buttoncolor);
        button.setTextColor(Color.WHITE);
        button.setTextSize(18);
        button.setText(label);
        button.setLayoutParams(LayoutParamsview);
        final String url=apiname;
        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        linearLayout.setGravity(Gravity.CENTER);
        relativelayout.addView(linearLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog.show();

                boolean issave=false;
                for (int k=0;k<editTextArrayList.size();k++){
                    EditText d=editTextArrayList.get(k);
                    Log.i(TAG, Field_Edittext.get(k)+": "+d.getText().toString());
                    if (editTextArrayList.get(k).getText().toString().equals("")){
                        issave=true;
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                            alldatatmap.put(Field_Edittext.get(k),editTextArrayList.get(k).getText().toString());
                    }
                }for (int a=0;a<totalfulldate.size();a++){
                    TextView t=totalfulldate.get(a);

                            alldatatmap.put(Field_Date.get(a),totalfulldate.get(a).getText().toString());

                    Log.i(TAG, Field_Date.get(a)+": "+t.getText().toString());
                }
                for (int a=0;a<totalspinner.size();a++){

                    Spinner s =totalspinner.get(a);
                    final int finalA = a;

                    alldatatmap.put(Field_Spinner.get(a),totalspinnerid.get(a).get(totalspinner.get(a).getSelectedItemPosition()));

                }
                for (int a=0;a<totalsearchablespinner.size();a++){

                    SearchableSpinner s =totalsearchablespinner.get(a);
                    final int finalA = a;

                    alldatatmap.put(total_search_spinner_field_names.get(a),totalsearchspinnerid.get(a).get(totalsearchablespinner.get(a).getSelectedItemPosition()));

                }
                for (int a=0;a<Field_Textview.size();a++) {
                    if (searchidlist != null){
                        String s = searchidlist;
                    final int finalA = a;

                        alldatatmap.put(Field_Textview.get(a), s);

                }
                }

                if (!issave) {
                    final JSONArray savejsonarray = new JSONArray();
                    JSONObject saveobj=new JSONObject();
                    for (String i : alldatatmap.keySet()) {
                        System.out.println("Name: " + i + " Age: " + alldatatmap.get(i));

                        try {
                            saveobj.put(i,alldatatmap.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    savejsonarray.put(saveobj);
                    StringRequest saverequest=new StringRequest(Request.Method.POST, BASE + url + ".php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Log.i(TAG, "onsaveResponse: "+response);
                                Toast.makeText(context, "Saved in Online ", Toast.LENGTH_SHORT).show();
                            if (!editTextArrayList.isEmpty()){
                                for (int i=0;i<editTextArrayList.size();i++)
                                {
                                    EditText ed=editTextArrayList.get(i);
                                    ed.setText("");
                                }
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();

                            String cid=sessionmanager.getValue("cid");
                            String roleid=sessionmanager.getValue("role_id");
                            String routeid=sessionmanager.getValue("route_id");
                            String uid=sessionmanager.getValue("uid");
                            String def_acc=sessionmanager.getValue("def_acc");
                            String bid=sessionmanager.getValue("bid");
                            params.put("cid",cid);
                            params.put("uid",uid);
                            params.put("role_id",roleid);
                            params.put("route_id",routeid);
                            params.put("def_acc",def_acc);
                            params.put("bid",bid);
                            params.put("data", savejsonarray.toString());
                            return params;
                        }
                    };
                    requestQueue.add(saverequest);
                }

            }
        });
    }
//    public void GeneratePrintButton(String label, String apiname, final String printtype){
//
//        LinearLayout linearLayout = new LinearLayout(context);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams LayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        //  linearlayoutlayoutparams.setMargins(50,50,50,50);
//        linearlayoutlayoutparams.setMargins(50, 50, 50, 50);
//        final Button button = new Button(context);
//        linearLayout.addView(button);
//        button.setBackgroundResource(R.drawable.buttoncolor);
//        button.setTextColor(Color.WHITE);
//        button.setTextSize(18);
//        button.setText(label);
//        button.setLayoutParams(LayoutParamsview);
//        final String url=apiname;
//        linearLayout.setLayoutParams(linearlayoutlayoutparams);
//        linearLayout.setGravity(Gravity.CENTER);
//        relativelayout.addView(linearLayout);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//                progressDialog.show();
//                if (printtype.equals("1")) {
//                    progressDialog.show();
//                    final int to = offlineSaveDatabse.numberOfRows();
//
//
//
//                    boolean issave = false;
//                    for (int k = 0; k < editTextArrayList.size(); k++) {
//                        EditText d = editTextArrayList.get(k);
//                        Log.i(TAG, Field_Edittext.get(k) + ": " + d.getText().toString());
//                        if (editTextArrayList.get(k).getText().toString().equals("")) {
//                            issave = true;
//                            Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        } else {
//
//                                alldatatmap.put(Field_Edittext.get(k), editTextArrayList.get(k).getText().toString());
//
//
//                        }
//                    }
//                    for (int a = 0; a < totalfulldate.size(); a++) {
//                        TextView t = totalfulldate.get(a);
//
//                            alldatatmap.put(Field_Date.get(a), totalfulldate.get(a).getText().toString());
//
//
//
//                        Log.i(TAG, Field_Date.get(a) + ": " + t.getText().toString());
//                    }
//                    for (int a=0;a<totalsearchablespinner.size();a++){
//
//                        SearchableSpinner s =totalsearchablespinner.get(a);
//                            alldatatmap.put(total_search_spinner_field_names.get(a),totalsearchspinnerid.get(a).get(totalsearchablespinner.get(a).getSelectedItemPosition()));
//
//                    }
//                    for (int a = 0; a < totalspinner.size(); a++) {
//
//                        Spinner s = totalspinner.get(a);
//
//                            alldatatmap.put(Field_Spinner.get(a), totalspinnerid.get(a).get(totalspinner.get(a).getSelectedItemPosition()));
//
//                            alldatatmap.put("spinvalue" + String.valueOf(a), totalspinner.get(a).getSelectedItem().toString());
//
//                    }
//                    for (int a = 0; a < Field_Textview.size(); a++) {
//
//                        String s = searchidlist;
//                            final int finalA = a;
//                                alldatatmap.put(Field_Textview.get(a), s);
//                                alldatatmap.put("AutoCompvalue" + String.valueOf(a), CUS_NAME);
//                        }
//                    if (CUS_NAME.equals("")) {
//                        issave = true;
//                        Toast.makeText(context, "Please Select a Name", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//
//                    if (!issave) {
//                        final JSONArray savejsonarray = new JSONArray();
//                        JSONObject saveobj=new JSONObject();
//                        for (String i : alldatatmap.keySet()) {
//                            System.out.println("Name: " + i + " Age: " + alldatatmap.get(i));
//
//                            try {
//                                saveobj.put(i,alldatatmap.get(i));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        progressDialog.show();
//                        String[] totmain=CUS_NAME.split("/");
//                        String Accno=totmain[0];
//                        String c_name=totmain[1];
//                        String balance=totmain[2];
//                        int tot = Integer.parseInt(balance) + Integer.parseInt(editTextArrayList.get(0).getText().toString());
//
//                        try {
//                            saveobj.put("cus_accno",Accno);
//                            saveobj.put("cus_name",c_name);
//                            saveobj.put("atTotal",tot);
//                            saveobj.put("balanceamt",balance);
//                            saveobj.put("url", url);
//                            saveobj.put("address",CUS_ADDRESS);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        savejsonarray.put(saveobj);
//                        Log.i(TAG, "onItemSelected: " + savejsonarray.toString());
//                        StringRequest saverequest = new StringRequest(Request.Method.POST, BASE + url + ".php", new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                progressDialog.dismiss();
//
//                                Log.i(TAG, "onsaveResponse: " + response);
//
//                                Toast.makeText(context, "Saved in Online ", Toast.LENGTH_SHORT).show();
//                                offlineSaveDatabse.Addoncontent(savejsonarray.toString(), "true", date, currentTime,"false");
////                                takeprint(savejsonarray.toString());
//
//                                    for (int i = 0; i < editTextArrayList.size(); i++) {
//                                        EditText ed = editTextArrayList.get(i);
//                                        ed.setText("");
//
//                                }
//                                if (to + 1 == offlineSaveDatabse.numberOfRows()) {
//                                    Takeprint();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                progressDialog.dismiss();
//                                offlineSaveDatabse.Addoncontent(savejsonarray.toString(), "false", date, currentTime,"false");
//                                for (int i = 0; i < editTextArrayList.size(); i++) {
//                                    EditText ed = editTextArrayList.get(i);
//                                    ed.setText("");
//                                }
//                                Toast.makeText(context, "Saved in Database", Toast.LENGTH_SHORT).show();
//                                if (to + 1 == offlineSaveDatabse.numberOfRows()) {
//                                    Takeprint();
//                                }
//
//                            }
//                        }) {
//                            @Override
//                            protected Map<String, String> getParams() {
//                                // Posting parameters to login url
//                                Map<String, String> params = new HashMap<String, String>();
//                                String cid=sessionmanager.getValue("cid");
//                                String roleid=sessionmanager.getValue("role_id");
//                                String routeid=sessionmanager.getValue("route_id");
//                                String uid=sessionmanager.getValue("uid");
//                                String def_acc=sessionmanager.getValue("def_acc");
//                                String bid=sessionmanager.getValue("bid");
//                                params.put("cid",cid);
//                                params.put("uid",uid);
//                                params.put("role_id",roleid);
//                                params.put("route_id",routeid);
//                                params.put("def_acc",def_acc);
//                                params.put("bid",bid);
//                                params.put("data", savejsonarray.toString());
//                                return params;
//                            }
//                        };
//                        requestQueue.add(saverequest);
//                    }
//
//                }if (printtype.equals("2")){
//                    progressDialog.dismiss();
//                            TakePrint2(view);
//                }
//            }
//        });
//    }
    public void GenerateTableRefreshButton(String label, String apiname){

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams LayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //  linearlayoutlayoutparams.setMargins(50,50,50,50);
        linearlayoutlayoutparams.setMargins(50, 50, 50, 50);
        final Button button = new Button(context);
        linearLayout.addView(button);
        button.setBackgroundResource(R.drawable.buttoncolor);
        button.setTextColor(Color.WHITE);
        button.setTextSize(18);
        button.setText(label);
        button.setLayoutParams(LayoutParamsview);
        final String url=apiname;
        linearLayout.setLayoutParams(linearlayoutlayoutparams);
        linearLayout.setGravity(Gravity.CENTER);
        relativelayout.addView(linearLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



                boolean issave = false;
                for (int k = 0; k < editTextArrayList.size(); k++) {
                    EditText d = editTextArrayList.get(k);
                    Log.i(TAG, Field_Edittext.get(k) + ": " + d.getText().toString());

                    if (editTextArrayList.get(k).getText().toString().equals("")) {
                        issave = true;
                        Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                    } else {

                            alldatatmap.put(Field_Edittext.get(k), editTextArrayList.get(k).getText().toString());


                    }
                }
                for (int a = 0; a < totalfulldate.size(); a++) {
                    TextView t = totalfulldate.get(a);

                        alldatatmap.put(Field_Date.get(a), totalfulldate.get(a).getText().toString());

                    Log.i(TAG, Field_Date.get(a) + ": " + t.getText().toString());
                }
                for (int a = 0; a < totalspinner.size(); a++) {

                    Spinner s = totalspinner.get(a);
                    final int finalA = a;



                        alldatatmap.put(Field_Spinner.get(a), totalspinnerid.get(a).get(totalspinner.get(a).getSelectedItemPosition()));


                }
                for (int a = 0; a < Field_Textview.size(); a++) {
                    if (searchidlist != null) {
                        String s = searchidlist;
                        final int finalA = a;



                            alldatatmap.put(Field_Textview.get(a), s);


                    } else {
                        Toast.makeText(context, "Please Select Name", Toast.LENGTH_SHORT).show();
                            issave=true;
                    }
                }
                if (!issave) {
                    progressDialog.show();
                    final JSONArray savejsonarray = new JSONArray();
                    JSONObject saveobj=new JSONObject();
                    for (String i : alldatatmap.keySet()) {
                        System.out.println("Name: " + i + " Age: " + alldatatmap.get(i));

                        try {
                            saveobj.put(i,alldatatmap.get(i));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    savejsonarray.put(saveobj);

                    StringRequest saverequest = new StringRequest(Request.Method.POST, BASE + "builder.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Log.i(TAG, "onsaveResponse: " + response);
                            Log.i(TAG, "onsaveResponse: " + savejsonarray.toString());

                            try {
                                final JSONObject object = new JSONObject(response);
                                relativelayout.removeView(tablelinear);
                                tableRefresh.onClick(view, 1, object.getString("header"), object.getString("cont"));
                                if (!editTextArrayList.isEmpty()) {
                                    for (int i = 0; i < editTextArrayList.size(); i++) {
                                        EditText ed = editTextArrayList.get(i);
                                        ed.setText("");
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();

                            String cid = sessionmanager.getValue("cid");
                            String roleid = sessionmanager.getValue("role_id");
                            String routeid = sessionmanager.getValue("route_id");
                            String uid = sessionmanager.getValue("uid");
                            String def_acc = sessionmanager.getValue("def_acc");
                            String bid = sessionmanager.getValue("bid");
                            params.put("cid", cid);
                            params.put("uid", uid);
                            params.put("role_id", roleid);
                            params.put("route_id", routeid);
                            params.put("url", url);
                            params.put("def_acc", def_acc);
                            params.put("bid", bid);
                            params.put("data", savejsonarray.toString());
                            return params;
                        }
                    };
                    requestQueue.add(saverequest);

                }
            }
        });
    }
    public void GenerateTableLayout(View view,String header,String content,int buildtype){

        LegacyTableView legacyTableView=new LegacyTableView(context);
        ArrayList<String> ref=new ArrayList<>();
        try {
            JSONArray headerarray=new JSONArray(header);
            for (int i=0;i<headerarray.length();i++){
                LegacyTableView.insertLegacyTitle(headerarray.getString(i));
                ref.add(headerarray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray conarray=new JSONArray(content);
            for (int j=0;j<conarray.length();j++){
                JSONObject jobj=conarray.getJSONObject(j);
                for (String s : ref) {
                    LegacyTableView.insertLegacyContent(jobj.getString(s));
                    }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //set table contents as string arrays

        legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        legacyTableView.setContent(LegacyTableView.readLegacyContent());
        legacyTableView.setBottomShadowVisible(true);

        legacyTableView.setHighlight(ODD);//highlight rows oddly or evenly
        legacyTableView.setTheme(OCEAN);
        //tableView.setHighlight(EVEN);



        legacyTableView.setTitleTextAlignment(CENTER);
        legacyTableView.setContentTextSize(25);
        legacyTableView.setTitleTextSize(30);
        legacyTableView.setContentTextAlignment(CENTER);
        legacyTableView.setTablePadding(20);//increasing spacing will increase the table size
        //tableView.setBottomShadowColorTint("#ffffff");

        //tableView.setBackgroundEvenColor("#FFCCBC");
        //tableView.setBackgroundEvenColor("#303F9F");
        legacyTableView.setBackgroundOddColor("#FFCCBC");
        //you can also declare your color values as global strings to make your work easy :)
        legacyTableView.setHeaderBackgroundLinearGradientBOTTOM("#FF5722");//header background bottom color
        legacyTableView.setHeaderBackgroundLinearGradientTOP("#009688");//header background top color
        legacyTableView.setBorderSolidColor("#009688");
        legacyTableView.setTitleFont(BOLD);
        legacyTableView.setZoomEnabled(true);
        legacyTableView.setShowZoomControls(true);
        //by default the initial scale is 0, you
        // may change this depending on initiale scale preferences
        //tableView.setInitialScale(100);//default initialScale is zero (0)
        legacyTableView.setContentTextColor("#009688");

        //remember to build your table as the last step
        if (buildtype ==2){
            relativelayout.removeViewAt(relativelayout.getChildCount() -1);
            legacyTableView.rebuild();
            relativelayout.addView(legacyTableView);
        }if (buildtype==1) {
            legacyTableView.build();
            relativelayout.addView(legacyTableView);
        }




    }

    public void GenerateRecylerView(String data,int num,int layouttype) throws JSONException {

                RecyclerView recyclerView=new RecyclerView(context);
        LinearLayout.LayoutParams LayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(LayoutParamsview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new GridLayoutManager(context,num));
            if (layouttype==1) {
                List<RecylerPasser> list=new ArrayList<>();
                RecylerAdapter adapter = new RecylerAdapter(context);

                try {
                    list.clear();
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String label = obj.getString("label");


                        String image = obj.getString("image");
                        boolean isimage = obj.getBoolean("is_image");
                        RecylerPasser rp = new RecylerPasser();

                        rp.setImage(image);
                        rp.setIsimage(isimage);
                        rp.setName(label);

                        list.add(rp);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.setdata(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            relativelayout.addView(recyclerView);

    }public void GenerateDashBoard(String data,int num){
        List<RecylerPasser> list=new ArrayList<>();
        RecyclerView recyclerView=new RecyclerView(context);
        LinearLayout.LayoutParams LayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(LayoutParamsview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(context,num));
        RecylerAdapter adapter=new RecylerAdapter(context);

        try {
            list.clear();
            JSONArray  jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj=jsonArray.getJSONObject(i);

                String label=obj.getString("label");
                boolean isimage=obj.getBoolean("is_image");



                String image=obj.getString("image");
                RecylerPasser rp=new RecylerPasser();
                rp.setIsimage(isimage);
                rp.setImage(image);
                rp.setName(label);


                list.add(rp);
            }
            recyclerView.setAdapter(adapter);
            adapter.setdata(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        relativelayout.addView(recyclerView);

    }
    @Override
    public void recyclerViewListClicked(View v, int position, String name,String orginalpos,String address) {

                totaltextview.get(finalI).setText("    " + name);
                CUS_NAME=name;
                CUS_ADDRESS=address;
//        nameid=position;
                    searchidlist =(String.valueOf(position));
                    Log.i(TAG, "recyclerViewListClicked: "+searchidlist);
                    totaltextview.get(1).setText(address);

        alertDialog.dismiss();

    }
     private void showCustomDialog(View view) {
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_search, viewGroup, false);
        builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();

        recyclerView = dialogView.findViewById(R.id.searchrecycle);
        searchView = dialogView.findViewById(R.id.search_edittext);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        searchView.setQueryHint("Search Here");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        customSeachAdapter.getFilter().filter(query);
        customSeachAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        customSeachAdapter.getFilter().filter(newText);
        customSeachAdapter.notifyDataSetChanged();
        return true;
    }


//    private void Takeprint() {
//            String spinner = null,amount=null;
//
//
//        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//        DBPasser kk = offlineSaveDatabse.getLastAddedRowId();
//        Log.i(TAG, "Takeprint: "+kk.getData());
//        String data = kk.getData();
//       String str = data.replaceAll("[\\[\\]\\(\\)]", "");
//        try {
//
//            JSONObject nn=new JSONObject(str);
//
//
//         spinner=nn.getString("spinvalue1");
//         amount =nn.getString("amount");
//
//
//
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//            String title = sessionmanager.getValue("company");
//            String agent=sessionmanager.getValue("uname");
//            String address = sessionmanager.getValue("address");
//            String[] totmain=CUS_NAME.split("/");
//
//
//            String Accno=totmain[0];
//            String name=totmain[1];
//            String balance=totmain[2];
//            String dilog2="Stay Home - Stay Safe";
//            String dilog1="Thank You Have a Nice Day";
//
//
//            //  String str4 =particularbuffer.toString();
//            //  String dtr6 =lbuffer.toString();
//            //  String dtr7 =bbuffer.toString();
//
//
//
//            String BILL = "\n";
//
//
//        BILL = BILL + StringUtils.center(title,32);
//        BILL = BILL + StringUtils.center(address,32);
//        BILL = BILL
//                + "--------------------------------";
//        BILL = BILL + StringUtils.center("COLLECTION RECEIPT",32);
//        BILL = BILL
//                + "--------------------------------";
//        BILL = BILL + String.format("%-15s %15s", date,  currentTime+"\n");
//        BILL = BILL + String.format("%-15s %15s", "RECEIPT No :",  kk.getId()+"\n");
//        BILL = BILL + String.format("%-15s %15s", "Cust Name  :",  name+"\n");
//        if (CUS_ADDRESS.length() <= 15) {
//            BILL = BILL + String.format("%-15s %15s", "Cust Addr  :", CUS_ADDRESS + "\n");
//        }else {
//
//            BILL = BILL + String.format("%-15s %15s", "Cust Addr  :", CUS_ADDRESS.substring(0,15) + "\n");
//            int cr=15;
//            for (int i=1;i<CUS_ADDRESS.length();i++){
//                if (i==cr) {
//                    try {
//                        BILL = BILL + String.format("%-15s %15s", "       ", CUS_ADDRESS.substring(i, i + 15) + "\n");
//                    }catch (IndexOutOfBoundsException e){
//                        BILL = BILL + String.format("%-15s %15s", "       ", CUS_ADDRESS.substring(i, CUS_ADDRESS.length()) + "\n");
//                    }
//                    cr+=15;
//                }
//            }
//        }
//        BILL = BILL + String.format("%-15s %15s", "Acc. No    :",  Accno+"\n");
//        BILL = BILL + String.format("%-15s %15s", "Pay Mode   :",  spinner+"\n");
//        BILL = BILL + String.format("%-15s %15s", "Bal Amt    :", "Rs ."+ Math.abs(Integer.parseInt(balance))+"\n");
//        BILL = BILL + String.format("%-15s %15s", "Coll Amt   :",  "Rs ."+amount+"\n");
//
//            BILL = BILL
//                    + "--------------------------------";
//
//
//                int tot = Integer.parseInt(balance) + Integer.parseInt(amount);
//                BILL = BILL + String.format("%-15s %15s", "Total      :",  "Rs ."+Math.abs(tot)+"\n");
//
//
//
//        BILL = BILL
//                + "--------------------------------";
//        BILL = BILL + String.format("%-15s %15s", "Agent      :",  agent+"\n");
//        BILL = BILL + StringUtils.center(dilog1,32);
//        BILL = BILL + StringUtils.center(dilog2,32);
//        BILL = BILL+"\n";
//            int iLen = 0;
//            int iCount = 0;
//            int iPoss = 0;
//
//            byte[] headl = BILL.getBytes();
//            iLen = headl.length;
//            iLen += 4;
//            final int[] baba = new int[iLen];
//            baba[0] = (byte) 0x1B;
//            baba[1] = (byte) 0x21;
//            baba[2] = (byte) 0x30;
//            iCount = 3;
//            for (; iCount < iLen - 1; iCount++, iPoss++) {
//                baba[iCount] = headl[iPoss];
//            }
//            baba[iCount] = (byte) 0x30;
//
//
//
//            if (sessionmanager.getValue("model").equals("SIL_PALMTECANDRO_4G")) {
//                palmtecandro.jnidevDataWrite(baba, iLen);
//
//            }else {
//                MainActivity.print(BILL);
//            }
//
//    }
//
//public void TakePrint2(View view){
//
//    String title = sessionmanager.getValue("company");
//    String agent=sessionmanager.getValue("uname");
//    String address = sessionmanager.getValue("address");
//    String dilog2="Stay Home - Stay Safe";
//    String dilog1="Thank You Have a Nice Day";
//        int totalamount=0;
//
//    List<DBPasser> contacts = offlineSaveDatabse.getAllContacts();
//    String BILL = "\n";
//    BILL = BILL + StringUtils.center(title,32);
//
//    BILL = BILL + StringUtils.center(address,32);
//
//    BILL = BILL
//            + "--------------------------------";
//
//    BILL = BILL +StringUtils.center("ACCTYPE : COLL",32);
//    BILL = BILL
//            + "--------------------------------";
//
//    BILL = BILL + String.format("%-10s %-10s %10s", "RCPT NO", "ACC NO", "AMOUNT");
//
//    BILL = BILL
//            + "--------------------------------";
//    BILL = BILL +"\n";
//            for (int i=0;i<contacts.size();i++) {
//                DBPasser dbPasser = contacts.get(i);
//                String id = dbPasser.getId();
//                String date = dbPasser.getDate();
//                String data = dbPasser.getData();
//                String time = dbPasser.getTime();
//                String isonline = dbPasser.getIsonline();
//                String acc_no = null;
//                String amount = null;
//                String str = data.replaceAll("[\\[\\]\\(\\)]", "");
//
//                try {
//
//                    JSONObject nn = new JSONObject(str);
//
//
//                    acc_no = nn.getString("cus_accno");
//                    amount = nn.getString("amount");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                String bb=String.format("%03d",Integer.parseInt(id));
//                BILL = BILL + String.format("%-10s %-10s %10s", bb, acc_no, amount+".00\n");
//
//
//                try {
//                    totalamount += Integer.parseInt(amount);
//                }catch (NumberFormatException e){e.printStackTrace();}
//            }
//
//    BILL = BILL
//            + "--------------------------------";
//
//    BILL = BILL + String.format("%-15s %15s", "Total      :",  "Rs ."+totalamount+"\n");
//
//    BILL = BILL
//            + "--------------------------------";
//
//
//    BILL = BILL+"\n";
//    showCustomDialog1(view,date,BILL);
//
//}
//    private void showCustomDialog1(View view, String date, final String data) {
//
//
//        ViewGroup viewGroup = view.findViewById(android.R.id.content);
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.view_receipt, viewGroup, false);
//        builder1 = new AlertDialog.Builder(context);
//        builder1.setView(dialogView);
//        alertDialog1 = builder1.create();
//        alertDialog1.show();
//
//        TextView date1=dialogView.findViewById(R.id.orderdate);
//        TextView payment=dialogView.findViewById(R.id.maintext);
//        Button close=dialogView.findViewById(R.id.enough);
//
//        date1.setText("  Date : "+date);
//        payment.setText(data);
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog1.dismiss();
//                int iLen = 0;
//                int iCount = 0;
//                int iPoss = 0;
//
//                byte[] headl = data.getBytes();
//                iLen = headl.length;
//                iLen += 4;
//                final int[] baba = new int[iLen];
//                baba[0] = (byte) 0x1B;
//                baba[1] = (byte) 0x21;
//                baba[2] = (byte) 0x00;
//                iCount = 3;
//                for (; iCount < iLen - 1; iCount++, iPoss++) {
//                    baba[iCount] = headl[iPoss];
//                }
//                baba[iCount] = (byte) 0x30;
//
//
//
//                if (sessionmanager.getValue("model").equals("SIL_PALMTECANDRO_4G")) {
//                    palmtecandro.jnidevDataWrite(baba, iLen);
//
//                }else {
//                    MainActivity.print(data);
//                }
//            }
//        });
//    }
//
//}
//class StringUtils {
//
//    public static String center(String s, int size) {
//        return center(s, size, ' ');
//    }
//
//    public static String center(String s, int size, char pad) {
//        if (s == null || size <= s.length())
//            return s;
//
//        StringBuilder sb = new StringBuilder(size);
//        for (int i = 0; i < (size - s.length()) / 2; i++) {
//            sb.append(pad);
//        }
//        sb.append(s);
//        while (sb.length() < size) {
//            sb.append(pad);
//        }
//        return sb.toString();
//    }
}