package com.btxy.basis.service.cfg;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;
import com.btxy.basis.dao.cfg.CfgEnumInfoDao;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgEnumInfoManager")
public class CfgEnumInfoManagerImpl extends MgGenericManagerImpl<CfgEnumInfo, Long> implements CfgEnumInfoManager {
    CfgEnumInfoDao cfgEnumInfoDao;

    @Autowired
    public CfgEnumInfoManagerImpl(CfgEnumInfoDao cfgEnumInfoDao) {
        super(cfgEnumInfoDao);
        this.cfgEnumInfoDao = cfgEnumInfoDao;
    }
    @Override
	public void saveMainBody(CfgEnumInfo cfgEnumInfo) {
		this.cfgEnumInfoDao.saveMainBody(cfgEnumInfo);
	}
    
    @Override
	public PaginatedListHelper<CfgEnumInfo> find(int currentPage, int pageSize,
			QueryContditionSet<CfgEnumInfo> qcs) {
		PaginatedListHelper<CfgEnumInfo> ph = new PaginatedListHelper<CfgEnumInfo>();
    	
    	Query<CfgEnumInfo> q = dao.createQuery(); 
    	
    	if(qcs!=null){
    		qcs.setContdition(q);
    	}
    	
    	Long totalSize=dao.count(q);
    	ph.setFullListSize(totalSize.intValue());
    	ph.setPageNumber(currentPage);
        ph.setObjectsPerPage(pageSize);
    	
    	if(totalSize>(currentPage-1)*pageSize){
    		q.order("-enumCode");
        	q.offset((currentPage-1)*pageSize);
        	q.limit(pageSize);
        	
        	
            ph.setList(dao.find(q).asList());
            
            return ph;
    	}else{
            return ph;
    	}
	}
	@Override
	public List<CfgEnumInfo> getCfgEnumInfoByCode(String enumCode) {
		Query<CfgEnumInfo> q = cfgEnumInfoDao.createQuery();
		q.and(q.criteria("enumCode").equal(enumCode));
		return cfgEnumInfoDao.find(q).asList();
	}
}