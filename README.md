# Viikkotehtava1 Kotlin-perusteet

## Yleiskuvaus
Android-sovellus tehtävien hallintaan.
[Tutorial video](https://youtu.be/b8hBJOAeSOY)

## Datamalli

### Task data class
```kotlin
data class Task(
    val id: Int,           // Yksilöllinen tunniste
    val title: String,     // Tehtävän otsikko
    val description: String, // Kuvaus
    val priority: Int,     // Tärkeysjärjestys (1-3)
    val dueDate: String,   // Päivämäärä (YYYY-MM-DD)
    val done: Boolean      // Suoritustila
)

