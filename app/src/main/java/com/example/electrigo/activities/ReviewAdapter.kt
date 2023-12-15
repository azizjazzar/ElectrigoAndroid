import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.ReviewItem
import com.example.electrigo.R

class ReviewAdapter(
    private var reviewList: List<ReviewItem>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
        val commentTextView: TextView = itemView.findViewById(R.id.commentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentReview = reviewList[position]
        holder.ratingTextView.text = currentReview.rating.toString()
        holder.commentTextView.text = currentReview.comment
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun updateReviews(newReviews: List<ReviewItem>) {
        reviewList = newReviews
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}
