package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.mongodb.DBObject;

@Entity(value="cfg_state_machine_value", noClassnameStored=true) 
@TableAnnoExtend(description="状态项",textSearch=false,combinedSearch=true,parent="" ,foreignParent="CfgStateMachineDefine")
@javax.persistence.Entity
@javax.persistence.Table(name="CfgStateMachineValue",catalog="bsquiz")
public class CfgStateMachineValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="状态项ID")
	private Long statId;
	
	@FieldAnnoExtend(description="状态机")
	private Long machineId;
	
	
	@FieldAnnoExtend(description="状态项名称",required=true,textSearch=true,name=true)
	String statName;

	

	
	
	@FieldAnnoExtend(type=1,enumCode="BE",description="源状态",pleaseSelect=true)
	private String sourceStat;
	
	
	
	
	@FieldAnnoExtend(type=5,childModel="CfgStateMachineButton")
	List<CfgStateMachineButton> buttons=new ArrayList<CfgStateMachineButton>();

	@FieldAnnoExtend(description="是否刷新状态",required=true)
	boolean ifRefresh;

	
	
	
	public boolean isIfRefresh() {
		return ifRefresh;
	}


	public void setIfRefresh(boolean ifRefresh) {
		this.ifRefresh = ifRefresh;
	}


	public Long getStatId() {
		return statId;
	}


	public void setStatId(Long statId) {
		this.statId = statId;
	}


	public Long getMachineId() {
		return machineId;
	}


	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}


	public String getStatName() {
		return statName;
	}


	public void setStatName(String statName) {
		this.statName = statName;
	}


	



	public String getSourceStat() {
		return sourceStat;
	}


	public void setSourceStat(String sourceStat) {
		this.sourceStat = sourceStat;
	}



	public List<CfgStateMachineButton> getButtons() {
		return buttons;
	}


	public void setButtons(List<CfgStateMachineButton> buttons) {
		this.buttons = buttons;
	}
	
	
}
