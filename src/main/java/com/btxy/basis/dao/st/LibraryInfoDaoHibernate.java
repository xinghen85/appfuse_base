

package com.btxy.basis.dao.st;

import com.btxy.basis.model.LibraryInfo;
import com.btxy.basis.dao.st.LibraryInfoDao;
import org.springframework.stereotype.Repository;
import org.mongodb.morphia.dao.BasicDAO;
import com.btxy.basis.common.SpringContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Repository("libraryInfoDao")
public class LibraryInfoDaoHibernate extends BasicDAO<LibraryInfo, Long> implements LibraryInfoDao {

    public LibraryInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(LibraryInfo libraryInfo)  {
		LibraryInfo libraryInfo2=super.get(libraryInfo.getLibraryId());
		if(libraryInfo!=null && libraryInfo2!=null){
			 
				
				
				
				
			
		}	
		super.save(libraryInfo);
	        /*Field[] fields = LibraryInfo.class.getDeclaredFields();
	        for(Field field : fields)
	        {
	            String name = field.getName();
	            
	            String firstLetter = name.substring(0,1).toUpperCase(); 
	            String getMethodName = "get" + firstLetter + name.substring(1);
	            String setMethodName = "set" + firstLetter + name.substring(1);            
	            //System.out.println(getMethodName + "," + setMethodName);
	            try{
		            Method getMethod = LibraryInfo.class.getMethod(getMethodName, new Class[]{});
		            Method setMethod = LibraryInfo.class.getMethod(setMethodName, new Class[]{field.getType()});
		            if(getMethod!=null && setMethod!=null){
			            Object value = getMethod.invoke(libraryInfo2, new Object[]{});
			            if(value instanceof java.util.ArrayList<?>){
			            	List tmp=(java.util.ArrayList<?>)value;
			            	if(tmp.size()>0){
			            		if(!(tmp.get(0) instanceof String || tmp.get(0) instanceof Boolean 
			            				|| tmp.get(0) instanceof Integer || tmp.get(0) instanceof Long 
			            				|| tmp.get(0) instanceof Float || tmp.get(0) instanceof Double  )){
						            setMethod.invoke(libraryInfo, new Object[]{value});
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
