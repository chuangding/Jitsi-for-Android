package com.bu.meet.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bu.meet.R;

/**
 * Created by HL on 28-09-2015.
 */
public class SignUp extends Activity {

    DatabaseHelper helper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }
    public void onSignUpClick(View v)
    {
        if(v.getId()==R.id.Bsignupbutton)
        {
            EditText name=(EditText)findViewById(R.id.TFname);
            EditText email=(EditText)findViewById(R.id.TFemail);
            EditText uname=(EditText)findViewById(R.id.TFuname);
            EditText pass1=(EditText)findViewById(R.id.TFpass1);
            EditText pass2=(EditText)findViewById(R.id.TFpass2);
            String namestr =name.getText().toString();
            String emailstr =email.getText().toString();
            String unamestr =uname.getText().toString();
            String pass1str =pass1.getText().toString();
            String pass2str =pass2.getText().toString();
            if(!pass1str.equals(pass2str))
            {
                //PopUp msg
                Toast pass=Toast.makeText(SignUp.this,"Passwords dont match",Toast.LENGTH_SHORT);
                pass.show();
            }
            else
            {
                //insert the details in database
                Contact c=new Contact();
                c.setName(namestr);
                c.setEmail(emailstr);
                c.setUname(unamestr);
                c.setPass(pass1str);
                helper.insertContact(c);
            }
        }
    }
}
