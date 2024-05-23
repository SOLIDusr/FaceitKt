# FaceitKt

## FaceitKt это оболочка для Faceit API . Made by developers, for developers!

## История идеи проекта

------

Оригинальная идея была реализована на python3 разработчиком [@milahnmartin](https://pypi.org/user/milahnmartin/) и поддерживалась до января 2022.
FaceitKt это решение для Faceit API написаное на чистом Kotlin. FaceitKt будет поддерживаться до момента его неактуальноси


## Быстрый старт

--------

```kotlin
val user: FaceitKt.FaceitUserFaceitKt.FaceitUser("s1mple", FaceitKt.Game.csgo)
println("S1mple's elo - ${user.elo}")
```

Поддерживаемая информация:
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

