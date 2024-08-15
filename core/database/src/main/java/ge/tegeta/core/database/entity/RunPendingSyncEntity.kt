package ge.tegeta.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ge.tegeta.core.domain.run.Run

@Entity
data class RunPendingSyncEntity(
    @Embedded val run: RunEntity,
    @PrimaryKey(autoGenerate = false)
    val runId: String = run.id,
    val mapPictureBytes:ByteArray,
    val userId:String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RunPendingSyncEntity

        if (run != other.run) return false
        if (runId != other.runId) return false
        if (!mapPictureBytes.contentEquals(other.mapPictureBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = run.hashCode()
        result = 31 * result + runId.hashCode()
        result = 31 * result + mapPictureBytes.contentHashCode()
        return result
    }
}
