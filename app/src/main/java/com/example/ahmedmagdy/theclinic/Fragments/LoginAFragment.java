package com.example.ahmedmagdy.theclinic.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedmagdy.theclinic.Notifications.Token;
import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAFragment extends Fragment {
    private TextView singin, create, forget;
    private EditText editTextemail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseUser fuser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_login, container, false);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        editTextemail = getActivity().findViewById(R.id.edit_email);
        editTextPassword = getActivity().findViewById(R.id.edit_password);
        singin = getActivity().findViewById(R.id.getstarted);
        create = getActivity().findViewById(R.id.create);
        forget = getActivity().findViewById(R.id.forget);
        progressBar = getActivity().findViewById(R.id.progressbar);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                 it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 finish();
                 startActivity(it);
                 **/
            }
        });

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to recover your password
                // forgetPassword();
                resetPasswordRequest();
            }
        });

        // to keep user logged in
        initAuthStateListener();

    }
    private void userLogin() {
        String mEmail = editTextemail.getText().toString().trim();
        String mPassword = editTextPassword.getText().toString().trim();

        if (mEmail.isEmpty()) {
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            editTextemail.setError("Please enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if (mPassword.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if (mPassword.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
// to login using email and password
        if (isNetworkConnected()) {
            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        //Log.d(TAG, "SIGNIN SUCCESS");
                        Toast.makeText(getActivity(), "SIGNIN SUCCESS", Toast.LENGTH_SHORT).show();
                        /** Intent intend= new Intent(LoginActivity.this, AllDoctorActivity.class);
                         intend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         finish();
                         startActivity(intend);**/
                        /*  updateToken(FirebaseInstanceId.getInstance().getToken());*/

                        getallData();

                    } else {
                        // Log.e(TAG, task.getException().getMessage());
                        //Log.e(TAG, "SIGNIN ERROR");
                        Toast.makeText(getActivity(), "SIGNIN ERROR", Toast.LENGTH_SHORT).show();

                    }
                }

            });
        } else {
            Toast.makeText(getActivity(), "please check the network connection", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }

    private void getallData() {
        DatabaseReference databaseChat = FirebaseDatabase.getInstance().getReference("ChatRoom");
        //**************************************************//
        // private void getallData();
        final ValueEventListener postListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                String usertype = dataSnapshot1.child(mAuth.getCurrentUser().getUid()).child("ctype").getValue(String.class);


                if (usertype.equals("User")) {
                    /**
                     Intent iii = new Intent(LoginActivity.this, SplashActivity.class);
                     iii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     finish();
                     startActivity(iii);
                     finish();
                     **/
                } else if (usertype.equals("Doctor")) {
                    /**
                     Intent iii = new Intent(LoginActivity.this, SplashActivity.class);
                     iii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     finish();
                     startActivity(iii);
                     finish();
                     **/
                } else {
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseChat.addValueEventListener(postListener1);
    }

    // to keep user logged when you leave app
    private void initAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
/**
 Intent iii = new Intent(LoginActivity.this, AllDoctorActivity.class);
 iii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 finish();
 startActivity(iii);
 **/
                    // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    // Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //  check if network is connected
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return true;
        } else {
            return false;
        }
    }

    private void displayResetPasswordDialog(String email) {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("reset_password_dialog_title");
        alertDialog.setMessage("sending_email");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void resetPasswordRequest() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        final EditText editTextemail = (EditText) dialog.findViewById(R.id.edit_email_tv);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel_tv);
        TextView submit = (TextView) dialog.findViewById(R.id.submit_tv);
        final TextView errorMessage = (TextView) dialog.findViewById(R.id.error_message_tv);

        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress_bar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mEmail = editTextemail.getText().toString().trim();

                if (mEmail.isEmpty()) {
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    editTextemail.setError("Please enter a valid email");
                    editTextemail.requestFocus();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
// to login using email and password
                if (isNetworkConnected()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        displayResetPasswordDialog(mEmail);
                                        dialog.dismiss();
                                    } else {
                                        errorMessage.setText("reset password error message");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "network connection error", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }
}
