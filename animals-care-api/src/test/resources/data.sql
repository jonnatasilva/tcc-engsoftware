delete from stock;
delete from sale_product;
delete from sale;
delete from product;
delete from manufacturer;

insert into manufacturer(manufacturer_id, name)
values (1, 'L''Oréal');

insert into product(product_id, name, price, specifications, manufacturer_id)
values (1, 'Shamppo', 20.00, 'Indicate: Dog, Cat, Breed: Small Breeds, Medium Breeds, Large Breeds', (select manufacturer_id from manufacturer where name = 'L''Oréal'));

insert into stock(stock_id, product_id, amount)
values (1, 1, 10);