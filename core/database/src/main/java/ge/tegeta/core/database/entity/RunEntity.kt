package ge.tegeta.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId
import java.net.URL

@Entity
data class RunEntity(
    val durationMills:Long,
    val distanceMeters:Int,
    val dateTimeUtc:String,
    val latitude:Double,
    val longitude:Double,
    val avgSpeedKmh:Double,
    val maxSpeedKmh:Double,
    val totalElevationMeters:Int,
    val mapPictureURL: String?,
    @PrimaryKey(autoGenerate = false)
    val id :String = ObjectId().toHexString()

)
