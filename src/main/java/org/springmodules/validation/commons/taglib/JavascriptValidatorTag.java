/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springmodules.validation.commons.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.appfuse.anno.AnnoInfo;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springmodules.validation.commons.ValidatorFactory;

import com.btxy.basis.cache.LibraryInfoCache;
import com.btxy.basis.cache.cfg.CfgCustomPropertyCache;
import com.btxy.basis.cache.cfg.CfgFormInfoCache;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.util.list.ListUtil;

/**
 * Custom tag that generates JavaScript for client side validation based
 * on the validation rules loaded by a <code>ValidatorFactory</code>.
 * <p/>
 * <p>The validator resources needed for this tag are retrieved from a
 * ValidatorFactory bean defined in the web application context or one
 * of its parent contexts. The bean is resolved by type
 * (<code>org.springmodules.commons.validator.ValidatorFactory</code>).</p>
 *
 * @author David Winterfeldt.
 * @author Daniel Miller
 */
public class JavascriptValidatorTag extends BodyTagSupport {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3283128332362376285L;

	protected RequestContext requestContext;

    /**
     * The name of the form that corresponds with the action name
     * in struts-config.xml. Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; around the javascript.
     */
    protected String formName = null;

    /**
     * The line ending string.
     */
    protected static String lineEnd = System.getProperty("line.separator");

    /**
     * The current page number of a multi-part form.
     * Only valid when the formName attribute is set.
     */
    protected int page = 0;

    /**
     * This will be used as is for the JavaScript validation method name if it has a value.  This is
     * the method name of the main JavaScript method that the form calls to perform validations.
     */
    protected String methodName = null;

    /**
     * The static JavaScript methods will only be printed if this is set to "true".
     */
    protected String staticJavascript = "true";

    /**
     * The dynamic JavaScript objects will only be generated if this is set to "true".
     */
    protected String dynamicJavascript = "true";

    /**
     * The src attribute for html script element (used to include an external script
     * resource). The src attribute is only recognized
     * when the formName attribute is specified.
     */
    protected String src = null;

    /**
     * The JavaScript methods will enclosed with html comments if this is set to "true".
     */
    protected String htmlComment = "true";

    /**
     * The generated code should be XHTML compliant when "true". When true,
     * this setting prevents the htmlComment setting from having an effect.
     */
    protected String xhtml = "false";

    /**
     * Hide JavaScript methods in a CDATA section for XHTML when "true".
     */
    protected String cdata = "true";

    private String htmlBeginComment = "\n<!--  \n";

    private String htmlEndComment = "//End --> \n";

    /**
     * Gets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     */
    public String getFormName() {
        return formName;
    }

    /**
     * Sets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     * Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; tag around the javascript.
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }

    /**
     * Gets the current page number of a multi-part form.
     * Only field validations with a matching page numer
     * will be generated that match the current page number.
     * Only valid when the formName attribute is set.
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the current page number of a multi-part form.
     * Only field validations with a matching page numer
     * will be generated that match the current page number.
     * Only valid when the formName attribute is set.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the method name that will be used for the Javascript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public String getMethod() {
        return methodName;
    }

    /**
     * Sets the method name that will be used for the Javascript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public void setMethod(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets whether or not to generate the static
     * JavaScript.  If this is set to 'true', which
     * is the default, the static JavaScript will be generated.
     */
    public String getStaticJavascript() {
        return staticJavascript;
    }

    /**
     * Sets whether or not to generate the static
     * JavaScript.  If this is set to 'true', which
     * is the default, the static JavaScript will be generated.
     */
    public void setStaticJavascript(String staticJavascript) {
        this.staticJavascript = staticJavascript;
    }

    /**
     * Gets whether or not to generate the dynamic
     * JavaScript.  If this is set to 'true', which
     * is the default, the dynamic JavaScript will be generated.
     */
    public String getDynamicJavascript() {
        return dynamicJavascript;
    }

    /**
     * Sets whether or not to generate the dynamic
     * JavaScript.  If this is set to 'true', which
     * is the default, the dynamic JavaScript will be generated.
     */
    public void setDynamicJavascript(String dynamicJavascript) {
        this.dynamicJavascript = dynamicJavascript;
    }

    /**
     * Gets the src attribute's value when defining
     * the html script element.
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets the src attribute's value when defining
     * the html script element. The src attribute is only recognized
     * when the formName attribute is specified.
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Gets whether or not to delimit the
     * JavaScript with html comments.  If this is set to 'true', which
     * is the default, the htmlComment will be surround the JavaScript.
     */
    public String getHtmlComment() {
        return htmlComment;
    }

    /**
     * Sets whether or not to delimit the
     * JavaScript with html comments.  If this is set to 'true', which
     * is the default, the htmlComment will be surround the JavaScript.
     */
    public void setHtmlComment(String htmlComment) {
        this.htmlComment = htmlComment;
    }

