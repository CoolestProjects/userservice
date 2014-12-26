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
    val userGetUrl  = s"$testPaymentGatewayURL/user/" + user.email
    Logger.info("requesting user url : {} ", userGetUrl)

    val response = await(WS.url(userGetUrl).get())
    Logger.info("verify retrieved user response {} ", response.body.toString())

    val userDetails: models.User = mapper.readValue(response.body.toString(), classOf[models.User])
    val newUser = createNewUserObject
    val updatedUser = updateUserObject(userDetails, newUser)

    val userJson = play.api.libs.json.Json.parse(Json.stringify(Json.toJson(updatedUser)))
    val userSaveUrl  = s"$testPaymentGatewayURL/user/update"
    Logger.info("verify save user obj: {} ", userJson);

    val updateResponse = await(WS.url(userSaveUrl).post(userJson))
    Logger.info("verify save user response {} ", updateResponse)

    updateResponse.status mustBe (CREATED)
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

  def updateUserObject (updateObj:models.User, userData:models.User) : models.User = {
    updateObj.email = userData.email
    updateObj.dateOfBirth = userData.dateOfBirth
    updateObj.firstname = userData.firstname
    updateObj.lastname = userData.lastname
    updateObj.coderdojoId = userData.coderdojoId
    updateObj.mobileNumber = userData.mobileNumber
    updateObj.twitter = userData.twitter
    return updateObj;
  }
}