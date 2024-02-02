# FaceitKt

## FaceitKt is a Faceit API wrapper. Made by developers, for developers!

## History of the idea

------

Original idea implemented in python3 by [@milahnmartin](https://pypi.org/user/milahnmartin/) and maintained until January 2022.
FaceitKt is a Faceit API solution made in pure Kotlin. FaceitKt will be maintained until it becomes irrelevant.


## Fast Start

--------

```kotlin
val user: FaceitKt.FaceitUserFaceitKt.FaceitUser("s1mple", FaceitKt.Game.csgo)
println("S1mple's elo - ${user.elo}")
```

Supported Data:
~~~
nicknameuser
avatarUrl
country
platforms
membership
isVerified
age
winsuser
winstreak
matches
averageHSRatio
averageKDRatio
lvlgameStats
regiongameStats
elogameStats
~~~

