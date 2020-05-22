create database utnphones;
use utnphones;
-- drop database utnphones;
SET GLOBAL time_zone =  '-3:00';
select * from users;

create table provinces(
	id_province int auto_increment primary key,
	province_name varchar(50)
);

create table cities(
	id_city int not null auto_increment primary key,
	city_name varchar(50),
	prefix int,
	id_province_fk int,
	constraint fk_province foreign key(id_province_fk) references provinces(id_province)
);

create table rates(
	id_rate int not null auto_increment primary key,
	price_per_min decimal(12,2),
	cost_per_min decimal(12,2),
	id_city_from_fk int,
	id_city_to_fk int,
	constraint fk_city_from foreign key (id_city_from_fk) references cities(id_city),
	constraint fk_city_to foreign key (id_city_to_fk) references cities(id_city)
);
    
create table users(
	id_user int not null auto_increment primary key,
	first_name varchar(50),
	surname varchar(50),
	dni int unique key,
	birthdate datetime,
	username varchar(50) unique key,
	pwd varchar(50),
	email varchar(50),
    user_type enum ('CLIENT','EMPLOYEE'),
    user_status enum('ACTIVE','DELETE'),
	id_city_fk int,
	constraint fk_id_city foreign key (id_city_fk) references cities(id_city)
);
    
create table user_lines(
	id_user_line int not null auto_increment primary key,
	line_number bigint unique key,
	type_line enum('MOBILE','RESIDENCE'),
	line_status enum('ACTIVE','SUSPENDED','DELETE'),
	id_client_fk int,
	constraint fk_id_client foreign key (id_client_fk) references users(id_user)
);

create table invoices(
	id_invoice int not null auto_increment primary key,
	call_count int,
	price_cost decimal (12,2),
	price_total decimal(12,2),
	date_emission datetime,
	date_expiration datetime,
	invoice_status enum('PAID','NOT_PAID'),
	id_line_fk int,
	constraint fk_id_line foreign key (id_line_fk) references user_lines(id_user_line)
);

create table phonecalls(
	id_phonecall int not null auto_increment primary key,
	line_number_from varchar(40),
    line_number_to varchar(40),
    id_line_number_from_fk int,
	id_line_number_to_fk int,
	id_city_from_fk int,
	id_city_to_fk int,
	duration int,
	call_date datetime,
-- 	cost_per_min decimal (12,2),
-- 	price_per_min decimal (12,2),
-- 	total_price decimal (12,2),
-- 	total_cost decimal(12,2),
	id_invoice_fk int,
	constraint fk_id_invoice foreign key (id_invoice_fk) references invoices(id_invoice),
    constraint fk_id_city_from foreign key (id_city_from_fk) references cities(id_city),
    constraint fk_id_city_to foreign key (id_city_to_fk) references cities(id_city),
    constraint fk_id_line_number_from foreign key (id_line_number_from_fk) references user_lines(id_user_line),
    constraint fk_id_line_number_to foreign key (id_line_number_to_fk) references user_lines(id_user_line)
);

INSERT INTO provinces (province_name) values('Buenos Aires'),('Buenos Aires-GBA'),('Capital Federal'),('Catamarca'),('Chaco'),('Chubut'),('Córdoba'),('Corrientes'),('Entre Ríos'),('Formosa'),('Jujuy'),('La Pampa'),('La Rioja'),('Mendoza'),('Misiones'),('Neuquén'),('Río Negro'),('Salta'),('San Juan'),('San Luis'),('Santa Cruz'),('Santa Fe'),('Santiago del Estero'),('Tierra del Fuego'),('Tucumán');
insert into cities(city_name,prefix,id_province_fk)values("Mar del Plata","223",1),("La Plata","011",3),("Bahia Blanca","291",1),("Catamarca ","383",4),("Córdoba ","351",7),("Corrientes","378",8),("Resistencia","372",5),("Rawson","296",10),("Parana","343",9);

insert into users(first_name,surname,dni,birthdate,username,pwd,email,user_type,user_status,id_city_fk) values("Ivan","Graciarena","38704049",'1995-01-11',"ivanmdq22","123","ivanigraciarena@hotmail.com",1,1,1),("Juan","Garcia","36993049",'1990-03-30',"juancho","j2020","juang@hotmail.com",1,1,5),("Jorge","Villordo","39748321",'1983-10-17',"villordin","villoria","villord@hotmail.com",1,1,4),("Pedro","Mujica","203935789",'1940-11-22',"mijuquin","mijuaquin","pepe@hotmail.com",1,1,3),("Miguel","Granados","25090384",'1943-01-22',"miguelon","elguachon","miguegrana@hotmail.com",1,1,2);

