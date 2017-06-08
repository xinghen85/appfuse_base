package com.btxy.basis.morphia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelayPaperInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long paperId;

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
    List<PaperQuestionInfo> paperQuestionList=new ArrayList<PaperQuestionInfo>();

	public List<PaperQuestionInfo> getPaperQuestionList() {
		return paperQuestionList;
	}

	public void setPaperQuestionList(List<PaperQuestionInfo> paperQuestionList) {
		this.paperQuestionList = paperQuestionList;
	}
    
}
