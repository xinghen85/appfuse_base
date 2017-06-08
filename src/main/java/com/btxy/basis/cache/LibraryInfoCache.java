package com.btxy.basis.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;

import com.btxy.basis.model.LibraryInfo;

public class LibraryInfoCache {
	private static LibraryInfoCache libraryInfoCache=null ;
	public static LibraryInfoCache getInstance() {
		return libraryInfoCache;
	}
	
	
	private Map<String,LibraryInfo> libraryInfoMapWithPath=new HashMap<String,LibraryInfo>();
	private Map<Long,LibraryInfo> libraryInfoMapWithId=new HashMap<Long,LibraryInfo>();
	
	
	public LibraryInfo getLibraryInfoByPath(String path){
		return libraryInfoMapWithPath.get(path);
	}
	
	public LibraryInfo getLibraryInfoById(Long id){
		return libraryInfoMapWithId.get(id);
	}
	public Long getLibraryIdByPath(String path){
		return libraryInfoMapWithPath.get(path)==null?0l:libraryInfoMapWithPath.get(path).getLibraryId();
	}
	public static void startUpInit(Datastore ds){
		libraryInfoCache=new LibraryInfoCache();
		List<LibraryInfo> list1=ds.find(LibraryInfo.class).asList();
		for(LibraryInfo oneLibrary:list1){
			libraryInfoCache.libraryInfoMapWithPath.put(oneLibrary.getPath(), oneLibrary);
			libraryInfoCache.libraryInfoMapWithId.put(oneLibrary.getLibraryId(), oneLibrary);
		}
	}
	
	
	public static void staticrefresh(){
		LibraryInfoCache.libraryInfoCache=null;
	}
	
}
