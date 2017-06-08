package com.btxy.basis.webapp.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.btxy.basis.cache.cfg.AuthPrivilegeInfoCache;
import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;
import com.btxy.basis.model.AuthPrivilegeInfo;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.model.CfgStateMachineValue;
import com.btxy.basis.util.freemarker.StringTemplate;


/**
 * <p>This class is designed to render a <label> tag for labeling your forms and
 * adds an asterik (*) for required fields.  It was originally written by Erik
 * Hatcher (http://www.ehatchersolutions.com/JavaDevWithAnt/).
 * <p/>
 * <p>It is designed to be used as follows:
 * <pre>&lt;tag:label key="userForm.username"/&gt;</pre>
 *
 * @jsp.tag name="label" bodycontent="empty"
 */
public class StateMachineTag extends TagSupport {
	private String formName;
	private String stateFieldElement;
	private String buttonGroupIFrameElement;
	private String stateValue;

	private String objectId;
	/*private String taskId;*/
	private String ctx;
	
	private String stateMachineCode;
	
	private String callBackUrl;
	
	
	private String otherObjectId1;
	private String otherObjectId2;
	private String otherObjectId3;
	
	
	public String getOtherObjectId1() {
		return otherObjectId1;
	}


	public void setOtherObjectId1(String otherObjectId1) {
		this.otherObjectId1 = otherObjectId1;
	}


	public String getOtherObjectId2() {
		return otherObjectId2;
	}


	public void setOtherObjectId2(String otherObjectId2) {
		this.otherObjectId2 = otherObjectId2;
	}


	public String getOtherObjectId3() {
		return otherObjectId3;
	}


	public void setOtherObjectId3(String otherObjectId3) {
		this.otherObjectId3 = otherObjectId3;
	}


