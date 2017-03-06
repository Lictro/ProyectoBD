TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `TR_Cuentas_AI` AFTER INSERT ON `cuentas` FOR EACH ROW BEGIN\n	INSERT INTO cuentas_log(Num_Cuenta,ID_Afiliado,Saldo,Fecha_Hora,Tipo)\n	VALUES(NEW.Num_Cuenta,NEW.ID_Afiliado,NEW.Saldo,NOW(),NEW.Tipo);\nEND' 'CREATE DEFINER=`root`@`localhost` TRIGGER `TR_Cuentas_AU` AFTER UPDATE ON `cuentas` FOR EACH ROW BEGIN\n	INSERT INTO cuentas_log(Num_Cuenta,ID_Afiliado,Saldo,Fecha_Hora,Tipo)\n	VALUES(NEW.Num_Cuenta,NEW.ID_Afiliado,NEW.Saldo,NOW(),NEW.Tipo);\nEND'
sql_modes=1075838976 1075838976
definers='root@localhost' 'root@localhost'
client_cs_names='utf8mb4' 'utf8mb4'
connection_cl_names='utf8mb4_general_ci' 'utf8mb4_general_ci'
db_cl_names='utf8_general_ci' 'utf8_general_ci'
