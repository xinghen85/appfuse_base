

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgFixedPropertyValue;

@Repository("cfgFixedPropertyValueDao")
public class CfgFixedPropertyValueDaoHibernate extends BasicDAO<CfgFixedPropertyValue, Long> implements CfgFixedPropertyValueDao {

    public CfgFixedPropertyValueDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgFixedPropertyValue cfgFixedPropertyValue)  {
		super.save(cfgFixedPropertyValue);
	}    
}
