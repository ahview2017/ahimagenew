# add by xia.yunan@20170903
alter table cp_pic_group add  MASVIDEO_SIGN int(11) default '0' COMMENT '媒资视频标识，0：无视频，1：稿件有视频';
alter table cp_pic_group add  VIDEO_ID int(11) default '0' COMMENT '视频ID';

# add by liu.jinfeng@20170904
alter table cp_pic_group add  QBSTATUS int(11) default '0' COMMENT '是否签报，0没签，1签过';
#新增签报权限
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(412,0,'签报','签报','groupPicCtro/signPic',NULL,0,'admin',now(),2,'',0);

INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(413,0,'已处理稿件','已处理稿件',NULL,NULL,0,'admin',now(),2,'',0);
INSERT INTO `cp_right` (`ID`,`TYPE`,`RIGHT_NAME`,`MEMO`,`TARGET_VALUE`,`P_ID`,`DELETE_FLAG`,`UPDATE_USER`,`UPDATE_TIME`,`STANDBY1`,`STANDBY2`,`LANG_TYPE`)VALUES(414,1,'已处理稿件列表','已处理稿件列表','groupPicCtro/getGroupPicsInDeal','413',0,'admin',now(),NULL,'',0);