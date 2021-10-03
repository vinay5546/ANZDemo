Feature: ANZ home loan calculator

  Scenario Outline: Verifying borrowing Estimate
    Given I launch the application
    Then I am on 'Home loan borrowing power calculator | ANZ' page
    When I select 'Application type' as '<applicationType>'
    And I select 'Number of dependents' as '<noOfDependents>'
    And I select 'Property i would like to buy' as '<propertyType>'
    And I enter 'Income' as '<income>'
    And I enter 'Other income' as '<otherIncome>'
    And I enter 'Living expenses' as '<livingExpense>'
    And I enter 'Current home loan repayments' as '<currentHomeLoanRepayments>'
    And I enter 'Other loan repayments' as '<otherLoanRepayments>'
    And I enter 'Other commitments' as '<otherCommitments>'
    And I enter 'Total credit card limits' as '<totalCreditCardLimits>'
    And I click on 'Work out how much i could borrow' button
    Then I verify the borrowing estimate as '<borrowingEstimate>'
    When I click 'Start over' button
    Then I validate form gets cleared
    Examples:
      | applicationType | noOfDependents | propertyType    | income | otherIncome | livingExpense | currentHomeLoanRepayments | otherLoanRepayments | otherCommitments | totalCreditCardLimits | borrowingEstimate |
      | Single          | 0              | Home to live in | 80000  | 10000       | 500           | 0                         | 100                 | 0                | 10000                 | $508,000          |

  Scenario: Verifying borrowing Estimate with 'Living expenses' as $1
    Given I launch the application
    Then I am on 'Home loan borrowing power calculator | ANZ' page
    When I enter 'Living expenses' as '1'
    And I click on 'Work out how much i could borrow' button
    Then I verify the error message
