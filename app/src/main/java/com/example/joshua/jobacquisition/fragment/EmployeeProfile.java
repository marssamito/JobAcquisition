package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joshua.jobacquisition.Activity.Login;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.model.Image;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmployeeProfile extends Fragment {

    private SweetAlertDialog Dialog;
    private ProgressDialog pDialog;
    private String TAG = EmployeeViewJob.class.getSimpleName();

    EditText name,dob,id,email,nationality,languages,low,high,other,mission,vission,experince,skills,refname,contact,occupation;
    private OnFragmentInteractionListener mListener;

    public EmployeeProfile() {
        // Required empty public constructor
    }

    
    // TODO: Rename and change types and number of parameters
    public static EmployeeProfile newInstance(String param1, String param2) {
        EmployeeProfile fragment = new EmployeeProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_employee_profile, container, false);
        getRequests();
        // Image image = new Image();
        name=(EditText) view.findViewById(R.id.EmployeeNames);
        //name.setText(image.getName());
        dob=(EditText) view.findViewById(R.id.EmployeeDob);
        //dob.setText(image.getDob());
        id=(EditText) view.findViewById(R.id.EmployeeID);
        //id.setText(image.getTid());
        email=(EditText) view.findViewById(R.id.EmployeeEmail);
        //email.setText(image.getEmail());
        nationality=(EditText) view.findViewById(R.id.EmployeeNationality);
        //nationality.setText(image.getNationality());
        languages=(EditText) view.findViewById(R.id.EmployeeLanguages);
        //languages.setText(image.getLanguages());
        low=(EditText) view.findViewById(R.id.EmployeeLow);
        //low.setText(image.getLow());
        high=(EditText) view.findViewById(R.id.EmployeeHigher);
        //high.setText(image.getHigh());
        other=(EditText) view.findViewById(R.id.EmployeeOther);
        //other.setText(image.getOther());
        mission=(EditText) view.findViewById(R.id.EmployeeMission);
        //mission.setText(image.getMission());
        vission=(EditText) view.findViewById(R.id.EmployeeVision);
        //vission.setText(image.getVission());
        experince=(EditText) view.findViewById(R.id.EmployeeExpirience);
        //experince.setText(image.getExperince());
        skills=(EditText) view.findViewById(R.id.Employeeskills);
        //skills.setText(image.getSkills());
        refname=(EditText) view.findViewById(R.id.EmployeeReffName);
        //refname.setText(image.getRefname());
        contact=(EditText) view.findViewById(R.id.EmployeeContact);
        //contact.setText(image.getContact());
        occupation=(EditText) view.findViewById(R.id.EmployeeOccupation);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getRequests() {

        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userID = editor.getString("THE_KEY_USERID", "null");


        Dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        Dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        Dialog.setTitleText("Loading");
        Dialog.setCancelable(false);
        Dialog.show();
        //pDialog.setCancelable(true);
        //pDialog.show();

//        final JsonArrayRequest req = new JsonArrayRequest(Keys.employeeViewEmployer+"?userID="+4,//EmployerJobsApplied.userID
//                new Response.Listener<JSONArray>() {
        StringRequest req = new StringRequest(Keys.employeeViewEmployer+"?userID="+userID,
                new Response.Listener<String>() {

                    //                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
                    @Override
                    public void onResponse(String response) {
                        Dialog.dismiss();

                        //for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object=new JSONObject(response.toString());
                            //JSONObject object = response.getJSONObject();
                            Image image = new Image();

                            name.setText(object.getString("name"));
                            dob.setText(object.getString("Dob"));
                            id.setText(object.getString("id"));
                            email.setText(object.getString("email"));
                            nationality.setText(object.getString("Nationality"));
                            languages.setText(object.getString("Languages"));
                            low.setText(object.getString("low"));
                            high.setText(object.getString("high"));
                            other.setText(object.getString("other"));
                            mission.setText(object.getString("mission"));
                            vission.setText(object.getString("vision"));
                            experince.setText(object.getString("experience"));
                            skills.setText(object.getString("skills"));
                            refname.setText(object.getString("refName"));
                            contact.setText(object.getString("contact"));
                            occupation.setText(object.getString("occupation"));

                        } catch (JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Dialog.hide();
                Toast.makeText(getActivity(), "sorry"+error, Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(req);
    }
}
