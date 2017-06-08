package com.btxy.basis
.util.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListUtil {
	public static List pasreList(Object[] t){
		List list=new ArrayList();
		if(t!=null){
			for(Object obj:t){
				list.add(obj);
			}
		}
		return list;
	}
	public static List<Long> pasreLongList(String value,String split){
		List<Long> list=new ArrayList<Long>();
		if(value!=null){
			int start=value.trim().indexOf('[');
        	int end=value.trim().indexOf(']');
        	if(start+1==end){
        		value="";
        	}else if(start<end){
        		value=value.trim().substring(start+1,end);
        	}
        	
        	String[] va=value.split(",");
        	if(va!=null && va.length>0){
        		for(String va0:va){
    				if(va0!=null){
    					list.add(new Long(va0.trim()));
    				}
    			}
        	}
		}
		
		return list;
	}
	public static List<String> pasreStringList(String value,String split){
		List<String> list=new ArrayList<String>();
		if(value!=null){
			int start=value.trim().indexOf('[');
        	int end=value.trim().indexOf(']');
        	if(start+1==end){
        		value="";
        	}else if(start<end){
        		value=value.trim().substring(start+1,end);
        	}
        	
        	String[] va=value.split(",");
        	if(va!=null && va.length>0){
        		for(String va0:va){
    				if(va0!=null){
    					list.add(va0);
    				}
    			}
        	}
		}
		
		return list;
	}
	public static String toString(List list){
		return ListUtil.toString(list,",");
	}
	public static String toString(List list,String split){
		String str="";
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				if(i==0){
					str=(list.get(i)==null?"":list.get(i).toString());
				}else{
					str=str+split+(list.get(i)==null?"":list.get(i).toString());

				}
			}
		}
		return str;
	}
	public static void forEach(List list,CallBack cb){
		if(list !=null){
			for(int i=0;i<list.size();i++){
				cb.doCURD(list.get(i));
			}
		}
	}
	public static boolean containsKey(List list,Object obj) {
		// TODO Auto-generated method stub
		if(list!=null){
			for(Object one:list){
				if(one!=null && obj!=null && one.toString().equals(obj.toString())){
					return true;
				}
			}
		}
		return false;
	}
	
    public static String stripHtml(String content) {
		if(content==null){
			return content;
		}
		content = content.replaceAll("<p.*?>", "");
		content = content.replaceAll("<brs*/?>", "");
		content = content.replaceAll("<.*?>", "");
		content = content.replaceAll("&nbsp;", "");
		return content;
	}
    
    
   
}
