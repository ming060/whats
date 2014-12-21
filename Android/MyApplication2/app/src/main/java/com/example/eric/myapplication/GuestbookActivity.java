package com.example.eric.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import entity.Message;
import entity.User;
import service.IServerService;
import service.ServerServiceImpl;


public class GuestbookActivity extends BaseActivity {
    private Button button_toWrite;
    private Button button_toRead;

    {
//        super.isLogEnable = true;
    }

    @Override
    protected void initUIComponent() {
        button_toWrite = (Button) findViewById(R.id.button_toWrite);
        button_toWrite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_button_toWrite(v);
                    }
                });

        button_toRead = (Button) findViewById(R.id.button_toRead);
        button_toRead.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_button_toRead(v);
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook);
        initUIComponent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guestbook, menu);
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

    private void onClick_button_toWrite(View v) {
        log.d("onClick_button_toWrite");
        Intent intent = new Intent(getApplicationContext(), GuestbookWriteActivity.class);
        startActivity(intent);
    }

    private void onClick_button_toRead(View v) {
        log.d("onClick_button_toRead");
        new ReadMsgAsyncTask().execute();
    }


    private class ReadMsgAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private IServerService serverService = new ServerServiceImpl();

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                boolean isSuccess = serverService.read();
                if (isSuccess) {
                    String receiveMsg = this.serverService.getMsg();
                    log.d("jsonString=" + receiveMsg);
//                    if (receiveMsg.equals("[]")) {
//                    } else {
                        try {
                            JSONArray jsonArray = new JSONArray(receiveMsg);
                            log.d("jsonArray.length=" + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Message msg = new Message();
                                msg.setFrom(jsonObject.getString("Provider"));
                                msg.setMsg(jsonObject.getString("Message"));
                                msg.setTime(jsonObject.getString("Timestamp"));
                                log.d(msg.toString());
                                User.me.addMsg(msg);
                            }
                        } catch (JSONException e) {
                            log.e(e);
                        } catch (ParseException e) {
                            log.e(e);
                        }
//                    }
                }
                return isSuccess;
            } catch (Exception e) {
                log.e(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            log.d("onPostExecute " + result);
            if (result) {
                Intent intent = new Intent(getApplicationContext(), GuestbookReadActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "讀取失敗!(" + this.serverService.getMsg() + ")",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
