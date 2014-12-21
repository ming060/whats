package com.example.eric.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import entity.Message;
import entity.User;


public class MessageActivity extends BaseActivity {
    private EditText editText_from;
    private EditText editText_msg;

    @Override
    protected void initUIComponent() {
        log.d("initUIComponent() -----------------");
        editText_from = (EditText) findViewById(R.id.editText_from);
        editText_msg = (EditText) findViewById(R.id.editText_msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initUIComponent();
        Intent intent = getIntent();
        int index = intent.getIntExtra("Index", 0);
        Message msg = User.me.readMsg(index);
        this.editText_from.setText(msg.getFrom());
        this.editText_msg.setText(msg.getMsg());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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
}
