This project was developed for the Avenue Code evaluation.
It consists of an API for managin products and their images.

**Usage**



**Run**

To run the application simply use maven and run the command:


    mvn spring-boot:run

**Tests**

To run the tests you can also use maven, simply run the command:

    mvn test

**Comments**

Regarding the two jpa criteria methods:
   - findByIdWithFilters
   - findAllProductsWithFilters
I would normally try to use one method for both, but since they needed different signatures, one returning a set and the other a single object i left then apart.
     