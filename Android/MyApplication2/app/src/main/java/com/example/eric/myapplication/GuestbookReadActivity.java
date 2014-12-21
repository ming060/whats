package com.example.eric.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import entity.Message;
import entity.User;
import service.IServerService;
import service.ServerServiceImpl;


public class GuestbookReadActivity extends BaseActivity {
    private ListView listView_msgList;
    private MyAdapter adapter;

    {
        super.isLogEnable = true;
    }

    @Override
    protected void initUIComponent() {
        listView_msgList = (ListView) findViewById(R.id.listView_msgList);
        // 建立自訂的 Adapter
        adapter = new MyAdapter(this);

        // 設定 ListView 的資料來源
        listView_msgList.setAdapter(adapter);
        listView_msgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                onItemClick_msgList(arg0, arg1, arg2, arg3);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_read);
        initUIComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setMsgList(User.me.getAllMsgList());
        adapter.notifyDataSetChanged();
        listView_msgList.invalidateViews();
        listView_msgList.refreshDrawableState();
    }

    @Override
    public void onBackPressed() {
        User.me.removeAllMsg();
        super.onBackPressed();
    }

    private void onItemClick_msgList(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Message msg = (Message) listView_msgList.getItemAtPosition(arg2);
        Intent intent = new Intent (getApplicationContext(), MessageActivity.class);
        intent.putExtra("Index", arg2);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guestbook_read, menu);
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

    public class MyAdapter extends BaseAdapter {
        private List<Message> msgList = User.me.getAllMsgList();

        private LayoutInflater myInflater;

        public MyAdapter(Context c) {
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public Object getItem(int position) {
            return msgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = myInflater.inflate(R.layout.item_layout, null);

            // 取得 mylayout.xml 中的元件
            ImageView imgLogo = (ImageView) convertView
                    .findViewById(R.id.imgLogo);
            TextView textView_from = ((TextView) convertView
                    .findViewById(R.id.textView_from));
            TextView textView_time = ((TextView) convertView
                    .findViewById(R.id.textView_time));

            // 設定元件內容
            Message msg = this.msgList.get(position);
            textView_from.setText(msg.getFrom());
            textView_time.setText(msg.getTime());

            return convertView;
        }

        public void setMsgList(List<Message> msgList) {
            this.msgList = msgList;
        }
    }


}
