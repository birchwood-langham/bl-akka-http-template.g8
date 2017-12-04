package $organization$.acceptance

import org.scalatest._

class TestFeatureSpec extends FeatureSpec with GivenWhenThen {
  info("As a user")
  info("I want a feature I can use")
  info("To do what I want to do")

  feature("The feature") {
    scenario("Scenario 1") {
      Given("A feature")
      // add code to instantiate object
      // create assertions
      // perform other tasks etc.
      When("Something happens")
      // add code to perform some action(s) etc.
      Then("There should be a result")
      // Add asserts etc.
      assert(true)
    }

    // scenario("Scenario 2") {
    //   Given("A feature")
    //   // add code to instantiate object
    //   // create assertions
    //   // perform other tasks etc.
    //   When("Something happens")
    //   // add code to perform some action(s) etc.
    //   Then("There should be a result")
    //   // Add asserts etc
    //   assert(false)
    // }
  }
}
