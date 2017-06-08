

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgFormInfo;

@Repository("cfgFormInfoDao")
public class CfgFormInfoDaoHibernate extends BasicDAO<CfgFormInfo, Long> implements CfgFormInfoDao {

    public CfgFormInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgFormInfo cfgFormInfo)  {
		super.save(cfgFormInfo);
	}    
}