insert into user_lines (id_client_fk,line_number,line_status,type_line) values (1,"2235765132",1,1),(1,"2234770469",1,2),(2,"35115575392",1,1),(3,"3834918309",1,2),(4,"2918309532",1,1),(5,"011499899",1,2);

insert into rates (price_per_min,cost_per_min,id_city_from_fk,id_city_to_fk)values(8,5,1,1),(8,5,2,2),(8,5,3,3),(8,5,4,4),(8,5,5,5),(8,5,6,6),(8,5,7,7),(8,5,8,8),(8,5,9,9),(9,5,1,2),(9,5,2,1),(10,5,1,3),(10,5,3,1),(9,5,1,4),(9,5,4,1),(9,7,1,5),(9,7,5,1),(15,10,1,6),(15,10,6,1),(14,11,1,7),(14,11,7,1),(10,8,1,8),(10,8,8,1),(8,6,1,9),(8,6,9,1),(10,5,2,3),(10,5,3,2),(9,5,2,4),(9,5,4,2),(9,7,2,5),(9,7,5,2),(15,10,2,6),(15,10,6,2),(14,11,2,7),(14,11,7,2),(10,8,2,8),(10,8,8,2),(8,6,2,9),(8,6,9,2),(9,5,3,4),(9,5,4,3),(9,7,3,5),(9,7,5,3),(15,10,3,6),(15,10,6,3),(14,11,3,7),(14,11,7,3),(10,8,3,8),(10,8,8,3),(8,6,3,9),(8,6,9,3),(9,7,4,5),(9,7,5,4),(15,10,4,6),(15,10,6,4),(14,11,4,7),(14,11,7,4),(10,8,4,8),(10,8,8,4),(8,6,4,9),(8,6,9,4),(15,10,5,6),(15,10,6,5),(14,11,5,7),(14,11,7,5),(10,8,5,8),(10,8,8,5),(8,6,5,9),(8,6,9,5),(14,11,6,7),(14,11,7,6),(10,8,6,8),(10,8,8,6),(8,6,6,9),(8,6,9,6),(10,8,7,8),(10,8,8,7),(8,6,7,9),(8,6,9,7),(8,6,8,9),(8,6,9,8);


#aca va un trigger de date expiration mas 15 dias de la fecha de emission
#aca va un trigger de price total cuando generamos la factura para agregarle un 20% mas del price cost
insert into invoices (call_count,date_emission,date_expiration,id_line_fk,price_cost,price_total,invoice_status)values(1,'2020-05-06','2020-05-21',1,200,350,1);
insert into phonecalls(line_number_from,line_number_to,id_line_number_from_fk,id_line_number_to_fk,id_city_from_fk,id_city_to_fk,duration,call_date,id_invoice_fk)values("2235765132","11499899",1,6,1,2,20,'2020-04-03',1),("2235765132","11499899",1,6,1,3,20,'2020-01-05',1),("2235765132","11499899",1,6,1,5,20,'2020-04-03',1),(6,5,2,3,20,'2020-01-05',1),(6,3,2,5,20,'2020-04-05',1),(5,4,3,4,20,'2020-02-05',1),(6,3,2,5,20,'2020-04-05',1),(1,5,1,2,20,'2020-07-05',1),(4,6,4,2,20,'2020-02-05',1),(5,6,3,2,20,'2020-07-05',1);

select * from user_lines;

SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;    
rollback;
show engines;
#sp_add_phone_line(line type, username ,line number, line status)
#agregar transaccion
set autocommit = 0;
Delimiter //
create procedure sp_add_user_line(pUserDni int,pLineNumber bigint,pLineStatus int,pLineType int)
begin
	declare vUser_id int default 0;
    declare vIdLine int;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
        BEGIN
            ROLLBACK;
            RESIGNAL;
        END;
    start transaction;
    select id into vUser_id from users us where us.dni=pUserDni;
    if(vUser_id <> 0) then
		insert into user_lines (line_number,type_line,line_status,id_client) values (pLineNumber,pLineType,pLineStatus,vUser_id);
         set vIdLine = last_insert_id();
		insert into invoices (call_count,price_cost,price_total,date_emission,date_expiration,invoice_statu,id_line) values (0,0,0,Date(now()),LAST_DAY(now()),1,vIdLine);
	else 
		signal sqlstate '45000'
		SET MESSAGE_TEXT = 'El Dni ingresado no coincide con un Cliente existente.',
		MYSQL_ERRNO = 1001;
	end if;
    commit;
