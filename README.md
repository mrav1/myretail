# My Retail service
My Retail RESTFul Service

#### Technology
- Java
- Spring boot
- Maven
- H2 database
- ehcache

#### Run tests and build jar
```
mvn clean package
```

#### Run application
Execute below command to run application
```
java -jar target/myretail-0.0.1-SNAPSHOT.jar
```
#### API endpoints
- Get product info api

   ``(GET) - http://localhost:8080/myretail/api/v1/products/13860428``

   Response:
    ```
    {
        "id": 13860428,
        "name": "The Big Lebowski (Blu-ray)",
        "current_price": {
            "value": 14.99,
            "currency_code": "USD"
        }
    }
    ```
  
- Get product info api
   ``(GET) - http://localhost:8080/myretail/api/v1/products/13860428/price``

   Response:
    ```
    {
        "value": 14.99,
        "currency_code": "USD"
    }
    ```
  
- Post API to create price
   ``(POST) - http://localhost:8080/myretail/api/v1/products``
   
   Request: 
   ```
   {
   	"id":13860428,
   	"name":"The Big Lebowski",
   	"current_price":{
   		"currency_code":"USD",
   		"value":14.59
   	}
   }

   ```
   Response:
    ```
    Status: 201 created
    Location: http://localhost:8080/myretail/api/v1/products/13860428
   ```
  
  
- PUT API to update price
   ``(PUT) - http://localhost:8080/myretail/api/v1/products/13860428``
   
   Request: 
   ```
   {
   	"id":13860428,
   	"name":"The Big Lebowski",
   	"current_price":{
   		"currency_code":"USD",
   		"value":14.59
   	}
   }

   ```
   Response:
    ```
    {
    	"id":13860428,
    	"name":"Test",
    	"current_price":{
    		"currency_code":"USD",
    		"value":14.59
    	}
    }
    ```