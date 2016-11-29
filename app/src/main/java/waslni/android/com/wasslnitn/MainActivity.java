package waslni.android.com.wasslnitn;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
EditText ednom,edprenom,edmail,edlogin,edphone,edpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ednom=(EditText) findViewById(R.id.ednom);
        edprenom=(EditText) findViewById(R.id.edprenom);
        edmail=(EditText) findViewById(R.id.edmail);
        edlogin=(EditText) findViewById(R.id.edlogin1);
        edphone=(EditText) findViewById(R.id.edphone);
        edpass=(EditText) findViewById(R.id.edpass);
    }

    public void inscrit(View view) {

        String url="http://10.0.2.2/coiv/inscript.php?nom="+ednom.getText().toString()+"&prenom="+edprenom.getText().toString()+
                "&login="+edlogin.getText().toString()+"&phone="+edphone.getText().toString()+"&password="+edpass.getText().toString();
        new MyAsyncTaskgetNews().execute(url);
    }
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                JSONObject json=new JSONObject(progress[0]);
                Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_LONG).show();

            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String  result2){


        }




    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }
}
