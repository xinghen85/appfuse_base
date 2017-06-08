

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgCustomProperty;

@Repository("cfgCustomPropertyDao")
public class CfgCustomPropertyDaoHibernate extends BasicDAO<CfgCustomProperty, Long> implements CfgCustomPropertyDao {

    public CfgCustomPropertyDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgCustomProperty cfgCustomProperty)  {
		super.save(cfgCustomProperty);
	}    
}
