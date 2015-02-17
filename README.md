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
GET     /user                       controllers.UserController.index()
GET     /user/:username             controllers.UserController.get(username: String)
POST    /user/create                controllers.UserController.createUser()
POST    /user/update                controllers.UserController.update()
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
* No response body
