package com.bu.meet.login;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.DBSyncUtil;

import java.io.IOException;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSignUpClick(View v) throws IOException {
        if(v.getId()==R.id.Bsignupbutton)
        {
            EditText firstName=(EditText)findViewById(R.id.TFname);
            EditText lastName =(EditText)findViewById(R.id.TLname);
            EditText emailID=(EditText)findViewById(R.id.TFemail);
            EditText pass1=(EditText)findViewById(R.id.TFpass1);
            EditText pass2=(EditText)findViewById(R.id.TFpass2);
            if(firstName.getText().toString().trim().length()==0){
                firstName.setError("First Name is required");
            }else if(lastName.getText().toString().trim().length()==0){
                lastName.setError("Last Name is required");
            }else if(emailID.getText().toString().trim().length()==0){
                emailID.setError("Email is required");
            }else if(pass1.getText().toString().trim().length()==0){
                pass1.setError("Password is required");
            }else {


                String firstNamestr = firstName.getText().toString();
                String lastNamestr = lastName.getText().toString();
                String emailIDStr = emailID.getText().toString();
                String pass1str = pass1.getText().toString();
                String pass2str = pass2.getText().toString();
                if (!pass1str.equals(pass2str)) {
                    //PopUp msg
                    Toast pass = Toast.makeText(Register.this, "Passwords dont match", Toast.LENGTH_SHORT);
                    pass.show();
                } else {
                    Contact newUser = new Contact();
                    newUser.setFirstName(firstNamestr);
                    newUser.setLastName(lastNamestr);
                    newUser.setEmailID(emailIDStr);
                    newUser.setPassword(pass1str);
                    new DBSyncUtil(this,"defaut").execute(newUser);

                }

            }
        }
    }
}
