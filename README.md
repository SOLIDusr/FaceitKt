# FaceitKt is out

![FaceitKt Preview](https://github.com/SOLIDusr/FaceitKt/raw/master/assets/FaceitKt_preview.png)

FaceitKt is a powerful API wrapper for Faceit.

Developed from python scratch based on milahnmartin's pyfaceit to a nice kotlin library.

## Installation

Just download it from github. You basically need just FaceitClient.kt file.

```bash
git clone https://github.com/SOLIDusr/FaceitKt.git
```

## Docs

here

## Usage

```kotlin
fun main(){
    val faceitClient = FaceitClient()
    val player = faceitClient.Players()
    val info = player.getPlayers("s1mple")
    println(info["country"])
    >> output - ua
}
```

and something like that...

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[GPL-3.0 license](https://www.gnu.org/licenses/gpl-3.0.html)