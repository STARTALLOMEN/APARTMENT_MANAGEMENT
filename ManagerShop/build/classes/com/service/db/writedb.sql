ALTER TABLE Material MODIFY COLUMN valueMaterial VARCHAR(50) NOT NULL;
ALTER TABLE Color MODIFY COLUMN valueColor VARCHAR(50) NOT NULL;

ALTER TABLE Products ADD COLUMN statusDelete BOOLEAN DEFAULT 1;

DELIMITER $$
DROP PROCEDURE IF EXISTS PRDelete$$
CREATE PROCEDURE PRDelete(IN idPrDetails INT)
BEGIN
    START TRANSACTION;
    DELETE FROM ImageProducts
    WHERE idPrDeltails IN (SELECT idPrDeltails FROM ImageProducts WHERE idPrDetails = idPrDeltails);
    DELETE FROM detailsProduct
    WHERE idPrDeltails IN (SELECT idPrDeltails FROM detailsProduct WHERE idPrDetails = idPrDeltails);
    COMMIT;
END$$
DELIMITER ;



CREATE TABLE InvoiceSell
(
    idInvoiceSell INT AUTO_INCREMENT PRIMARY KEY,
    idCustomer INT,
    idHumanSell INT,
    idVoucher INT,
    dateCreateInvoice DATE,
    `description` VARCHAR(255),
    statusPay BOOLEAN,
    statusInvoice BOOLEAN,
    FOREIGN KEY (idCustomer) REFERENCES Customer(idCustomer),
    FOREIGN KEY (idHumanSell) REFERENCES `User`(idUser),
    FOREIGN KEY (idVoucher) REFERENCES Voucher(idVoucher)
);

CREATE TABLE detailsInvoiceSELL
(
    idDetailsInvoiceSELL INT AUTO_INCREMENT PRIMARY KEY,
    idInvoiceSell INT,
    idPrDetails INT,
    quatity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (idInvoiceSell) REFERENCES InvoiceSell(idInvoiceSell),
    FOREIGN KEY (idPrDetails) REFERENCES detailsProduct(idPrDeltails)
);

ALTER TABLE InvoiceSell ADD COLUMN totalMoney DECIMAL(10, 2);

CREATE TABLE InvoiceReturn
(
    idInvoiceReturn INT AUTO_INCREMENT PRIMARY KEY,
    idInvoiceSell INT,
    idCustomer INT,
    `description` VARCHAR(255),
    totalReturn DECIMAL(10, 2),
    FOREIGN KEY (idInvoiceSell) REFERENCES InvoiceSell(idInvoiceSell),
    FOREIGN KEY (idCustomer) REFERENCES Customer(idCustomer)
);

ALTER TABLE InvoiceReturn ADD COLUMN idUser INT;
ALTER TABLE InvoiceReturn ADD CONSTRAINT FOREIGN KEY (idUser) REFERENCES `User`(idUser);

ALTER TABLE InvoiceReturn ADD COLUMN dateCreateInvoice DATETIME;

CREATE TABLE DetailInvoiceReturn
(
    idDetailInvoiceReturn INT AUTO_INCREMENT PRIMARY KEY,
    idInvoiceReturn INT,
    idPrDetails INT,
    quatity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (idInvoiceReturn) REFERENCES InvoiceReturn(idInvoiceReturn),
    FOREIGN KEY (idPrDetails) REFERENCES detailsProduct(idPrDeltails)
);

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_statistical$$
CREATE PROCEDURE sp_statistical(IN p_year INT, IN p_month INT)
BEGIN
    SELECT Products.idProduct, nameProduct, SUM(detailsInvoiceSELL.quatity) AS quantitySell
    FROM detailsProduct
    JOIN detailsInvoiceSELL ON detailsInvoiceSELL.idPrDetails = detailsProduct.idPrDeltails
    JOIN InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
    JOIN Products ON Products.idProduct = detailsProduct.idProduct
    WHERE YEAR(dateCreateInvoice) = p_year AND MONTH(dateCreateInvoice) = p_month
    GROUP BY Products.idProduct, nameProduct
    ORDER BY quantitySell DESC;
