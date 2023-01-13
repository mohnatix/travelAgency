DROP DATABASE IF EXISTS travel_agency;
CREATE DATABASE IF NOT EXISTS travel_agency DEFAULT CHARACTER SET UTF8MB4 ;
USE travel_agency;


DROP TABLE IF EXISTS role;

CREATE TABLE IF NOT EXISTS role (
  id INT UNSIGNED NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT CHECK(name!='')
  );



DROP TABLE IF EXISTS user_status;

CREATE TABLE IF NOT EXISTS user_status(
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT CHECK(status!='')
   );



DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  surname VARCHAR(255) NOT NULL,
  role_id INT UNSIGNED NOT NULL REFERENCES role(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  user_status_id INT UNSIGNED NOT NULL REFERENCES user_status(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT CHECK((login!='')AND(password!='')AND(email LIKE'%@%.%')AND(name!='')AND(surname!=''))
  );


DROP TABLE IF EXISTS accommodation;

CREATE TABLE IF NOT EXISTS accommodation (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT CHECK(type!='')
  );


DROP TABLE IF EXISTS tour_type;

CREATE TABLE IF NOT EXISTS tour_type (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(255) NOT NULL UNIQUE, 
  CONSTRAINT CHECK(type!='')
  );


DROP TABLE IF EXISTS tour_status;

CREATE TABLE IF NOT EXISTS tour_status (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT CHECK(status!='')
  );


DROP TABLE IF EXISTS tour ;

CREATE TABLE IF NOT EXISTS tour (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  tour_type_id INT UNSIGNED NOT NULL REFERENCES tour_type(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  price DECIMAL(10,2) NOT NULL,
  persons_number INT UNSIGNED NOT NULL,
  duration INT UNSIGNED NOT NULL,
  accommodation_id INT UNSIGNED NOT NULL REFERENCES accommodation(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  image_name VARCHAR(255) NOT NULL  UNIQUE,
  tour_status_id INT UNSIGNED NOT NULL REFERENCES tour_status(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT CHECK((name!='')AND(price>=0)AND(persons_number>0)AND(duration>0)AND(image_name LIKE'%.%'))
);



DROP TABLE IF EXISTS order_status ;

CREATE TABLE IF NOT EXISTS order_status (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT CHECK(status!='')
 );



DROP TABLE IF EXISTS user_order ;

CREATE TABLE IF NOT EXISTS user_order (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  tour_id INT UNSIGNED NOT NULL REFERENCES tour(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  price_fixed DECIMAL(10,2) NOT NULL,
  discount_fixed INT UNSIGNED NOT NULL,
  order_status_id INT UNSIGNED NOT NULL REFERENCES order_status(id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT CHECK((price_fixed>=0)AND(discount_fixed>=0)AND(discount_fixed<=100) )
    );


DROP TABLE IF EXISTS discount ;

CREATE TABLE IF NOT EXISTS discount (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  step INT UNSIGNED NOT NULL ,
  max INT UNSIGNED NOT NULL,
  CONSTRAINT CHECK((step>0) AND (max>0) AND (max <= 100) AND (max >= step) AND (max%step=0))
   );

INSERT INTO role VALUES (DEFAULT, 'customer');
INSERT INTO role VALUES (DEFAULT, 'manager');
INSERT INTO role VALUES (DEFAULT, 'administrator');

INSERT INTO user_status VALUES (DEFAULT, 'unblocked');
INSERT INTO user_status VALUES (DEFAULT, 'blocked');

INSERT INTO user VALUES (DEFAULT, 'manager', 'managerpass', 'manager@gmail.com', 'Максим', 'Перебийніс', 2, 1);
INSERT INTO user VALUES (DEFAULT, 'admin', 'adminpass', 'admin@gmail.com', 'Андрій', 'Чумак', 3, 1);
INSERT INTO user VALUES (DEFAULT, 'customer1', 'customer1pass', 'customer1@gmail.com', 'John', 'Doe', 1, 2);
INSERT INTO user VALUES (DEFAULT, 'customer2', 'customer2pass', 'customer2@gmail.com', 'Jane', 'Doe', 1, 1);
INSERT INTO user VALUES (DEFAULT, 'customer3', 'customer3pass', 'customer3@gmail.com', 'John', 'Black', 1, 1);
INSERT INTO user VALUES (DEFAULT, 'customer4', 'customer4pass', 'customer4@gmail.com', 'Jane', 'Black', 1, 1);
INSERT INTO user VALUES (DEFAULT, 'customer5', 'customer5pass', 'customer5@gmail.com', 'John', 'White', 1, 1);
INSERT INTO user VALUES (DEFAULT, 'customer6', 'customer6pass', 'customer6@gmail.com', 'Jane', 'White', 1, 1);
INSERT INTO user VALUES (DEFAULT, 'customer7', 'customer7pass', 'customer7@gmail.com', 'Іван', 'Іванов', 1, 1);
INSERT INTO user VALUES (DEFAULT, 'customer8', 'customer8pass', 'customer8@gmail.com', 'Іванна', 'Іванова', 1, 1);

INSERT INTO accommodation VALUES (DEFAULT, 'tent');
INSERT INTO accommodation VALUES (DEFAULT, 'hostel');
INSERT INTO accommodation VALUES (DEFAULT, '1-star hotel');
INSERT INTO accommodation VALUES (DEFAULT, '2-star hotel');
INSERT INTO accommodation VALUES (DEFAULT, '3-star hotel');
INSERT INTO accommodation VALUES (DEFAULT, '4-star hotel');
INSERT INTO accommodation VALUES (DEFAULT, '5-star hotel');

INSERT INTO tour_type VALUES (DEFAULT, 'rest');
INSERT INTO tour_type VALUES (DEFAULT, 'excursion');
INSERT INTO tour_type VALUES (DEFAULT, 'shopping');
INSERT INTO tour_type VALUES (DEFAULT, 'active');

INSERT INTO tour_status VALUES (DEFAULT, 'hot');
INSERT INTO tour_status VALUES (DEFAULT, 'ordinary');
INSERT INTO tour_status VALUES (DEFAULT, 'archived');


INSERT INTO tour VALUES (DEFAULT, 'SVYDOVETS: TREKKING IN THE LAND OF MOUNTAIN LAKES', 4, 616.00, 8, 4, 1, "svydovets.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'BORZHAVA: TREKKING IN TRANSCARPATHIA', 4, 896.00, 8, 5, 1, "borzhava.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'RAFTING ON THE CHEREMOSH RIVER: CARPATHIAN RODEO', 4, 448.00, 4, 3, 1, "cheremosh.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'RAFTING ON THE DNIESTER: WEEK IN THE CANYON', 4, 1384.00, 8, 7, 1, "dniester.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'MLYNKY CAVE: CRAZY WEEKEND', 4, 392.00, 8, 2, 2, "mlynky.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'DEAD SEA: COMPLETE RELAXATION', 1, 1500.00, 2, 7, 7, "deadSea.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'SKHIDNYTSIA: WATER RETRIAT', 1, 200.00, 2, 7, 3, "skhidnytsya.jpg",1 );
INSERT INTO tour VALUES (DEFAULT, 'MILAN: TOUR FOR SHOPPING JUNKIE', 3, 1200.00, 2, 5, 6, "milan.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'KHMELNYTSKYI: CRAZY SHOPPING WEEKEND', 3, 30.00, 2, 2, 2, "hmelnytsk.jpg",1 );
INSERT INTO tour VALUES (DEFAULT, 'LUTSK: CASTLE WEEKEND', 2, 130.00, 2, 2, 4, "lutsk.jpg",3 );
INSERT INTO tour VALUES (DEFAULT, 'ROME: DIVE INTO ANTIENT HISTORY', 2, 1700.00, 2, 5, 5, "rome.jpg",2 );
INSERT INTO tour VALUES (DEFAULT, 'AMSTERDAM: BIKES AND CANALS', 2, 900.00, 2, 3, 4, "amsterdam.jpg",2 );

INSERT INTO order_status  VALUES (DEFAULT, 'registered');
INSERT INTO order_status  VALUES (DEFAULT, 'paid');
INSERT INTO order_status  VALUES (DEFAULT, 'cancelled');



INSERT INTO discount VALUES (DEFAULT, 'loyalty', 2, 10);

/*
SELECT tour.name, tour_status.status as status FROM tour LEFT JOIN tour_status ON tour.tour_status_id=tour_status.id LEFT JOIN tour_type ON tour.tour_type_id=tour_type.id LEFT JOIN accommodation ON tour.accommodation_id=accommodation.id where tour.tour_status_id='3' ORDER BY tour_status.id ASC;
SELECT tour.id AS id, tour.name AS name, tour_type.type AS tour_type, tour.price AS price, 
tour.persons_number AS persons_number, tour.duration AS duration, 
accommodation.type AS accommodation, tour.image_name as image_name, 
tour_status.status AS tour_status from tour 
LEFT JOIN tour_status ON tour.tour_status_id=tour_status.id 
LEFT JOIN tour_type ON tour.tour_type_id=tour_type.id 
LEFT JOIN accommodation ON tour.accommodation_id=accommodation.id  
order by tour.tour_status_id ASC, persons_number ASC;

select * from user;
SELECT user.id AS id, user.login AS login, user.password AS password, user.email AS email, user.name AS name, user.surname AS surname,
role.name AS role, user_status.status as user_status from user
LEFT JOIN role ON user.role_id=role.id 
LEFT JOIN user_status ON user.user_status_id=user_status.id 
WHERE login='customer1' AND password='customer1pass';
select * from role;
select * from user;
update tour, tour_status set  tour.tour_status_id=tour_status.id where tour.id=10 AND tour_status.status="archived";

update tour set  tour.name='SKHIDNYTSIA: WATER RETRIAT' where tour.id=7;
select * from tour;
SELECT user.id AS id, user.login AS login, user.password AS password, 
					user.email AS email, user.name AS name, user.surname AS surname, 
					role.name AS role, user_status.status as user_status from user 
					LEFT JOIN role ON user.role_id=role.id 
					LEFT JOIN user_status ON user.user_status_id=user_status.id ;
select * from order_status;
select COUNT(*) from user_order left join order_status on user_order.order_status_id=order_status.id  where user_order.user_id=5 AND order_status.status='paid';
select discount.step from discount where discount.id = 1;
select discount.max from discount where discount.id = 1;
insert into user_order values (DEFAULT, 5, 7, (select tour.price from tour where tour.id =7), 0 , 1);
select * from user_order;
*/