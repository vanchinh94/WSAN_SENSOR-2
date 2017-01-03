package c9.edu.lab411.sensortracking;
import org.json.JSONException;
import org.json.JSONObject;
import c9.edu.lab411.sensortracking.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
public class SensorTracking extends Activity{
	private BroadcastReceiver smsReceive = null;	
	private  IntentFilter intentFilter;
	private SmsManager smsManager = null;
	private static final String PHONE_NUM = "01675211874";
	private static final String PHONE_SMS = "MAP";
	private ProgressDialog pDialog;
	private Paint paint = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_tracking);
		intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		smsManager = SmsManager.getDefault();
		paint = new Paint();
		smsReceive = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				try{
					processSms(context, intent);				
				}
				catch(Exception ex){
					ex.printStackTrace();
				}						
			}		
		};
		registerReceiver(smsReceive, intentFilter);		
	}
	public void btnok_click(View view){
		smsManager.sendTextMessage(PHONE_NUM, null, PHONE_SMS, null, null);
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading Motion Sensor. Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();		
		
		setContentView(new DrawMap(this));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor_tracking, menu);
		return true;
	}
	public void processSms(Context context, Intent intent) throws JSONException{
		String smsExtra = "pdus";
		Bundle bundle = intent.getExtras();
		Object []objArr= (Object[]) bundle.get(smsExtra);
		if(objArr.length >0){
			pDialog.dismiss();		
		}
		for(int i=0; i<objArr.length;i++){
			 SmsMessage smsMsg=SmsMessage.createFromPdu((byte[]) objArr[i]);
			String body=smsMsg.getMessageBody();
			JSONObject json = new JSONObject(body);						
		}
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
