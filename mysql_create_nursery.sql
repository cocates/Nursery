CREATE DATABASE IF NOT EXISTS `nursery`;
USE `nursery`;

DROP TABLE IF EXISTS `orderlisting`;
DROP TABLE IF EXISTS `inventory`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
	employeeID INT NOT NULL AUTO_INCREMENT,				-- Unique employee number
	firstName VARCHAR(32) NOT NULL,						-- first name
    mInit CHAR(1) DEFAULT NULL,							-- middle initial
    lastName VARCHAR(32) NOT NULL,						-- last name
    address VARCHAR(128) NOT NULL,						-- street address
    city VARCHAR(32) NOT NULL,							-- city of residence
    phoneNumber VARCHAR(14) NOT NULL,					-- phone number
    email VARCHAR(45) DEFAULT NULL,						-- email
    wage DOUBLE(4, 2) NOT NULL,							-- hourly wage
	PRIMARY KEY (employeeID)
);	-- add job title attribute?
ALTER TABLE `employees`
AUTO_INCREMENT = 100;
INSERT INTO `employees` (firstName, mInit, lastName, address, city, phoneNumber, email, wage) 
VALUES ('Manager', 'A', 'McBoss', '123 Street North', 'Tacoma', '1-987-654-3210', 'boss@nursery.biz' , 30.00), 
    ('Hue', NULL, 'Man', '4231 Outer Space', 'Orting', '1-111-111-1111', 'alien@nursery.biz', 20.00),
    ('George', NULL, 'Patterson', '8730 25th Street East', 'Mars', '1-222-222-2222', 'georgepat@nursery.biz', 16.47),
    ('Employee', 'F' , 'Staff', '31413 Some Address West', 'Milton', '1-222-333-4444', 'employees@nursery.biz', 12.47),
    ('No', 'E', 'Mail', '13431 91st Street Southwest', 'Walla Walla', '1-999-888-7777', 'nomail@nursery.biz', 12.43),
    ('John', NULL, 'Doe', '1111 Warren Street', 'Roy', '1-360-720-1440', NULL, 9.47),
    ('Ben', NULL, 'Derisgreat', '5454 Some Place', 'Nowhere', '1-960-970-9876', 'bigben@nursery.biz', 18.34);

CREATE TABLE `customers` (
	customerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(32) NOT NULL,
    mInit CHAR(1) DEFAULT NULL,
    lastName VARCHAR(32) NOT NULL,
    address VARCHAR(128) NOT NULL,
    city VARCHAR(32) NOT NULL,
    phoneNumber VARCHAR(14) DEFAULT NULL,
    email VARCHAR(45) DEFAULT NULL
);
ALTER TABLE `customers`
AUTO_INCREMENT = 1000;
INSERT INTO `customers` -- (firstName, mInit, lastName, address, city, phoneNumber, email)
VALUES (NULL, 'John', NULL, 'Jones', '8472 Some Place NW', 'Port Townsend', '1-888-765-4321', 'jojo@landscape.biz'),
	(NULL, 'Steve', 'P', 'Stewart', '4297 Southwest 334th Street', 'Tacoma', '1-253-431-7139', 'steves@landscape.biz'),
	(NULL, 'Jack', 'O', 'Lantern', '835 Pacific Avenue South', 'Spanaway', '1-222-222-2222', 'jacksonlantern@halloween.biz'),
    (NULL, 'Joan', 'M', 'Soa', '5742 128th Street East', 'Tacoma', '1-333-333-3333', 'joans@user.com'),
	(NULL, 'Test', NULL, 'McBrief', 'Test Street South', 'Testington', NULL, NULL);

CREATE TABLE `orders` (
	orderID INT AUTO_INCREMENT PRIMARY KEY,
	employeeID INT NOT NULL,
	customerID INT DEFAULT NULL,
    CONSTRAINT ordersIntoEmployeeFK FOREIGN KEY (employeeID) REFERENCES employees (employeeID),
 	CONSTRAINT ordersIntoCustomerFK FOREIGN KEY (customerID) REFERENCES customers (customerID) ON DELETE SET NULL
);	-- possibly add a total cost attribute?
ALTER TABLE `orders`
AUTO_INCREMENT = 1000;
INSERT INTO `orders` (employeeID, customerID)
VALUES (100, 1000),
	(100, 1001),
    (101, 1000),
    (101, 1001),
    (102, 1000),
    (102, 1001);
INSERT INTO `orders` (employeeID)
 VALUES (100);
 -- , (101), (102), (103), (104), (105), (106); 

