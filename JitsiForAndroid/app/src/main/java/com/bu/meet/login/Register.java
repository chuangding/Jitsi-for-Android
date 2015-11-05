package com.bu.meet.login;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bu.meet.R;

public class Register extends AppCompatActivity {

    DatabaseHelper helper=new DatabaseHelper(this);
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
                Toast pass=Toast.makeText(Register.this,"Passwords dont match",Toast.LENGTH_SHORT);
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