	public String getCallBackUrl() {
		return callBackUrl;
	}


	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}


	


	public String getStateMachineCode() {
		return stateMachineCode;
	}


	public void setStateMachineCode(String stateMachineCode) {
		this.stateMachineCode = stateMachineCode;
	}


	public String getButtonGroupIFrameElement() {
		return buttonGroupIFrameElement;
	}


	public void setButtonGroupIFrameElement(String buttonGroupIFrameElement) {
		this.buttonGroupIFrameElement = buttonGroupIFrameElement;
	}


	public String getCtx() {
		return ctx;
	}


	public void setCtx(String ctx) {
		this.ctx = ctx;
	}


	public String getObjectId() {
		return objectId;
	}


	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}


	/*public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}*/


	


	public String getStateValue() {
		return stateValue;
	}


	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	


	public String getFormName() {
		return formName;
	}


	public void setFormName(String formName) {
		this.formName = formName;
	}


	public String getStateFieldElement() {
		return stateFieldElement;
	}


	public void setStateFieldElement(String stateFieldElement) {
		this.stateFieldElement = stateFieldElement;
	}

	private String libraryPath;
	
	
	public String getLibraryPath() {
		return libraryPath;
	}


	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}


	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		StringBuffer funsb = new StringBuffer();
		try {
			Map<String,String> map=new HashMap<String,String>();
			
			Long stateMachineId=null;
			CfgStateMachineDefine stateMachineDefine=CfgStateMachineDefineCache.getInstance().getCfgStateMachineDefineByCode(stateMachineCode);
			map.put("objectId", objectId);
			//map.put("taskId", taskId);
			map.put("formName", this.formName);
			
			map.put("otherObjectId1", this.otherObjectId1);
			map.put("otherObjectId2", this.otherObjectId2);
			map.put("otherObjectId3", this.otherObjectId3);
			
			
			if(stateMachineDefine!=null){
				stateMachineId=stateMachineDefine.getMachineId();
				map.put("machineId", stateMachineId.toString());
			}
			
			
			
			int random=(int) (Math.random()*1000000);
			
			funsb.append("<script type=\"text/javascript\">\r\n");
			sb.append("<input type='hidden' value='"+this.stateValue+"' id='stateIdElementFor"+this.objectId+"_"+random+"'/><div class='btn-group'>\r\n");
			List<AuthPrivilegeInfo> authlist=AuthPrivilegeInfoCache.getInstance().getButtonGroupByFormCode(formName);
			
			/*lyz
			 * 
			
			StTaskInfo unfinishedTask=StTaskInfoCache.getInstance().getUnFinishedTaskList(formName, new Long(objectId));
			if(unfinishedTask!=null){
				map.put("taskId", unfinishedTask.getTaskId().toString());
			}else{
				map.put("taskId", "0");
			}
			 */
			
			funsb.append("function stateMachineRefreshOfObject"+objectId+"_"+random+"_Init(time){\r\n");
			funsb.append("	if(time==null || time==\"\") time=1000;\r\n");
			funsb.append("	setTimeout('stateMachineRefreshOfObject"+objectId+"_"+random+"_Execute()',time); \r\n");
			funsb.append("	$(\"#"+stateFieldElement+"\").addClass('state_machine_text_loading'); \r\n");
			
			funsb.append("}\r\n");
			funsb.append("function stateMachineRefreshOfObject"+objectId+"_"+random+"_Execute(){\r\n");	
			funsb.append("	$(\"#"+stateFieldElement+"\").removeClass('state_machine_text_loading'); \r\n");
			funsb.append("	var nowStateId=$(\"#stateIdElementFor"+this.objectId+"_"+random+"\").val();\r\n");
			funsb.append("	if($(\"#stateIdElementFor"+this.objectId+"_"+random+"\").length > 0){\r\n");
			
			funsb.append("		$.ajax({ type: 'get', \r\n");
			funsb.append("			url: '"+ctx+"/lb/"+libraryPath+"/cfgStateMachineDefine/view/refreshButtonGroup/"+formName+"/"+stateMachineId+"/"+stateFieldElement+"/"+buttonGroupIFrameElement+"/'+nowStateId+'/"+objectId+"/"+otherObjectId1+"/"+otherObjectId2+"/"+otherObjectId3+"/"+123+"/php?random="+random+"',\r\n");
			funsb.append("			data: 'ctxValue="+this.ctx+"',\r\n");
			funsb.append("			dataType: 'json',\r\n");
			funsb.append("			error: function(XMLHttpRequest, textStatus, errorThrown) {\r\n");
			funsb.append("			    console.log(XMLHttpRequest.status);\r\n");
			funsb.append("			    console.log(XMLHttpRequest.readyState);\r\n");
			funsb.append("			    console.log(textStatus);\r\n");
			funsb.append("			}, \r\n");
			funsb.append("			success: function(msg){		\r\n");
			//funsb.append("			    var msg=eval('('+msgStr+')');  \r\n");
			funsb.append("			    console.log(\"success:\"+msg);\r\n");
			funsb.append("			    if(msg!=null && msg.newState!=null && msg.newState!=nowStateId){	\r\n");
			
			funsb.append("			    	var btnGroupHtml=\"<input type='hidden' value='\"+msg.newState+\"' id='stateIdElementFor"+this.objectId+"_"+random+"'/><div class='btn-group'>\";\r\n");
			funsb.append("			    	btnGroupHtml=btnGroupHtml+msg.btnGroupHtml;\r\n");
			//funsb.append("			    	$.each(msg.buttons,function(index, item){\r\n");
			//funsb.append("			    		btnGroupHtml=btnGroupHtml+\"<a class='btn btn-info  btn-sm' href='"+this.ctx+"/lb/"+this.libraryPath+"\"+item.url+\"' >\";\r\n");
			//funsb.append("			    		btnGroupHtml=btnGroupHtml+item.name;\r\n");
			//funsb.append("			    		btnGroupHtml=btnGroupHtml+\"</a>\";\r\n");
			//funsb.append("			    	});\r\n");
			
			funsb.append("			    	btnGroupHtml=btnGroupHtml+\"</div>\";\r\n");
			funsb.append("			    	console.log(\"btnGroupHtml:\"+btnGroupHtml);\r\n");
			funsb.append("			    	$(\"#"+stateFieldElement+"\").text(msg.newStateText);\r\n");
			
			funsb.append("			    	$(\"#"+buttonGroupIFrameElement+"\").empty();\r\n");
			funsb.append("			    	$(\"#"+buttonGroupIFrameElement+"\").html(btnGroupHtml);\r\n");
			funsb.append("			    	if(msg.ifRefresh==1){\r\n");
			funsb.append("			    		setTimeout('stateMachineRefreshOfObject"+this.objectId+"_"+random+"_Execute()',5000);\r\n");
			funsb.append("						$(\"#"+stateFieldElement+"\").addClass('state_machine_text_loading'); \r\n");
			funsb.append("			    	}\r\n");
			funsb.append("			    }else if(msg!=null && msg.ifRefresh==1){\r\n");
			funsb.append("			    	setTimeout('stateMachineRefreshOfObject"+this.objectId+"_"+random+"_Execute()',5000); \r\n");
			funsb.append("					$(\"#"+stateFieldElement+"\").addClass('state_machine_text_loading'); \r\n");
			funsb.append("			    }\r\n");
			funsb.append("			}\r\n");
			funsb.append("		});\r\n");
			funsb.append("	}\r\n");
			funsb.append("}\r\n");
			
			CfgStateMachineValue machineValue=CfgStateMachineValueCache.getInstance().getStateMachineValue(stateMachineId, this.stateValue);
			if(machineValue!=null && machineValue.getButtons()!=null){
				for(CfgStateMachineButton button:machineValue.getButtons()){
					
					map.put("buttonId", button.getButtonId().toString());
					
					String realUrl=button.getURL();
					
					if(realUrl!=null){
						realUrl=StringTemplate.executeSmall(realUrl, map);
					}
					
					
					
					sb.append("	<button class='btn btn-info  btn-sm' type='button' onclick='btnStateOperatorOf"+button.getButtonId()+"Of"+this.objectId+"()' id='btnStateOperatorOf"+button.getButtonId()+"Of"+this.objectId+"Element'  >\r\n");
					sb.append("		"+button.getButtonName()+"\r\n");
					sb.append(" </button>\r\n");
					
					
					funsb.append("function btnStateOperatorOf"+button.getButtonId()+"Of"+this.objectId+"(){\r\n");
					funsb.append("	$(\"#btnStateOperatorOf"+button.getButtonId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
					
					
					funsb.append("	ajaxProcessBtnStateChangeOperator($(\"#btnStateOperatorOf"+button.getButtonId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\",'post','"+callBackUrl+"');\r\n");
					funsb.append("}\r\n");
					
				}
			}
			if(authlist!=null ){
				for(AuthPrivilegeInfo cmb:authlist){
					String realUrl=cmb.getUrl();
					
					if(realUrl!=null){
						realUrl=StringTemplate.executeSmall(realUrl, map);
					}
					boolean showButton=false;
					if(stateMachineId!=null && stateMachineId!=0){
						if(cmb.getMachineId()!=null && cmb.getMachineId().longValue()==stateMachineId.longValue()){
							
							if(ifShowButton(this.stateValue,cmb.getEnumCodeList())){//状态机不为空时显示按钮（按钮所定义的显示状态 为当前状态）
								//例外情况：有未完成任务，且未完成任务类型和按钮任务类型相同
								/*
								if(!(unfinishedTask!=null && unfinishedTask.getTaskType().equals(cmb.getTaskType()))){
									showButton=true;
								}	
								 */							
							}
						
						}
						
					}else{
						if(cmb.getMachineId()==null || cmb.getMachineId().longValue()==0){
							showButton=true;
						}
					}
					/*
					if(unfinishedTask!=null && cmb.isIfHiddenOnTaskRunning()){
						showButton=false;
					}
					 */
					if(showButton){
						sb.append("	<button class='btn btn-info  btn-sm' type='button' onclick='btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"()' id='btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element'  >\r\n");
						sb.append("		"+cmb.getPrivilegeName()+"\r\n");
						sb.append(" </button>\r\n");
					}
					
					if("BQB".equals(cmb.getOperatorRequestType())){//WebPost请求
						
						funsb.append("function btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(){\r\n");
						funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
						funsb.append("	ajaxLoadHtmlInDiv($(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\",\"post\");\r\n");
						funsb.append("}\r\n");
					}else if("BQC".equals(cmb.getOperatorRequestType())){//AjaxGet请求								
						
						funsb.append("function callBackOfBtnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(rtnObj){\r\n");
						funsb.append(cmb.getJavaScriptFunction());
						funsb.append("}\r\n");
						
						funsb.append("function btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(){\r\n");
						funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
						
						if(cmb.getJavaScriptFunction()!=null && cmb.getJavaScriptFunction().trim()!=""){
							funsb.append("	ajaxProcessBtnGroupOperator($(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\",'get',stateMachineRefreshOfObject"+objectId+"_"+random+"_Init,callBackOfBtnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+");\r\n");
							funsb.append("}\r\n");
							
						}else{
							funsb.append("	ajaxProcessBtnGroupOperator($(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\",'get',stateMachineRefreshOfObject"+objectId+"_"+random+"_Init);\r\n");
							funsb.append("}\r\n");
						}
						
					}else if("BQD".equals(cmb.getOperatorRequestType())){//AjaxPost请求
						
						funsb.append("function callBackOfBtnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(rtnObj){\r\n");
						funsb.append(cmb.getJavaScriptFunction());
						funsb.append("}\r\n");
						
						funsb.append("function btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(){\r\n");
						funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
						
						if(cmb.getJavaScriptFunction()!=null && cmb.getJavaScriptFunction().trim()!=""){
							funsb.append("	ajaxProcessBtnGroupOperator($(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\",'post',stateMachineRefreshOfObject"+objectId+"_"+random+"_Init,callBackOfBtnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+");\r\n");
							funsb.append("}\r\n");
							
						}else{
							funsb.append("	ajaxProcessBtnGroupOperator($(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\",'post',stateMachineRefreshOfObject"+objectId+"_"+random+"_Init);\r\n");
							funsb.append("}\r\n");
						}
					}else if("BQE".equals(cmb.getOperatorRequestType())) {//JavaScript调用
						funsb.append("function btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(){\r\n");
						funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
						if(cmb.getJavaScriptFunction()!=null && cmb.getJavaScriptFunction().trim()!=""){
							funsb.append("	"+cmb.getJavaScriptFunction());
						}
						funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',false);\r\n");
						funsb.append("}\r\n");
					}else if("BQF".equals(cmb.getOperatorRequestType())) {//打开新Web请求
						funsb.append("function btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(){\r\n");
						//funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
						funsb.append("	window.open(\""+ctx+"/lb/"+libraryPath+realUrl+"\");\r\n");
						funsb.append("}\r\n");
					}else{//WebGet请求
						funsb.append("function btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"(){\r\n");
						funsb.append("	$(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\").attr('disabled',true);\r\n");
						funsb.append("	ajaxLoadHtmlInDiv($(\"#btnGroupOperatorOf"+cmb.getPrivilegeId()+"Of"+this.objectId+"Element\"),\""+ctx+"/lb/"+libraryPath+realUrl+"\");\r\n");
						funsb.append("}\r\n");
					}
					
				}
			}
			sb.append("</div>\r\n");
			funsb.append("$(document).ready(function() {\r\n");		
			if(stateMachineId!=null && stateMachineId!=0){
				
				/*lyz
				if(unfinishedTask!=null){
					funsb.append("	stateMachineRefreshOfObject"+objectId+"_"+random+"_Init(5000);\r\n");
					
				}
				 */
				
			
			}
			funsb.append("});\r\n");
			funsb.append("</script>\r\n");
			try {

				pageContext.getOut().write(sb.toString());
				pageContext.getOut().write(funsb.toString());

			} catch (IOException io) {
				throw new JspException(io);
			}

		} catch (Exception e) {
			e.printStackTrace();

			try {
				pageContext.getOut().write("");
			} catch (IOException io) {
				throw new JspException(io);
			}
		}

		return super.doStartTag();
	}
	private boolean ifShowButton(String state,List<String> allowStateList){
		if(allowStateList!=null){
			for(String str0:allowStateList){
				if(state!=null && state.endsWith(str0)){
					return true;
				}
			}
		}
		return false;
	}
}
