insert into user values(101,'john@gmail.com','John','Dicosta','admin','ssn101','john101')
insert into user values(102,'vk@gmail.com','Virat','Kohli','admin','ssn102','vk102')
insert into user values(103,'ms@gmail.com','MS','Dhoni','admin','ssn103','ms103')

insert into orders (order_id,order_description,user_id_fk) values(2001,'order11',101)
insert into orders (order_id,order_description,user_id_fk) values(2002,'order12',101)
insert into orders (order_id,order_description,user_id_fk) values(2003,'order13',101)
insert into orders (order_id,order_description,user_id_fk) values(2004,'order21',102)
insert into orders (order_id,order_description,user_id_fk) values(2005,'order22',102)
insert into orders (order_id,order_description,user_id_fk) values(2006,'order31',103)

insert into address (addr_id,city,state,country) values(4001,'Nagpur','Maharashtra','India')
insert into address (addr_id,city,state,country) values(4002,'Hyderabad','Telengana','India')
insert into address (addr_id,city,state,country) values(4003,'Pune','Maharashtra','India')