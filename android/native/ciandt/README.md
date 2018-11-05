# Android Test

We need you to create an app that allows users to make searches for locations and present the results in a list, allowing them to be presented in a map view inside the app.  
The app architecture, project and code organization, file structure and comments will be evaluated.

### Acceptance Criteria:

1. The app should support Android 4 and later. The look and feel is the system's default.
1. Text field on the top of the screen.
1. Tapping search button makes the search through the call to the Google Maps API.
1. The UI must be responsive while searching through the API call.
1. The results must be listed in the same order as received by the API response. Each item in the list should show the name of the related location.
1. If there is only one result, display only one row in the list.
1. if there are more than one results, show a row for "Display All on Map" in a separated section.
1. Selecting this row would display the map with all results centered.
1. If there is no results, a "No Results" text should be displayed in the view instead of the result rows.
1. Selecting one item in the list should present a map view with all result items presented as markers. The selected marker should be centered. Feel free to set the zoom level.
1. Selecting a marker in the map view should present its location name and coordinates in the marker title.
1. In the Map screen, add a "Save" button to the action bar, allowing the user to save the selected result object. This action should save the current object to a SQLite database and the object should be unique in the database.
    * **This is not applicable to the "Display All on Map" option.**
1. Case the object has been previously saved, the "Save" button should become into a "Delete" button. Tapping this button will delete the current object from the database, after popping up a dialog confirming the deletion. User should be able to "Cancel" or "Delete" the item from the presented dialog.
    * **This is not applicable to the "Display All on Map" option.**
1. Provide unit tests for models, activities and fragments, using the Android Testing API.



### Technical Details:

* **Language:** Java
* **Tests:** Automated tests are very important
* **Documentation:** [Google Maps API](https://developers.google.com/maps/documentation/geocoding/)
* **API call example:** http://maps.googleapis.com/maps/api/geocode/json?address=Springfield&sensor=false
* **Location name key to be used:** `formatted_address`
* **Location coordinates key path:** `geometry.location`

### Notes

* This assessment must be delivered within 2 days.
* You can use whatever third party library you want to accomplish these requirements.
* You must provide a COMMENTS.txt (plain text) or a COMMENTS.md (Markdown) file at the root of your repository, explaining:

    * Main architecture decisions you've made and a quick explanation of why.
    * List of third party libraries and why you chose each one.
    * What in your code could be improved if you had more time.
    * Mention anything that was asked but not delivered and why, and any additional comments.
  
* Any questions, please send an email to **recruitment.engineering@avenuecode.com**

### Delivery Instructions

1. You must provide his BitBucket username. A free BitBucket account can be created at http://bitbucket.org
1. The recruiter will give you read permission to a repository named **android-challenge**, at https://bitbucket.org/ac-recruitment/android-challenge
1. You must fork this repository into a private repository on your own account and push your code in there.
1. Once finished, you must give the user **ac-recruitment** read permission on your repository so that you can be evaluated. Then, please contact back your recruiter and he will get an engineer to evaluate your test.
1. After you are evaluated, the recruiter will remove your read permission from the original repository.

Thank you,  
The AvenueCode Recruiting Team