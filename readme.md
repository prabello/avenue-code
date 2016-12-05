This project was developed for the Avenue Code evaluation.
It consists of an API for managin products and their images.

**Usage**

To make it easier, the app is deployed at Heroku:

https://avenue-code.herokuapp.com/products

**`Creating a product`**

If you want to create a product you can post a json at:
 https://avenue-code.herokuapp.com/products
 
 Example of json:
 
    {"name":"PS4","images":[{"uri":"https://psmedia.playstation.com/is/image/psmedia/ps4-overview-screen-02-eu-06sep16?$MediaCarousel_LargeImage$"},{"uri":"http://lebes.vteximg.com.br/arquivos/ids/176356/560140-5.jpg"}]}
    
    
**`Updating a product`**

To update a product you can send a PUT to https://avenue-code.herokuapp.com/products sending the JSON on the body, the ID will be used to find the product to be changed.

**`Deleting a product`**
 
 To delete a product you can sent a DELETE to https://avenue-code.herokuapp.com/products sending a json that represents the product to be deleted.
 
 **`Getting all products`**
 
 To get all the products you can simply call https://avenue-code.herokuapp.com/products, and you'll receive a json with all the products, without the images or associated product.
 
 **`Getting all products with images and associetaded products`**
 
 To get all products with the images and products you can provide 2 headers:
 
 - images (boolean)
 - parent (boolean)
 If you want to bring the images just set it true, if you want the parent product set it to true, or if you want both, then set both to true
 
 **`Single product`**
 
 Just like the list methods you can search for a single product, you only need to pass the ID on the path of you request, eg:
  
    https://avenue-code.herokuapp.com/products/1
     
 This will bring the product with the ID number 1, if you want it to bring the images and parent product, then just set the headers.
 
 
 `Finding the childs of a product`
 
 If you want to find all products that are associated to a product you can call:
 
    https://avenue-code.herokuapp.com/products/1/child
    
 This will return all products that are associated to the product of id 1.
 
 `Finding the images of a product`
 
 If you want to find all images of a product you can call:
 
    https://avenue-code.herokuapp.com/products/1/images
    
 This will return all images associated to this product.


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
     