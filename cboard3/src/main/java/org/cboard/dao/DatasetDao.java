package org.cboard.dao;

import org.cboard.pojo.DashboardDataset;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by yfyuan on 2016/10/11.
 */
@Repository
public interface DatasetDao {

    List<String> getCategoryList(String companyId);

    List<DashboardDataset> getAllDatasetList(String companyId);

    List<DashboardDataset> getDatasetList(String userId, String companyId);

    List<DashboardDataset> getDatasetListAdmin(String userId, String companyId);

    int save(DashboardDataset dataset);

    long countExistDatasetName(Map<String, Object> map);

    int update(DashboardDataset dataset);

    int delete(Long id, String userId);

    DashboardDataset getDataset(Long id);

    long checkDatasetRole(String userId, Long widgetId, String permissionPattern, String companyId);

}
