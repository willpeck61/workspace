package flatfinder.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  private Integer userid;
  
  private Integer hits = 0;
  
  private String headline;
  
  private String about;
  
  private String interests;
  
  private String studying;
  
  private String workplace;
  
  private String wpostcode;
  
  private String studyplace;
  
  private String spostcode;
  
  private Integer studyyear;
  
  private Integer quietorlively;
  
  private Integer samesexormixed;
  
  private Integer smoking;
  
  private Integer pets;
  
  private Integer ensuite;
  
  private Integer maxsharers;
  
  private String facebookurl;
  
  private String twitterurl;
  
  private String googleplusurl;
  
  private String linkedinurl;
  
  private Integer firstEdit;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }

  public void setHits(){
	  this.hits += 1;
  }
  
  public Integer getHits(){
	  return hits;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public String getInterests() {
    return interests;
  }

  public void setInterests(String interests) {
    this.interests = interests;
  }

  public String getStudying() {
    return studying;
  }

  public void setStudying(String studying) {
    this.studying = studying;
  }

  public String getWorkplace() {
    return workplace;
  }

  public void setWorkplace(String workplace) {
    this.workplace = workplace;
  }

  public Integer getStudyyear() {
    return studyyear;
  }

  public void setStudyyear(Integer studyyear) {
    this.studyyear = studyyear;
  }

  public Integer getQuietorlively() {
    return quietorlively;
  }

  public void setQuietorlively(Integer quietorlively) {
    this.quietorlively = quietorlively;
  }

  public Integer getSamesexormixed() {
    return samesexormixed;
  }

  public void setSamesexormixed(Integer samesexormixed) {
    this.samesexormixed = samesexormixed;
  }

  public Integer getSmoking() {
    return smoking;
  }

  public void setSmoking(Integer smoking) {
    this.smoking = smoking;
  }

  public Integer getPets() {
    return pets;
  }

  public void setPets(Integer pets) {
    this.pets = pets;
  }

  public Integer getEnsuite() {
    return ensuite;
  }

  public void setEnsuite(Integer ensuite) {
    this.ensuite = ensuite;
  }

  public Integer getMaxsharers() {
    return maxsharers;
  }

  public void setMaxsharers(Integer maxsharers) {
    this.maxsharers = maxsharers;
  }

  public String getFacebookurl() {
    return facebookurl;
  }

  public void setFacebookurl(String facebookurl) {
    this.facebookurl = facebookurl;
  }

  public String getTwitterurl() {
    return twitterurl;
  }

  public void setTwitterurl(String twitterurl) {
    this.twitterurl = twitterurl;
  }

  public String getGoogleplusurl() {
    return googleplusurl;
  }

  public void setGoogleplusurl(String googleplusurl) {
    this.googleplusurl = googleplusurl;
  }

  public String getLinkedinurl() {
    return linkedinurl;
  }

  public void setLinkedinurl(String linkedinurl) {
    this.linkedinurl = linkedinurl;
  }

  public Integer getUserid() {
    return userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public Integer getFirstEdit() {
    return firstEdit;
  }

  public void setFirstEdit(Integer firstEdit) {
    this.firstEdit = firstEdit;
  }

  public String getWpostcode() {
    return wpostcode;
  }

  public void setWpostcode(String wpostcode) {
    this.wpostcode = wpostcode;
  }

  public String getStudyplace() {
    return studyplace;
  }

  public void setStudyplace(String studyplace) {
    this.studyplace = studyplace;
  }

  public String getSpostcode() {
    return spostcode;
  }

  public void setSpostcode(String spostcode) {
    this.spostcode = spostcode;
  }
  
}