    /**
     * Returns the cdata setting "true" or "false".
     *
     * @return String - "true" if JavaScript will be hidden in a CDATA section
     */
    public String getCdata() {
        return cdata;
    }

    /**
     * Sets the cdata status.
     *
     * @param cdata The cdata to set
     */
    public void setCdata(String cdata) {
        this.cdata = cdata;
    }

    /**
     * Gets whether or not to generate the xhtml code.
     * If this is set to 'true', which is the default,
     * XHTML will be generated.
     */
    public String getXhtml() {
        return xhtml;
    }

    /**
     * Sets whether or not to generate the xhtml code.
     * If this is set to 'true', which is the default,
     * XHTML will be generated.
     */
    public void setXhtml(String xhtml) {
        this.xhtml = xhtml;
    }

    /**
     * Render the JavaScript for to perform validations based on the form name.
     *
     * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
     */
    
    public String getNewValidateTag(Form form){
    	List<AnnoInfo> annos=CfgFormInfoCache.getInstance().getCfgFormInfoByFormCode(form.getName()).getFieldAnnoInfoList();
    	
    	List<String> rules=new ArrayList<String>();
    	
    	if(annos!=null && annos.size()>0){
	    	for(AnnoInfo one:annos){
	    		
	    		List<String> validates=new ArrayList<String>();
	    		if(one.isRequired() && !"boolean".equals(one.getField().getType().getName()) && !"java.lang.Boolean".equals(one.getField().getType().getName())){
	    			validates.add("required:true");
	    		}
	    		if(one.isDate()){
	    			validates.add("dateISO:true");
	    		}
	    		if(one.isDigits()){
	    			validates.add("digits:true");
	    		}
	    		if(one.isNumber()){
	    			validates.add("number:true");
	    		}
	    		if(one.isUrl()){
	    			validates.add("url:true");
	    		}
	    		if(one.isEmail()){
	    			validates.add("email:true");
	    		}
	    		if(one.getMaxlength()>0){
	    			validates.add("maxlength:"+one.getMaxlength()+"");
	    		}
	    		if(one.getMinlength()>0){
	    			validates.add("minlength:"+one.getMinlength()+"");
	    		}
	    		if(one.getMax()!=0 || one.getMin()!=0){
	    			validates.add("range:["+one.getMin()+","+one.getMax()+"]");
	    		}
	    		if(validates.size()>0){
	    			rules.add(""+one.getField().getName()+":{"+ListUtil.toString(validates, ",")+"}");
	    		}
	    	}
    	}
    	List<CfgCustomProperty> flist=CfgCustomPropertyCache.getInstance(LibraryInfoCache.getInstance().getLibraryIdByPath((String)this.pageContext.getAttribute("libraryPath"))).getCfgCustomPropertyList(formName 			);
        for(CfgCustomProperty ccp:flist){
        	
        	List<String> validates=new ArrayList<String>();
        	if("AVA".equals(ccp.getValueType())){
        		//requireds.add("long");
        	}else if("AVB".equals(ccp.getValueType())){
        		validates.add("digits:true");
        	}else if("AVC".equals(ccp.getValueType())){
        		validates.add("number:true");
        	}else if("AVD".equals(ccp.getValueType()) || "AVE".equals(ccp.getValueType())){
        		if(ccp.getMaxlength()>0){
        			validates.add("maxlength:"+ccp.getMaxlength());
	        	}
        	}else if("AVF".equals(ccp.getValueType())){
        		//requireds.add("double");
        	}else if("AVG".equals(ccp.getValueType())){
        		validates.add("dateISO:true");
        	}else if("AVH".equals(ccp.getValueType())){
        		validates.add("email:true");
        	}
        	if(ccp.isRequired()){
        		validates.add("required:true");
        	}
        	
        	if(validates.size()>0){
    			rules.add("customPropertyMap_"+ccp.getPropertyCode()+":{"+ListUtil.toString(validates, ",")+"}");
    		}
        }
    	StringBuffer sb=new StringBuffer();
    	sb.append("var validate_"+form.getName()+"Form=$(\"#"+form.getName()+"Form\").validate({\r\n");
    	sb.append("	rules:{\r\n");
    	sb.append("			"+ListUtil.toString(rules, ",			\r\n")+"\r\n");
    	sb.append("	},\r\n");
    	sb.append("	errorClass: \"help-inline\",\r\n");
    	sb.append("	errorElement: \"span\",\r\n");
    	sb.append("	highlight:function(element, errorClass, validClass) {\r\n");
    	sb.append("		$(element).parents('.form-group').removeClass('has-success').addClass('has-error');\r\n");
    	sb.append("	},\r\n");
    	sb.append("	unhighlight: function(element, errorClass, validClass) {\r\n");
    	sb.append("		$(element).parents('.form-group').removeClass('has-error').addClass('has-success');\r\n");
    	sb.append("	}\r\n");
    	sb.append("});\r\n");
    	return sb.toString();
    }
    
