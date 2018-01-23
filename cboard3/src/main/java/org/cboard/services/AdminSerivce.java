package org.cboard.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.cboard.dao.*;
import org.cboard.dto.User;
import org.cboard.pojo.*;
import org.cboard.services.role.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yfyuan on 2016/12/2.
 */
@Repository
public class AdminSerivce {

    @Value("${admin_user_id:1}")
    private String adminUid;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private DatasetDao datasetDao;

    @Autowired
    private WidgetDao widgetDao;

    @Autowired
    private BoardDao boardDao;
    
    @Autowired
    private AuthenticationService authenticationService;

    public String addUser(String userId, String loginName, String userName, String userPassword, String companyId) {
        String md5 = Hashing.md5().newHasher().putString(userPassword, Charsets.UTF_8).hash().toString();
        DashboardUser user = new DashboardUser();
        user.setLoginName(loginName);
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPassword(md5);
        user.setCompanyIds(companyId);
        userDao.save(user);
        return "1";
    }

    public String updateUser(String userId, String loginName, String userName, String userPassword, String companyId) {
        DashboardUser user = new DashboardUser();
        user.setLoginName(loginName);
        user.setUserId(userId);
        user.setUserName(userName);
        if (StringUtils.isNotBlank(userPassword)) {
            String md5 = Hashing.md5().newHasher().putString(userPassword, Charsets.UTF_8).hash().toString();
            user.setUserPassword(md5);
        }
        user.setCompanyIds(companyId);
        userDao.update(user);
        return "1";
    }

    public String addRole(String roleId, String roleName, String userId, String roleType, String companyId) {
        DashboardRole role = new DashboardRole();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setUserId(userId);
        role.setRoleType(roleType);
        role.setCompanyId(companyId);
        roleDao.save(role);
        return "1";
    }

    public String updateRole(String roleId, String roleName, String userId, String roleType, String companyId) {
        DashboardRole role = new DashboardRole();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setUserId(userId);
        role.setRoleType(roleType);
        role.setCompanyId(companyId);
        roleDao.update(role);
        return "1";
    }

    @Transactional
    public String deleteRole(String roleId) {
        userDao.deleteUserRoleByRoleId(roleId);
        roleDao.deleteRoleRes(roleId);
        roleDao.deleteRole(roleId);
        return "1";
    }

    public String updateUserRole(String[] userId, String[] roleId, final String curUid, String companyId) {

        for (String uid : userId) {
            Map<String, Object> params = new HashedMap();
            params.put("objUid", uid);
            params.put("curUid", curUid);
            params.put("adminUid", adminUid);
            params.put("companyId", companyId);
            userDao.deleteUserRole(params);
            if (roleId != null && roleId.length > 0) {
                List<DashboardUserRole> list = new ArrayList<>();
                for (String rid : roleId) {
                    DashboardUserRole userRole = new DashboardUserRole();
                    userRole.setUserId(uid);
                    userRole.setRoleId(rid);
                    userRole.setCompanyId(companyId);
                    list.add(userRole);
                }
                userDao.saveUserRole(list);
            }
        }
        return "1";
    }

    public String deleteUserRoles(String[] userId, String[] roleId, final String curUid, String companyId) {
        Map<String, Object> params = new HashedMap();
        params.put("userIds", userId);
        params.put("roleIds", roleId);
        params.put("curUid", curUid);
        params.put("adminUid", adminUid);
        params.put("companyId", companyId);
        userDao.deleteUserRoles(params);
        return "1";
    }

    public String updateRoleResUser(String[] roleId, String resIdArr) {
    	User user = authenticationService.getCurrentUser();
    	String companyId = user.getCompany();
        JSONArray arr = JSONArray.parseArray(resIdArr);
        for (Object res : arr) {
            JSONObject jo = (JSONObject) res;
            roleDao.deleteRoleResByResId(jo.getLong("resId"), jo.getString("resType"));
            List<DashboardRoleRes> list = new ArrayList<>();
            for (String rid : roleId) {
                DashboardRoleRes roleRes = new DashboardRoleRes();
                roleRes.setRoleId(rid);
                roleRes.setResId(jo.getLong("resId"));
                roleRes.setResType(jo.getString("resType"));
                roleRes.setPermission("" + (false ? 1 : 0) + (false ? 1 : 0));
                roleRes.setCompanyId(companyId);
                list.add(roleRes);
            }
            roleDao.saveRoleRes(list);
        }
        return "1";
    }

