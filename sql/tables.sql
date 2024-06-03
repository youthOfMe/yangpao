create table tag
(
    id           bigint       null,
    tag_name     varchar(256) null,
    user_id      bigint       null,
    parent_id    bigint       null,
    isParent     tinyint      null,
    created_time datetime     null,
    updated_time datetime     null,
    isDelete     tinyint      null
);

create table team
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    description varchar(512)                       null comment '描述',
    maxNum      tinyint  default 1                 not null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint                             null comment '用户ID',
    status      tinyint  default 0                 not null comment '公开 = 0 私有 = 1 加密 = 2',
    password    varchar(256)                       null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除 0 = 不 1 = 删除',
    name        varchar(512)                       not null comment '队伍名称'
);

create table user
(
    id           bigint auto_increment comment '主键'
        primary key,
    username     varchar(256) default 'love'            not null comment '用户昵称',
    userAccount  varchar(256)                           null comment '账号哦',
    avatarUrl    varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '用户性别',
    userPassword varchar(512)                           not null comment '密码',
    phone        varchar(128)                           null comment '手机号',
    email        varchar(512)                           null comment '邮箱',
    userStatus   int          default 0                 not null comment '用户状态 0 -> 正常',
    createTime   datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    userRole     int          default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员 ',
    planetCode   varchar(512)                           not null comment '星球编号',
    tags         varchar(1024)                          null comment '用户标签'
)
    comment '用户表';

create table user_team
(
    id         bigint auto_increment
        primary key,
    userId     bigint                             null,
    teamId     bigint                             null,
    joinTime   datetime default CURRENT_TIMESTAMP not null,
    createTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null
);

