-- 初始化数据脚本
-- 创建测试用的店铺和菜品数据

-- 创建 shops 表
CREATE TABLE IF NOT EXISTS shops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    cuisine_type VARCHAR(100),
    flavor_tags VARCHAR(500),
    avg_price DECIMAL(10, 2),
    address VARCHAR(500),
    rating DECIMAL(3, 1),
    description VARCHAR(1000)
);

-- 创建 dishes 表
CREATE TABLE IF NOT EXISTS dishes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    shop_id BIGINT NOT NULL,
    price DECIMAL(10, 2),
    description VARCHAR(1000),
    flavor_tags VARCHAR(200),
    is_spicy BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (shop_id) REFERENCES shops(id)
);

-- 插入店铺数据
INSERT INTO shops (name, cuisine_type, flavor_tags, avg_price, address, rating, description) VALUES
('川香阁', '川菜', '麻辣，香辣，重口味', 85.00, '北京市朝阳区建国路 88 号', 4.5, '正宗川菜馆，主打水煮鱼、麻婆豆腐等经典川菜，厨师来自四川本地。'),
('粤海轩', '粤菜', '清淡，鲜美，原汁原味', 120.00, '北京市海淀区中关村大街 1 号', 4.7, '高档粤菜餐厅，提供正宗广式点心和海鲜料理。'),
('东来顺', '京菜', '咸鲜，酱香', 95.00, '北京市东城区王府井大街 2 号', 4.3, '百年老字号，以涮羊肉和京味菜闻名。'),
('樱花屋', '日本料理', '清淡，鲜美，生食', 180.00, '北京市朝阳区三里屯路 19 号', 4.6, '日式居酒屋风格，提供新鲜刺身、寿司和烤物。'),
('泰香园', '泰国菜', '酸辣，香甜', 75.00, '北京市海淀区五道口街 3 号', 4.4, '正宗泰国风味，冬阴功汤和绿咖喱是招牌菜。'),
('西域风情', '新疆菜', '香辣，孜然，烤肉', 65.00, '北京市朝阳区望京街 5 号', 4.2, '新疆特色餐厅，大盘鸡和烤羊肉串非常受欢迎。');

-- 插入菜品数据
-- 川香阁的菜品
INSERT INTO dishes (shop_id, name, price, description, flavor_tags, is_spicy) VALUES
(1, '水煮鱼', 88.00, '选用鲜活草鱼，配以豆芽、金针菇，麻辣鲜香', '麻辣，鲜香', true),
(1, '麻婆豆腐', 32.00, '传统川菜，豆腐嫩滑，麻辣入味', '麻辣，咸香', true),
(1, '宫保鸡丁', 45.00, '鸡肉丁配花生米，酸甜微辣', '酸甜，微辣', true),
(1, '夫妻肺片', 38.00, '牛杂凉拌，红油香辣', '香辣，麻辣', true);

-- 粤海轩的菜品
INSERT INTO dishes (shop_id, name, price, description, flavor_tags, is_spicy) VALUES
(2, '白切鸡', 68.00, '选用三黄鸡，皮爽肉滑，配姜葱蓉', '清淡，鲜美', false),
(2, '虾饺', 48.00, '晶莹剔透，虾仁饱满弹牙', '清淡，鲜美', false),
(2, '烧鹅', 128.00, '皮脆肉嫩，蜜汁香甜', '香甜，咸鲜', false),
(2, '清蒸石斑鱼', 198.00, '新鲜石斑鱼，清蒸保留原汁原味', '清淡，鲜美', false);

-- 东来顺的菜品
INSERT INTO dishes (shop_id, name, price, description, flavor_tags, is_spicy) VALUES
(3, '涮羊肉', 108.00, '精选内蒙古羔羊肉，配秘制麻酱', '咸鲜，酱香', false),
(3, '京酱肉丝', 42.00, '猪肉丝配甜面酱，卷饼食用', '酱香，咸甜', false),
(3, '炸酱面', 28.00, '老北京特色，肉酱浓郁', '酱香，咸鲜', false);

-- 樱花屋的菜品
INSERT INTO dishes (shop_id, name, price, description, flavor_tags, is_spicy) VALUES
(4, '三文鱼刺身', 88.00, '挪威进口三文鱼，新鲜切片', '清淡，鲜美', false),
(4, '鳗鱼寿司', 65.00, '烤鳗鱼配寿司饭，刷特制酱汁', '香甜，咸鲜', false),
(4, '天妇罗', 58.00, '虾仁蔬菜裹面糊炸制，外酥里嫩', '清淡，香脆', false);

-- 泰香园的菜品
INSERT INTO dishes (shop_id, name, price, description, flavor_tags, is_spicy) VALUES
(5, '冬阴功汤', 58.00, '泰式酸辣虾汤，香茅柠檬叶调味', '酸辣，鲜香', true),
(5, '绿咖喱鸡', 68.00, '椰奶绿咖喱炖鸡肉，配泰国香米', '香辣，椰香', true),
(5, '芒果糯米饭', 32.00, '泰国糯米配新鲜芒果，淋椰浆', '香甜', false);

-- 西域风情的菜品
INSERT INTO dishes (shop_id, name, price, description, flavor_tags, is_spicy) VALUES
(6, '大盘鸡', 78.00, '鸡肉土豆炖煮，配宽面条', '香辣，咸鲜', true),
(6, '烤羊肉串', 8.00, '新疆羊肉炭火烤制，撒孜然辣椒', '香辣，孜然', true),
(6, '手抓饭', 45.00, '羊肉胡萝卜焖饭，粒粒分明', '咸鲜，香浓', false);
