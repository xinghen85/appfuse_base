package com.btxy.basis.dao.st;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.StSystemNoticeInfo;

@Repository("stSystemNoticeInfoDao")
public class StSystemNoticeInfoDaoHibernate extends BasicDAO<StSystemNoticeInfo, Long> implements StSystemNoticeInfoDao {

    public StSystemNoticeInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(StSystemNoticeInfo stSystemNoticeInfo)  {
		StSystemNoticeInfo stSystemNoticeInfo2=super.get(stSystemNoticeInfo.getNoticeId());
		if(stSystemNoticeInfo!=null && stSystemNoticeInfo2!=null){
			 
				
				
				
				
				
				
				
				
				
				
			
		}	
		super.save(stSystemNoticeInfo);
	        /*Field[] fields = StSystemNoticeInfo.class.getDeclaredFields();
	        for(Field field : fields)
	        {
	            String name = field.getName();
	            
	            String firstLetter = name.substring(0,1).toUpperCase(); 
	            String getMethodName = "get" + firstLetter + name.substring(1);
	            String setMethodName = "set" + firstLetter + name.substring(1);            
	            //System.out.println(getMethodName + "," + setMethodName);
	            try{
		            Method getMethod = StSystemNoticeInfo.class.getMethod(getMethodName, new Class[]{});
		            Method setMethod = StSystemNoticeInfo.class.getMethod(setMethodName, new Class[]{field.getType()});
		            if(getMethod!=null && setMethod!=null){
			            Object value = getMethod.invoke(stSystemNoticeInfo2, new Object[]{});
			            if(value instanceof java.util.ArrayList<?>){
			            	List tmp=(java.util.ArrayList<?>)value;
			            	if(tmp.size()>0){
			            		if(!(tmp.get(0) instanceof String || tmp.get(0) instanceof Boolean 
			            				|| tmp.get(0) instanceof Integer || tmp.get(0) instanceof Long 
			            				|| tmp.get(0) instanceof Float || tmp.get(0) instanceof Double  )){
						            setMethod.invoke(stSystemNoticeInfo, new Object[]{value});
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
