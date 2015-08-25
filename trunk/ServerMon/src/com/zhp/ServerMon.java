/**
 * 
 */
package com.zhp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * @author 张鹏
 * 
 */
public class ServerMon {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
//			HttpClient myclient = new DefaultHttpClient();
//			HttpGet httpget = new HttpGet("http://www.sohu.com");
//			HttpResponse response = myclient.execute(httpget);
		// System.out.println(response.getStatusLine());
		URI uri = new URI("http://www.baidu.com/index.html");
		HttpGet httpget = new HttpGet(uri);
		HttpClient myclient = new DefaultHttpClient();
		HttpResponse response = myclient.execute(httpget);
		System.out.println(response);

		// Get hold of the response entity
		HttpEntity entity = response.getEntity();

		// If the response does not enclose an entity, there is no need
		// to worry about connection release
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				// do something useful with the response
				System.out.println(reader.readLine());

			} catch (IOException ex) {

				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw ex;

			} catch (RuntimeException ex) {

				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				httpget.abort();
				throw ex;

			} finally {

				// Closing the input stream will trigger connection release
				instream.close();

			}

			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			myclient.getConnectionManager().shutdown();

		}
	}
}
