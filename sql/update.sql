# add by xia.yunan@20170903
alter table cp_pic_group add  MASVIDEO_SIGN int(11) default '0' COMMENT '媒资视频标识，0：无视频，1：稿件有视频';
alter table cp_pic_group add  VIDEO_ID int(11) default '0' COMMENT '视频ID';

# add by liu.jinfeng@20170904
alter table cp_pic_group add  QBSTATUS int(11) default '0' COMMENT '是否签报，0没签，1签过';
alter table cp_pic_group add  SHOWSTATUS int(11) default '0' COMMENT '是否显示购物车，0不显示，1显示';
#新增签报权限
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(412,0,'签报','签报','groupPicCtro/signPic',NULL,0,'admin',now(),2,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(413,0,'已处理稿件','已处理稿件',NULL,NULL,0,'admin',now(),2,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(414,1,'已处理稿件列表','已处理稿件列表','groupPicCtro/getGroupPicsInDeal','413',0,'admin',now(),NULL,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(415,0,'老照片管理','老照片管理',NULL,NULL,0,'admin',now(),1,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(416,0,'EXIF图片控制','EXIF图片控制','uploadpic',NULL,0,'admin',now(),2,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(417,1,'一键撤稿','一键撤稿','groupPicCtro/downAllGroupPic',245,0,'admin',now(),NULL,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(418,1,'一键签报','一键撤稿','groupPicCtro/signGroups',141,0,'admin',now(),NULL,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(419,1,'资料库子栏目稿件列表','资料库子栏目稿件列表','groupPicCtro/getSginSubGroup',245,0,'admin',now(),NULL,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(420,1,'资料库搜索稿件列表','资料库搜索稿件列表','groupPicCtro/getSginGroupOnlySeach',245,0,'admin',now(),NULL,'',0);


DROP TABLE IF EXISTS `cp_pic_group_thumbsup`;

CREATE TABLE `cp_pic_group_thumbsup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '稿件点赞记录ID',
  `CRTIME` timestamp NULL DEFAULT NULL COMMENT '编辑时间',
  `IP` varchar(255) DEFAULT NULL COMMENT '来源IP',
  `GROUPID` int(11) DEFAULT NULL COMMENT '稿件ID',
  `STATUS` tinyint(4) DEFAULT '0' COMMENT '点赞状态，0：正常，1：取消',
  `APPEND` varchar(255) DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='稿件点赞记录表';

#摄影师管理
alter table cp_user add  HOMEPAGE_COLUMNID int(11) default '0' COMMENT '摄影师主页栏目ID';
alter table cp_user add  USER_DETAIL varchar(8000) DEFAULT NULL COMMENT '用户简介';
alter table cp_user add  USER_CLASS int(11) default '0' NULL COMMENT '用户分类 0：其它 1：摄影名家 2：艺术家';

alter table cp_pic_group add  QB_TIME timestamp NULL DEFAULT NULL COMMENT '签报时间';

alter table cp_log modify OPE_PARAM VARCHAR(6000) DEFAULT NULL;


alter table cp_user_delete add  HOMEPAGE_COLUMNID int(11) default '0' COMMENT '摄影师主页栏目ID';
alter table cp_user_delete add  USER_DETAIL varchar(8000) DEFAULT NULL COMMENT '用户简介';
alter table cp_user_delete add  USER_CLASS int(11) default '0' NULL COMMENT '用户分类 0：其它 1：摄影名家 2：艺术家';

ALTER TABLE cp_picture ADD PRIMARY KEY cp_pictur_GROUP_ID_DELETE_FLAG (GROUP_ID,DELETE_FLAG)


/**************add by hexinxin@20180110************/
CREATE INDEX cp_pic_group_ID_AUTHOR_ID
  ON cp_pic_group (ID,AUTHOR_ID);

CREATE INDEX cp_pic_group_ID
  ON cp_pic_group (ID);

CREATE INDEX cp_picture_GROUP_ID
  ON cp_picture (GROUP_ID);

CREATE INDEX cp_pic_group_category_GROUP_ID
  ON cp_pic_group_category (GROUP_ID);

CREATE INDEX cp_user_ID
  ON cp_user (ID);


CREATE INDEX cp_category_ID
  ON cp_category (ID);

CREATE INDEX cp_pic_group_process_PICGROUP_ID_FLOW_TYPE
  ON cp_pic_group_process (PICGROUP_ID,FLOW_TYPE);

CREATE INDEX cp_group_push_GROUP_ID
  ON cp_group_push (GROUP_ID);

CREATE INDEX cp_pic_group_category_CATEGORY_ID
  ON cp_pic_group_category (CATEGORY_ID);

CREATE INDEX cp_log_id
  ON cp_log (id);

CREATE INDEX cp_log_type_id
  ON cp_log_type (id);
  
  
/**************add by xiayunan@20180115************/
ALTER TABLE cp_picture ADD INDEX cp_picture_GROUP_ID_DELETE_FLAG(GROUP_ID,DELETE_FLAG);	
ALTER TABLE cp_pic_group_category ADD INDEX cp_pic_group_category_CATEGORY_ID_TYPE(CATEGORY_ID,TYPE);
ALTER TABLE cp_pic_group_process ADD INDEX cp_pic_group_process_PICGROUP_ID_FLOW_TYPE(PICGROUP_ID,FLOW_TYPE);	
ALTER TABLE cp_pic_allpath ADD INDEX cp_pic_allpath_TRAGET_ID_PIC_TYPE(PICGROUP_ID,PIC_TYPE);


alter table cp_category add  ALLOW_SUBMISSIONS int(11) DEFAULT NULL  COMMENT '是否允许投稿，null:允许，0：允许，1：不允许';

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(421,1,'保存稿件至草稿箱','保存稿件至草稿箱','groupPicCtro/saveDraftBox',113,0,'admin',now(),NULL,'',0);

alter table cp_picture add  IS_SIGN int(11) default NULL COMMENT '图片是否允许签报标识,0:显示，1：隐藏';

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(422,0,'根据作者统计稿件','根据作者统计稿件','groupStatistical/GroupStatisticalForAuthorList',NULL,0,'admin',now(),3,'',0);

alter table cp_picture_downloadrecord  modify column AUTHOR_LOGIN_NAME varchar(100) DEFAULT NULL COMMENT '作者登录名';

ALTER TABLE cp_picture_downloadrecord ADD INDEX cp_ID_PIC_GROUP_ID(PICTURE_GROUP_ID,ID);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(423,0,'类别统计稿件','类别统计稿件','groupStatistical/groupStatisticalForCategoryList',NULL,0,'admin',now(),3,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(424,0,'地市统计稿件','地市统计稿件','groupStatistical/groupStatisticalForAnHuiCityList',NULL,0,'admin',now(),3,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(425,0,'栏目统计稿件','栏目统计稿件','groupStatistical/groupStatisticalForWebSiteShowColumnList',NULL,0,'admin',now(),3,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(426,0,'图片下载统计','图片下载统计','groupStatistical/groupStatisticalForPicDownloadList',NULL,0,'admin',now(),3,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(427,0,'审核人员统计','图片下载统计','groupStatistical/groupStatisticalForEditorList',NULL,0,'admin',now(),3,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(428,0,'用户统计稿件','用户统计稿件','groupStatistical/groupStatisticalForUserList',NULL,0,'admin',now(),3,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(429,1,'资料库搜索稿件列表','资料库搜索稿件列表','groupPicCtro/getDatabaseGroupsCountAndPicCount',245,0,'admin',now(),NULL,'',0);
  





