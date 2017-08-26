<#macro exe bean useCommon>
<#if useCommon>
	<@sbCommon bean=bean/>
<#else>
	<@sbSelect2 bean=bean/>
</#if>
</#macro>


<#macro sbSelect2 bean>

		 <#if bean.cssClass != null>
	        	<#if mt ||bean.type ==1>
	        		      <input id='${bean.id}' name='${bean.name}' type="text" onChange='${bean.onSelectChange}' value='${bean.value}' class='${bean.getCssClass1()}' style='${bean.cssStyle}' />

	        	<#else>
	        		      <input id='${bean.id}' name='${bean.name}' type="text" onChange='${bean.onSelectChange}'  value='${bean.value}' class='${bean.getCssClass1()}' style='${bean.cssStyle}' />

	        	</#if>
	        <#else>
	        	      <input id='${bean.id}' name='${bean.name}' type="text" onChange='${bean.onSelectChange}'  value='${bean.value}' style='${bean.cssStyle}' />
	        </#if>
		       
	        <script type="text/javascript">
	        $(document).ready(function() {
	        $("#"+id}').select2({"+(mt?"multiple: true,":"")+"
	        	"+${this.PlaceholderCode}+${this.AllowClearCode} data:[
	        
	        <#if library!=null>
	        	<#if bean.type ==1>
	        		<#list valueList as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'"+value.FullCode()+"',text:'"+value.Value()+"'}			
						</#list>
		        	
		        <#elseif bean.type ==2>
	        		<#list FixedPropertyEnums as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.id}+"',text:'${one.name}'}			

			        </#list>
		        <#elseif bean.type ==3>
	        		<#list fixedPropertyEnums as fixedPropertyEnum>
			        		<#if one_index gt 0 >,</#if>
			        		<#if "true" == bean.useFullId>
			        			{id:'${one.FullId}',text:'${one.Name}'}			
			        		<#else>
			        			{id:'${one.Id}',text:'${one.Name}'}			
			        		</#if>

			        </#list>
		        <#elseif bean.type ==4>
	        		<#list fixedPropertyEnums as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.Id}',text:'${one.Name}'}			
			        		       <option value='${one.id}' <@getSelectCode value=value selVal=one.id/> > ${one.Name}</option>
		        	

			        </#list>
		        <#elseif bean.type ==9 || bean.type ==6>
		        	
	        		<#list labelValues as one>
			        		<#if one_index gt 0 >,</#if>
			        		{id:'${one.value}',text:'${one.label}'}			
			        

			        </#list>
		        	
		        <#elseif bean.type ==100>
		        	
	        		<#list ValueList as one>
			        		<#if one_index gt 0 >,</#if>
				        	{id:'${one}',text:'${one}'}			
			        </#list>
		        </#if>
	        </#if>
	        
	        		
	        	 ]		
	        });		
	         });
			    
	        </script>

</#macro>

<#macro sbCommon bean>

		 <#if bean.isCssClassIsNotNull>
	        	<#if mt ||bean.type ==1>
	        		       <select id='${bean.id}' name='${bean.name}' class='${bean.cssClass}' onChange='${bean.onSelectChange}' style='${bean.cssStyle}'>

	        	<#else>
	        		       <select id='${bean.id}' name='${bean.name}' onChange='${bean.onSelectChange}' class='${bean.getCssClass1()}' style='${bean.cssStyle}'>

	        	</#if>
	        <#else>
	        	       <select id='${bean.id}' name='${bean.name}' onChange='${bean.onSelectChange}' style='${bean.cssStyle}'>
	        </#if>
	        
		 	<#if !mt && "true" ==pleaseSelect>
				       <option></option>
			<#elseif !mt && placeholder!=null && !"" ==placeholder>
				       <option></option>
			</#if>
		 
	        <#if library!=null>
	        	<#if bean.type ==1>//form
	        		
	        		<#list valueList as one>
			        		<option value='${value.fullCode}' <@getSelectCode value=value selVal=one.fullCode/> >${one.value}</option>
			        </#list>
		        	
		       <#elseif bean.type ==2>
	        		<#list FixedPropertyEnums as one>
			        		<option value='${fixedPropertyEnum.id}' <@getSelectCode value=value selVal=one.id/> >${one.Name} </option>
			        </#list>
		        <#elseif bean.type ==3>
	        		<#list FixedPropertyEnums as one>
			        		<#if one_index gt 0 >,</#if>
				        		
			        			<#if  "true" == useFullId>
			        				       <option value='${bean.fullId}' <@getSelectCode value=value selVal=one.fullId/> > ${one.Name}</option>
			        			<#else>
			        				       <option value='${bean.id}' <@getSelectCode value=value selVal=one.id/> > ${fixedPropertyEnum.Name}</option>
				        		</#if>
			        </#list>
		        <#elseif bean.type ==4>
	        		<#list FixedPropertyEnums as fixedPropertyEnum>
		        		
			        		       <option value='${bean.list1.get(ii).getId()}' <@getSelectCode value=value selVal=one.id /> > ${fixedPropertyEnum.Name}</option>
			        </#list>
		        <#elseif bean.type ==9 || bean.type ==6>
		        	
	        		<#list labelValues as labelValue>
			        		       <option value='${bean.list.get(ii).getValue()}' <@getSelectCode value=value setVal=labelValue.value/> >${labelValue.label}</option>
			        </#list>
		        	
		        <#elseif bean.type ==100>
	        		<#list bean.cp.valueList as one>
				        <option value='${one}' <@getSelectCode value=value setVal=one/> > ${one} </option>
			        
			        </#list>
				</#if>
			</#if>
	               </select>
	        
	        <#if !mt && bean.type !=1>
	        	<script type="text/javascript">
	        	$(document).ready(function() {
	        	$('#${id}').select2({${bean.placeholderCode} ${bean.allowClearCode} });		
	        	 });
				    
	        	</script>
			</#if>
</#macro>

<#macro getSelectCode  value selVal >
		 <#if value!=null && value==selVal>
			 selected ='true';
		 </#if>
</#macro>