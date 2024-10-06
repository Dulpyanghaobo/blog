create table tarot_categories
(
    id            bigint auto_increment
        primary key,
    category_name varchar(255) not null,
    description   text         null
);

INSERT INTO blog.tarot_categories (id, category_name, description) VALUES (1, '基础运势解读', '分析用户的整体运势走向，提供日常决策的参考');
INSERT INTO blog.tarot_categories (id, category_name, description) VALUES (2, '桃花正缘', '帮助用户了解自己在感情中的桃花运势以及正缘到来的时机');
INSERT INTO blog.tarot_categories (id, category_name, description) VALUES (3, '事业发展', '结合塔罗牌分析用户在事业中的机遇与挑战');
INSERT INTO blog.tarot_categories (id, category_name, description) VALUES (4, '财富之路', '解读用户的财富运势，提供财务管理建议');
