CREATE TABLE `academia` (
  `aca_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `aca_nome` varchar(60) NOT NULL,
  `aca_endereco` varchar(60) NOT NULL,
  `aca_fone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`aca_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `cliente` (
  `cli_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `cli_nome` varchar(99) NOT NULL,
  `cli_endereco` varchar(99) NOT NULL,
  `cli_data_nasc` date NOT NULL,
  `cli_sexo` char(1) DEFAULT NULL,
  `cli_necessidade` varchar(20) DEFAULT NULL,
  `cli_tra_codigo` int(11) NOT NULL,
  PRIMARY KEY (`cli_codigo`),
  KEY `cli_tra_codigo` (`cli_tra_codigo`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`cli_tra_codigo`) REFERENCES `trainer` (`tra_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `equipamento` (
  `equ_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `equ_nome` varchar(60) NOT NULL,
  `equ_pesomax` double NOT NULL,
  `equ_descricao` varchar(60) DEFAULT NULL,
  `equ_aca_codigo` int(11) DEFAULT NULL,
  PRIMARY KEY (`equ_codigo`),
  KEY `equ_aca_codigo` (`equ_aca_codigo`),
  CONSTRAINT `equipamento_ibfk_1` FOREIGN KEY (`equ_aca_codigo`) REFERENCES `academia` (`aca_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `exercicio` (
  `exe_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `exe_nome` varchar(99) NOT NULL,
  `exe_descricao` varchar(99) NOT NULL,
  `exe_equ_codigo` int(11) NOT NULL,
  PRIMARY KEY (`exe_codigo`),
  KEY `exe_equ_codigo` (`exe_equ_codigo`),
  CONSTRAINT `exercicio_ibfk_1` FOREIGN KEY (`exe_equ_codigo`) REFERENCES `equipamento` (`equ_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `progresso` (
  `pro_cli_codigo` int(11) NOT NULL,
  `pro_data` date NOT NULL,
  `pro_cli_massa` double NOT NULL,
  `pro_cli_altura` double NOT NULL,
  KEY `pro_cli_codigo` (`pro_cli_codigo`),
  CONSTRAINT `progresso_ibfk_1` FOREIGN KEY (`pro_cli_codigo`) REFERENCES `cliente` (`cli_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `trainer` (
  `tra_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `tra_nome` varchar(99) NOT NULL,
  `tra_data_nasc` date NOT NULL,
  `tra_sexo` char(1) NOT NULL,
  `tra_aca_codigo` int(11) NOT NULL,
  PRIMARY KEY (`tra_codigo`),
  KEY `tra_aca_codigo` (`tra_aca_codigo`),
  CONSTRAINT `trainer_ibfk_1` FOREIGN KEY (`tra_aca_codigo`) REFERENCES `academia` (`aca_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `treino` (
  `tre_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `tre_vencimento` date NOT NULL,
  `tre_cli_codigo` int(11) NOT NULL,
  `tre_exe_codigo` int(11) NOT NULL,
  PRIMARY KEY (`tre_codigo`),
  KEY `tre_cli_codigo` (`tre_cli_codigo`),
  KEY `tre_exe_codigo` (`tre_exe_codigo`),
  CONSTRAINT `treino_ibfk_1` FOREIGN KEY (`tre_cli_codigo`) REFERENCES `cliente` (`cli_codigo`),
  CONSTRAINT `treino_ibfk_2` FOREIGN KEY (`tre_exe_codigo`) REFERENCES `exercicio` (`exe_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

