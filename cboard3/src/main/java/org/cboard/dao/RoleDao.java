package org.cboard.dao;

import org.cboard.pojo.DashboardRole;
import org.cboard.pojo.DashboardRoleRes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yfyuan on 2016/12/6.
 */
@Repository
public interface RoleDao {
    int save(DashboardRole role);

    List<DashboardRole> getRoleList(String userId, String companyId);

    List<DashboardRole> getRoleListAll(String companyId);

    int update(DashboardRole role);

    List<DashboardRoleRes> getRoleResList(String companyId);

    int saveRoleRes(List<DashboardRoleRes> list);

    int deleteRoleRes(String roleId);

    int deleteRoleResByResId(Long resId,String resType);

    List<Long> getRoleResByResIds(String userId, String resType, String companyId);

    DashboardRole getRole(String roleId);

    int deleteRole(String roleId);

    List<DashboardRoleRes> getUserRoleResList(String userId, String resType, String companyId);
}
