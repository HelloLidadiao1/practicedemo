create table stuff(
                      U_ID int(10) primary key auto_increment comment '员工信息表主键',
                      U_NAME varchar(100) comment '员工姓名',
                      ID_CARD varchar(30) comment '身份证号',
                      JOB varchar(30) comment '工种',
                      BANK_ACCOUNT varchar(50) comment '银行账号',
                      BANK_INFO varchar(100) comment '开户银行',
                      TELEPHONE_NUM varchar(20) comment '电话号码',
                      index ID_CARD_INDEX (ID_CARD)      -- 给身份证号添加索引
);

-- 查看执行计划
explain select * from staff where ID_CARD = 1;

-- 查看创建表语句
show create table stuff;

show create database stuff_info;

-- 创建数据库语句：
CREATE DATABASE stuff_info DEFAULT CHARACTER SET utf8;

create table STUFF_WAGE(
       WAGE_ID INT(10) PRIMARY KEY AUTO_INCREMENT COMMENT '',
       UID INT(10) COMMENT '员工id',
       WAGE DOUBLE(10,2) COMMENT '工资',
       MONTH INT(10) COMMENT '月份',
       INDEX UID_REF_INDEX(UID)
);


alter table STUFF_WAGE rename to STAFF_INFO;
alter table STUFF rename to STAFF;


