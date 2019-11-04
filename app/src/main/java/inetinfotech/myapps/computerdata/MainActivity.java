package inetinfotech.myapps.computerdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv1,tv2,tv3,tv4;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.imageView);
        tv1=findViewById(R.id.textView3);
        tv2=findViewById(R.id.textView4);
        tv3=findViewById(R.id.textView5);
        tv4.findViewById(R.id.textView6);
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
        iv.setOnClickListener(this);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result!= null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+result.getContents()));//change the number.
                startActivity(callIntent);

            }

        }

        else
        {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

    {
        @Override
        public void getSystemData(String ) {
            try {
                Toast.makeText(this,"",Toast.LENGTH_LONG).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://transvestic-bear.000webhostapp.com/SystemImages.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//If we are getting success from server
                    String latitude;
                    Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json_obj = jsonArray.getJSONObject(i);
                            latitude = json_obj.getString("latitude");
                                           /* longitude=json_obj.getString("longitude");
                                            Toast.makeText(MapsActivity.this,latitude,Toast.LENGTH_SHORT).show();*/
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                }

            }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
//Adding parameters to request
            params.put("", String.valueOf(Integer.parseInt(result.getContents())));
            params.put("id","12");

//returning parameter
            return params;
        }
    };

    //Adding the string request to the queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
                    } catch (Exception e) {

                    }

