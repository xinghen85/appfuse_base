

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgStateMachineValue;

@Repository("cfgStateMachineValueDao")
public class CfgStateMachineValueDaoHibernate extends BasicDAO<CfgStateMachineValue, Long> implements CfgStateMachineValueDao {

    public CfgStateMachineValueDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgStateMachineValue cfgStateMachineValue)  {
		CfgStateMachineValue cfgStateMachineValue2=super.get(cfgStateMachineValue.getStatId());
		if(cfgStateMachineValue!=null && cfgStateMachineValue2!=null){
			cfgStateMachineValue.setButtons(cfgStateMachineValue2.getButtons());
		}	
		super.save(cfgStateMachineValue);
	}    
}
