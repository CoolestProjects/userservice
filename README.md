Coolest Projects - User Service
=================================

# API

### Error Format

All error http codes will return a json structure which will contain a list of errors inside the errorDetails variable.

````javascript
 {
 	"errorDetails": [""] //list string error messages
 }
````

### User Service

__conf/routes__
````

# User service
GET     /user/:email                controllers.UserController.get(email: String)
POST    /user/create                controllers.UserController.createUser()
POST    /user/update                controllers.UserController.update()
POST    /user/updatepassword        controllers.UserController.updatePassword()
POST    /user/authenticate          controllers.UserController.authenticate()

POST    /user/lostpassword/:username           controllers.UserController.lostPassword(username: String)
````

#### Create User

| Heading | Details |
|------|------ |
| Url | /user/create |
| Http Method | POST |

__Request Body Format__

````javascript
 {
 	"firstname": "", //string
 	"lastname": "", //string
 	"dateOfBirth": DD-MM-YYYY, //date
 	"coderdojoId": 0l, //long
 	"email": "", //string
 	"password": "", //string
 	"acceptTerms": true | false, //boolean
 	"parentName": "", //string
    "parentEmail": "", //string
    "parentMobile": "", //string
    "twitter": "", //string
    "mobileNumber": "" //string
 }
````

__Response Body Format__

* Http Success or Error Code

````javascript
 {
    "id": 0l, //long
 	"firstname": "", //string
 	"lastname": "", //string
 	"dateOfBirth": DD-MM-YYYY, //date
 	"coderdojoId": 0l, //long
 	"email": "", //string
 	"parentName": "", //string
    "parentEmail": "", //string
    "parentMobile": "", //string
    "twitter": "", //string
    "mobileNumber": "", //string
    "profileImage": "",//string
    "roles": [
        {
            "id": 0l, //long
            "name": "" //string
        }
    ]
 }
````

#### Get User

| Heading | Details |
|------|------ |
| Url | /user/:email |
| Http Method | GET |

__Request Body Format__

No request body

__Response Body Format__

````javascript
 {
    "id": 0l, //long
 	"firstname": "", //string
 	"lastname": "", //string
 	"dateOfBirth": DD-MM-YYYY, //date
 	"coderdojoId": 0l, //long
 	"email": "", //string
 	"parentName": "", //string
    "parentEmail": "", //string
    "parentMobile": "", //string
    "twitter": "", //string
    "mobileNumber": "", //string
    "profileImage": "",//string
    "roles": [
        {
            "id": 0l, //long
            "name": "" //string
        }
    ]
 }
````

#### Update User

| Heading | Details |
|------|------ |
| Url | /user/update |
| Http Method | POST |

__Request Body Format__

````javascript
 {
     "id": 0l, //long
  	"firstname": "", //string
  	"lastname": "", //string
  	"dateOfBirth": DD-MM-YYYY, //date
  	"coderdojoId": 0l, //long
  	"email": "", //string
  	"parentName": "", //string
     "parentEmail": "", //string
     "parentMobile": "", //string
     "twitter": "", //string
     "mobileNumber": "" //string
 }
````

__Response Body Format__

* Http Success or Error Code

````javascript
 {
    "id": 0l, //long
 	"firstname": "", //string
 	"lastname": "", //string
 	"dateOfBirth": DD-MM-YYYY, //date
 	"coderdojoId": 0l, //long
 	"email": "", //string
 	"parentName": "", //string
    "parentEmail": "", //string
    "parentMobile": "", //string
    "twitter": "", //string
    "mobileNumber": "", //string
    "profileImage": "",//string
    "roles": [
        {
            "id": 0l, //long
            "name": "" //string
        }
    ]
 }
````


#### Update User Password

| Heading | Details |
|------|------ |
| Url | /user/updatepassword |
| Http Method | POST |

__Request Body Format__

````javascript
 {
    "email": "", //string
 	"password": "" //string	
 }
````

__Response Body Format__

* Http Success or Error Code

````javascript
 {
    "id": 0l, //long
 	"firstname": "", //string
 	"lastname": "", //string
 	"dateOfBirth": DD-MM-YYYY, //date
 	"coderdojoId": 0l, //long
 	"email": "", //string
 	"parentName": "", //string
    "parentEmail": "", //string
    "parentMobile": "", //string
    "twitter": "", //string
    "mobileNumber": "", //string
    "profileImage": "",//string
    "roles": [
        {
            "id": 0l, //long
            "name": "" //string
        }
    ]
 }
````

