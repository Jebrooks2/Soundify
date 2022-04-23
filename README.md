# Soundify

## Features
- You can upload music to storage
- You can listen your music from storage
- You can easily modify the program code
- You can search for songs in the database
- You can log in anonymously or through google 
- Availability of notifications
- Track covers

# Firebase Storage Rules
```
service firebase.storage {
    match /b/YOUR_APP_ID.appspot.com/o {
        match /{allPaths=**} {
            allow read, write: if true;
        }
    }
}
```
# Firebase Realtime Database Rules
```
{
  "rules": {
    ".read": true,
    ".write": true
  }
}
```
