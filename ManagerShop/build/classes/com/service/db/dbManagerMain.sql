
USE is208;

CREATE TABLE `List`
(
    idList INT AUTO_INCREMENT PRIMARY KEY,
    nameList VARCHAR(255) NOT NULL
);

CREATE TABLE Size
(
    idSize INT AUTO_INCREMENT PRIMARY KEY,
    valueSize VARCHAR(5) NOT NULL
);

CREATE TABLE Color
(
    idColor INT AUTO_INCREMENT PRIMARY KEY,
    valueColor VARCHAR(5) NOT NULL
);

CREATE TABLE Material
(
    idMaterial INT AUTO_INCREMENT PRIMARY KEY,
    valueMaterial VARCHAR(5) NOT NULL
);

CREATE TABLE `User`
(
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    birthday DATE,
    gender BOOLEAN,
    phoneNumber VARCHAR(15),
    email VARCHAR(255),
    `address` VARCHAR(255),
    salary DECIMAL(10, 2),
    `role` BOOLEAN,
    `status` BOOLEAN
);

CREATE TABLE Customer
(
    idCustomer INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    gender BOOLEAN,
    `address` VARCHAR(255) NOT NULL
);

CREATE TABLE Account
(
    idAccount INT AUTO_INCREMENT PRIMARY KEY,
    idUser INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    FOREIGN KEY (idUser) REFERENCES `User`(idUser)
);

CREATE TABLE Voucher
(
    idVoucher INT AUTO_INCREMENT PRIMARY KEY,
    valueVoucher VARCHAR(255),
    `value` DOUBLE,
    dateStart DATE,
    dateEnd DATE,
    quatity INT
);

CREATE TABLE Invoice
(
    idInvoice INT AUTO_INCREMENT PRIMARY KEY,
    idCustomer INT NOT NULL,
    idEmpolyee INT NOT NULL,
    idVoucher INT,
    dateCreateInvoice DATE,
    statusInvoice BOOLEAN,
    statusPay BOOLEAN,
    FOREIGN KEY (idCustomer) REFERENCES Customer(idCustomer),
    FOREIGN KEY (idEmpolyee) REFERENCES `User`(idUser),
    FOREIGN KEY (idVoucher) REFERENCES Voucher(idVoucher)
);

CREATE TABLE detailsProduct
(
    idPrDeltails INT AUTO_INCREMENT PRIMARY KEY,
    idProduct INT,
    idSize INT,
    idColor INT,
    idMaterial INT,
    price DECIMAL(10, 2),
    sku VARCHAR(255),
    quatity INT,
    `status` BOOLEAN,
    FOREIGN KEY (idSize) REFERENCES Size(idSize),
    FOREIGN KEY (idColor) REFERENCES Color(idColor),
    FOREIGN KEY (idMaterial) REFERENCES Material(idMaterial)
);

CREATE TABLE Supplier
(
    idSupplier INT AUTO_INCREMENT PRIMARY KEY,
    nameMaterial VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    `address` VARCHAR(255)
);

CREATE TABLE detailsInvoice
(
    idInvoice INT,
    idPrDeltails INT,
    detailsInvoice INT,
    quatity INT,
    `status` BOOLEAN,
    idSupplier INT,
    PRIMARY KEY (idInvoice, idPrDeltails),
    FOREIGN KEY (idInvoice) REFERENCES Invoice(idInvoice),
    FOREIGN KEY (idPrDeltails) REFERENCES detailsProduct(idPrDeltails),
    FOREIGN KEY (idSupplier) REFERENCES Supplier(idSupplier)
);

CREATE TABLE Products
(
    idProduct INT AUTO_INCREMENT PRIMARY KEY,
    idSupplier INT NOT NULL,
    idList INT NOT NULL,
    nameProduct VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `status` BOOLEAN,
    FOREIGN KEY (idSupplier) REFERENCES Supplier(idSupplier),
    FOREIGN KEY (idList) REFERENCES `List`(idList)
);

CREATE TABLE ImageProducts
(
    idImage INT AUTO_INCREMENT PRIMARY KEY,
    idPrDeltails INT,
    valueImage VARCHAR(255) NOT NULL,
    FOREIGN KEY (idPrDeltails) REFERENCES detailsProduct(idPrDeltails)
);