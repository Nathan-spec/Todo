package com.example.todo.data

import android.content.Context
import androidx.room.*

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val details: String
)

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): List<Todo>

    @Query("SELECT * FROM todos WHERE id = :mId")
    fun getTodo(mId: Int): Todo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(todo: Todo)

    @Delete
    fun delete(todo: Todo)

}

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var mInstance: TodoDatabase? = null
        fun instance(context: Context): TodoDatabase {
            if (mInstance == null)
                synchronized(this) {
                    mInstance = Room
                        .databaseBuilder(context, TodoDatabase::class.java, "todo_database")
                        .build()
                }

            return mInstance!!
        }
    }
}
