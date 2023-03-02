package com.bit.model.bbs;

public class BbsVo {
	private int bbsID, bbsAvailable;
	private String bbsTitle, userID, bbsContent, bbsDate;
	
	public BbsVo() {
	}
	
	public BbsVo(int bbsID, int bbsAvailable, String bbsTitle, String userID, String bbsContent, String bbsDate) {
		super();
		this.bbsID = bbsID;
		this.bbsAvailable = bbsAvailable;
		this.bbsTitle = bbsTitle;
		this.userID = userID;
		this.bbsContent = bbsContent;
		this.bbsDate = bbsDate;
	}

	public int getBbsID() {
		return bbsID;
	}
	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
	public int getBbsAvailable() {
		return bbsAvailable;
	}
	public void setBbsAvailable(int bbsAvailable) {
		this.bbsAvailable = bbsAvailable;
	}
	public String getBbsTitle() {
		return bbsTitle;
	}
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getBbsContent() {
		return bbsContent;
	}
	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}
	public String getBbsDate() {
		return bbsDate;
	}
	public void setBbsDate(String bbsDate) {
		this.bbsDate = bbsDate;
	}
	
	@Override
	public String toString() {
		return "{\"bbsID\":" + bbsID + ", \"bbsAvailable\":" + bbsAvailable + ", \"bbsTitle\":\"" + bbsTitle + "\", \"userID\":\""
				+ userID + "\", \"bbsContent\":\"" + bbsContent + "\",\"bbsDate\":\"" + bbsDate + "\"}";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bbsAvailable;
		result = prime * result + ((bbsContent == null) ? 0 : bbsContent.hashCode());
		result = prime * result + ((bbsDate == null) ? 0 : bbsDate.hashCode());
		result = prime * result + bbsID;
		result = prime * result + ((bbsTitle == null) ? 0 : bbsTitle.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BbsVo other = (BbsVo) obj;
		if (bbsAvailable != other.bbsAvailable)
			return false;
		if (bbsContent == null) {
			if (other.bbsContent != null)
				return false;
		} else if (!bbsContent.equals(other.bbsContent))
			return false;
		if (bbsDate == null) {
			if (other.bbsDate != null)
				return false;
		} else if (!bbsDate.equals(other.bbsDate))
			return false;
		if (bbsID != other.bbsID)
			return false;
		if (bbsTitle == null) {
			if (other.bbsTitle != null)
				return false;
		} else if (!bbsTitle.equals(other.bbsTitle))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	
	
	
}
