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




