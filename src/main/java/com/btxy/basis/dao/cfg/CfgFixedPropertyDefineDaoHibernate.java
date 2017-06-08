

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgFixedPropertyDefine;

@Repository("cfgFixedPropertyDefineDao")
public class CfgFixedPropertyDefineDaoHibernate extends BasicDAO<CfgFixedPropertyDefine, Long> implements CfgFixedPropertyDefineDao {

    public CfgFixedPropertyDefineDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgFixedPropertyDefine cfgFixedPropertyDefine)  {
		CfgFixedPropertyDefine cfgFixedPropertyDefine2=super.get(cfgFixedPropertyDefine.getPropertyId());
		if(cfgFixedPropertyDefine!=null && cfgFixedPropertyDefine2!=null){
			cfgFixedPropertyDefine.setFieldList(cfgFixedPropertyDefine2.getFieldList());
		}	
		super.save(cfgFixedPropertyDefine);
	}    
}
