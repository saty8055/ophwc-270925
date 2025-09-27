package com.gcs.ophwc.services.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.resources.request.SiteVisitRequest;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.service.SiteVisitService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.ProjectResponse;
import com.gcs.ophwc.services.util.Response;
import com.gcs.ophwc.services.util.SiteVisitResponse;
import com.gcs.ophwc.services.util.UploadBlobImg;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/siteVisit")
public class SiteVisitResource {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");
	
	private static Long number = 0L;
	public static byte[] imageBytes;

	@Autowired
	private SiteVisitService siteService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/setSiteLocation", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Projects setSiteLocation(@RequestBody SiteVisitRequest request) {
		applicationLog.debug("Came Into setSiteLocation()");
		return siteService.updateSiteLocation(request.getProjects().getId(), request.getLatitude(),
				request.getLangitude());

	}

	// Images Uploading into the Local Server
	/*
	 * @RequestMapping(value = "/setSiteCapturePhoto", headers =
	 * ("content-type=multipart/*"), method = RequestMethod.POST) public
	 * ProjectResponse setSiteCapturePhoto(@RequestParam("image") MultipartFile
	 * image,
	 * 
	 * @RequestParam("projectId") Long projId, @RequestParam("conStatus") String
	 * conStatus,
	 * 
	 * @RequestParam("createdUser") Long userId, @RequestParam("comments") String
	 * comments,
	 * 
	 * @RequestParam("divisionId") Long divisionId) {
	 * 
	 * System.out.println("image==" + image); System.out.println("projId==" + projId
	 * + ",constatus=" + conStatusId + ",createdUser=" + userId + ",comments=" +
	 * comments + ",divisionId=" + divisionId);
	 * 
	 * 
	 * SiteVisit visit = new SiteVisit(); Projects proj = new Projects(); User user
	 * = new User(); String imagePath = null; ProjectResponse resp = new
	 * ProjectResponse();
	 * 
	 * proj.setId(projId); user.setId(userId);
	 * 
	 * if (!image.isEmpty()) { try { byte[] bytes = image.getBytes();
	 * 
	 * // Creating the directory to store file File dir = new File("images"); if
	 * (!dir.exists()) dir.mkdirs();
	 * 
	 * System.out.println("Catalina-Home=" + System.getProperty("user.dir"));
	 * Properties props = System.getProperties(); // props.list(System.out);
	 * 
	 * //System.out.println("Directory Path Before Image Saved=" +
	 * dir.getAbsolutePath());
	 * 
	 * // Create the file on server File serverFile = new File(
	 * "/opt/wildfly/welcome-content/images" + File.separator +
	 * image.getOriginalFilename()); BufferedOutputStream stream = new
	 * BufferedOutputStream(new FileOutputStream(serverFile)); stream.write(bytes);
	 * stream.close();
	 * 
	 * //System.out.println("Directory Path After Image Saved=" +
	 * serverFile.getAbsolutePath()); imagePath = serverFile.getAbsolutePath();
	 * System.out.println("imagePath=="+imagePath); visit.setProjects(proj);
	 * visit.setImagePath(imagePath); visit.setComments(comments);
	 * visit.setUser(user); visit.setConstructionPercentage(conStatus);
	 * visit.setCreatedDate(new Date()); String path = siteService.save(visit);
	 * 
	 * if (path != null && path != "") { File file = new File(path); file.delete();
	 * }
	 * 
	 * List<Projects> projectList =
	 * projectService.getAllDivisionProjects(divisionId); if (projectList.size() !=
	 * 0) { resp.setStatusCode(Constants.SUCCESS);
	 * resp.setStatusMessage(Constants.SUCCESSMSG); resp.setPrjectList(projectList);
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return resp; } else {
	 * resp.setStatusCode(Constants.FAILURE);
	 * resp.setStatusMessage(Constants.FAILURESMSG); resp.setPrjectList(null);
	 * return resp; } }
	 */

	// Images Uploading into the Cloud (Azure)
	@RequestMapping(value = "/setSiteCapturePhoto", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ProjectResponse setSiteCapturePhoto(@RequestParam("image") MultipartFile image,
											   @RequestParam("projectId") Long projId, @RequestParam("conStatus") String conStatus,
											   @RequestParam("createdUser") Long userId, @RequestParam("comments") String comments,
											   @RequestParam("divisionId") Long divisionId, HttpServletRequest request) {
		
		applicationLog.debug("Came Into setSiteCapturePhoto()");
		
		SiteVisit visit = new SiteVisit();
		Projects proj = new Projects();
		User user = new User();
		String imagePath = null;
		ProjectResponse resp = new ProjectResponse();

		UploadBlobImg upload = new UploadBlobImg();

		proj.setId(projId);
		user.setId(userId);

		if (!image.isEmpty()) {
			try {
				byte[] bytes = image.getBytes();

				// Creating the directory to store file
				File dir = new File("images");
				if (!dir.exists())
					dir.mkdirs();

				String rootLocation = request.getServletContext().getRealPath("/")
						+ File.separator + "/opt/wildfly/welcome-content/images";

				if (!new File(rootLocation).exists())
					new File(rootLocation).mkdir();

				// Create the file on server
				File serverFile = new File(
						 rootLocation + File.separator + image.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
				imagePath = serverFile.getAbsolutePath();
				String cloudPathResp = upload.uploadToBlob(image.getOriginalFilename(), imagePath, image.getContentType());
				
				//String cloudPathResp = upload.uploadToBlob(image.getOriginalFilename(), image.getContentType(),(FileInputStream) image.getInputStream());
				
				visit.setProjects(proj);
				visit.setImagePath(cloudPathResp);
				visit.setComments(comments);
				visit.setUser(user);
				visit.setConstructionPercentage(conStatus);
				visit.setCreatedDate(new Date());
				String path = null;
				if (cloudPathResp != null)
					path = siteService.save(visit);

				if (path != null && path != "") {
					File file = new File(path);
					file.delete();
				}

				List<Projects> projectList = projectService.getAllDivisionProjects(divisionId);
				if (projectList.size() != 0) {
					resp.setStatusCode(Constants.SUCCESS);
					resp.setStatusMessage(Constants.SUCCESSMSG);
					resp.setPrjectList(projectList);
				}

			} catch (Exception e) {
				errorLog.error("Error In setSiteCapturePhoto()="+e.getMessage());
				e.printStackTrace();
			}
			return resp;
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
			return resp;
		}
	}

	@RequestMapping(value = "/getAllSiteVisits", produces = "application/json", method = RequestMethod.GET)
	public SiteVisitResponse getAllSiteVisits() {
		applicationLog.debug("Came Into getAllSiteVisits()");
		SiteVisitResponse resp = new SiteVisitResponse();
		List<SiteVisit> siteList = siteService.getSiteVisitList();
		if (siteList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setSiteList(siteList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setSiteList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/deleteImage", produces = "application/json", method = RequestMethod.DELETE)
	public Response deleteImageByPath(@RequestBody SiteVisitRequest req) {
		applicationLog.debug("Came Into deleteImageByPath()");
		Response resp = new Response();
		//String path = "/opt/wildfly/welcome-content" + req.getImagePath();
		String path = req.getImagePath();
		int val = siteService.deleteImageByPath(path);
		System.out.println("val=" + val + ",path=" + path);
		if (val != 0) {
			File file = new File(path);
			file.delete();
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
		}
		return resp;
	}
}
