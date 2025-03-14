# Android Technical Assignment

This document describes the assignment to be completed by candidates interviewing for an Android position at [Shape](https://shape.dk).

👀 **Check out [our profile](https://github.com/shape-interviews) to better understand our technical interview process and the criteria we use to evaluate our candidates.**

## 🎯 Task

You need to develop an app that uses the [Dog Breeds API](https://dog.ceo/dog-api) to allow users to browse through the list of dog breeds, their pictures, and add pictures to favorites.

### 📱 App Structure

We expect your app to have three screens with following features:

#### 1. List of Breeds

- Shows the list of available breeds.
- Navigates the app to screen 2 when the user taps any of the breeds.
- Has "favorites" button that navigates the app to the screen described in **3**.

#### 2. Breed Pictures

- Shows all the available pictures of a given breed.
- Allows users to like/unlike specific images by tapping the image or a like button.

#### 3. Favorite Pictures

- Shows the images that the user liked.
- Shows which breed a particular image belongs to.
- Allows user to filter images by selecting a breed.

### 📋 Requirements

- You should use the following API: https://dog.ceo/dog-api
- Your app should be written in Kotlin.
- Your app's UI should be built with Jetpack Compose.
- You can use any third party libraries that you find suitable.
- Also feel free to add any notes you'd like us to read before we review your assignment by editing the [Notes for interviewer](#-notes-for-interviewer) section below.

### 💻 Practicalities

- We are not expecting you to spend more than 1 day (or 8 hours) on this task, but this is not a strict limitation. Feel free to exceed that time, if you feel like it.
- Rest assured that we are equally happy to evaluate an incomplete solution. We will only have less material to review, in such instance.
- Once your task is ready, please push it to this repository.
- Be prepared to the possibility to showcase your solution during a technical interview. In that case, you would be asked to run the app and show your solution from the user perspective, as well as to present the codebase, highlighting the most compelling parts of the solution, before answering ad-hoc questions concerning your app.

## 📝 Notes for reviewers

Implementation details:
- Navigation is implemented using Compose Navigation
- There is a PR open for the implementation of dependency injection using KOIN
- Favorites are handled using the Room library

The start destination is the Breeds tab, a 2-column grid showing each breed available from the API.
Each card in the grid is clickable, resulting in navigation to a details page for that specific breed, which includes a favorites button (Heart icon).
The heart icon is clickable and results in the image being added to the list of favorites.

Navigating to the favorites view is done with the bottomNavBar, and this page shows a list of all the favorites added.
The list is sortable in the top bar, where clicking on a breed in the drop-down results in only that breed being shown, and clicking show all favorites returns the list to the default state.
This is done by observing the Room database as flows.
