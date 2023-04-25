package com.demo.tyact1customerrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateNewCustomer extends AppCompatActivity {
    private static Button btnQuery;
    private static EditText etID,etlastname,etfirstname,etmiddlename,etcontact,etcourse,etyear;
    private static JSONParser jParser = new JSONParser();
    private static String urlHost = "http://192.168.254.105/ancuin/InsertTrans.php";
    private static String TAG_MESSAGE = "message", TAG_SUCCESS = "success";
    private static String online_dataset = "";
    private static String StudentID = "";
    private static String LastName = "";
    private static String FirstName = "";
    private static String MiddleInitial = "";
    private static String ContactNumber = "";
    private static String Course = "";
    private static String Year = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_customer);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        etID = findViewById(R.id.etID);
        etlastname = findViewById(R.id.etLName);
        etfirstname = findViewById(R.id.etFName);
        etmiddlename = findViewById(R.id.etMName);
        etcontact = findViewById(R.id.etContNum);
        etcourse = findViewById(R.id.etCourse);
        etyear = findViewById(R.id.etYear);

        //btn
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentID = etID.getText().toString();
                LastName = etlastname.getText().toString();
                FirstName = etfirstname.getText().toString();
                MiddleInitial = etmiddlename.getText().toString();
                ContactNumber = etcontact.getText().toString();
                Course = etcourse.getText().toString();
                Year = etyear.getText().toString();
                new uploadDatatoURL().execute();
            }
        });

    }
    private class uploadDatatoURL extends AsyncTask<String, String, String> {
        String cPOST = "", cPostSQL = "", cMessage = "Querying data...";
        int nPostValueIndex;
        ProgressDialog pDialog = new ProgressDialog(CreateNewCustomer.this);

        public uploadDatatoURL(){}
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setMessage(cMessage);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            int nSuccess;
            try {
                ContentValues cv = new ContentValues();
                cPostSQL = " '" + StudentID + "' , '" + LastName + "' , '" + FirstName + "' , '"
                        + MiddleInitial + "' , '" + ContactNumber + "' , '" + Course + "' , '" + Year + "' ";
                cv.put("code", cPostSQL);

                JSONObject json = jParser.makeHTTPRequest(urlHost, "POST", cv);
                if (json != null) {
                    nSuccess = json.getInt(TAG_SUCCESS);
                    if (nSuccess == 1) {
                        online_dataset = json.getString(TAG_MESSAGE);
                        return online_dataset;
                    } else {
                        return json.getString(TAG_MESSAGE);
                    }
                } else {
                    return "HTTPSERVER_ERROR";
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            pDialog.dismiss();
            String isEmpty = "";
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateNewCustomer.this);
            if (s !=null) {
                if (isEmpty.equals("") && !s.equals("HTTPSERVER_ERROR")) {
                }
                Toast.makeText(CreateNewCustomer.this, s, Toast.LENGTH_SHORT).show();
            }else{
                alert.setMessage("Query Interrupted ... \nPlease Check Internet Connection");
                alert.setTitle("Error");
                alert.show();
            }
        }
    }
}
