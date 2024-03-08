Feature: Test of Login Eclipso mail

  Background:
    Given open Eclipso mail page

  Scenario: Signing with empty credentials in Proton mail
    When signin with empty login and password
    Then receive 2 warning messages

  Scenario Outline: Signing with incorrect credentials in Proton mail
    When signin with <login> login and <password> password
    Then alert message is displayed
    Examples:
      | login              | password          |
      | "caraqa@proton.me" | "123"             |
      | "caraqa@prton.me"  | "justForTest999_" |