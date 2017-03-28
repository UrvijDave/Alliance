package alliancebackend.dao;

import alliancebackend.model.UploadFile;

public interface FileUploadDao {

	public void save(UploadFile uploadFile);

	public UploadFile getFile(String username);

	public UploadFile update(int fileuploadid, UploadFile uploadfile);

	
}
