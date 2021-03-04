# MINT Test Task

<img src="https://firebasestorage.googleapis.com/v0/b/uploadvideo-4d8f6.appspot.com/o/Screenshot_20210304_155357.png?alt=media&token=f8e4884e-bb16-4618-bf1d-215334bf42ae" width="300" height="500">

This is a simple application that provides details of a user's card.
The user is to input his card number into the field provided. If this card number is valid, the app will fetch the following details of the card after the user clicks on
the proceed button: 
1) Brand
2) Type
3) Bank
4) Country

If no match for the card number is found, an error message will be shown to the user.

## Dependencies
- Retrofit
- Hilt
- Mockito
- Coroutines
- Paystack SDK (to validate users card numbers): https://docs-v1.paystack.com/docs/test-cards
- MockWebserver https://github.com/square/okhttp/tree/master/mockwebserver
- Espresso https://developer.android.com/training/testing/espresso

## Architecture
MVVM

## Units Tests
The code contains both Local unit tests, Instrumented unit tests and UI tests.
