package amhacks.hangoutai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RecommenderActivity extends AppCompatActivity {

    TextView opText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommender);

        opText = (TextView) findViewById(R.id.opp);
        String res = getIntent().getStringExtra("result");
        opText.setText(res);

    }
}