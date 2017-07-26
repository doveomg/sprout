package com.dove.sprout.common.search;

import com.github.pagehelper.Page;

public class Search<T> {
	public static final int PAGE_SIZE = 10;
	public static final int PAGE_NUM = 1;

	private Page<T> page;
	
	public void setSize(int pageSize){
		initPage();
		pageSize = pageSize <= 0 ? PAGE_SIZE : pageSize ;
		page.pageSize(pageSize);
	}
	
	public void setNum(int pageNum){
		initPage();
		pageNum = pageNum <= 0 ? PAGE_NUM : pageNum ;
		page.setPageNum(pageNum);
		if(page.getPageSize()==0){
			setSize(10);
		}
	}
	
	public void initPage(int pageSize,int pageNum){
		setSize(pageSize);
		setNum(pageNum);
	}

	public void initPage(){
		if(page==null){
			page = new Page<>();
		}
	}
	
	public void initDefultPage(){
		if(page==null){
			page = new Page<>(PAGE_NUM,PAGE_SIZE);
		}
	}
	
	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}
	

	
}
