drop TABLE if exists dashboard_board;
CREATE TABLE `dashboard_board` (
  `board_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `board_name` varchar(100) NOT NULL,
  `layout_json` text,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`board_id`)
) ENGINE=MyISAM AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_board_param;
CREATE TABLE `dashboard_board_param` (
  `board_param_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `board_id` bigint(20) NOT NULL,
  `config` text,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`board_param_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_category;
CREATE TABLE `dashboard_category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_dataset;
CREATE TABLE `dashboard_dataset` (
  `dataset_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  `dataset_name` varchar(100) DEFAULT NULL,
  `data_json` text,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dataset_id`)
) ENGINE=MyISAM AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_datasource;
CREATE TABLE `dashboard_datasource` (
  `datasource_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `source_name` varchar(100) NOT NULL,
  `source_type` varchar(100) NOT NULL,
  `config` text,
  PRIMARY KEY (`datasource_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_job;
CREATE TABLE `dashboard_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(200) DEFAULT NULL,
  `cron_exp` varchar(200) DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `job_type` varchar(200) DEFAULT NULL,
  `job_config` text,
  `user_id` varchar(100) DEFAULT NULL,
  `last_exec_time` timestamp NULL DEFAULT NULL,
  `job_status` bigint(20) DEFAULT NULL,
  `exec_log` text,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_role;
CREATE TABLE `dashboard_role` (
  `role_id` varchar(100) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `role_type` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_role_res;
CREATE TABLE `dashboard_role_res` (
  `role_res_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(100) DEFAULT NULL,
  `res_type` varchar(100) DEFAULT NULL,
  `res_id` bigint(20) DEFAULT NULL,
  `permission` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`role_res_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1962 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_user;
CREATE TABLE `dashboard_user` (
  `user_id` varchar(50) NOT NULL,
  `login_name` varchar(100) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `user_status` varchar(100) DEFAULT NULL,
  `company_ids` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_user_role;
CREATE TABLE `dashboard_user_role` (
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `role_id` varchar(100) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

drop TABLE if exists dashboard_widget;
CREATE TABLE `dashboard_widget` (
  `widget_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `category_name` varchar(100) DEFAULT NULL,
  `widget_name` varchar(100) DEFAULT NULL,
  `data_json` text,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`widget_id`)
) ENGINE=MyISAM AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;


call updateUser_CB();

commit;
