package com.ttu.spm.appsum;

import android.os.Handler;
import android.location.Location;
import android.location.LocationManager;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.ttu.spm.appsum.adapter.ImageAdapter;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    AppLocationService appLocationService;
    // Array to hold the menu item details
    static final String[] MENU_ITEMS = new String[] { "ACCOMMODATION","FOOD", "TRANSPORT",
            "TOURISM" };
MenuItem citytextview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set grid view
        gridView = (GridView) findViewById(com.ttu.spm.appsum.R.id.gridViewHome);
        gridView.setAdapter(new ImageAdapter(this, MENU_ITEMS));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    // TODO: Displaying text for testing purpose. Main functionality yet to implement
                    Toast.makeText(
                            getApplicationContext(),
                            ((TextView) v.findViewById(R.id.grid_item_text))
                                    .getText(), Toast.LENGTH_SHORT).show();

                }
            });
       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //TODO: Check and remove code of adding menus to action bar if not required
       // MenuItem actionmenuitem1 = menu.add(Menu.NONE, Menu.NONE, 101, "Text");
        //MenuItem actionmenuitem2 = menu.add(Menu.NONE, Menu.NONE, 102, "Icon");

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

    // TODO: Method to find the location. Coding not yet completed
   private void findLocation(){
       Log.d("loc","1");
       Location gpsLocation = appLocationService
               .getLocation(LocationManager.GPS_PROVIDER);
       Log.d("loc","2");

       if (gpsLocation != null) {
           Log.d("loc","3");
           double latitude = gpsLocation.getLatitude();
           double longitude = gpsLocation.getLongitude();
           LocationAddress locationAddress = new LocationAddress();
           locationAddress.getAddressFromLocation(latitude, longitude,
                   getApplicationContext(), new GeocoderHandler());
       }
   }
    //TODO: Class to get the location
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            Menu menu;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            citytextview.setTitle(locationAddress);
        }
    }
}
