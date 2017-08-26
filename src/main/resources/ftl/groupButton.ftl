 <#macro ddd bean>
	 <div >
     	<#if bean.type ==1>
     		
	        	<#list values as one>
			        		
			        <label class=' <@getSelectCode value=value setVal=one.fullCode rtn="active"/> '>
			        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='${one.fullCode}' <@getSelectCode value=value setVal=one.fullCod rtn="checked"/> name="${one.name}"> ${one.value}+"
			        </label>
				</#list>
	        	
	       <#elseif bean.type ==2>
	        		<#list FixedPropertyEnums as one>
		        <label class=' <@getSelectCode value=value setVal=one.Id rtn="active"/> '>
		        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='"+${one.Id}' <@getSelectCode value=value setVal=one.Id rtn="checked"/> name="${one.name}"> "+${one.Name}
		        </label>
				</#list>
	       <#elseif bean.type ==3>
	        		<#list FixedPropertyEnums as one>
		        		
		        		<#if "true"==useFullId>
				        	<label class=' <@getSelectCode value=value setVal=one.FullId rtn="active"/> '>
					        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='"+${one.Id}' <@getSelectCode value=value setVal=one.FullId rtn="checked"/> name="${one.name}"> "+${one.Name}
					        </label>
			        	<#else>
					        <label class=' <@getSelectCode value=value setVal=one.Id rtn="active"/> '>
					        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='"+${one.Id}' <@getSelectCode value=value setVal=one.Id rtn="checked"/> name="${one.name}"> "+${one.Name}
					        </label>
			        	</#if>
				</#list>
	       <#elseif bean.type ==4>
	        		<#list FixedPropertyEnums as one>
	        	
		        <label class=' <@getSelectCode value=value setVal=one.Id rtn="active"/> '>
		        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='"+${one.Id}' <@getSelectCode value=value setVal=one.Id rtn="checked"/> name="${one.name}"> "+${one.Name}
		        </label>
		        		
				</#list>
	       <#elseif bean.type ==6 || bean.type==9>
	        		<#list labelValues as one>
			        <label class=' <@getSelectCode value=value setVal=one.value rtn="active"/> '>
			        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='${one.value}' <@getSelectCode value=value setVal=one.value rtn="checked"/> name="${one.name}"> ${one.label}
			        </label>
			        		
				</#list>
	        	
	       <#elseif bean.type ==100>
	        	<#if !useSelect2 && "true" =bean.pleaseSelect>
     		       <option value="">请选择</option>
     			</#if>
        		<#list labelValues as labelValue>
			        <label class=' <@getSelectCode value=value setVal=one rtn="active"/> '>
			        <input type='<#if mt==null>checkbox<#else>radio</#if>' value='${one}' <@getSelectCode value=value setVal=one rtn="checked"/> name="${one.name}"> ${one}
			        </label>
				</#list>
	        }
     	</#if>
	</div>
			    
</#macro>
<#macro getSelectCode  value selVal rtn>
		 <#if value!=null && value==selVal>
			 ${rtn}
		 </#if>
</#macro>