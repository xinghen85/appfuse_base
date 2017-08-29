<#macro exe bean>
<#if bean.multiple1>
	<@sbSelect2 bean=bean/>
<#else>
	<@sbCommon bean=bean/>
</#if>
</#macro>
<@exe bean=bean/>

<#macro sbSelect2 bean>

		 <#if bean.cssClass != "">
        	<input id="${bean.id}" name="${bean.name}" type="text" onChange="${bean.onSelectChange}" value="${bean.value}"                           style="${bean.cssStyle}" />
        <#else>
        	<input id="${bean.id}" name="${bean.name}" type="text" onChange="${bean.onSelectChange}" value="${bean.value}" class="${bean.cssClass1}" style="${bean.cssStyle}" />
        </#if>
		       
	        <script type="text/javascript">
	        $(document).ready(function() {
	        $("#${bean.id}").select2({<#if bean.multiple1>multiple:true,</#if>
	        	${bean.placeholderCode}${bean.allowClearCode} data:[
	        	<#if bean.type =="1">
	        		<#list valueList as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.fullCode}',text:'${one.value}'}			
						</#list>
		        <#elseif bean.type =="2">
	        		<#list fixedPropertyEnums as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.id}',text:'${one.name}'}	
			        </#list>
		        <#elseif bean.type =="3">
	        		<#list fixedPropertyEnums as one>
			        		<#if one_index gt 0 >,</#if>
			        		<#if "true" == bean.useFullId>
			        			{id:'${one.fullId}',text:'${one.name}'}			
			        		<#else>
			        			{id:'${one.id}',text:'${one.name}'}			
			        		</#if>
			        </#list>
		        <#elseif bean.type =="4">
	        		<#list fixedPropertyEnums as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.Id}',text:'${one.Name}'}			
			        </#list>
		        <#elseif bean.type =="9" || bean.type =="6">
	        		<#list labelValues as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.value}',text:'${one.label}'}
			        </#list>
		        <#elseif bean.type =="100">
	        		<#list valueList as one>
			        		<#if one_index gt 0 >,</#if>
				        	{id:'${one}',text:'${one}'}			
			        </#list>
		        </#if>
	        		
	        	 ]		
	        });		
	         });
			    
	        </script>

</#macro>

<#macro sbCommon bean>
		 <#if bean.cssClass??>
	        	<#if bean.multiple1 ||bean.type =="1">
        			<select id="${bean.id}" name="${bean.name}" onChange="${bean.onSelectChange}" class="${bean.cssClass}" style="${bean.cssStyle}">
	        	<#else>
    		    	<select id="${bean.id}" name="${bean.name}" onChange="${bean.onSelectChange}" class="${bean.cssClass1}" style="${bean.cssStyle}">
	        	</#if>
        <#else>
        	    <select id="${bean.id}" name="${bean.name}" onChange="${bean.onSelectChange}" style="${bean.cssStyle}">
        </#if>
	 	<#if !bean.multiple1>
	 		<#if "true" == bean.pleaseSelect>
		       <option></option>
			<#elseif "" != bean.placeholder>
		       <option></option>
			</#if>
	 	</#if> 
        	<#if bean.type =="1">
        		<#list valueList as one>
        			<@option value=one.fullCode label=one.value selVal=bean.value/>
		        </#list>
	       <#elseif bean.type =="2" || bean.type =="4">
        		<#list fixedPropertyEnums as one>
        			<@option value=(one.id+"") label=one.name selVal=bean.value/>
		        </#list>
	        <#elseif bean.type =="3">
        		<#list fixedPropertyEnums as one>
        			<#if "true" == bean.useFullId>
        				<@option value=one.fullId label=one.name selVal=bean.value/>
        			<#else>
        				<@option value=(one.id+"") label=one.name selVal=bean.value/>
        			</#if>
		        </#list>
	        <#elseif bean.type =="9" || bean.type =="6">
        		<#list labelValues as one>
        			<@option value=one.value label=one.label selVal=bean.value/>
		        </#list>
	        <#elseif bean.type =="100">
        		<#list valueList as one>
        			<@option value=one label=one selVal=bean.value/>
		        </#list>
			</#if>
               </select>
        <#if !bean.multiple1 && bean.type !="1">
        	<script type="text/javascript">
        	$(document).ready(function() {
        		$("#${bean.id}").select2({${bean.placeholderCode}${bean.allowClearCode} });		
        	 });
			    
        	</script>
		</#if>
</#macro>

<#macro option value label selVal>
	<option value="${value}" <#if selVal?? && selVal == value>selected ="true"</#if> >${label}</option>
</#macro>