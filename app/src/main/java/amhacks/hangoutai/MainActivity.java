package amhacks.hangoutai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Spinner cuisineSpinner, paymentSpinner, budgetSpinner, ambianceSpinner, transSpinner, alcoholSpinner, parkSpinner;
    String cuisine, payment, budget, ambiance, transport, alcohol, parking;
    Button predictButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cuisineSpinner = (Spinner) findViewById(R.id.cuisineSpinner);
        paymentSpinner = (Spinner) findViewById(R.id.paymentSpinner);
        budgetSpinner = (Spinner) findViewById(R.id.budgetSpinner);
        ambianceSpinner = (Spinner) findViewById(R.id.ambianceSpinner);
        transSpinner = (Spinner) findViewById(R.id.transportSpinner);
        alcoholSpinner = (Spinner) findViewById(R.id.alcoholSpinner);
        parkSpinner = (Spinner) findViewById(R.id.parkingSpinner);
        predictButton = (Button) findViewById(R.id.find_restaurants_btn);

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuisine = cuisineSpinner.getSelectedItem().toString();
                payment = paymentSpinner.getSelectedItem().toString();
                budget = budgetSpinner.getSelectedItem().toString();
                ambiance = ambianceSpinner.getSelectedItem().toString();
                transport = transSpinner.getSelectedItem().toString();
                alcohol = alcoholSpinner.getSelectedItem().toString();
                parking = parkSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(cuisine) || TextUtils.isEmpty(payment) || TextUtils.isEmpty(budget)
                        || TextUtils.isEmpty(ambiance) || TextUtils.isEmpty(transport) || TextUtils.isEmpty(alcohol)
                            || TextUtils.isEmpty(parking))
                {
                    Toast.makeText(MainActivity.this, "Please provide us all the details to find the best restaurant for you", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String query = "";
                    query = "rec_query = { \"from\":\"ratings\"\"where\": {\n" +
                            "        \"userID\": {\n" +
                            "            \"cuisine\": \"" + cuisine + "\", \n" +
                            "            \"payment\": \""+ payment + "\"\n" +
                            "            \"budget\": \""+ budget + "\"\n" +
                            "            \"ambiance\": \""+ ambiance + "\"\n" +
                            "            \"transport\": \""+ transport + "\"\n" +
                            "            \"alcohol\": \""+ alcohol + "\"\n" +
                            "            \"parking_lot\": \""+ parking + "\"\n" +
                            "        }\n" +
                            "    },\n" +
                            "    \"recommend\": \"placeID\",\n" +
                            "    \"goal\": {\"rating\": 2}";

                    String beg = "from aito.schema import AitoStringType, AitoTextType, AitoDelimiterAnalyzerSchema, AitoTableSchema, AitoColumnLinkSchema, AitoDatabaseSchema\n" +
                            "from aito.client import AitoClient\n" +
                            "import aito.api as aito_api\n" +
                            "import json\n" +
                            "\n" +
                            "AITO_INSTANCE_URL = 'https://recommender.aito.app/'\n" +
                            "AITO_API_KEY = '8l2T7nRxqy1kyydH7fiqf5H65TjHit5T6G8S1wFr'\n" +
                            "\n" +
                            "client = AitoClient(instance_url=AITO_INSTANCE_URL, api_key=AITO_API_KEY)\n";

                    String end = "res = aito_api.recommend(client=client, query=rec_query)\n" +
                            "rec_dict = json.loads(str(res.hits[0]))\n print(\"Restaurant name: \" + rec_dict[\"name\"]\n";

                    String final_ip = beg + query + end;
                    ProcessBuilder processBuilder = new ProcessBuilder("python -c \"" + final_ip + "\"");

                    String op = "";
                    Process p = null;
                    try {
                        p = processBuilder.start();
                        p.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = "";
                    while (true) {
                        try {
                            if (!((line = bfr.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        op += line;
                    }
                    Intent cIntent = new Intent(MainActivity.this, RecommenderActivity.class);
                    cIntent.putExtra("result" , op);
                    startActivity(cIntent);
                }
            }
        });




    }
}