# Favorite Places Documentation

## Introduction
The application’s purpose is to keep track of user’s favorite places. Often people want to remember a nice place that they visit so that later they can share it with friends or revisit. This app serves that purpose. A user can choose a place they visited earlier and save it after adding details and comments as well as a picture from their gallery, or alternatively they could take a new picture. Moreover, users can choose the option of getting their current location while adding a new place. Functionality for managing the places as well as user authentication is also in place.

## Features
### Authentication
Once the app starts the user is met with a welcome screen where they can choose to sign in if they have an account or sign up if they do not. Each option opens a new screen with input fields for introducing data. The input is checked and only if it is accurate it will be accepted. Firebase is used for authentication. As soon as a user signs up their login details are saved in firebase and they can sign up with them later on.

<img width="698" alt="Screenshot 2021-08-26 at 12 05 05" src="https://user-images.githubusercontent.com/61206601/130944126-24211027-43c9-4c2f-8a4b-268db7aa2e3a.png">

### Add new place
Once the user chooses to add a new place they will have to pick a name for the place, a description and the location. For location picking I used google places API, which contains millions of places and thus the user could find almost any place they would want to save.
   
When searching for a place they will get suggestions based on what they are typing, like in the screenshot below.

<img width="778" alt="Screenshot 2021-08-26 at 12 05 52" src="https://user-images.githubusercontent.com/61206601/130944238-f15149d3-3343-4f6e-ad08-a7d80760c973.png">

As a last step a picture for the place needs to be picked, when clicking “Add image” a popup will appear asking whether the place should be picked from the gallery or whether a new picture needs to be taken, in which case the camera will open. In order to handle camera and gallery permissions I used the library ‘dexter’.

<img width="796" alt="Screenshot 2021-08-26 at 12 06 39" src="https://user-images.githubusercontent.com/61206601/130944337-2a5d17cd-258c-4360-92b5-32da851ec549.png">

The user has the option to choose current location, in which case the user will be asked to give permission to the up for their current location. If done their current location will be saved for the place.

<img width="801" alt="Screenshot 2021-08-26 at 12 06 58" src="https://user-images.githubusercontent.com/61206601/130944385-81337b6e-bdfd-4266-aabc-2acb5a97c181.png">

### View existing place
All saved places will be displayed for the user in a RecyclerView where they can browse through them or click any individual one for more details.

<img width="180" alt="Screenshot 2021-08-26 at 12 07 54" src="https://user-images.githubusercontent.com/61206601/130944520-d518d4b1-dea0-438a-be78-55a0c4dcd645.png">

### Taping any place for details
Once a place is picked it will open in a new screen where the user will also have the option to view it on a map. If picked a nice animation will zoom into the place on a google map.

<img width="428" alt="Screenshot 2021-08-26 at 12 08 28" src="https://user-images.githubusercontent.com/61206601/130944592-7421be2c-e31d-44fc-a560-d26c559d2f90.png">

## Technical details
### Architecture
I opted for a Single – Activity architecture. There is only one activity that initializes the first fragment and holds the logic for changing between fragments. The main benefit of this approach is speed since switching between fragments is less resource intensive than creating and switching between activities.

<img width="647" alt="Screenshot 2021-08-26 at 12 09 09" src="https://user-images.githubusercontent.com/61206601/130944691-97ada0a8-c060-4f3a-8c36-bf2d715a53f0.png">

### Dexter
Dexter is an Android library that simplifies the process of requesting permissions at runtime. Android Marshmallow and above includesa new functionality to let users grant or deny permissions when running an app instead of granting them all when installing it. This approach gives the user more control over applications but requires developers to add lots of code to support it. GitHub: https://github.com/Karumi/Dexter

### Firebase Authentication
Most apps need to know the identity of a user. Knowing the user’s identity allows an app to personalize their experience and save their data. Firebase Authentication provides SDKs and ready-made UI libraries to help in the process of authenticating users. https://firebase.google.com/docs/auth

### Database
Room was used to store data on the device. The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
https://developer.android.com/training/data-storage/room

### Google places API
This API from google contains millions of places and I am using it to find places of interest. The Places API uses a place ID to uniquely identify a place. https://developers.google.com/maps/documentation/places/web-service/overview
