/**
 * @Administrator
 */
package com.btxy.basis.util.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PercentRank {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PercentRank pr=new PercentRank();
		pr.add(1d);
		pr.add(1d);
		pr.add(2d);
		//pr.add(90d);
		System.out.println(pr.getPercentRank(2d));
		//System.out.println(pr.getPercentRank(60d));
		//System.out.println(pr.getPercentRank(72d));
		//System.out.println(pr.getPercentRank(73d));
		//System.out.println(pr.getPercentRank(84d));
		//System.out.println(pr.getPercentRank(100d));
	}
	List<Double> values=new ArrayList<Double>();
	
	public void add(Double d){
		values.add(d);
	}
	
	public void addList(List dlist){
		values = dlist;
	}
	public Double getPercentRank(Double d){
		if(values.size()==0){
			return 0d;
		}else if(values.size()==1){
			if(d<values.get(0)){
				return 0d;
			}else if(d>values.get(0)){
				return 1d;
			}else{
				return 1d;
			}
		}
		
		Collections.sort(values);
		System.out.println("======getPercentRank|d:"+d+",size:"+values.size());
		
		Double last=null;
		for(int i=0;i<values.size();i++){
			Double d1=values.get(i);
			if(d-d1>=-0.000001 && d-d1<=0.000001){
				System.out.println("============result1:"+(double)(i)+"|"+(double)(values.size()-1)+"|"+values.size()+"|"+BigDecimalUtil.divide((double)(i), (double)(values.size()-1)));
				return  BigDecimalUtil.divide((double)(i), (double)(values.size()-1));
			}else if(d1>d){
				if(last==null){
					System.out.println("============result2:"+(double)(i));
					return 0d;
				}else{
					double x1=last;
					double y1=BigDecimalUtil.divide((double)(i-1), (double)(values.size()-1));
					double x2=d1;
					double y2=BigDecimalUtil.divide((double)(i-1), (double)(values.size()-1));
					
					System.out.println("============result3:"+(double)(i)+"|x1:"+x1+"|y1:"+y1+"|x2:"+x2+"|y2:"+y2+"|result:"+Double.parseDouble(BigDecimalUtil.add(y1+"",BigDecimalUtil.divide(BigDecimalUtil.multiply(BigDecimalUtil.subtract(y2, y1), BigDecimalUtil.subtract(d, x1)),  BigDecimalUtil.subtract(x2, x1))+"")));
					
					return Double.parseDouble(BigDecimalUtil.add(y1+"",BigDecimalUtil.divide(BigDecimalUtil.multiply(BigDecimalUtil.subtract(y2, y1), BigDecimalUtil.subtract(d, x1)),  BigDecimalUtil.subtract(x2, x1))+""));
				//	return y1+(y2-y1)*(d-x1)/(x2-x1);
				}
			}
			last=d1;
		}
		
		return 1d;
	}
}