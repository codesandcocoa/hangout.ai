package amhacks.hangoutai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class RecommenderActivity extends AppCompatActivity {

    TextView opText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommender);

      /*  opText = (TextView) findViewById(R.id.opp);
        String res = getIntent().getStringExtra("result");
        opText.setText(res);
        Toast.makeText(this, "OP: " + res, Toast.LENGTH_SHORT).show();*/

    }
}