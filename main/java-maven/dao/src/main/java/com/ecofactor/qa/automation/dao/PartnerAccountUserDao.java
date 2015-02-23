
package com.ecofactor.qa.automation.dao;

import com.ecofactor.common.pojo.PartnerAccountUser;

public interface PartnerAccountUserDao extends BaseDao<PartnerAccountUser> {


    public PartnerAccountUser getPartnerAccountUserByName(String partnerAccountUserName);


    public int getPartnerAccountByAccessLogin(String accessLogin);

    public PartnerAccountUser findById(int id);

}