CREATE TABLE `inventory` (
	itemid INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(64) NOT NULL,
    description VARCHAR(128) DEFAULT NULL,
    price DOUBLE(8, 2) NOT NULL,
    stock INT DEFAULT 0,
    greenHouseStock INT DEFAULT 0
);	-- add hyperlink or blob attribute for pictures?
INSERT INTO `inventory` (`name`, stock, greenHouseStock, price, description)
VALUES ('Encore Azalea Autumn Ivory', 16, 0, 39.98, 'Flowering shrub. Blooms white in the spring, summer, and fall.'),
	('Wintergreen Boxwood', 12, 0, 39.99, 'Green shrub. Popular for hedges and landscape design.'),
    ('Dwarf Burford Holly', 5, 8, 34.99, 'Green shrub.'),
    ('Freeman\'s Maple', 10, 0, 49.99, 'Hybrid maple tree. Hybrid of red maple and silver maple.'),
    ('Little Gem Dwarf Southern Magnolia', 7, 4, 49.99, 'Broad leaf evergreen tree. Naturally a small tree or large shrub.'),
    ('Leyland Cypress', 8, 0, 36.99, 'Tall, slender evergreen tree. Popularly used as a windbreak or on boundary lines.'),
    ('Japanese Flowering Cherry', 7, 0, 89.99, 'Also known as the Yoshino cherry. Known for its vibrant white-pink blossoms.'),
    ('Douglasfir', 3, 3, 19.97, 'Coniferous evergreen tree. Popularly used as Christmas trees.'),
    ('Kousa Dogwood', 5, 0, 89.99, 'Dogwood tree. Blossoms white in the late spring.'),
    ('River Birch', 6, 0, 89.99, 'Naturally grows along river banks but commonly used as a landscape tree. Relatively quick growth.'),
    ('Marilee Crabapple', 4, 0, 61.99, 'Narrow upright growth. Unusually large white blossoms.'),
    ('Golden Harvest Crabapple', 3, 0, 89.99, 'Dark grene foliage. White blossoms and yellow fruit.'),
    ('Carolina Sapphire Cypress', 2, 1, 44.98, 'Conical evergreen tree. Silvery blue colored foliage.'),
    ('Blue Point Juniper', 5, 0, 97.98, 'Abnormally hardy juniper. Dense blue green foliage year round.'),
    ('Fuji Apple Tree', 5, 1, 29.96, 'Popular dessert apple tree. Fuji apples have a mild, sweet flavor.'),
    ('Patriot Blueberry Shrub', 8, 2, 7.98, 'Fruit bearing potted shrub.'),
    ('Keiffer Pear Tree', 5, 1, 29.98, 'A hardy pear tree. Resistant to weather and disease. Produces fruit from late September into October.'),
    ('Burbank Plum Fruit Tree', 4, 0, 89.99, 'Produces a popular variety of plums. A common choice for home orchards.'),
    ('Blue Laurentia', 18, 0, 5.99, 'Light violet flowers. Blooms in the spring. Not for the faint of heart.'),
    ('Brand Name Mulch', 50, 0, 3.67, 'A bag of landscaping mulch. Two cubic feet of mulch per bag.'),
    ('Oxygrip Gardening Set', 14, 0, 32.99, 'Hand tools for maintaining your garden. Now sporting new patented grip technology.'),
    ('Never Break Water Hose', 5, 0, 39.99, 'Guarunteed to never break in your lifetime. Probably.'),
    ('Luxury Garden Planter', 4, 0, 999.99, 'The biggest planter money can buy. Locally. Probably.'),
    ('Lawn Mower 9000 Deluxe', 2, 0, 999999.99, 'You could probably mow a mountain with this.');

CREATE TABLE `orderlisting` (
	orderID INT NOT NULL,
    itemID INT NOT NULL,
    quantity INT NOT NULL,
--    orderDate DATE,
    PRIMARY KEY (orderID, itemID),
    FOREIGN KEY (orderID)
		REFERENCES orders(orderID),
	FOREIGN KEY (itemID)
		REFERENCES inventory(itemID)
);
INSERT INTO `orderlisting` (orderID, itemID, quantity)
VALUES (1000, 10, 2),
	(1000, 7, 3),
    (1000, 11, 3),
    (1000, 13, 1),
    (1000, 18, 1),
    (1001, 3, 1),
    (1001, 4, 7),
    (1001, 9, 10),
    (1001, 19, 5),
    (1002, 6, 4),
    (1002, 15, 3),
    (1002, 19, 1),
    (1003, 3, 3),
    (1004, 12, 7),
    (1004, 1, 2),
    (1004, 5, 1),
    (1004, 8, 3),
    (1004, 14, 7),
    (1004, 20, 20),
    (1005, 15, 2),
    (1006, 7, 1);


-- POSSIBLE FUNCTIONS
/*
DROP FUNCTION IF EXISTS `startOrder`;
DELIMITER *
CREATE FUNCTION `startOrder`(employee INT, customer INT)
RETURNS INT -- Returns the orderID of the new order
BEGIN
	INSERT INTO `orders` (employeeID, customerID)
	VALUE(employee, customer);
    
END *
DELIMITER ;

DROP FUNCTION IF EXISTS `addItemToOrder`;
DELIMITER *
CREATE FUNCTION `addItemToOrder`(orderNumber INT, itemNumber INT)
RETURNS DECIMAL(8,2)
BEGIN
	DECLARE temp INT;
    SELECT quantity INTO temp FROM orderlisting WHERE itemID = itemNumber AND orderID = orderNumber;
    IF (temp IS NULL) THEN CALL createListing(orderNumber, itemNumber); END IF;
	UPDATE orderlisting 
    SET quantity = quantity + 1 
    WHERE orderID = orderNumber AND itemID = itemNumber;
	RETURN (SELECT price
			FROM inventory
            WHERE itemID = itemNumber);
END *
DELIMITER ;

DROP PROCEDURE IF EXISTS `setCustomer`;
DELIMITER *
CREATE PROCEDURE `setCustomer`(customer INT, `order` INT)
BEGIN
	UPDATE orders
    SET customerID = customer
    WHERE orderID = `order`;
END *
DELIMITER ;

*/