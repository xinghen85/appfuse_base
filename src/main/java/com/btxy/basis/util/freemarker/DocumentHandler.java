package com.btxy.basis.util.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocumentHandler {
	private Configuration configuration = null;
	public DocumentHandler() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");//设置默认编码方式
	}
/**
 * !注意dataMap里存放的数据Key值要与模板中的参数相对应,图片为String类型的Base64编码
 * @param dataMap 载入的数据文件  
 * @param templatePackagePath 模版文件包路径
 * @param templateName 模版文件名称
 * @param docPath  写出的doc文件路径
 * @throws IOException 
 * @throws TemplateException 
 */
	public void createDoc(Map<String, Object> dataMap,String templatePackagePath,String docPath) throws IOException, TemplateException {
		String path = templatePackagePath.substring(0, templatePackagePath.lastIndexOf("/"));
		String templateName = templatePackagePath.substring(templatePackagePath.lastIndexOf("/")+1);
		configuration.setDirectoryForTemplateLoading(new File(path));
		configuration.setObjectWrapper(new DefaultObjectWrapper()); 
		
		Template t = configuration.getTemplate(templateName);
		// 输出文档路径及名称
		File outFile = new File(docPath);
		File pfile = outFile.getParentFile();
		if(!pfile.exists()){
			pfile.mkdirs();
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		t.process(dataMap, out);
		out.close();
	}

	/**
	 * 注意dataMap里存放的数据Key值要与模板中的参数相对应
	 * 
	 * @param dataMap
	 */
	public void getData(Map<String, Object> dataMap) {
		dataMap.put("author", "张三");
		dataMap.put("remark", "这是测试备注信息");
		List<String[]> _table1 = new ArrayList<String[]>();

		String[] t1 = new String[2];
		t1[0] = "2011-07-1";
		t1[1] = "制定10月开发计划内容。";
		_table1.add(t1);

		String[] t2 = new String[2];
		t2[0] = "2011-07-2";
		t2[1] = "开会讨论开发计划";
		_table1.add(t2);

		dataMap.put("table1", _table1);

		List<String[]> _table2 = new ArrayList<String[]>();
		for (int i = 0; i <10; i++) {
			String[] _t2 = new String[6];
			_t2[0] = (i+1+"a");
			_t2[1] = ("a测试开发计划" + i);
			_t2[2] = ("a张三——" + i);
			_t2[3] = ("a2013-07-1");
			_t2[4] = ("a2011-07-31");
			_t2[5] = ("a备注信息");
			_table2.add(_t2);
			
		}
		dataMap.put("table2", _table2);
	}

	/**
	 * 注意dataMap里存放的数据Key值要与模板中的参数相对应
	 * 
	 * @param dataMap
	 */
	public void getData1(Map<String, Object> dataMap) {
		dataMap.put("schoolName", "延庆县十一学校初中部");
		dataMap.put("teacheName", "赵秀臣");
		dataMap.put("date", "2012年10月");
		List<String[]> table1 = new ArrayList<String[]>();
		
		String[] s1 = new String[8];
		s1[0] = "教师道德素养";
		s1[1] = "93.6";
		s1[2] = "88.7";
		s1[3] = "88.7";
		s1[4] = "88.7";
		s1[5] = "88.7";
		s1[6] = "88.7";
		s1[7] = "88.7";
		table1.add(s1);
		String[] s2 = new String[8];
		s2[0] = "教学专业素养";
		s2[1] = "94.8";
		s2[2] = "88.7";
		s2[3] = "88.7";
		s2[4] = "88.7";
		s2[5] = "88.7";
		s2[6] = "88.7";
		s2[7] = "88.7";
		table1.add(s2);
		String[] s3 = new String[8];
		s3[0] = "教育科研能力";
		s3[1] = "100.0";
		s3[2] = "88.7";
		s3[3] = "88.7";
		s3[4] = "88.7";
		s3[5] = "88.7";
		s3[6] = "88.7";
		s3[7] = "88.7";
		table1.add(s3);
		String[] s4 = new String[8];
		s4[0] = "教育管理能力";
		s4[1] = "100.0";
		s4[2] = "88.7";
		s4[3] = "88.7";
		s4[4] = "88.7";
		s4[5] = "88.7";
		s4[6] = "88.7";
		s4[7] = "88.7";
		table1.add(s4);
		dataMap.put("zipingdefen", table1);

		
		
		String img=null;
		InputStream in;
		byte[] picdata=null;
		try {
			in=new FileInputStream("C:\\Users\\Administrator\\Desktop\\新建文件夹\\b.png");
			picdata=new byte[in.available()];
			in.read(picdata);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder=new BASE64Encoder();
		img=encoder.encode(picdata);
		
		dataMap.put("zpzdfl", img);
	}

}