end; //
select * from user_lines;
select * from invoices;
drop procedure sp_add_user_line;
delete from user_lines where id=8;
delete from invoices where id=2;
call sp_add_user_line(38704049,2235679563,1,2);
-- 
-- #falta hacer
-- #trigger para calcular el costo por minuto el precio por minuto de la tabla tarifas
-- create trigger tbi_cost_per_min before insert on fees for each row
-- begin 
-- 	
-- 	
-- 	
-- end
-- #trigger para calcular el date expiration +15 dias en invoices
-- create trigger tbi_date_expiration before insert on invoices for each row 
-- begin 
-- 	
-- 	
-- 	
-- end
-- #trigger para calcular el price cost y price total en invoices
-- create trigger tbi_total_price before insert on invoices for each row 
-- begin 
-- 	
-- 	
-- 	
-- end
-- #trigger para calcular el call count en invoices
-- create trigger tbi_calculate_call_amout before insert on invoices for each row 
-- begin 
-- 	
-- 	
-- 	
-- end

# consulta llamadas de una usuario logueado por rango de fechas
create procedure sp_phonecalls_betweendates(in pId int,in date_from date, in date_to date)
begin 
	select p.id AS "ID",
		   p.line_number_from AS "NUMBER FROM",
		   p.line_number_to AS "NUMBER TO",
		   p.duration AS "DURATION",
		   p.call_date as "DATE",
		   p.total_price as "TOTAL" 
	from phonecalls as p 
	join invoices as inv on p.id_invoice  = inv.id 
	join user_lines as ul 	 on ul.id = inv.id_line 
	where p.call_date >= date_from 
	and   p.call_date <= date_to
	and   ul.id_client=Pid;
end

#consulta de los top 10 destinos mas llamados por el usuario
-- no andaaaaa
drop procedure sp_user_top10;
create procedure sp_user_top10(in pid int)
begin
	
	select  ul.line_number as "OWNER",c.city_name as "CITY NAME",count(p.id_city_to)
	from phonecalls as p
	join cities as c on p.id_city_from = c.id 
	join users as u on c.id = u.id_city
	join user_lines as ul on u.id = ul.id_client 
	where pid=u.id and ul.line_number = p.line_number_from 
	group by c.city_name 
	limit 10;
	
end

# consulta de tarifas 
create procedure sp_employee_fee(in pcity_from int)
begin
	select f.price_per_min as "PRICE"
	from fees f
	where pcity_from = f.id_city_from
	order by f.price_per_min;
end

# consulta de llamada por usuario



call sp_phonecalls_betweendates(1, '2020-02-01','2020-05-20');
call sp_user_top10(1);

select * from users;
update users set user_status=2 where id=15;
insert into users(first_name,surname,dni,birthdate,username,pwd,email,user_type,user_status,id_city) values("juan","choto","37989322",'1995-01-11',"juanchoto","123","juanchoto@hotmail.com",2,1,1);

                          
select f.line_number_to as "destino mas llamado" ,concat(u.first_name," ",u.surname) as "Nombre Apellido"
from users as u 
join (select line_number_to, id_line_number_from_fk
	  from phonecalls
	  where line_number_from="2235765132"
	  group by line_number_to
	  order by count(line_number_to) desc
	  limit 1) as f
on f.id_line_number_from_fk = u.id_user;

select * from user_lines;
insert into phonecalls(line_number_from,line_number_to,id_line_number_from_fk,id_line_number_to_fk,id_city_from_fk,id_city_to_fk,duration,call_date,id_invoice_fk)values
("2235765132","3834918309",1,4,1,2,20,'2020-04-03',1),("2235765132","3834918309",1,4,1,2,20,'2020-04-03',1),("2235765132","3834918309",1,4,1,2,20,'2020-04-03',1),("2235765132","3834918309",1,4,1,2,20,'2020-04-03',1)

select * from users