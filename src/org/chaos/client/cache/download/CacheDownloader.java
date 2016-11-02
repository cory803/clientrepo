package org.chaos.client.cache.download;

import org.chaos.client.Client;
import org.chaos.client.Signlink;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.net.*;
import java.io.*;

/**
 * Enchanced cache downloader
 * Handles cache downloading & unzipping
 * @author Gabriel Hannason
 */
public class CacheDownloader {

	private Cache cache = Cache.NO_CACHE;

	/**
	 * Handles the different types of cache
	 */
	public enum Cache {

		PARTIAL_CACHE("partial_cache.zip", "https://dl.dropboxusercontent.com/u/344464529/Chaos/cache/partial_cache.zip"),
		NO_CACHE(null, null);

		Cache(String fileName, String link) {
			this.fileName = fileName;
			this.link = link;
		}

		private String fileName;
		private String link;

		/**
		 * Get the cache download url
		 * @return
		 */
		public String getLink() {
			return this.link;
		}

		/**
		 * Get the cache file name
		 * @return
		 */
		public String getFileName() {
			return this.fileName;
		}
	}

	/**
	 * Gets your current cache set
	 * @return
	 */
	public Cache getCache() {
		return this.cache;
	}


	public static String cacheVersion = "1";
	public static final File cacheVersionFile = new File(Signlink.getCacheDirectory() + "versions/cache_version");

	/**
	 * Starts the initial download process
	 * @param cache
	 */
	public static void process(Cache cache) {
		try {
			if(!cacheVersionFile.exists()) {
				download(cache.getFileName(), cache.getLink());
				unZip(cache.getFileName());
				if(!new File(Signlink.getCacheDirectory() +"versions").exists()) {
					new File(Signlink.getCacheDirectory() +"versions").mkdir();
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(cacheVersionFile));
				writer.write(String.valueOf(cacheVersion));
				writer.close();
				Signlink.startpriv(InetAddress.getLocalHost());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the cahce downloading text
	 * @param amount
	 * @param text
	 */
	private static void drawLoadingText(int amount, String text) {
		Client.loadingPercentage = amount;
		Client.loadingText = text;
	}

	/**
	 * Downloads the cache
	 * @param fileName
	 * @param downloadUrl
	 * @throws IOException
	 */
	public static void download(String fileName, String downloadUrl) throws IOException {
		URL url = new URL(downloadUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = Signlink.getCacheDirectory() + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			long startTime = System.currentTimeMillis();
			int downloaded = 0;
			long numWritten = 0;
			int length = httpConn.getContentLength();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				numWritten += bytesRead;
				downloaded += bytesRead;
				int percentage = (int)(((double)numWritten / (double)length) * 100D);
				int downloadSpeed = (int) ((downloaded / 1024) / (1 + ((System.currentTimeMillis() - startTime) / 1000)));
				drawLoadingText(percentage, (new StringBuilder()).append("Downloading "+fileName+": "+percentage+"% ").append("@ "+downloadSpeed+"Kb/s").toString());
			}

			outputStream.close();
			inputStream.close();

		} else {
			System.out.println("dropbox.com replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

	/**
	 * Starts the intiial file unzipping process
	 * @param fileName
	 */
	public static void unZip(String fileName) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(Signlink.getCacheDirectory() + fileName));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry e;
			while ((e = zin.getNextEntry()) != null) {
				if (e.isDirectory()) {
					(new File(Signlink.getCacheDirectory() + e.getName())).mkdir();
				} else {
					if (e.getName().equals(Signlink.getCacheDirectory() + fileName)) {
						unzip(zin, Signlink.getCacheDirectory() + fileName);
						break;
					}
					unzip(zin, Signlink.getCacheDirectory() + e.getName());
				}
			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actually unzips the file...
	 * @param zin
	 * @param s
	 * @throws IOException
	 */
	public static void unzip(ZipInputStream zin, String s) throws IOException {
		FileOutputStream out = new FileOutputStream(s);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = zin.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
	}
}
