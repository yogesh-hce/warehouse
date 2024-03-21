POST : http://localhost:8080/api/warehouses
{
  "name" : "ware-1"
}

POST : http://localhost:8080/api/warehouses/1/boxes
{
  "location" : "Aisle-1",
  "capacity" : 100
}


POST : http://localhost:8080/api/products

{
  "name" : "Product-1"
}

POST : http://localhost:8080/api/products

{
  "name" : "Product-2"
}


PUT : http://localhost:8080/api/products/1/box/1   -- Update box for a product



GET : http://localhost:8080/api/products/search/pro

{"products":[{"name":"Product-1","description":null,"location":"Aisle-1"},{"name":"Product-2","description":null,"location":null}]}
