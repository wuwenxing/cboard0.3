alter table dashboard_user add column company_ids varchar(20) DEFAULT NULL;
alter table dashboard_role add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_role add column role_type varchar(20) DEFAULT NULL;
alter table dashboard_user_role add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_role_res add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_board add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_board_param add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_dataset add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_widget add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_category add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_job add column company_id varchar(20) DEFAULT NULL;
commit;

