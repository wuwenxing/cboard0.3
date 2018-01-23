package org.cboard.services;

import org.cboard.dao.RoleDao;
import org.cboard.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yfyuan on 2016/12/14.
 */
@Repository
public class RoleService {

    public static final String RES_BOARD = "board";

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Long> getResRole(String resType) {
    	User user = authenticationService.getCurrentUser();
    	String userid = user.getUserId();
    	String companyId = user.getCompany();
        return roleDao.getRoleResByResIds(userid, resType, companyId);
    }
}
