package com.example.cabpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Options extends AppCompatActivity {

    Button logout;
    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        logout=findViewById(R.id.logoutbtn);
        dp=findViewById(R.id.display);

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try{
                            String fullname=object.getString("name");
                            String url=object.getJSONObject("picture").getJSONObject("data").getString("url");
                            Toast.makeText(Options.this, "logged in as"+ fullname, Toast.LENGTH_SHORT).show();
                            Picasso.get().load(url).into(dp);

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(Options.this,signupactivity.class));
                finish();
            }
        });

    }
}