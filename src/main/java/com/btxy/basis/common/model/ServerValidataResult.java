package com.btxy.basis.common.model;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ServerValidataResult implements BindingResult{

	public String getAllErrorMessage(){
		return null;
	}
	@Override
	public void addAllErrors(Errors arg0) {
		// TODO Auto-generated method stub
		ObjectError obj=new ObjectError(null, null);
	}

	@Override
	public List<ObjectError> getAllErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FieldError getFieldError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldError getFieldError(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFieldErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFieldErrorCount(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FieldError> getFieldErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FieldError> getFieldErrors(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getFieldType(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectError getGlobalError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGlobalErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ObjectError> getGlobalErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNestedPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFieldErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFieldErrors(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasGlobalErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void popNestedPath() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushNestedPath(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reject(String arg0, Object[] arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectValue(String arg0, String arg1, Object[] arg2, String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNestedPath(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addError(ObjectError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropertyEditor findEditor(String arg0, Class<?> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyEditorRegistry getPropertyEditorRegistry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRawFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSuppressedFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recordSuppressedField(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] resolveMessageCodes(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] resolveMessageCodes(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
