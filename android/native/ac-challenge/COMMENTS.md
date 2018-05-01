# android-challenge

### Acceptance Criteria:

See: README.MD

### Technical Details:

* **Language:** Java
* **Tests:** JUnit and Expresso

### Notes

* Third party library: In order to keep application architecture simple, supporting old android versions
and reduce the configuration management no third party libraries was used.

### Delivery Instructions

* Please, change the Google Map Key. I commited a secondary key but is recommeded use yours.
* Built using Android Studio 3.0.0 on Windows 10. 

### Tests
* Turn off animations to run test on device: leaving system animations turned on in the test device might
cause unexpected results or may lead your test to fail. Turn off animations from Settings by opening
Developer options and turning all the following options off:

    Window animation scale

    Transition animation scale

    Animator duration scale


### Considerations and pendencies

 Considerations and pendencies.
Due a time limit some features wasn't properly implement though still close to the requirement. Here is a list of missing features and what need to be improved:

* The to use the Google Map Geocoding I used the GoogleMap Geocode native classes provided in the SDK instead perform Http requests to the Web API version.
This decision was due time limit, close results on both APIs and previous experience. Using http requets could lead the use o AsynkTask instead of Intent Service
but the result for the user will be the same: no UI freezing.
* The look and feel of some icons needs to be improved. The one I choosen was designed for a different App Theme.
* Finish to add the extra functionalities to the extended widgets like validation on empty search field.
* The UnitTests and instrument test also needs more attention.

### Architecture

* I have been designing enterprise mobile application with a mvc like architecture since 2009 when the ASP.NET MVC was launch. It helped to reduce the distance
between developers of different levels or without experience with mobile apps. 
Basically we have a some interfaces  (ViewExt) designed communication. Controllers was suposed to know view only by the interface and vice versa but this was not used in this project due time limit. 

The controllers works like a proxy to business logic and business entities. It converts View Entities and Entities to avoid coupling of views and logics.

The bussiness logic runs validation and operation in order to meet the requirements. There some missing componentes here but once again, time limit drove to a simple solution.

The view was using a compose view design witch the activities works simple as containers that handlers some fragments or toolbar events and calls controllers methods to perform orchestrate business operations.