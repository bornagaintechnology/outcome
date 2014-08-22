package deftsoft.android.iground.utility;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class WebServiceResponse extends AsyncTask<Void, Void, String> {

	public interface onWebResponse {

		public void onSuccess(String result);

		public void onError(String error);
	}

	private onWebResponse listener;

	private boolean isresult = false;

	private final String PLEASE_WAIT = "Please wait...";

	private String url;

	private ProgressDialog mProgressDialog;

	public WebServiceResponse(Context context, String url,

	onWebResponse callback) {
		// TODO Auto-generated constructor stub
		this.url = url;

		mProgressDialog = new ProgressDialog(context);

		mProgressDialog.setMessage(PLEASE_WAIT);

		mProgressDialog.setCanceledOnTouchOutside(false);

		listener = callback;

		execute();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		mProgressDialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String result = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpGet httpget = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpget);

			HttpEntity httpEntity = httpResponse.getEntity();

			result = EntityUtils.toString(httpEntity);

			isresult = true;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			result = "ClientProtocolException";
			isresult = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			result = "IOException";
			isresult = false;
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}

		if (isresult) {
			listener.onSuccess(result);
		} else {

			listener.onError(result);
		}
	}
}
