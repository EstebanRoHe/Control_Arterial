package cr.ac.una.controlarterial.db

/*
@Database(entities = [TomaArtetial::class], version = 1)//se agregan las entitys

abstract class AppDatabase : RoomDatabase() {
    abstract fun tomaArterialDao(): TomaArterialDAO

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "tomaArterial-database"
                    ).build()
                }
            }
            return instance!!
        }
    }
}*/