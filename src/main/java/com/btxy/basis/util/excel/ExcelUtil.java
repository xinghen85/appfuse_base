/**
 * @Administrator
 */
package com.btxy.basis.util.excel;

/**
 * 
 * @author Administrator
 */
public class ExcelUtil {

	public static String getRowColunmString(int startRow,int startCol,String value){
		String str = "";
		str+="Range(\""+getExcelColumnLabel(startCol)+""+(startRow)+"\").Select\r\n";
		str+="ActiveCell.FormulaR1C1 = \""+value+"\"\r\n";
		return str;
	}
	
	public static String getStartEndPoint(int startRow,int startCol,int endRow,int endCol){
		String str = "";
		str+=getExcelColumnLabel(startCol)+""+(startRow);
		str+=":";
		str+=getExcelColumnLabel(endCol)+""+(endRow);
		return str;
	}
	
	public static String getExcelColumnLabel(int index) {
		String rs = "";
		do {
			index--;
			rs = ((char) (index % 26 + (int) 'A')) + rs;
			index = (int) ((index - index % 26) / 26);
		} while (index > 0);
		return rs;
	}
	
	
}
