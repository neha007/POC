package gsonuse.com.gson_poc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.github.com/users";
    RecyclerView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userlist = (RecyclerView)findViewById(R.id.userlist);
        userlist.setLayoutManager(new LinearLayoutManager(this));

        StringRequest stringRequest = new StringRequest( URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Code",response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = new Gson();
                        User[]users = gson.fromJson(response,User[].class);
                        userlist.setAdapter(new GithubAdapter(MainActivity.this,users));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"something went wrong.....",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}
