

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgEnumValueInfo;

@Repository("cfgEnumValueInfoDao")
public class CfgEnumValueInfoDaoHibernate extends BasicDAO<CfgEnumValueInfo, Long> implements CfgEnumValueInfoDao {

    public CfgEnumValueInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgEnumValueInfo cfgEnumValueInfo)  {
		super.save(cfgEnumValueInfo);
	}    
}
