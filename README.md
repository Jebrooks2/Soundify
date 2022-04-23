# Soundify

## Features
- You can upload music to storage
- You can listen your music from storage
- You can easily modify the program code
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
