package alliancebackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "U_news")
public class NewsBulletin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bulletinId;

	private String bulletinHead;
	private String bulletinBody;
	private String bulletinDate;

	public int getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getBulletinHead() {
		return bulletinHead;
	}

	public void setBulletinHead(String bulletinHead) {
		this.bulletinHead = bulletinHead;
	}

	public String getBulletinBody() {
		return bulletinBody;
	}

	public void setBulletinBody(String bulletinBody) {
		this.bulletinBody = bulletinBody;
	}

	public String getBulletinDate() {
		return bulletinDate;
	}

	public void setBulletinDate(String bulletinDate) {
		this.bulletinDate = bulletinDate;
	}

}
