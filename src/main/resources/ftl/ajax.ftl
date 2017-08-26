<#macro exe bean useCommon>
<#if useCommon>
	<@sbCommon bean=bean/>
<#else>
	<@sbSelect2 bean=bean/>
</#if>
</#macro>
<#macro sbSelect2 bean>
		<#if bean.cssClass !="" >
        	      <input id='${bean.id}' name='${bean.name}' type="text" value='${bean.value}' onChange='${bean.onSelectChange}' class='${bean.cssClass1}' style='${bean.cssStyle1}' />
        <#else>
        	      <input id='${bean.id}' name='${bean.name}' type="text" value='${bean.value}' onChange='${bean.onSelectChange}' style='${bean.cssStyle1}' />
        </#if>
        
	       
		<script type="text/javascript">
		$(document).ready(function() {
		$.ajax({ 
			type: "get", 
			url: '${bean.url}', 
			dataType: "json",
			success: function(msg){	
				$("#"+id}').select2({"+(mt?"multiple: true,":"")+"
			 		"+this.getPlaceholderCode()+this.getAllowClearCode()+"data:msg
				});		
		 	}	 
		 });	  
		});	  
		</script>
		
</#macro>
<#macro sbCommon bean>
		<#if bean.cssClass !="" >
        	       <select id='${bean.id}' name='${bean.name}' class='${bean.cssClass}' onChange='${bean.onSelectChange}' style='${bean.cssStyle1}'></select>
        <#else>
        	       <select id='${bean.id}' name='${bean.name}' onChange='${bean.onSelectChange}'  style='${bean.cssStyle1}'></select>
        </#if>
		<script type="text/javascript">
		$(document).ready(function() {
		<#if bean.value !="" >
				select2ForFormElement($('#${bean.id}'),'${bean.url}',${bean.pleaseSelect1},'${bean.value}');
		<#else>
				select2ForFormElement($('#${bean.id}'),'${bean.url}',${bean.pleaseSelect1});
		</#if>
		 });	        
		</script>
</#macro>

		