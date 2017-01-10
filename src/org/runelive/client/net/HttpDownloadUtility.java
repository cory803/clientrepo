package org.runelive.client.net;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.runelive.client.GameWindow;

public class HttpDownloadUtility {

	private static final int BUFFER_SIZE = 4096;

	public static boolean downloadFile(String fileURL, String saveDir, boolean directory) {
		try {
			URL url = new URL(fileURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
			int responseCode = httpConn.getResponseCode();

			// always check HTTP response code first
			if (responseCode == HttpURLConnection.HTTP_OK) {
				String fileName = "";
				String disposition = httpConn.getHeaderField("Content-Disposition");

				if (disposition != null) {
					// extracts file name from header field
					int index = disposition.indexOf("filename=");
					if (index > 0) {
						fileName = disposition.substring(index + 10, disposition.length() - 1);
					}
				} else {
					// extracts file name from URL
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}

				if(!directory) {
					fileName = "RuneLive_"+fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				} else {
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}

				// opens input stream from the HTTP connection
				InputStream inputStream = httpConn.getInputStream();
				String saveFilePath;
				if(!directory ) {
					saveFilePath = saveDir + File.separator + fileName;
				} else {
					saveFilePath = saveDir;
				}
				FileOutputStream outputStream = new FileOutputStream(saveFilePath);
				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				outputStream.close();
				inputStream.close();

			} else {
				System.out.println("imgur.com replied HTTP code: " + responseCode + " for file: " + fileURL);
				return false;
			}
			httpConn.disconnect();
            GameWindow.text.setBackground(new Color(57,196,66));
            GameWindow.text.setText("File saved successfully.");
			return true;
		} catch (FileNotFoundException e) {
            GameWindow.text.setBackground(Color.RED);
            GameWindow.text.setText("Wrong file directory/name!");
			return false;
		} catch (Exception e) {
		    e.printStackTrace();
            return false;
        }
	}

	public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

		} else {
			System.out.println("ikov2.org replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}
}
