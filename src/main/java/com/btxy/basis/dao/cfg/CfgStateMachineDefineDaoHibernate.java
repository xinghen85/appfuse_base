package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgStateMachineDefine;

@Repository("cfgStateMachineDefineDao")
public class CfgStateMachineDefineDaoHibernate extends BasicDAO<CfgStateMachineDefine, Long> implements CfgStateMachineDefineDao {

    public CfgStateMachineDefineDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(CfgStateMachineDefine cfgStateMachineDefine)  {
		super.save(cfgStateMachineDefine);
	}    
}
