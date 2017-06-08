

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgIconInfo;

@Repository("cfgIconInfoDao")
public class CfgIconInfoDaoHibernate extends BasicDAO<CfgIconInfo, Long> implements CfgIconInfoDao {

    public CfgIconInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgIconInfo cfgIconInfo)  {
		super.save(cfgIconInfo);
	}    
}
