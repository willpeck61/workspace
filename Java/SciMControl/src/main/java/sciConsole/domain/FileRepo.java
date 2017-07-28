package sciConsole.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="filerepo")
public class FileRepo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "filerepo_id")
	private Integer id;
	private String fileName;
	private String fileType;
	private String folderName;
	private String fileIndex;
	
	public Integer getId() {
        return id;
    }
	public void setId(Integer id) {
        this.id = id;
    }
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public String getFileName(){
		return fileName;
	}
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	public String getFileType(){
		return fileType;
	}
	public void setFolderName(String folderName){
		this.folderName = folderName;
	}
	public String getFolderName(){
		return folderName;
	}
	public void setFileIndex(String fileIndex){
		this.fileIndex = fileIndex;
	}
	public String getFileIndex(){
		return fileIndex;
	}
}

