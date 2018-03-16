# ImageService Spring boot application
A Spring boot application to upload, get and delete images from an S3 bucket

## End points
* [Upload Image](http://localhost:9080/storage/uploadFile) POST
* [Get Image](http://localhost:9080/storage/getFile?fileName=<fileName>) GET
* [Get All Image Names in Bucket](http://tds.sbg.intuit.com/v4/graphql) GET
* [Delete Image](http://localhost:9080/storage/deleteFile?url=<s3_url_of_image>) DELETE



## How to run
The easiest way to run the application is using docker 
* Prerequisite : Have Docker , JDK 1.8 and maven 3.5 installed on your machine
* AWS account : Create an AWS account, generate access key and secret key. Set env vars on your machine 

```export ACCESS_KEY=<your_access_key>```

```export SECRET_KEY=<your_secret_key>```

* You need to have an S3 bucket created in your AWS account , I am using a bucket named 'sambokar-images-storage-new',
you would need to edit the src/main/resources/application.yml and rename the bucket name

Clone this directory to a local directory and run 

```mvn clean install``` 

this will create a docker image and store it locally. 

Next , start the application by running 

```docker-compose up -d```

This should start your application, now you are ready to try it out.

## API Reference

* ### Upload Image ###

POST : http://localhost:9080/storage/uploadFile 

This is a Post request , you would have to use Postman 
<img width="995" alt="screen shot 2018-03-16 at 1 39 02 pm" src="https://user-images.githubusercontent.com/25832353/37543667-8d43d630-291f-11e8-8cc9-ca341f0eff19.png">

* ### Get Image ###

GET : http://localhost:9080/storage/getFile?fileName=1521232739525-homer.jpg 
Header:
Accept : image/jpeg

* ### Get All Image Names ###

GET : http://localhost:9080/storage/getAllFileNames 

* ### Delete Image ###

DELETE : http://localhost:9080/storage/deleteFile?url=https://s3.us-west-2.amazonaws.com/sambokar-images-storage/1520988298070-homer.jpg 
