<#macro exe bean useCommon>
<#if useSelect2>
	<@sbSelect2 bean=bean/>
<#else>
	<@sbCommon bean=bean/>
</#if>
</#macro>
<@exe bean=bean useCommon=useCommon/>
<#macro sbSelect2 bean>
		<#if bean.cssClass !="" >
    	      <input id='${bean.id}' name='${bean.name}' type="text" value='${bean.value}' onChange='${bean.onSelectChange}' class='${bean.cssClass1}' style='${bean.cssStyle1}' />
        <#else>
    	      <input id='${bean.id}' name='${bean.name}' type="text" value='${bean.value}' onChange='${bean.onSelectChange}'                            style='${bean.cssStyle1}' />
        </#if>
        
	       
		<script type="text/javascript">
		$(document).ready(function() {
		$.ajax({ 
			type: "get", 
			url: '${bean.url}', 
			dataType: "json",
			success: function(msg){	
				$("#${bean.id}').select2({<#if mt>multiple:true</#if>
			 		${bean.placeholderCode}${bean.allowClearCode}data:msg
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
    	    <select id='${bean.id}' name='${bean.name}'                          onChange='${bean.onSelectChange}' style='${bean.cssStyle1}'></select>
        </#if>
		<script type="text/javascript">
		$(document).ready(function() {
			<#if bean.value !="" >
					select2ForFormElement($('#${bean.id}'),'${bean.url}',${bean.pleaseSelect1},'${bean.value}');
			<#else>
					select2ForFormElement($('#${bean.id}'),'${bean.url}',${bean.pleaseSelect1}                );
			</#if>
		 });	        
		</script>
</#macro>

		