package com.example.eric.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import service.IServerService;
import service.ServerServiceImpl;


public class GuestbookWriteActivity extends BaseActivity {
    private EditText editText_to;
    private EditText editText_write;
    private Button button_send;

    @Override
    protected void initUIComponent() {
        log.d("initUIComponent() -----------------");
        editText_to = (EditText) findViewById(R.id.editText_to);
        editText_write = (EditText) findViewById(R.id.editText_write);

        button_send = (Button) findViewById(R.id.button_send);
        button_send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick_button_send(v);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_write);
        initUIComponent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guestbook_write, menu);
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

    private void onClick_button_send(View v) {
        String receiver = this.editText_to.getText().toString();
        String msg = this.editText_write.getText().toString();
        log.d("onClick_button_send {"+receiver+":"+msg+"}" );
        new SendMsgAsyncTask().execute(receiver, msg);
    }

    private class SendMsgAsyncTask extends AsyncTask<String, Void, Boolean> {
        private IServerService serverService = new ServerServiceImpl();

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                boolean isSuccess = serverService.write(params[0], params[1]);
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
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(),
                        "訊息傳送失敗!(" + this.serverService.getMsg() + ")",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
