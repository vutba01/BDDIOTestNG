Feature: ScenariosExample Example Feature File

Scenario Outline: Login Test Scenario

Given user is already on Login Page
Then user enters "<username>" and "<password>" and clicks on login
Then user is on home page

Examples:
	| username         | password |
	| bahaa@shasha.io  | asdqwe12 |
	
Scenario: create Campaigns
    Given click Create Campaign redirected to Campaign page    
    Then period click have week start date with list of upcoming weeks showing weeknumber, start, end dates
    And select the schedules
    Then page have save and cancel buttons
    Then click the media button
    And assign images to my campaign
    Then click save the campaign
    
    