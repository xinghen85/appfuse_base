

package com.btxy.basis.dao.st;

import com.btxy.basis.model.AuthOrgUser;
import com.btxy.basis.dao.st.AuthOrgUserDao;
import org.springframework.stereotype.Repository;
import org.mongodb.morphia.dao.BasicDAO;
import com.btxy.basis.common.SpringContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Repository("authOrgUserDao")
public class AuthOrgUserDaoHibernate extends BasicDAO<AuthOrgUser, Long> implements AuthOrgUserDao {

    public AuthOrgUserDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(AuthOrgUser authOrgUser)  {
		AuthOrgUser authOrgUser2=super.get(authOrgUser.getUserId());
		if(authOrgUser!=null && authOrgUser2!=null){
			 
				
				
				
				
				
				
				
				
			authOrgUser.setLibraryRoleList(authOrgUser2.getLibraryRoleList());
				
				
				
				
				
				
				
			
		}	
		super.save(authOrgUser);
	        /*Field[] fields = AuthOrgUser.class.getDeclaredFields();
	        for(Field field : fields)
	        {
	            String name = field.getName();
	            
	            String firstLetter = name.substring(0,1).toUpperCase(); 
	            String getMethodName = "get" + firstLetter + name.substring(1);
	            String setMethodName = "set" + firstLetter + name.substring(1);            
	            //System.out.println(getMethodName + "," + setMethodName);
	            try{
		            Method getMethod = AuthOrgUser.class.getMethod(getMethodName, new Class[]{});
		            Method setMethod = AuthOrgUser.class.getMethod(setMethodName, new Class[]{field.getType()});
		            if(getMethod!=null && setMethod!=null){
			            Object value = getMethod.invoke(authOrgUser2, new Object[]{});
			            if(value instanceof java.util.ArrayList<?>){
			            	List tmp=(java.util.ArrayList<?>)value;
			            	if(tmp.size()>0){
			            		if(!(tmp.get(0) instanceof String || tmp.get(0) instanceof Boolean 
			            				|| tmp.get(0) instanceof Integer || tmp.get(0) instanceof Long 
			            				|| tmp.get(0) instanceof Float || tmp.get(0) instanceof Double  )){
						            setMethod.invoke(authOrgUser, new Object[]{value});
						            System.out.println("setMethod.invoke:"+name+"|"+value.getClass()+"|"+value);
			            		}
			            	}
			            }
			            
		            }
		            
		            
	            }catch(NoSuchMethodException e){
	            	//e.printStackTrace();
	            } catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	            
	        }*/
		
        
	}    
}
