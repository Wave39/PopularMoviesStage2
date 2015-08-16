# Popular Movies Stage 2
This is the Popular Movies Stage 2 app.
## Building Popular Movies Stage 2
You need to fill in your own API key to access the web services provided by The Movie Database (TMDb). Once you fill out the form on their web site to get your own API key, you will need to create a new file called api_keys.xml in the app/res/values folder of the project. The contents of this file will need to be as follows, where you replace INSERT_YOUR_API_KEY_HERE with the actual API key you get from TMDb:

```
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <string name="TMDB_API_KEY">INSERT_YOUR_API_KEY_HERE</string>

</resources>
```
