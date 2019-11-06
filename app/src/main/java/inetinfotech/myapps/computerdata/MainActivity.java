package inetinfotech.myapps.computerdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    String a,b,c,d,e,f,img;
    ProgressBar br;
    private IntentIntegrator qrScan;
    IntentResult result;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.imageView);
        tv1=findViewById(R.id.textView3);
        tv2=findViewById(R.id.textView4);
        tv3=findViewById(R.id.textView5);
        tv4=findViewById(R.id.textView6);
        br=findViewById(R.id.progressBar);
        tv5=findViewById(R.id.textView);
        tv6=findViewById(R.id.textView2);
        btn=findViewById(R.id.button);
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {



                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                SystemData();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }



    public  void SystemData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://transvestic-bear.000webhostapp.com/SystemImages.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
br.setVisibility(View.INVISIBLE);
Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json_obj = jsonArray.getJSONObject(i);
                                a= json_obj.getString("Name");
                                b= json_obj.getString("RAM");
                                c= json_obj.getString("HDD");
                                d= json_obj.getString("OS");
                                e= json_obj.getString("Keyboard");
                                f= json_obj.getString("Mouse");
                                img=json_obj.getString("Image");
                                Picasso.with(MainActivity.this).load(img).into(iv);

                                tv1.setText(a);
                                tv2.setText(b);
                                tv3.setText(c);
                                tv4.setText(d);
                                tv5.setText(e);
                                tv6.setText(f);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();


                params.put("Systemid",result.getContents());

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}


