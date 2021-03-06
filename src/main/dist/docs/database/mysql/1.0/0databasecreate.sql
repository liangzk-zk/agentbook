一、mysql8 版：

1)建立数据库：agentbook,使用utf8mb4字符集，并设置数据值大小写敏感；建立用户帐号：agentbook，各用户对各自的database拥有所有权限

使用mysql客户端，连接数据库：
mysql -u root -p
建库和用户语句：
create database agentbook character set utf8mb4 collate utf8mb4_0900_as_cs;
create user 'agentbook'@'%' identified by 'agentbook';
GRANT ALL PRIVILEGES ON agentbook.* TO 'agentbook'@'%'WITH GRANT OPTION;