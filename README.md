# Popular Movies Stage 2
This is the Popular Movies Stage 2 app.
## Building Popular Movies Stage 2
You need to fill in your own API key to access the web services provided by The Movie Database (TMDb). Once you fill out the form on their web site to get your own API key, you will need to get this API key into the source code so that it can be seen as a string resource.

There are two ways to do this. The easiest way is to go into the build.gradle file for (Module: app), and replace the two instances of TMDB_API_KEY in the buildTypes section with your API key surrounded by double quotes. 

Alternatively, the way that I am doing it is that my gradle.properties file is not in the source code repository, so like me you can go into your gradle.properties file and create a line like this, where you replace INSERT_YOUR_API_KEY_HERE with the actual API key you get from TMDb:

```
TMDB_API_KEY=INSERT_YOUR_API_KEY_HERE
```

Notice there are no double quotes surrounding the API key if you use the gradle.properties method.
