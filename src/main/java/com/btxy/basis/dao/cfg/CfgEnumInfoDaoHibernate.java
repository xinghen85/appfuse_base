

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgEnumInfo;

@Repository("cfgEnumInfoDao")
public class CfgEnumInfoDaoHibernate extends BasicDAO<CfgEnumInfo, Long> implements CfgEnumInfoDao {

    public CfgEnumInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgEnumInfo cfgEnumInfo)  {
		CfgEnumInfo cfgEnumInfo2=super.get(cfgEnumInfo.getEnumId());
		if(cfgEnumInfo!=null && cfgEnumInfo2!=null){
			cfgEnumInfo.setValues(cfgEnumInfo2.getValues());
		}	
		super.save(cfgEnumInfo);
	}    
}
