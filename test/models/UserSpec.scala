package models

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing.Validation
import org.coolestprojects.support.UserFixture
import org.scalatest.{FeatureSpec, GivenWhenThen}
import play.data.validation.Validation


/**
 * @author Noel King
 */
class UserSpec  extends FeatureSpec with GivenWhenThen {

  feature("User firstname input must be valid") {

    scenario("Firstname must be at least 2 characters") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user enters a one character firstname")
        user.id = 1l
        user.firstname = "a"
      Then("A validator error is returned for the firstname")
        val validationErrors = Validation.getValidator().validate(user);
        val error = validationErrors.iterator().next()
        assert(String.valueOf(error.getPropertyPath) === "firstname")
      And(" only one error message is returned")
        assert(validationErrors.size() === 1)
    }

    scenario("Firstname must be set") {
      Given("A user enters their info")
       val user = UserFixture.createUser
      When("A user does not enter firstname")
       user.id = 1l
       user.firstname = null
      Then("A validator error is returned for the firstname")
        val validationErrors = Validation.getValidator().validate(user);
        val error = validationErrors.iterator().next()
        assert(String.valueOf(error.getPropertyPath) === "firstname")
      And(" only one error message is returned")
       assert(validationErrors.size() === 1)
    }

    scenario("Firstname has more than 1 character") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user enters a one character firstname")
        user.id = 1l
        user.firstname = "Noel"
      Then("A validator is ran for the firstname and no errors returned")
        val validationErrors = Validation.getValidator().validate(user);
        assert(validationErrors.size() === 0)
    }
  }

  feature("User lastname input must be valid") {

    scenario("Lastname must be at least 2 characters") {
      Given("A user enters their info")
       val user = UserFixture.createUser
      When("A user enters a one character lastname")
       user.id = 1l
       user.lastname = "b"
      Then("A validator error is returned for the lastname")
       val validationErrors = Validation.getValidator().validate(user);
       val error = validationErrors.iterator().next()
       assert(String.valueOf(error.getPropertyPath) === "lastname")
      And(" only one error message is returned")
       assert(validationErrors.size() === 1)
    }

    scenario("Lastname has more than 1 character") {
      Given("A user enters their info")
       val user = UserFixture.createUser
      When("A user enters a one character lastname")
       user.id = 1l
       user.lastname = "Brennan"
      Then("A validator is ran for the lastname and no errors returned")
       val validationErrors = Validation.getValidator().validate(user);
       assert(validationErrors.size() === 0)
    }
  }

  feature("User coderdojoId input is mandatory") {

    scenario("CoderDojo is not set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user does not select a coderdojo")
        user.id = 1l
        user.coderdojoId = null
      Then("A validator error is returned for the coderdojoId")
        val validationErrors = Validation.getValidator().validate(user);
        val error = validationErrors.iterator().next()
        assert(String.valueOf(error.getPropertyPath) === "coderdojoId")
      And(" only one error message is returned")
        assert(validationErrors.size() === 1)
    }

    scenario("CoderDojo is set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user enters a coderdojo ")
        user.id = 1l
        user.coderdojoId = 100l
      Then("A validator is ran for the coderdojo and no errors returned")
        val validationErrors = Validation.getValidator().validate(user);
        assert(validationErrors.size() === 0)
    }
  }

  feature("User date of birth input is mandatory") {

    scenario("Date of Birth is not set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user does not enter a date of birth")
        user.id = 1l
        user.dateOfBirth = null
      Then("A validator error is returned for the date of birth")
        val validationErrors = Validation.getValidator().validate(user);
        val error = validationErrors.iterator().next()
        assert(String.valueOf(error.getPropertyPath) === "dateOfBirth")
      And(" only one error message is returned")
        assert(validationErrors.size() === 1)
    }

    scenario("CoderDojo is not set") {
      Given("A user enters their info")
      val user = UserFixture.createUser
      When("A user does not select a coderdojo")
      user.id = 1l
      user.coderdojoId = null
      Then("A validator error is returned for the coderdojoId")
      val validationErrors = Validation.getValidator().validate(user);
      val error = validationErrors.iterator().next()
      assert(String.valueOf(error.getPropertyPath) === "coderdojoId")
      And(" only one error message is returned")
      assert(validationErrors.size() === 1)
    }

    scenario("Date of birth is set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user enters a coderdojo ")
        user.id = 1l
        user.dateOfBirth = new java.util.Date
      Then("A validator is ran for the dateOfBirth and no errors returned")
        val validationErrors = Validation.getValidator().validate(user);
        assert(validationErrors.size() === 0)
    }
  }

  feature("User email is mandatory and valid") {

    scenario("Email is not set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user does not enter an email")
        user.id = 1l
        user.email = null
      Then("A validator error is returned for the email")
        val validationErrors = Validation.getValidator().validate(user);
        val error = validationErrors.iterator().next()
        assert(String.valueOf(error.getPropertyPath) === "email")
      And(" only one error message is returned")
        assert(validationErrors.size() === 1)
    }

    scenario("Invalid email is not set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user does not enter an email")
        user.id = 1l
        user.email = "invalidemail"
      Then("A validator error is returned for the email")
        val validationErrors = Validation.getValidator().validate(user);
        val error = validationErrors.iterator().next()
        assert(String.valueOf(error.getPropertyPath) === "email")
      And(" only one error message is returned")
        assert(validationErrors.size() === 1)
    }

    scenario("Email is set") {
      Given("A user enters their info")
        val user = UserFixture.createUser
      When("A user enters a coderdojo ")
        user.id = 1l
        user.email = UserFixture.createRandomEmail
      Then("A validator is ran for the email and no errors returned")
        val validationErrors = Validation.getValidator().validate(user);
        assert(validationErrors.size() === 0)
    }
  }
}
