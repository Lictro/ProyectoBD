TYPE=VIEW
query=select `abc`.`afiliados`.`ID_Afiliado` AS `ID_Afiliado`,`abc`.`afiliados`.`Contraseña` AS `Contraseña`,`abc`.`afiliados`.`Primer_Nombre` AS `Primer_Nombre`,`abc`.`afiliados`.`Segundo_Nombre` AS `Segundo_Nombre`,`abc`.`afiliados`.`Primer_Apellido` AS `Primer_Apellido`,`abc`.`afiliados`.`Segundo_Apellido` AS `Segundo_Apellido`,`abc`.`afiliados`.`Telefono` AS `Telefono`,`abc`.`afiliados`.`Direccion` AS `Direccion`,`abc`.`afiliados`.`Correo_Primario` AS `Correo_Primario`,`abc`.`afiliados`.`Correo_Secundario` AS `Correo_Secundario`,`abc`.`afiliados`.`Fecha_Nacimiento` AS `Fecha_Nacimiento`,`abc`.`afiliados`.`Fecha_Inicio` AS `Fecha_Inicio`,`abc`.`afiliados`.`Activo` AS `Activo` from `abc`.`afiliados` where (`abc`.`afiliados`.`Activo` = 1)
md5=4b6126401b41a0135ee2b8b709359485
updatable=1
algorithm=0
definer_user=root
definer_host=localhost
suid=2
with_check_option=0
timestamp=2017-02-27 14:22:01
create-version=2
source=SELECT * FROM AFILIADOS WHERE Activo=1
client_cs_name=utf8mb4
connection_cl_name=utf8mb4_general_ci
view_body_utf8=select `abc`.`afiliados`.`ID_Afiliado` AS `ID_Afiliado`,`abc`.`afiliados`.`Contraseña` AS `Contraseña`,`abc`.`afiliados`.`Primer_Nombre` AS `Primer_Nombre`,`abc`.`afiliados`.`Segundo_Nombre` AS `Segundo_Nombre`,`abc`.`afiliados`.`Primer_Apellido` AS `Primer_Apellido`,`abc`.`afiliados`.`Segundo_Apellido` AS `Segundo_Apellido`,`abc`.`afiliados`.`Telefono` AS `Telefono`,`abc`.`afiliados`.`Direccion` AS `Direccion`,`abc`.`afiliados`.`Correo_Primario` AS `Correo_Primario`,`abc`.`afiliados`.`Correo_Secundario` AS `Correo_Secundario`,`abc`.`afiliados`.`Fecha_Nacimiento` AS `Fecha_Nacimiento`,`abc`.`afiliados`.`Fecha_Inicio` AS `Fecha_Inicio`,`abc`.`afiliados`.`Activo` AS `Activo` from `abc`.`afiliados` where (`abc`.`afiliados`.`Activo` = 1)
mariadb-version=100121
