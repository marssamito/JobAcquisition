package com.example.joshua.jobacquisition.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {

    Button loginsave,loginsignup,signsave,signgoback;
    LinearLayout login,signup;
    EditText loginUser,loginPassword,FirstName,LastName,Email,Password,Id,PhoneNumber;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginUser=(EditText) findViewById(R.id.login_email);
        loginPassword=(EditText) findViewById(R.id.login_password);

        FirstName=(EditText) findViewById(R.id.Sign_Firstname);
        LastName=(EditText) findViewById(R.id.Sign_lastname);
        Email=(EditText) findViewById(R.id.Sign_email);
        PhoneNumber=(EditText) findViewById(R.id.Sign_Phoneno);
        Password=(EditText) findViewById(R.id.Sign_password);
        Id=(EditText) findViewById(R.id.SignId);

        login=(LinearLayout) findViewById(R.id.Login_layout);
        signup=(LinearLayout) findViewById(R.id.sign_layout);

        loginsave=(Button) findViewById(R.id.Login);
        loginsignup=(Button) findViewById(R.id.LogGosignup);
        signsave=(Button) findViewById(R.id.signjoin);
        signgoback=(Button)  findViewById(R.id.signaccount);

        loginsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "signup", Toast.LENGTH_SHORT).show();
                post_users();
            }
        });

        loginsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                signup.setVisibility(View.VISIBLE);
            }
        });
        signgoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.VISIBLE);
                signup.setVisibility(View.GONE);
            }
        });
    }


    /**
     * start save to database*
  **/
//    public static boolean isValidEmailAddress(String email) {
//        boolean result = true;
//        try {
//            InternetAddress emailAddr = new InternetAddress(email);
//            emailAddr.validate();
//        } catch (AddressException ex) {
//            result = false;
//        }
//        return result;
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);}

    private void check()
    {
        final String fname = FirstName.getText().toString().trim();
        final String lname = LastName.getText().toString().trim();
        final String phoneno = PhoneNumber.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String id = Id.getText().toString().trim();


        if (TextUtils.isEmpty(fname))
    {
        FirstName.setError("This field is empty");
        return;
    }else if (TextUtils.isEmpty(lname)) {
        LastName.setError("This field is empty");
        return;
    }
        else
                if (TextUtils.isEmpty(phoneno)) {
        PhoneNumber.setError("This field is empty");
        return;
    }else
            if (TextUtils.isEmpty(email)) {
        Email.setError("This field is empty");
        return;
    }else
            if (TextUtils.isEmpty(password)) {
        Password.setError("This field is empty");
        return;
    }else
            if (TextUtils.isEmpty(id)) {
        Id.setError("This field is empty");
        return;
    }
        else{
        post_users();}
}


    private void post_users()
    {


        final String fname = FirstName.getText().toString().trim();
        final String lname = LastName.getText().toString().trim();
        final String phoneno = PhoneNumber.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String id = Id.getText().toString().trim();

        final ProgressDialog progress = ProgressDialog.show(Login.this,"Loading", "Please wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.Register,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        Toast.makeText(Login.this, "success "+response, Toast.LENGTH_LONG).show();

                        if(response.equals("success"))
                        {
                            login.setVisibility(View.VISIBLE);
                            signup.setVisibility(View.GONE);

                            loginUser.setText(email);
                            loginPassword.setText(password);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(Login.this, "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("phoneno", phoneno);                params.put("email", email);
                params.put("password", password);
                params.put("idNo", id);

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);

    }


    /**end save to database**/

    /**start save to database**/
    private void login()
    {
        final String usernames=loginUser.getText().toString();
        final String userpassword=loginPassword.getText().toString();

        final ProgressDialog progress = ProgressDialog.show(Login.this,"Loading", "Please wait...");


        StringRequest strinRequest = new StringRequest(Request.Method.POST, Keys.LogIn,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();

                        try {
                            JSONObject jObj=new JSONObject(response.toString());
                            String name = jObj.getString("name").toString();
                            String userID = jObj.getString("userId").toString();
                            String IDNO = jObj.getString("IDNO").toString();

//                                    Intent i = new Intent(Login.this, Category.class);
//                                    startActivity(i);
                            if (Category.catId.equals("1"))
                            {
                                Intent i = new Intent(Login.this, Employers.class);
                                startActivity(i);
                            }else if (Category.catId.equals("2")){
                                Intent i = new Intent(Login.this, Employee.class);
                                startActivity(i);
                            }


                                    Toast.makeText(Login.this, "Welcome "+name, Toast.LENGTH_LONG).show();

                                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
                                    editor.putString("KEY_USERNAME",name);
                                    editor.putString("THE_KEY_USERID",userID);
                                    editor.putString("THE_KEY_IDNO",IDNO);
                                    editor.commit();

                            // Toast.makeText(LogInActivity.this, role, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(Login.this, "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",usernames);
                params.put("password", userpassword);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(strinRequest);

    }
    /**end save to database**/

}
