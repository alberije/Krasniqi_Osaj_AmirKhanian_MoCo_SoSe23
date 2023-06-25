package com.example.plantsaver.database

import com.example.plantsaver.database.model.PlantFamily
import com.example.plantsaver.database.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Repository(private val db: AppDatabase) {
    // dieser Flow enthält einen user und kann immer wieder aktualisiert werden
    private val _currentUserFlow: MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUserFlow = _currentUserFlow.asStateFlow()

    private val _plantListFlow = MutableStateFlow(listOf<com.example.plantsaver.database.model.Plant>())
    val plantListFlow = _plantListFlow.asStateFlow()

    private val _plantFamilyListFlow = MutableStateFlow(listOf<PlantFamily>())
    val plantFamilyListFlow = _plantFamilyListFlow.asStateFlow()


    // wird beim starten der app aufgerufen und speichert pflanzen familien in die datenbank, falls noch keine drin stehen
    suspend fun initDbIfEmpty() {
        if (db.plantFamilyDao.isEmpty()) {
            val plantList: List<PlantFamily> = listOf(
                PlantFamily(
                    "Bogenhanf (Sansevieria)",
                    "Lange, aufrechte Blätter mit gelblicher oder grünlicher Musterung",
                    "Ideal für helle bis halbschattige Standorte",
                    "Pflegehinweise für Bogenhanf"
                ),
                PlantFamily(
                    "Zimmerpalme (Chamaedorea elegans)",
                    "Kleine Palme mit zarten, gefiederten grünen Blättern",
                    "Stellt keine hohen Ansprüche an den Standort",
                    "Pflegehinweise für Zimmerpalme"
                ),
                PlantFamily(
                    "Gummibaum (Ficus elastica)",
                    "Große, glänzende, dunkelgrüne Blätter und markante Stämme",
                    "Benötigt einen hellen Standort ohne direkte Sonneneinstrahlung",
                    "Pflegehinweise für Gummibaum"
                ),
                PlantFamily(
                    "Zamioculcas (Zamioculcas zamiifolia)",
                    "Dunkelgrüne, glänzende, dicke Blätter, die aufrechte Stängel bilden",
                    "Toleriert verschiedene Lichtverhältnisse, von hell bis halbschattig",
                    "Pflegehinweise für Zamioculcas"
                ),
                PlantFamily(
                    "Friedenslilie (Spathiphyllum)",
                    "Dunkelgrüne Blätter und auffällige, weiße Blüten",
                    "Braucht einen halbschattigen bis schattigen Standort",
                    "Pflegehinweise für Friedenslilie"
                ),
                PlantFamily(
                    "Einblatt (Spathiphyllum wallisii)",
                    "Grüne Blätter und weiße Hochblätter, die wie Blüten aussehen",
                    "Möchte an einem hellen bis halbschattigen Standort stehen",
                    "Pflegehinweise für Einblatt"
                )
            )
            db.plantFamilyDao.insertAll(plantList)
        }
    }

    // user

    suspend fun insertUser(user: User){
        db.userDao.insertUser(user)
    }

    suspend fun getUserByName(name: String): User?{
        return db.userDao.getUserByName(name)
    }

    suspend fun getUsers(): List<User>{
        return db.userDao.getUsers()
    }

    fun setCurrentUser(user: User){
        _currentUserFlow.update {
            user
        }
    }


    // plant
    suspend fun insertPlant(plant: com.example.plantsaver.database.model.Plant){
        db.plantDao.insertPlant(plant)
        _currentUserFlow.value?.let { loadPlantsForUserName(it.username) }

    }

    suspend fun loadPlantsForUserName(userName: String){
        _plantListFlow.update {
            db.plantDao.getPlantsForUserName(userName)
        }
    }


    // plant family

    suspend fun insertPlantFamily(plantFam: PlantFamily): Long{
        return db.plantFamilyDao.insertPlantFamily(plantFam)
    }

    suspend fun loadAllPlantFamilies(){
        _plantFamilyListFlow.update {
            db.plantFamilyDao.getAllPlantFamilies()
        }
    }



    // singleton pattern, sorgt dafür dass es immer nur ein repository gibt -> wenn es mehrere repositories gibt dann gibt es konflikte
    companion object {
        private lateinit var repoInstance: Repository
        fun getRepository(db: AppDatabase): Repository {
            synchronized(AppDatabase::class.java) {
                if (!::repoInstance.isInitialized) {
                    repoInstance = Repository(db)
                }
            }
            return repoInstance
        }
    }


}

/*
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
*/