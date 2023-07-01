package com.example.plantsaver.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.plantsaver.database.model.Plant
import com.example.plantsaver.database.model.PlantFamily
import com.example.plantsaver.database.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.NullPointerException

class Repository(private val db: AppDatabase, private val context: Context) {
    // dieser Flow enthält einen user und kann immer wieder aktualisiert werden
    private val _currentUserFlow: MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUserFlow = _currentUserFlow.asStateFlow()

    private val _plantListFlow = MutableStateFlow(listOf<Plant>())
    val plantListFlow = _plantListFlow.asStateFlow()

    // die neusten bilder von allen pflanzen - wenn eine pflanze keine bilder hat ist null in der liste
    private val _plantThumbnailsFlow = MutableStateFlow(listOf<ImageBitmap?>())
    val plantThumbnailsFlow = _plantThumbnailsFlow.asStateFlow()

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
    suspend fun insertPlant(plant: Plant): Long{
        val newId = db.plantDao.insertPlant(plant)
        //_currentUserFlow.value?.let { loadPlantsForUserName(it.username) }
        return newId
    }

    suspend fun deletePlant(plant:Plant) {
        db.plantDao.deletePlant(plant)
    }

    suspend fun loadPlantsForUserName(userName: String){
        val list = db.plantDao.getPlantsForUserName(userName)
        _plantListFlow.update {
            list
        }
        //load images
        loadPlantThumbnails()
    }

    suspend fun loadPlantThumbnails() {

        val plantList: List<Plant> = _plantListFlow.value

        val imageList = mutableListOf<ImageBitmap?>()
        // die thumbnails auch laden

        plantList.forEach { plant ->
            imageList.add(getLastImage(plant.id))
        }
        _plantThumbnailsFlow.update { imageList.toList() }

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


    // images

    // für myplants -> lädt das neuste bild für eine pflanze
    suspend fun getLastImage(plantId: Long): ImageBitmap? {
        val dir = File(context.filesDir, "$plantId")

        // hier sind jetzt alle files aus dem ordner drin. die bilder sind einfach benannt als 1.png, 2.png usw.
        val files = dir.listFiles()

        Log.e("REPO", "plantId: $plantId. files: $files : ${files?.size}")

        if (!files.isNullOrEmpty()) {
            // sortiert nach namen damit das neuste bild oben ist
            files.sortBy { it.name }
            try {
                Log.e("REPO", "files.last(): ${files.last()}")
                if (files.last() != null) {
                    // das letzte file aus der liste zu nem bitmap machen (neustes bild)
                    return withContext(Dispatchers.IO) {
                        BitmapFactory.decodeStream(FileInputStream(files.last()))
                    }.asImageBitmap()
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                Log.e("REPO", "NULLPOINTER")
                Log.e("REPO", "$files")
                Log.e("REPO", "${files.last()}")
                e.printStackTrace()
            }
        }
        files.isNullOrEmpty()
        plantId
        return null
    }

    // alle bilder für eine pflanze -> myplantsout
    suspend fun getImages(plantId: Long, context: Context): List<ImageBitmap> {
        val dir = File(context.filesDir, "$plantId")

        // hier sind jetzt alle files aus dem ordner drin. die bilder sind einfach benannt als 1.png, 2.png usw.
        val files = dir.listFiles()



        val images = mutableListOf<ImageBitmap>()

        // die files durchgehen und bitmaps daraus machen
        if (files != null) {
            // files nach name aufsteigend sortieren (alt -> neu)
            files.sortBy { it.name }
            try {
                files.forEach {
                    images.add(withContext(Dispatchers.IO) {
                        BitmapFactory.decodeStream(FileInputStream(it))
                    }.asImageBitmap())
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return images
    }

    suspend fun getImage(imageName: String, context: Context): ImageBitmap? {
        Log.e("Repo", "Get Image")
        if (imageName == "")
            return null
        try {
            val f = File(context.filesDir, "$imageName.png")
            return withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(FileInputStream(f))
            }.asImageBitmap()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    // pflanze mit der id 1 und ich speicher ein foto: 1/1.png
    // jede pflanze hat nen eigenen ordner benannt nach ihrer id
    suspend fun saveImage(plantId: Long, imageName: String, bitmap: ImageBitmap) {
        Log.e("Repo", "Save Image")
        // ordner
        val dir = File(context.filesDir, "$plantId")
        // datei
        val f = File(context.filesDir, "${plantId}/$imageName.png")

        withContext(Dispatchers.IO) {
            if (!f.exists()) {
                dir.mkdir()
                f.createNewFile()
            }

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(f)

                // das bild (bitmap) wird in das file (f) reingeschrieben
                val androidBitmap = bitmap.asAndroidBitmap()
                androidBitmap.setHasAlpha(true) // important for saving transparent background
                androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Log.e("SAVE IMAGE", "FILE NOT FOUND")
            } finally {
                try {
                    fos?.let { fos.close() }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }



    // singleton pattern, sorgt dafür dass es immer nur ein repository gibt -> wenn es mehrere repositories gibt dann gibt es konflikte
    companion object {
        private lateinit var repoInstance: Repository
        fun getRepository(db: AppDatabase, context: Context): Repository {
            synchronized(AppDatabase::class.java) {
                if (!::repoInstance.isInitialized) {
                    repoInstance = Repository(db, context)
                }
            }
            return repoInstance
        }
    }


}
