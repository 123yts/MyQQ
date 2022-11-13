#版本一   页面效果（页面写的一般见谅！）  


##一、注册  

###步骤一  

![登录页面点击注册](./images/register_step_1.png)
###步骤二  

![img](./images/register_step_2.png)
###步骤三  

![img](./images/register_step_3.png)
##二、登录  

###登录界面  

![img](./images/login_step_1.png)
###主界面  

![img](./images/login_step_2.png)
##三、添加好友  

###再注册登录一个用户  

![img](./images/addFriend_step_1.png)
###输入QQ添加好友  

![img](./images/addFriend_step_2.png)
###确认后刷新好友列表界面  

![img](./images/addFriend_step_3.png)
![img](./images/addFriend_step_4.png)
##四、发送消息（只是实现了在线聊天，离线消息未实现）  

![img](./images/chat_step_1.png)
###对方下线，再发送消息会提示“对方不在线”  

![img](./images/chat_step_2.png)
##五、数据库SQL  

用户表

create table `user`(
`account` varchar(10) not null primary key,
`name` varchar(30) not null,
`password` varchar(30) not null
)ENGINE=INNODB CHARSET=UTF8 COLLATE=UFT8_GENERAL_CI;

好友关系表

create table `friend`(
`me` varchar(10) not null,
`friend` varchar(10) not null
)ENGINE=INNODB CHARSET=UTF8 COLLATE=UTF8_GENERAL_cI;