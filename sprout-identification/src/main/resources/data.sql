MERGE INTO USERENTITY (id,account,password,name) KEY (id) VALUES (1,'feng','123','fengshuaiju');

MERGE INTO ROLEENTITY (roleid,role_name,description) KEY (id) VALUES (1,'admin','管理员');

MERGE INTO user_role VALUES (1,1);