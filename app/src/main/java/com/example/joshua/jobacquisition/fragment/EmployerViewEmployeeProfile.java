package com.example.joshua.jobacquisition.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joshua.jobacquisition.Activity.Login;
import com.example.joshua.jobacquisition.Adapter.YourJobAdapter;
import com.example.joshua.jobacquisition.R;
import com.example.joshua.jobacquisition.model.Image;
import com.example.joshua.jobacquisition.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmployerViewEmployeeProfile extends Fragment {

    private SweetAlertDialog Dialog;
    private ProgressDialog pDialog;
    private String TAG = EmployeeViewJob.class.getSimpleName();
    //private ArrayList<Image> images;
    //private YourJobAdapter mAdapter;
    //private RecyclerView recyclerView;

    Button interview;
    TextView name,dob,id,email,nationality,languages,low,high,other,mission,vission,experince,skills,refname,contact,occupation;
    private OnFragmentInteractionListener mListener;

    public EmployerViewEmployeeProfile() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static EmployerViewEmployeeProfile newInstance(String param1, String param2) {
        EmployerViewEmployeeProfile fragment = new EmployerViewEmployeeProfile();
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
        View view= inflater.inflate(R.layout.fragment_employer_view_employee_profile, container, false);
        getRequests();


       // Image image = new Image();
        name=(TextView) view.findViewById(R.id.EmployeeNames);
        //name.setText(image.getName());
        dob=(TextView) view.findViewById(R.id.EmployeeDob);
        //dob.setText(image.getDob());
        id=(TextView) view.findViewById(R.id.EmployeeID);
        //id.setText(image.getTid());
        email=(TextView) view.findViewById(R.id.EmployeeEmail);
        //email.setText(image.getEmail());
        nationality=(TextView) view.findViewById(R.id.EmployeeNationality);
        //nationality.setText(image.getNationality());
        languages=(TextView) view.findViewById(R.id.EmployeeLanguages);
        //languages.setText(image.getLanguages());
        low=(TextView) view.findViewById(R.id.EmployeeLow);
        //low.setText(image.getLow());
        high=(TextView) view.findViewById(R.id.EmployeeHigher);
        //high.setText(image.getHigh());
        other=(TextView) view.findViewById(R.id.EmployeeOther);
        //other.setText(image.getOther());
        mission=(TextView) view.findViewById(R.id.EmployeeMission);
        //mission.setText(image.getMission());
        vission=(TextView) view.findViewById(R.id.EmployeeVision);
        //vission.setText(image.getVission());
        experince=(TextView) view.findViewById(R.id.EmployeeExpirience);
        //experince.setText(image.getExperince());
        skills=(TextView) view.findViewById(R.id.Employeeskills);
        //skills.setText(image.getSkills());
        refname=(TextView) view.findViewById(R.id.EmployeeReffName);
        //refname.setText(image.getRefname());
        contact=(TextView) view.findViewById(R.id.EmployeeContact);
        //contact.setText(image.getContact());
        occupation=(TextView) view.findViewById(R.id.EmployeeOccupation);

        interview=(Button) view.findViewById(R.id.invite);
        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: get phoneNumber,employeeName, jobType and initiate sending sms
                employer_invite();


            }
        });

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

//        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
//        final String userID = editor.getString("THE_KEY_USERID", "null");


        Dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        Dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        Dialog.setTitleText("Loading");
        Dialog.setCancelable(false);
        Dialog.show();
        //pDialog.setCancelable(true);
        //pDialog.show();

//        final JsonArrayRequest req = new JsonArrayRequest(Keys.employeeViewEmployer+"?userID="+4,//EmployerJobsApplied.userID
//                new Response.Listener<JSONArray>() {
        StringRequest req = new StringRequest(Keys.employeeViewEmployer+"?userID="+EmployerJobsApplied.userID,
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

                                name.setText("Name: "+object.getString("name"));
                                dob.setText("DOB: "+object.getString("Dob"));
                                id.setText("Id Number: "+object.getString("id"));
                                email.setText("Email: "+object.getString("email"));
                                nationality.setText("Nationality: "+object.getString("Nationality"));
                                languages.setText("Languages"+object.getString("Languages"));
                                low.setText("Low Levels: "+object.getString("low"));
                                high.setText("Higher Levels: "+object.getString("high"));
                                other.setText("Others: "+object.getString("other"));
                                mission.setText("Mission: "+object.getString("mission"));
                                vission.setText("Vision: "+object.getString("vision"));
                                experince.setText("Experience: "+object.getString("experience"));
                                skills.setText("Skills: "+object.getString("skills"));
                                refname.setText("Ref Name: "+object.getString("refName"));
                                contact.setText("Ref Contact: "+object.getString("contact"));
                                occupation.setText("Ref Occupation: "+object.getString("occupation"));

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


    //TODO pass to API to send sms
    public void employer_invite()
    {

        SharedPreferences editor = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        final String userid = editor.getString("THE_KEY_USERID", "null");


        final String Title =  EmployerYourJobs.title;;
        final String phone = EmployerJobsApplied.phoneNumber;
        final String name = EmployerJobsApplied.name;
        Toast.makeText(getActivity(), ""+Title+phone+name, Toast.LENGTH_SHORT).show();

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading", "Please wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.employerInviteInterview,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), " "+response, Toast.LENGTH_LONG).show();


//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.content_contractor, new ContractorViewJobs(), getString(R.string.app_name));
//                        ft.commit();


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Sorry "+error, Toast.LENGTH_LONG).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("JobTitle", Title);
                params.put("EmployeeName", name);
                params.put("PhoneNumber", phone);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    /**end save to database**/
}
