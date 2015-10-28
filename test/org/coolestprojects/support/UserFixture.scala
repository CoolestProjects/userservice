package org.coolestprojects.support

import java.util.Date

import scala.util.Random

/**
 * Created by noelking on 10/19/14.
 */
object UserFixture {

  def UserFixture() {}

  def createUser : models.User = {
    val user = new models.User

    user.email = createRandomEmail
    user.dateOfBirth = createRandomDateOfBirth
    user.firstname = "Noel"
    user.lastname = "King"
    user.mobileNumber = "+353800000000"
    user.coderdojoId = 1
    user.setPassword("password122344")

    return user
  }

  def createUser(password : String) : models.User = {
    val user = new models.User

    user.email = createRandomEmail
    user.dateOfBirth = createRandomDateOfBirth
    user.firstname = "Noel"
    user.lastname = "Keanen"
    user.mobileNumber = "+353800000000"
    user.coderdojoId = 1
    user.setPassword(password);

    return user
  }

  def createRandomEmail : String = {
    return Random.alphanumeric.take(15).mkString + "@coolestprojects.org"
  }

  def createRandomDateOfBirth: Date = {
    val dateRan = new Random()
    val dateRange = 1 to 28
    val monthRan = new Random()
    val monthRange = 0 to 11
    val yearRan = new Random()
    val yearRange = 0 to 40

    val dateOfBirth = new Date()
    dateOfBirth.setDate(dateRange(dateRan.nextInt(dateRange length)))
    dateOfBirth.setMonth(monthRange(monthRan.nextInt(monthRange length)))
    dateOfBirth.setYear(yearRange(yearRan.nextInt(yearRange length)))

    return dateOfBirth
  }

}
