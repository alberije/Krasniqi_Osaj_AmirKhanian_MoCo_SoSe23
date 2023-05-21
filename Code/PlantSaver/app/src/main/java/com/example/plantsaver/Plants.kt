package com.example.plantsaver

data class Plants(val name: String, val description: String, val care: String, val location: String) // Model-Klasse, die die Daten für die Zimmerpflanzen enthält

class PlantViewModel { //ViewModel-Klasse, die die Logik für den Zugriff auf die Daten und deren Bereitstellung an die View enthält
    private val plantList: List<Plant> = listOf(
        Plant("Bogenhanf (Sansevieria)", "Lange, aufrechte Blätter mit gelblicher oder grünlicher Musterung", "Ideal für helle bis halbschattige Standorte", "Pflegehinweise für Bogenhanf"),
        Plant("Zimmerpalme (Chamaedorea elegans)", "Kleine Palme mit zarten, gefiederten grünen Blättern", "Stellt keine hohen Ansprüche an den Standort", "Pflegehinweise für Zimmerpalme"),
        Plant("Gummibaum (Ficus elastica)", "Große, glänzende, dunkelgrüne Blätter und markante Stämme", "Benötigt einen hellen Standort ohne direkte Sonneneinstrahlung", "Pflegehinweise für Gummibaum"),
        Plant("Zamioculcas (Zamioculcas zamiifolia)", "Dunkelgrüne, glänzende, dicke Blätter, die aufrechte Stängel bilden", "Toleriert verschiedene Lichtverhältnisse, von hell bis halbschattig", "Pflegehinweise für Zamioculcas"),
        Plant("Friedenslilie (Spathiphyllum)", "Dunkelgrüne Blätter und auffällige, weiße Blüten", "Braucht einen halbschattigen bis schattigen Standort", "Pflegehinweise für Friedenslilie"),
        Plant("Einblatt (Spathiphyllum wallisii)", "Grüne Blätter und weiße Hochblätter, die wie Blüten aussehen", "Möchte an einem hellen bis halbschattigen Standort stehen", "Pflegehinweise für Einblatt")
    )

    fun getAllPlants(): List<Plant> {
        return plantList
    }
}