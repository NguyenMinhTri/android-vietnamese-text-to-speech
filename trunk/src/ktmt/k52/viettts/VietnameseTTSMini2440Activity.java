package ktmt.k52.viettts;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.ParseException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class VietnameseTTSMini2440Activity extends Activity {
	/** Called when the activity is first created. */

	private EditText inputText;
	private Button btSubmit, btChoose, btPlay, btStop, btExit;
	private ListView listText;
	private CheckBox cbText;
	private TextView status;

	private StreamMedia audioStreamer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initControl();

		// test
		inputText.setText("Thử nghiệm tiếng nói");

		// đặt sự kiện ấn nút submit
		btSubmit.setOnClickListener(new OnClickListener() {
			String temp = inputText.getText().toString();

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String response = HttpHelp.postPageIsolar(temp);
					status.setText("Requesting to isolar..");

					String audioUrl = HttpHelp.getIsolarAudioUrl(response);
					status.setText("Getting audio url..");
					
					String mediaName = mediaName(audioUrl);
					audioStreamer.startStreaming(audioUrl, mediaName);
					btSubmit.setEnabled(false);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		
		
	}

	private void initControl() {
		inputText = (EditText) findViewById(R.id.Input);
		btSubmit = (Button) findViewById(R.id.submit);
		btChoose = (Button) findViewById(R.id.Choose);
		btPlay = (Button) findViewById(R.id.play);
		btStop = (Button) findViewById(R.id.stop);
		btExit = (Button) findViewById(R.id.exit);

		listText = (ListView) findViewById(R.id.list);
		cbText = (CheckBox) findViewById(R.id.Get_text);
		status = (TextView) findViewById(R.id.text_kb_streamed);

		audioStreamer = new StreamMedia(this, status, btPlay, btSubmit);

	}

	private String mediaName(String mediaUrl) {
		int i = mediaUrl.lastIndexOf("/");
		String mediaName = mediaUrl.substring(i + 1);

		return mediaName;
	}
}