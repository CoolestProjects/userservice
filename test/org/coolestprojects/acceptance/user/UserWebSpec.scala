package org.coolestprojects.acceptance.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.coolestprojects.support.UserFixture
import org.scalatestplus.play._
import play.Logger
import play.api.libs.ws.WS
import play.api.test.Helpers._
import play.libs.Json

class UserWebSpec extends PlaySpec with OneServerPerSuite {

  val myPublicAddress =  s"localhost:$port"
  val testPaymentGatewayURL = s"http://$myPublicAddress"
  var user: models.User = new models.User
  var mapper: ObjectMapper = new ObjectMapper
/*
  "verify server started logic" in {
    val callbackURL = s"http://$myPublicAddress/user"
    val response = await(WS.url(testPaymentGatewayURL).withQueryString("callbackURL" -> callbackURL).get())
    response.status mustBe (OK)
  }

  "verify save user" in {
      user = UserFixture.createUser
      val userJson = play.api.libs.json.Json.parse(Json.stringify(Json.toJson(user)))
      val userSaveUrl  = s"$testPaymentGatewayURL/user/create"
      Logger.info("verify save user obj: {} ", userJson);

      val response = await(WS.url(userSaveUrl).post(userJson))
      Logger.info("verify save user response {} ", response)

      response.status mustBe (CREATED)
  }

  "verify retrieving user" in {
    val userGetUrl  = s"$testPaymentGatewayURL/user/" + user.email
    Logger.info("requesting user url : {} ", userGetUrl)

    val response = await(WS.url(userGetUrl).get())
    Logger.info("verify retrieved user response {} ", response.body.toString())

    val userJson = Json.parse(response.body.toString())
    val emailAddr = userJson.findValue("email")
    Logger.info("returned email address {} ", emailAddr)

    response.status mustBe (OK)
    (emailAddr).textValue() mustBe (String.valueOf(user.email))
  }


  "verify updating user" in {

    val newUser = createNewUserObject
    val userJson = play.api.libs.json.Json.parse(Json.stringify(Json.toJson(newUser)))
    val userSaveUrl  = s"$testPaymentGatewayURL/user/create"
    Logger.info("verify save user obj: {} ", userJson);
    val createResponse = await(WS.url(userSaveUrl).post(userJson))

    val userDetails: models.User = mapper.readValue(createResponse.body.toString(), classOf[models.User])
    userDetails.firstname = "James"
    userDetails.lastname = "Brown"
    val updateUserJson = play.api.libs.json.Json.parse(Json.stringify(Json.toJson(userDetails)))
    val userUpdateUrl  = s"$testPaymentGatewayURL/user/update"

    Logger.info("verify save user obj: {} ", updateUserJson);
    val updateResponse = await(WS.url(userUpdateUrl).post(updateUserJson))
    Logger.info("verify save user response {} ", updateResponse)

    updateResponse.status mustBe (CREATED)
  }
*/
  "verify authenticated user" in {
    val password = "password122344";
    val user = createUserRemote(password);

    val userAuthJson = play.api.libs.json.Json.parse(Json.stringify(Json.newObject.put("email", user.email).put("password", password)))
    val userAuthUrl  = s"$testPaymentGatewayURL/user/authenticate"
    val response = await(WS.url(userAuthUrl).post(userAuthJson))
    response.status mustBe (OK)
  }
  
  def createUserRemote(password : String) :  models.User = {
    val user = UserFixture.createUser(password)
    val userJson = play.api.libs.json.Json.parse(Json.stringify(Json.toJson(user)))
    val userSaveUrl  = s"$testPaymentGatewayURL/user/create"
    Logger.info("verify save user obj: {} ", userJson);

    val response = await(WS.url(userSaveUrl).post(userJson))
    Logger.info("verify save user response {} ", response)

    response.status mustBe (CREATED);
    
    return user
  }


  def createNewUserObject : models.User = {
    val user = new models.User
    user.email = UserFixture.createRandomEmail
    user.dateOfBirth = UserFixture.createRandomDateOfBirth
    user.firstname = "Ted"
    user.lastname = "Smith"
    user.coderdojoId = 50l
    user.mobileNumber = "+999"
    user.twitter = "coolestprojects"
    return user;
  }

}