    public int doStartTag() throws JspException {
        StringBuffer results = new StringBuffer();

        Locale locale = RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest());

        ValidatorResources resources = getValidatorResources();

        Form form = resources.getForm(locale, formName);
        
        if (form != null) {
        	
            if ("true".equalsIgnoreCase(dynamicJavascript)) {

                
                results.append("<script type=\"text/javascript\">");
                
                
                results.append(getNewValidateTag(form));

                
                results.append("</script>");
                //results.append(getJavascriptBegin(methods));

                
            } else if ("true".equalsIgnoreCase(staticJavascript)) {
                results.append(this.getStartElement());
                if ("true".equalsIgnoreCase(htmlComment)) {
                    results.append(htmlBeginComment);
                }
            }
        }

        if ("true".equalsIgnoreCase(staticJavascript)) {
            results.append(getJavascriptStaticMethods(resources));
        }

        if (form != null
            && ("true".equalsIgnoreCase(dynamicJavascript)
            || "true".equalsIgnoreCase(staticJavascript))) {

            //results.append(getJavascriptEnd());
        }


        JspWriter writer = pageContext.getOut();
        try {
            writer.print(results.toString());
        }
        catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return (SKIP_BODY);

    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        //		bundle = Globals.MESSAGES_KEY;
        formName = null;
        page = 0;
        methodName = null;
        staticJavascript = "true";
        dynamicJavascript = "true";
        htmlComment = "true";
        cdata = "true";
        src = null;
    }

    /**
     * Returns the opening script element and some initial javascript.
     */
    protected String getJavascriptBegin(String methods) {
        StringBuffer sb = new StringBuffer();
        String name =
            formName.substring(0, 1).toUpperCase()
                + formName.substring(1, formName.length());

        sb.append(this.getStartElement());

        if (this.isXhtml() && "true".equalsIgnoreCase(this.cdata)) {
            sb.append("//<![CDATA[\r\n");
        }

        if (!this.isXhtml() && "true".equals(htmlComment)) {
            sb.append(htmlBeginComment);
        }
        sb.append("\n     var bCancel = false; \n\n");

        if (methodName == null || methodName.length() == 0) {
            sb.append("    function validate"
                + name
                + "(form) {                                                                   \n");
        } else {
            sb.append("    function "
                + methodName
                + "(form) {                                                                   \n");
        }
        sb.append("        if (bCancel) \n");
        sb.append("      return true; \n");
        sb.append("        else \n");

        // Always return true if there aren't any Javascript validation methods
        if (methods == null || methods.length() == 0) {
            sb.append("       return true; \n");
        } else {
            sb.append("       return " + methods + "; \n");
        }

        sb.append("   } \n\n");

        return sb.toString();
    }

    protected String getJavascriptStaticMethods(ValidatorResources resources) {
        StringBuffer sb = new StringBuffer();

        sb.append("\n\n");

        @SuppressWarnings("rawtypes")
		Iterator actions = resources.getValidatorActions().values().iterator();
        while (actions.hasNext()) {
            ValidatorAction va = (ValidatorAction) actions.next();
            if (va != null) {
                String javascript = va.getJavascript();
                if (javascript != null && javascript.length() > 0) {
                    sb.append(javascript + "\n");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Returns the closing script element.
     */
    protected String getJavascriptEnd() {
        StringBuffer sb = new StringBuffer();

        sb.append("\n");
        if (!this.isXhtml() && "true".equals(htmlComment)) {
            sb.append(htmlEndComment);
        }

        if (this.isXhtml() && "true".equalsIgnoreCase(this.cdata)) {
            sb.append("//]]>\r\n");
        }

        sb.append("</script>\n\n");

        return sb.toString();
    }

    /**
     * Constructs the beginning &lt;script&gt; element depending on xhtml status.
     */
    private String getStartElement() {
        StringBuffer start = new StringBuffer("<script type=\"text/javascript\"");

        // there is no language attribute in xhtml
        if (!this.isXhtml()) {
            start.append(" language=\"Javascript1.1\"");
        }

        if (this.src != null) {
            start.append(" src=\"" + src + "\"");
        }

        start.append("> \n");
        return start.toString();
    }

    /**
     * Returns true if this is an xhtml page.
     */
    private boolean isXhtml() {
        return "true".equalsIgnoreCase(xhtml);
    }


    /**
     * Get the validator resources from a ValidatorFactory defined in the
     * web application context or one of its parent contexts.
     * The bean is resolved by type (org.springmodules.commons.validator.ValidatorFactory).
     *
     * @return ValidatorResources from a ValidatorFactory
     */
    private ValidatorResources getValidatorResources() {
        WebApplicationContext ctx = (WebApplicationContext)
            pageContext.getRequest().getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (ctx == null) {
            // look in main application context (i.e. applicationContext.xml)
            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
        }
        ValidatorFactory factory = (ValidatorFactory)
            BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, ValidatorFactory.class, true, true);
        return factory.getValidatorResources();
    }

}
