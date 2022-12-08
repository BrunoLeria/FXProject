
CREATE DATABASE IF NOT EXISTS `db_fluxo`;
USE `db_fluxo`;

CREATE TABLE IF NOT EXISTS `categoriascontas` (
  `ctc_codigo` int(11) NOT NULL,
  `ctc_descricao` varchar(255) NOT NULL,
  `ctc_positva` tinyint(1) NOT NULL,
  PRIMARY KEY (`ctc_codigo`)
);

CREATE TABLE IF NOT EXISTS `fluxocaixa` (
  `flc_codigo` int(11) NOT NULL,
  `flc_descricao` varchar(255) NOT NULL,
  `flc_data_ocorrencia` date NOT NULL DEFAULT current_timestamp(),
  `flc_valor` decimal(10,2) NOT NULL,
  `flc_forma_pagamento` int(11) NOT NULL,
  `flc_fk_ctc_codigo` int(11) NOT NULL,
  `flc_fk_sbc_codigo` int(11) DEFAULT NULL,
  PRIMARY KEY (`flc_codigo`),
  KEY `flc_fk_ctc_codigo` (`flc_fk_ctc_codigo`),
  KEY `flc_fk_sbc_codigo` (`flc_fk_sbc_codigo`)
);

CREATE TABLE IF NOT EXISTS `subcategorias` (
  `sbc_codigo` int(11) NOT NULL,
  `sbc_descricao` varchar(255) NOT NULL,
  `sbc_fk_ctc_codigo` int(11) NOT NULL,
  PRIMARY KEY (`sbc_codigo`),
  KEY `sbc_fk_ctc_codigo` (`sbc_fk_ctc_codigo`)
);


ALTER TABLE `fluxocaixa`
  ADD CONSTRAINT `fluxocaixa_ibfk_1` FOREIGN KEY (`flc_fk_ctc_codigo`) REFERENCES `categoriascontas` (`ctc_codigo`),
  ADD CONSTRAINT `fluxocaixa_ibfk_2` FOREIGN KEY (`flc_fk_sbc_codigo`) REFERENCES `subcategorias` (`sbc_codigo`);

ALTER TABLE `subcategorias`
  ADD CONSTRAINT `subcategorias_ibfk_1` FOREIGN KEY (`sbc_fk_ctc_codigo`) REFERENCES `categoriascontas` (`ctc_codigo`);