TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `TR_Retiros_BI` BEFORE INSERT ON `retiros` FOR EACH ROW BEGIN\n	IF MONTH(NOW())!=12 THEN\n		SIGNAL SQLSTATE \'45000\' SET MESSAGE_TEXT = \'Aun no es diciembre\';\n	END IF;\nEND'
sql_modes=1075838976
definers='root@localhost'
client_cs_names='utf8mb4'
connection_cl_names='utf8mb4_general_ci'
db_cl_names='utf8_general_ci'
