package com.btxy.basis.common;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * ldap 工具类
 * @author liangzc
 *
 */
public class LdapHelper {
	//服务器IP
	private static String ip = "10.58.1.16";
	//root
	private static String root = "OU=letv,DC=letv,DC=local";
	//域
	private static String domain = "letv\\";
	private static DirContext ctx;
	/**
	 * 校验用户有效性
	 * @param account  账号
	 * @param password 密码
	 * @return
	 */
    public static boolean getCtx(String account,String password) {
    	boolean res = true;
        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://"+ip+"/" + root);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, domain+account);
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            ctx = new InitialDirContext(env);
            System.out.println("认证成功");
//            getUserDN("wangyingjing"+"@letv.com");
//            closeCtx();
        } catch (javax.naming.AuthenticationException e) {
        	System.out.println(e.getMessage());
            System.out.println("认证失败");
            res= false;
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("认证出错：");
            res= false;
        }
        return res;
    }
    
    public static String getUserDN(String email){
        String userDN = "";
        try{
               SearchControls constraints = new SearchControls();
               constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
               NamingEnumeration en = ctx.search("", "mail="+email+"@le.com", constraints); //The UID you are going to query,* means all nodes
               if(en == null){
                System.out.println("Have no NamingEnumeration.");
               }
               if(!en.hasMoreElements()){
                System.out.println("Have no element.");
               }
               while (en != null && en.hasMoreElements()){//maybe more than one element
                   Object obj = en.nextElement();
                   if(obj instanceof SearchResult){
                       SearchResult si = (SearchResult) obj;
                       userDN += si.getName();
                       userDN += ",";
                       System.out.println(userDN);
                   }
                   else{
                       System.out.println(obj);
                   }
                   System.out.println();
               }
              }catch(Exception e){
               System.out.println("Exception in search():"+e);
              }
       
        return userDN;
    }
    
    /**
     * 关闭连接
     */
    public static void closeCtx(){
        try {
            ctx.close();
        } catch (NamingException ex) {
            Logger.getLogger(LdapHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
    	String account = "liangzc"; 
        String password = "ABC1234567~";  
        System.out.println(getCtx(account,password));
    }
  
}