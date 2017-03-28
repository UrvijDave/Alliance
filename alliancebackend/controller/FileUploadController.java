package alliancebackend.controller;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import alliancebackend.dao.FileUploadDao;
import alliancebackend.model.UploadFile;
import alliancebackend.model.User;

@RestController
public class FileUploadController {

	@Autowired
	private FileUploadDao fileUploadDao;

	@RequestMapping("/doup")
	public String fileup() {
		return "Hello";
	}

	/*-------------------- FILE UPLOAD --------------------*/

	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request, HttpSession session,
			@RequestParam CommonsMultipartFile fileUpload) throws Exception {
		User user = (User) session.getAttribute("user");
		if (user == null)
			throw new RuntimeException("Not logged in");

		System.out.println("USER is " + user.getUsername());
		if (fileUpload != null) {
			CommonsMultipartFile aFile = fileUpload;
			System.out.println("Saving file: " + aFile.getOriginalFilename());

			UploadFile getUploadFile = fileUploadDao.getFile(user.getUsername());
			if (getUploadFile == null) {
				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileName(aFile.getOriginalFilename());
				uploadFile.setData(aFile.getBytes());// image
				uploadFile.setUsername(user.getUsername());// login details
				System.out.println("trying to save file data.......................");
				fileUploadDao.save(uploadFile);
				System.out.println("saving file data over.......................");
			} else {
				getUploadFile.setFileName(aFile.getOriginalFilename());
				getUploadFile.setData(aFile.getBytes());// image
				getUploadFile.setUsername(user.getUsername());// login details
				System.out.println("trying to update file data.......................");
				fileUploadDao.update(getUploadFile.getId(), getUploadFile);
				System.out.println("saving file data over.......................");
			}
			getUploadFile = fileUploadDao.getFile(user.getUsername());
			String name = getUploadFile.getFileName();
			System.out.println(getUploadFile.getData());
			byte[] imagefiles = getUploadFile.getData();
			try {
				// String path =
				// "C:/Users/mruser/workspace/niravbackend/src/main/webapp/WEB-INF/resources/images/"+
				// user.getUsername()+".jpg";
				String path = "C:/Users/Admin/workspace/Collaboration/AllianceFrontEnd/WebContent/U_images/"
						+ user.getUsername() + ".jpg";
				System.out.println(path);
				File file = new File(path);
				if (file.exists()) {
					System.out.println("deleting file .......................");
					file.delete();
				} else {
					System.out.println("file does not exists.......................");
				}
				// file.mkdirs();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(imagefiles);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "Successfully uploaded the Profile Picture";
	}

	/*-------------------- GET FILE --------------------*/

	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public ResponseEntity<?> getFile(HttpSession session) {
		User user = (User) session.getAttribute("user");
		UploadFile uploadFile = fileUploadDao.getFile(user.getUsername());
		String name = uploadFile.getFileName();
		System.out.println(uploadFile.getData());
		byte[] imagefiles = uploadFile.getData();

		return new ResponseEntity<byte[]>(imagefiles, HttpStatus.OK);
	}
}
