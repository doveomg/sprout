MERGE INTO USERENTITY (id,account,password,name) values (1,'feng','123','fengshuaiju');

MERGE INTO ROLEENTITY (roleid,role_name,description) values (1,'admin','管理员');

MERGE INTO user_role values (1,1);