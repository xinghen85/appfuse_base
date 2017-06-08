

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgParameter;

@Repository("cfgParameterDao")
public class CfgParameterDaoHibernate extends BasicDAO<CfgParameter, Long> implements CfgParameterDao {

    public CfgParameterDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgParameter cfgParameter)  {
		super.save(cfgParameter);
	}    
}
