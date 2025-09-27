package com.gcs.ophwc.services.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UploadBlobImg {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	/*
	 * //Image Storage(Azure) of Fankick public static String URL =
	 * "https://fankickuat.blob.core.windows.net/images/"; public static String
	 * tokenUrl = "https://qa.fankick.io/rest/generateSASToken";
	 */


	// Image Storage(Azure) of Client(OPHWC) UAT
	// public static String URL = "https://ophwcimages.blob.core.windows.net/uat/";

	// Image Storage(Azure) of Client(OPHWC) Production
	   public static String URL = "https://ophwcimages.blob.core.windows.net/production/";


	// Dev and UAT Token Generation
	// public static String tokenUrl = "http://183.82.43.237:9090/ophwc/rest/generateSASToken";


	// Production Token Generation with IP and Port
	//   public static String tokenUrl = "http://164.100.141.155:8080/ophwc/rest/generateSASToken";
	   
	// Production Token Generation with domain
	   public static String tokenUrl = "http://ophwc.org/ophwc/rest/generateSASToken";
	   

	public String uploadToBlob(String fileName, String filePath, String fileType) throws Exception {
		applicationLog.debug("Came Into uploadToBlob");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<TokenResponce> response = restTemplate.getForEntity(tokenUrl, TokenResponce.class);

		if (filePath.endsWith(".jpg") || filePath.endsWith(".JPG") || filePath.endsWith(".jpeg")
				|| filePath.endsWith(".JPEG")) {
			fileType = "image/jpeg";
		} else if (filePath.endsWith(".png") || filePath.endsWith(".PNG")) {
			fileType = "image/png";
		} else if (filePath.endsWith(".gif") || filePath.endsWith(".GIF")) {
			fileType = "image/gif";
		} else if (filePath.endsWith(".mp3") || filePath.endsWith(".MP3") || filePath.endsWith(".m4a")
				|| filePath.endsWith(".m4a")) {
			fileType = "audio/m4a";
		} else if (filePath.endsWith(".mp4") || filePath.endsWith(".MP4")) {
			fileType = "video/mp4";
		}

		URL url = new URL(URL + fileName + "?" + response.getBody().getToken());
		HttpsURLConnection conn = getHttpURLConnection(url, "PUT", fileType);
		conn.setDoOutput(true);

		// Index definition
		OutputStream osw = conn.getOutputStream();

		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		byte[] buf = new byte[1024];
		int numRead;
		while ((numRead = fis.read(buf)) >= 0) {
			osw.write(buf, 0, numRead);
		}
		applicationLog.debug("Response In uploadToBlob=" + conn.getResponseCode());
		String imagePath = URL + fileName;
		return imagePath;
	}

	public static HttpsURLConnection getHttpURLConnection(URL url, String method, String fileType) throws IOException {
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type", fileType);// "image/jpeg"
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");

		return connection;
	}

}
