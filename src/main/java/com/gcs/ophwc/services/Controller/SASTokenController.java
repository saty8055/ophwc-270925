package com.gcs.ophwc.services.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.ophwc.services.util.TokenResponce;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions;
import com.microsoft.azure.storage.blob.SharedAccessBlobPolicy;

@RestController
@RequestMapping("/rest")

public class SASTokenController {

	private final static Logger LOG = Logger.getLogger(SASTokenController.class);

	// UAT
	// private static final String containerName = "uat";

	// Production
	 private static final String containerName = "production";

	@Autowired
	private SessionData sessionobj;

	@RequestMapping(value = "/generateSASToken", method = RequestMethod.GET)
	public TokenResponce generateSASToken() {
		TokenResponce res = null;
		try {
			if (sessionobj != null && sessionobj.getToken() != null) {
				Date currentDate = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
				Date expiryDate = dateFormat.parse(sessionobj.getToken().getExpiryTime());

				if (currentDate.before(expiryDate)) {
					res = sessionobj.getToken();
					//System.out.println("Time Not Expired..." + res);
				} else {
					res = new TokenResponce();
					// Date utc = getUTCTime();
					String token = getSASUri(new Date());
					res.setToken(token);
					res.setExpiryTime(getExpiryTime(token));
					//System.out.println("Time Expired..." + res);
				}
			} else {
				res = new TokenResponce();
				// Date utc = getUTCTime();
				String token = getSASUri(new Date());
				res.setToken(token);
				res.setExpiryTime(getExpiryTime(token));
				//System.out.println("In sessionobj==null...." + res);
			}
			sessionobj.setToken(res);

		} catch (Exception ex) {
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			ex.getMessage();
			LOG.error("Exception Message: " + errors.toString());
		}
		return res;
	}

	private String getSASUri(Date utc) throws Exception {

		CloudStorageAccount storageAccount = CloudStorageAccount.parse(
				"DefaultEndpointsProtocol=https;AccountName=ophwcimages;AccountKey=iAWdyJHZjU6di+MFzivwsqTkjiej/KNgQaWqZaEA8GkEZR55K5eYQA2sVJcQ4inQbaLOmK7X6Cg8e06wSrO1cQ==;EndpointSuffix=core.windows.net");

		// Create the blob client object.
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		// System.out.println("blobClient =" + blobClient);

		// Get a reference to the container for which shared access signature
		// will be created.
		CloudBlobContainer container = blobClient.getContainerReference(containerName);
		// System.out.println("container =" + container);

		container.createIfNotExists();

		// Get the current permissions for the blob container.
		BlobContainerPermissions blobPermissions = container.downloadPermissions();

		SharedAccessBlobPolicy sharedAccessBlobPolicy = new SharedAccessBlobPolicy();
		sharedAccessBlobPolicy
				.setPermissions(EnumSet.of(SharedAccessBlobPermissions.READ, SharedAccessBlobPermissions.WRITE));
		// sharedAccessBlobPolicy.setSharedAccessStartTime(utc);

		sharedAccessBlobPolicy.setSharedAccessExpiryTime(new Date(utc.getTime() + 86400000));
		// The public access setting explicitly specifies that
		// the container is private, so that it can't be accessed anonymously.
		blobPermissions.setPublicAccess(BlobContainerPublicAccessType.BLOB);

		// Set the permission policy on the container.
		container.uploadPermissions(blobPermissions);

		// Get the shared access signature token to share with users.
		String sasToken = container.generateSharedAccessSignature(sharedAccessBlobPolicy, null);
		System.out.println("sasToken=" + sasToken);
		return sasToken;
	}

	public static String getExpiryTime(String token) {
		String tokenLocal = "";
		if (token != null) {
			;
			String[] tokenArr = token.split("&");
			if (tokenArr != null) {
				for (String t : tokenArr) {
					if (t.indexOf("se") >= 0) {
						tokenLocal = t.substring(3).replaceAll("%3A", ":");
						break;
					}
				}
			}
		}
		return tokenLocal;
	}
}