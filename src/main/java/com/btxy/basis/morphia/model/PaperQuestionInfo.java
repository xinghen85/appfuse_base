package com.btxy.basis.morphia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity(value="dm_paper_question_info", noClassnameStored=true) 
public class PaperQuestionInfo implements Serializable{
	public static 	String QUESTION_TYPE_PDT	="1HA";//	判断题
	public static 	String QUESTION_TYPE_DANXT	="1HB";//	单选题
	public static 	String QUESTION_TYPE_DUOXT	="1HC";//	多选题
	public static 	String QUESTION_TYPE_LIKETE	="1HD";//	里克特题
	public static 	String QUESTION_TYPE_TKT	="1HE";//	填空题
	public static 	String QUESTION_TYPE_WDT	="1HF";//	问答题
	public static 	String QUESTION_TYPE_PXT	="1HG";//	排序题
	public static 	String QUESTION_TYPE_LXT	="1HK";//	连线题
	public static 	String QUESTION_TYPE_YDLJT	="1HL";//	阅读理解题
	public static 	String QUESTION_TYPE_HXT	="1HM";//	划消题
	public static 	String QUESTION_TYPE_WB	="1HN";//	文本
	public static 	String QUESTION_TYPE_XLXZT	="1HP";//	下拉选择题
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	private Long rawPaperQuestionId; 
	
	
	public Long getRawPaperQuestionId() {
		return rawPaperQuestionId;
	}
	public void setRawPaperQuestionId(Long rawPaperQuestionId) {
		this.rawPaperQuestionId = rawPaperQuestionId;
	}
	
	public Long getPaperQuestionId() {
		return paperQuestionId;
	}
	public void setPaperQuestionId(Long paperQuestionId) {
		this.paperQuestionId = paperQuestionId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Double getQuestionScore() {
		return questionScore;
	}
	public void setQuestionScore(Double questionScore) {
		this.questionScore = questionScore;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public Boolean getTakePartInScore() {
		return takePartInScore;
	}
	public void setTakePartInScore(Boolean takePartInScore) {
		this.takePartInScore = takePartInScore;
	}
	public Boolean getIsModifyDimensions() {
		return isModifyDimensions;
	}
	public void setIsModifyDimensions(Boolean isModifyDimensions) {
		this.isModifyDimensions = isModifyDimensions;
	}

	public Long getPaperId() {
		return paperId;
	}



	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}





	public List<SelectOptionInfo> getSelectOptionList() {
		return selectOptionList;
	}



	public void setSelectOptionList(List<SelectOptionInfo> selectOptionList) {
		this.selectOptionList = selectOptionList;
	}





	public Integer getRawQuestionRealIndex() {
		return rawQuestionRealIndex;
	}
	public void setRawQuestionRealIndex(Integer rowQuestionRealIndex) {
		this.rawQuestionRealIndex = rowQuestionRealIndex;
	}
	public Integer getQuestionIndex() {
		return questionIndex;
	}
	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}


	//Long paperId;
	Integer rawQuestionRealIndex;
	
	Integer questionIndex;
	
	@Id 
	Long paperQuestionId;
	Long questionId;
	Double questionScore;
	String contentText;
	String questionType;
	Boolean takePartInScore;
	Boolean isModifyDimensions;
	Long paperId;
	
	List<SelectOptionInfo> selectOptionList=new ArrayList<SelectOptionInfo>();
	
	public List<CatalogInfo> getCatalogInfoList() {
		return catalogInfoList;
	}



	public void setCatalogInfoList(List<CatalogInfo> catalogInfoList) {
		this.catalogInfoList = catalogInfoList;
	}




	List<CatalogInfo> catalogInfoList=new ArrayList<CatalogInfo>();
	
	/*added*/
	
	String associate;

	public String getAssociate() {
		return associate;
	}



	public void setAssociate(String associate) {
		this.associate = associate;
	}
	
	@Transient
	String identification;

	public String getIdentification() {
		return identification;
	}



	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	/*自定义方法*/
	

	public SelectOptionInfo getSelectOptionInfoBySelectOptionIndex(
			Integer integer) {
		// TODO Auto-generated method stub
		for(int i=0;i<this.selectOptionList.size();i++){
			if(this.selectOptionList.get(i).getSelectOptionIndex().intValue()==integer.intValue()){
				return this.selectOptionList.get(i);
			}
		}
		return null;
	}
}
