
<@exe bean=bean/>
 <#macro exe bean>
	 <div >
     	<#if bean.type =="1">
	        	<#list values as one>
			        <@label1 bean=bean value=one.fullCode label=one.value/>
				</#list>
	       <#elseif bean.type =="2" || bean.type =="4">
        		<#list FixedPropertyEnums as one>
			        <@label bean=bean value=one.id selVal=one.Id label=one.name/>
				</#list>
	       <#elseif bean.type =="3">
        		<#list FixedPropertyEnums as one>
	        		<#if "true"== bean.useFullId>
			        	<@label bean=bean value=one.id selVal=one.FullId label=one.name/>
		        	<#else>
			        	<@label bean=bean value=one.id selVal=one.Id label=one.name/>
		        	</#if>
				</#list>
	       <#elseif bean.type =="6" || bean.type=="9">
        		<#list labelValues as one>
			        <@label1 bean=bean value=one.value label=one.label/>
				</#list>
	       <#elseif bean.type =="100">
	        	<#if !useSelect2 && "true" == bean.pleaseSelect>
     		       <option value="">请选择</option>
     			</#if>
        		<#list labelValues as one>
			        <@label1 bean=bean value=one label=one/>
				</#list>
     		</#if>
	</div>
			    
</#macro>
<#macro getSelectCode  value selVal rtn>
		 <#if value?? && value == selVal>${rtn}</#if>
</#macro>

<#macro label bean value selVal label>
	<label class='<#if bean.value?? && bean.value == value>active</#if>'>
    <input type='<#if bean.multiple1>checkbox<#else>radio</#if>' value='${value}' <#if bean.value?? && bean.value == value>checked</#if> name="${bean.name}"> ${label}
    </label>
</#macro>

<#macro label1 bean value label>
    <label class='<#if bean.value?? && bean.value == value>active</#if>'>
    <input type='<#if bean.multiple1>checkbox<#else>radio</#if>' value='${value}' <#if bean.value?? && bean.value == value>checked</#if> name="${bean.name}"> ${label}
    </label>
</#macro>