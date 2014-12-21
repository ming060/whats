package com.example.eric.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.internal.widget.TintEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import service.FakeServerServiceImpl;
import service.IServerService;
import service.ServerServiceImpl;


public class MainActivity extends BaseActivity {
    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;
    private Button button_cancel;
    private Button button_register;

    {
//        super.isLogEnable = true;
    }

    @Override
    protected void initUIComponent() {
        log.d("initUIComponent() -----------------");
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);

        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_button_login(v);
                    }
                });

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_button_cancel(v);
                    }
                });

        button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_button_register(v);
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClick_button_login(View v) {
        String username = this.editText_username.getText().toString();
        String password = this.editText_password.getText().toString();
        log.d("onClick_button_login()  {username:" + username + ", password:" +password+"}");
        new LoginAsyncTask().execute(username, password);
    }

    private void onClick_button_cancel(View v) {
        this.editText_username.setText("");
        this.editText_password.setText("");

    }

    private void onClick_button_register(View v) {
        String username = this.editText_username.getText().toString();
        String password = this.editText_password.getText().toString();
        log.d("onClick_button_register()  {username:" + username + ", password:" +password+"}");
        new RegisterAsyncTask().execute(username, password);
    }

    //===============================================================================================

    private class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {
        private IServerService serverService = new ServerServiceImpl();

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                boolean isSuccess = serverService.login(params[0], params[1]);
                return isSuccess;
            } catch (Exception e) {
                log.e(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            log.d("onPostExecute "+ result);
            if (result) {
                Intent intent = new Intent(getApplicationContext(), GuestbookActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "登入失敗!請檢察帳號密碼!("+this.serverService.getMsg()+")",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class RegisterAsyncTask extends AsyncTask<String, Void, Boolean> {
        private IServerService serverService = new ServerServiceImpl();

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                boolean isSuccess =  serverService.register(params[0], params[1]);
                return isSuccess;
            } catch (Exception e) {
                log.e(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            log.d("onPostExecute "+ result);
            if (result) {
                Intent intent = new Intent(getApplicationContext(), GuestbookActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "註冊失敗!("+this.serverService.getMsg()+")",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}

