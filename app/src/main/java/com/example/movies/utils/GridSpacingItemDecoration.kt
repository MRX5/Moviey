
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class GridSpacingItemDecoration(
    private val space: Int,
) :
    ItemDecoration() {
    var cnt=0
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition=parent.getChildLayoutPosition(view)
        if((itemPosition-1)%3==0){
            outRect.left = space/2;
            outRect.right = space/2;
        }else if(itemPosition%3==0){
            outRect.right=space/2
            outRect.left=space
        }else{
            outRect.left=space/2
            outRect.right=space
        }

        outRect.bottom = space


        // Add top margin only for the first item to avoid double space between items
        if (itemPosition<3) {
            outRect.top = space*3;
        } else {
            outRect.top = 0;
        }
    }

}