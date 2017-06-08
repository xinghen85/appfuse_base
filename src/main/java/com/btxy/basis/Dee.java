package com.btxy.basis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Dee {
	public static void main(String[] args) throws IOException {
		List<String> dd1=FileUtils.readLines(new File("/Volumes/WORK/git/appfuse_base/src/dd.txt"));
		List<String> dd2=FileUtils.readLines(new File("/Volumes/WORK/git/appfuse_base/src/ddd.txt"));
		List<String> dd3=new ArrayList<String>();
		int dd=0;
		for (String string : dd1) {
			if(dd2.contains(string)){
				dd++;
			}else{
				dd3.add("INSERT INTO  `vip_select_phone` (`month`, `phoneNumber`) VALUES (201704,"+string+");");
			}
		}
		System.out.println(dd+","+dd3.size()+","+(dd+dd3.size()));
		FileUtils.writeLines(new File("/Volumes/WORK/git/appfuse_base/src/out.txt"),dd3);
	}
}
