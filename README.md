# O2 Interview Assignment

## O2 Slovakia Android home assignment

Create an Android application in Kotlin (minimum Android version is at your discretion,
libraries are allowed, framework choices - if any - are up to you as well).
The application should model a scratch card and its usage. The scratch card is initially
unscratched, then gets scratched (revealing the code), and subsequently needs to be
activated (i.e. 3 states).
The UI should be as follows:

**On the main screen**, always display
1. the current state of the scratch card,
2. a button that navigates to the scratch screen,
3. a button that navigates to the activation screen.

**On the scratch screen**, there should be

a button whose click changes the state of the card to scratched.
Scratching off the card means to reveal a code (just generate a random UUID). Let's
pretend this is a heavy operation and takes 2 seconds.
The operation should be canceled if the user closes the screen (back button) and
the operation has not yet successfully completed.


**On the activation screen**,

there should be a button whose click activates the scratch card. Activation means
sending the revealed code to an API.
Definition of the service:
```
url "https://api.o2.sk/version",
query parameter "code",
method "GET",
no authentication,
example response: { "android": "287028" }
```

If the value of "android" is greater than 277028, then the card in the app changes to
an activated state, otherwise, an error modal should be displayed.
If the user closes the screen during this operation, the operation should **not be
canceled**.
Critical parts of the application should be covered by unit tests.
Focus on proper software engineering first. Publish the source code on github.com or a
similar service, and send us the URL to the repository.

## Submitted Assignment Info

Hey, so this is my assignment's solution. About the architecture, I went
with clean architecture + MVVM solution, if everything was as it is supposed to, every feature
would have it's own module (activation, card, scratch) and it's module would have 3 separate submodule (ui/data/domain).
I have created it with the same structure as is it is supposed to be, but I didn't bother to separate it
into standalone gradle modules as it is a tedious task and it doesn't show my coding expertise, so pardon me for not doing this :)

For storing the card I have used simple in-memory model, but it could be easily extended with some shared prefs/ORM
persistence.

If you have any questions ask me:)