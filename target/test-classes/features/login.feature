Feature: Login functionality

 Scenario Outline: Login with multiple scenarios
    When user logs in using data for "<TestCase>"
    Then user should <ExpectedResult>

    Examples:
      | TestCase     | ExpectedResult                                |
      | amitmohanta  | land on homepage if input status is valid     |
      | ama.         | see login error message if input status is invalid |

    
 