package com.btxy.basis.util.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Poi2007Util {
	public static void setCellValue(XSSFSheet sheet,int row,int col,Object value){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow==null){
			aRow = sheet.createRow(row);
		}
		XSSFCell aCell=aRow.getCell(col);
		if(aCell==null){
			aCell=aRow.createCell(col);
		}
		aCell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
		aCell.setCellValue(value==null?"":value.toString());//写入内容  
	}
	public static void setCellValue(XSSFRow aRow,int col,Object value){
		
		XSSFCell aCell=aRow.getCell(col);
		if(aCell==null){
			aCell=aRow.createCell(col);
		}
		aCell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
		aCell.setCellValue(value==null?"":value.toString());//写入内容  
	}
	public static void setCellValue(XSSFSheet sheet,int row,int col,String value){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow==null){
			aRow = sheet.createRow(row);
		}
		XSSFCell aCell=aRow.getCell(col);
		if(aCell==null){
			aCell=aRow.createCell(col);
		}
		aCell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
		aCell.setCellValue(value);//写入内容  
	}
	public static void setCellValue(XSSFRow aRow,int col,String value){
		
		XSSFCell aCell=aRow.getCell(col);
		if(aCell==null){
			aCell=aRow.createCell(col);
		}
		aCell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
		aCell.setCellValue(value);//写入内容  
	}
	public static void setCellValue(XSSFSheet sheet,int row,int col,Long value){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow==null){
			aRow = sheet.createRow(row);
		}
		XSSFCell aCell=aRow.getCell(col);
		if(aCell==null){
			aCell=aRow.createCell(col);
		}
		aCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);//文本格式  
		aCell.setCellValue(value);//写入内容  
	}
	public static void setCellValue(XSSFRow aRow,int col,Long value){
		
		XSSFCell aCell=aRow.getCell(col);
		if(aCell==null){
			aCell=aRow.createCell(col);
		}
		aCell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);//文本格式  
		aCell.setCellValue(value);//写入内容  
	}
	public static XSSFWorkbook  getWorkBook(String excelFullName){
		XSSFWorkbook  workbook;
		FileInputStream in;
		try {
			in =new FileInputStream(excelFullName);

			//POIFSFileSystem fs = new POIFSFileSystem(in);
			workbook = new XSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件["+excelFullName+"]未找到，请联系管理员");
		} catch (IOException e) {
			throw new RuntimeException("excel文件格式错误，请联系管理员");
		}
		return workbook;
	}
	public static  XSSFSheet getSheet(XSSFWorkbook workbook,String sheetName)
	{
		XSSFSheet  sheet = workbook.getSheet(sheetName);
		if(sheet==null)
		{
			throw new RuntimeException("Sheet\""+sheetName+"\"不存在");
		}
		return sheet;
	}
	public static  XSSFSheet getSheet(XSSFWorkbook workbook,int sheetIndex)
	{
		XSSFSheet  sheet = workbook.getSheetAt(sheetIndex);
		if(sheet==null)
		{
			throw new RuntimeException("Sheet\""+sheetIndex+"\"不存在");
		}
		return sheet;
	}
	public static Float getFloatValue(XSSFSheet sheet,int row,int col){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				String strCell3 = null;
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					strCell3 = aCell.getStringCellValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					strCell3 = Double.toString(aCell.getNumericCellValue());
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
				if(strCell3!=null && !"".equals(strCell3)){
					return new Float(strCell3);
				}
				
			}
		}
		 
		return null;
	}
	public static Double getDoubleValue(XSSFSheet sheet,int row,int col){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				String strCell3 = null;
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					strCell3 = aCell.getStringCellValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					return aCell.getNumericCellValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
				if(strCell3!=null && !"".equals(strCell3)){
					return new Double(strCell3);
				}
				
			}
		}
		return null;
	}
	public static Integer getIntValue(XSSFSheet sheet,int row,int col){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				String strCell3 = null;
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					strCell3 = aCell.getStringCellValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					return new Double(aCell.getNumericCellValue()).intValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
			
				if(strCell3!=null && !"".equals(strCell3)){
					return new Integer(strCell3);
				}
				
			}
		}
		 
		return null;
	}
	public static Long getLongValue(XSSFSheet sheet,int row,int col){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				String strCell3 = null;
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					strCell3 = aCell.getStringCellValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					return new Double(aCell.getNumericCellValue()).longValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
			
				if(strCell3!=null && !"".equals(strCell3)){
					return new Long(strCell3);
				}
				
			}
		}
		 
		return null;
	}
	public static String getStringValue(XSSFSheet sheet,int row,int col){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				String strCell3 = null;
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					strCell3 = aCell.getStringCellValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					strCell3 = new BigDecimal(aCell.getNumericCellValue()).stripTrailingZeros().toPlainString();//Double.toHexString(aCell.getNumericCellValue());
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
				
				return strCell3;			
			}
		} 
		return null;
	}
	public static Object getObjectValue(XSSFSheet sheet,int row,int col){
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				String strCell3 = null;
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					strCell3 = aCell.getStringCellValue();
					return strCell3;
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					return new Double(aCell.getNumericCellValue()).longValue();
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
			
			}
		}
		 
		return null;
	}
	static SimpleDateFormat sdf0=null;
	static SimpleDateFormat sdf1=null;
	static SimpleDateFormat sdf2=null;
	static SimpleDateFormat sdf3=null;
	public static Date getDateValue(XSSFSheet sheet,int row,int col) throws ParseException{
		if(sdf0==null){
    		sdf0=new SimpleDateFormat("yyyy/MM/dd");
    		sdf0.setLenient(false);
    	    sdf1=new SimpleDateFormat("yyyy-MM-dd");
    	    sdf1.setLenient(false);
    	    sdf2=new SimpleDateFormat("yyyy年MM月dd日");
    	    sdf2.setLenient(false);
    	    sdf3=new SimpleDateFormat("yyyy年MM月dd号");
    	    sdf3.setLenient(false);
    	}
		XSSFRow aRow = sheet.getRow(row);
		if(aRow!=null){
			XSSFCell aCell=aRow.getCell(col);
			if(aCell!=null){
				if(aCell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					String value = aCell.getStringCellValue();
					//try {
	        			if(value.toString().indexOf('/')>0){
	        				return  sdf0.parse(value.toString());
	        			}else if(value.toString().indexOf('-')>0){
	        				return  sdf1.parse(value.toString());
	        			}else if(value.toString().indexOf('日')>0){
	        				return  sdf2.parse(value.toString());
	        			}else if(value.toString().indexOf('号')>0){
	        				return  sdf3.parse(value.toString());
	        			}else{
	        				throw new RuntimeException("日期格式错误"+value);
	        			}
						
					//} catch (ParseException e) {
						//e.printStackTrace();
						//throws DateException();
					//}
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					 /*short format = aCell.getCellStyle().getDataFormat();  
				   if(format == 14 || format == 31 || format == 57 || format == 58){  
				        //日期  
				        sdf = new SimpleDateFormat("yyyy-MM-dd");  
				    }else if (format == 20 || format == 32) {  
				        //时间  
				        sdf = new SimpleDateFormat("HH:mm");  
				    } */ 
				    double value = aCell.getNumericCellValue();  
				    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
				    return date;
				}else if(aCell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					
				}
			
			}
		}
		 
		return null;
	}
}
