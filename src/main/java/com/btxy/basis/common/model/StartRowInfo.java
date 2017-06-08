package com.btxy.basis.common.model;

public class StartRowInfo {
	Long id=0l;
	public StartRowInfo(){
		id=0l;
	}
	public StartRowInfo(Long id1){
		id=id1;
	}
	public Long getId() {
		return id;
	}

	public void increase(int size){
		this.id=this.id+size;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