    public String updateRoleRes(String[] roleId, String resIdArr) {
    	User user = authenticationService.getCurrentUser();
    	String companyId = user.getCompany();
        JSONArray arr = JSONArray.parseArray(resIdArr);
        for (String rid : roleId) {
            roleDao.deleteRoleRes(rid);
            if (arr != null && arr.size() > 0) {
                List<DashboardRoleRes> list = new ArrayList<>();
                for (Object res : arr) {
                    JSONObject jo = (JSONObject) res;
                    DashboardRoleRes roleRes = new DashboardRoleRes();
                    roleRes.setRoleId(rid);
                    roleRes.setResId(jo.getLong("resId"));
                    roleRes.setResType(jo.getString("resType"));
                    boolean edit = jo.getBooleanValue("edit");
                    boolean delete = jo.getBooleanValue("delete");
                    roleRes.setPermission("" + (edit ? 1 : 0) + (delete ? 1 : 0));
                    roleRes.setCompanyId(companyId);
                    list.add(roleRes);
                }
                roleDao.saveRoleRes(list);
            }
        }
        return "1";
    }

    public ServiceStatus changePwd(String userId, String curPwd, String newPwd, String cfmPwd) {
        curPwd = Hashing.md5().newHasher().putString(curPwd, Charsets.UTF_8).hash().toString();
        newPwd = Hashing.md5().newHasher().putString(newPwd, Charsets.UTF_8).hash().toString();
        cfmPwd = Hashing.md5().newHasher().putString(cfmPwd, Charsets.UTF_8).hash().toString();
        if (newPwd.equals(cfmPwd)) {
            if (userDao.updateUserPassword(userId, curPwd, newPwd) == 1) {
                return new ServiceStatus(ServiceStatus.Status.Success, "success");
            }
        }
        return null;
    }

    public List<DashboardBoard> getBoardList(String userId) {
    	User user = authenticationService.getCurrentUser();
    	String companyId = user.getCompany();
        if (adminUid.equals(userId)) {
            return boardDao.getBoardList(userId, companyId);
        } else {
            List<DashboardRoleRes> resList = roleDao.getUserRoleResList(userId, "board", companyId);

            List<Long> resIdList = resList.stream().filter(e -> RolePermission.isEdit(e.getPermission())).map(e -> e.getResId()).distinct().collect(Collectors.toList());
            return boardDao.getBoardList(userId, companyId).stream().filter(e -> resIdList.contains(e.getId()) || e.getUserId().equals(userId)).collect(Collectors.toList());
        }
    }

    public List<DashboardDataset> getDatasetList(String userId) {
    	User user = authenticationService.getCurrentUser();
    	String companyId = user.getCompany();
        if (adminUid.equals(userId)) {
            return datasetDao.getDatasetList(userId, companyId);
        } else {
            List<DashboardRoleRes> resList = roleDao.getUserRoleResList(userId, "dataset", companyId);
            List<Long> resIdList = resList.stream().filter(e -> RolePermission.isEdit(e.getPermission())).map(e -> e.getResId()).distinct().collect(Collectors.toList());
            return datasetDao.getDatasetList(userId, companyId).stream().filter(e -> resIdList.contains(e.getId()) || e.getUserId().equals(userId)).collect(Collectors.toList());
        }
    }

    public List<DashboardWidget> getWidgetList(String userId) {
    	User user = authenticationService.getCurrentUser();
    	String companyId = user.getCompany();
        if (adminUid.equals(userId)) {
            return widgetDao.getWidgetList(userId, companyId);
        } else {
            List<DashboardRoleRes> resList = roleDao.getUserRoleResList(userId, "widget", companyId);
            List<Long> resIdList = resList.stream().filter(e -> RolePermission.isEdit(e.getPermission())).map(e -> e.getResId()).distinct().collect(Collectors.toList());
            return widgetDao.getWidgetList(userId, companyId).stream().filter(e -> resIdList.contains(e.getId()) || e.getUserId().equals(userId)).collect(Collectors.toList());
        }
    }


}