END$$

DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_revenue$$
CREATE PROCEDURE sp_revenue(IN p_year INT)
BEGIN
    SELECT MONTH(InvoiceSell.dateCreateInvoice) AS MonthDate, SUM(detailsInvoiceSELL.quatity) AS quantity,
           CAST(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) AS SIGNED) AS totalSell,
           CAST(SUM(COALESCE(totalReturn, 0)) AS SIGNED) AS totalReturn,
           CAST(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) - SUM(COALESCE(totalReturn, 0)) AS SIGNED) AS revenue
    FROM detailsInvoiceSELL
    JOIN InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell
    LEFT JOIN InvoiceReturn ON InvoiceReturn.idInvoiceSell = InvoiceSell.idInvoiceSell
    WHERE YEAR(InvoiceSell.dateCreateInvoice) = p_year
    GROUP BY MONTH(InvoiceSell.dateCreateInvoice);
END$$
DELIMITER ;

ALTER TABLE InvoiceImportPr MODIFY COLUMN dateCreateInvoice DATETIME;
ALTER TABLE InvoiceReturn MODIFY COLUMN dateCreateInvoice DATETIME;
ALTER TABLE InvoiceSell MODIFY COLUMN dateCreateInvoice DATETIME;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_Quantity$$
CREATE PROCEDURE sp_Quantity()
BEGIN
    SELECT `name`, IF(gender = 0, 'Nữ', 'Nam') AS gender, phoneNumber, SUM(quatity) AS SumBuy
    FROM Customer
    JOIN InvoiceSell ON InvoiceSell.idCustomer = Customer.idCustomer
    JOIN detailsInvoiceSELL ON detailsInvoiceSELL.idInvoiceSell = InvoiceSell.idInvoiceSell
    GROUP BY `name`, gender, phoneNumber;
END$$
DELIMITER ;

CREATE TABLE InvoiceChangeProducts
(
    idInvoiceChangeProducts INT AUTO_INCREMENT PRIMARY KEY,
    idCustomer INT,
    idInvoiceSell INT,
    dateCreateInvoice DATETIME,
    idUser INT,
    `description` VARCHAR(255),
    FOREIGN KEY (idCustomer) REFERENCES Customer(idCustomer),
    FOREIGN KEY (idInvoiceSell) REFERENCES InvoiceSell(idInvoiceSell),
    FOREIGN KEY (idUser) REFERENCES `User`(idUser)
);

ALTER TABLE InvoiceSell ADD COLUMN moneyCustom DECIMAL(10, 2);
ALTER TABLE InvoiceSell ADD COLUMN moneyReturn DECIMAL(10, 2);

CREATE TABLE DetailsInvoiceChange
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    idInvoiceChangeProducts INT,
    idDetailsPr INT,
    quantity INT,
    FOREIGN KEY (idInvoiceChangeProducts) REFERENCES InvoiceChangeProducts(idInvoiceChangeProducts),
    FOREIGN KEY (idDetailsPr) REFERENCES detailsProduct(idPrDeltails)
);

CREATE TABLE DetailsChangeProducts
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    idDetailsPr INT,
    idDetailsInvoiceChange INT,
    quantity INT,
    FOREIGN KEY (idDetailsInvoiceChange) REFERENCES DetailsInvoiceChange(id),
    FOREIGN KEY (idDetailsPr) REFERENCES detailsProduct(idPrDeltails)
);

INSERT INTO `User`
(
    `name`,
    birthday,
    gender,
    phoneNumber,
    `address`,
    salary,
    `role`,
    `status`,
    email
)
VALUES
(
    'Nguyễn Văn Đức',
    '2002-09-25',
    1,
    '0332429178',
    'Hà Nội',
    3000000,
    1,
    1,
    'ducit2509@gmail.com'
);

INSERT INTO Account
(
    idUser,
    username,
    `password`
)
VALUES
(
    (SELECT idUser FROM `User` ORDER BY idUser DESC LIMIT 1),
    'admin',
    '123'
);