# Ci&T Interview - Weather App

### Acceptance Criteria:

* Use MVC or MVP
* Best practices

### Technical Details:

* **Language:** Java 1.8
* **Tests:** JUnit and Expresso

### Notes

* Third party library (non Google): 
	* picasso: 2.5.2 to retrieve the openweather icons
	* eventbus: 3.1.1 to notify controllers about result of background/asynchronous operations

### Delivery Instructions

* Please, change the Google Map Key and Openweather API Key. I commited a secondary account key but is recommeded to use yours.
* Built using Android Studio 3.2.1 on Windows 10. 

### Tests
* Turn off animations to run test on device: leaving system animations turned on in the test device might
cause unexpected results or may lead your test to fail. Turn off animations from Settings by opening
Developer options and turning all the following options off:

    Window animation scale
    Transition animation scale
    Animator duration scale


### Considerations and pendencies

Due a time limit some features wasn't properly implement though still close to the requirement. 
Here is a list of missing features and what need to be improved:

* Show the forecast instead current weather
* Implement Facebook authentication
* Check accessibility requisites
* The look and feel of some icons needs to be improved.
* Finish to add the extra functionalities to the extended widgets like validation on empty fields
* Allow the user to choose a location clicking in the Map (usefull when GPS is not available)
* The UnitTests and instrument test also needs more attention.

### Architecture

* I have been designing enterprise mobile application with a mvc like architecture since 2009 when the ASP.NET MVC was launch. It helped to reduce the distance
between developers of different levels or without experience with mobile apps. 
Basically we have a some interfaces  (ViewExt) designed communication. Controllers was suposed to know view only by the interface and vice versa but this was not used in this project due time limit. 

The controllers works like a proxy to business logic and business entities. It converts View Entities and Entities to avoid coupling of views and logics.

The bussiness logic runs validation and operation in order to meet the requirements. There some missing componentes here but once again, time limit drove to a simple solution.

The view was using a compose view design witch the activities works simple as containers that handlers some fragments or toolbar events and calls controllers methods to perform orchestrate business operations.