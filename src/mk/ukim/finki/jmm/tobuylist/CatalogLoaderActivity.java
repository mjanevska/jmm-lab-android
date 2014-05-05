package mk.ukim.finki.jmm.tobuylist;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

public class CatalogLoaderActivity extends Activity {

    private ImageView downloadedImg;
    private ProgressDialog simpleWaitDialog;
    private Spinner mCatalog;
    private String downloadUrl = null;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_catalog);
        mCatalog = (Spinner) findViewById(R.id.catalogs);
        downloadedImg = (ImageView) findViewById(R.id.imageView);
    }
    
    public void loadCatalog(View view) {
    	String catalog = (String) mCatalog.getSelectedItem();
    	if (catalog.equals("Catalog1")){
    		downloadUrl = "http://bit.ly/1km7cjW";
    	}else if (catalog.equals("Catalog2")){
    		downloadUrl = "http://bit.ly/1fm1ujh";
    	}
    	else {
    		downloadUrl = "http://bit.ly/QJHVpw";
    	}
        new ImageDownloader().execute(downloadUrl);
	}
    
    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    	
    	/**
    	 * This function will be executed to download the image in a background
    	 * process.
    	 * 
    	 */
        protected Bitmap doInBackground(String... param) {
            // TODO Auto-generated method stub
            return downloadBitmap(param[0]);
        }

        @Override
        protected void onPreExecute() {
            Log.i("Async-Example", "onPreExecute Called");
            simpleWaitDialog = ProgressDialog.show(CatalogLoaderActivity.this,
                    "Wait", "Loading Image");

        }
        
        /**
    	 * This function will be called after the image download and attaches
    	 * the bitmap to the ImageView.
    	 * 
    	 */
        protected void onPostExecute(Bitmap result) {
            Log.i("Async-Example", "onPostExecute Called");
            downloadedImg.setImageBitmap(result);
            simpleWaitDialog.dismiss();

        }

        private Bitmap downloadBitmap(String url) {
            // initilize the default HTTP client object
            final DefaultHttpClient client = new DefaultHttpClient();

            //forming a HttoGet request
            final HttpGet getRequest = new HttpGet(url);
            try {

                HttpResponse response = client.execute(getRequest);

                //check 200 OK for success
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("ImageDownloader", "Error " + statusCode +
                            " while retrieving bitmap from " + url);
                    return null;

                }

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        // getting contents from the stream
                        inputStream = entity.getContent();

                        // decoding stream data back into image Bitmap that android understands
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        return bitmap;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                // You Could provide a more explicit error message for IOException
                getRequest.abort();
                Log.e("ImageDownloader", "Something went wrong while" +
                        " retrieving bitmap from " + url + e.toString());
            }

            return null;
        }
    }
}
