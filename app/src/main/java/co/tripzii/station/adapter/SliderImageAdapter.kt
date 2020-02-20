package co.tripzii.station.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import co.tripzii.station.R
import co.tripzii.station.model.ImageModel

class SliderImageAdapter(private val context: Context, private val myImageList: ArrayList<ImageModel>)
    : PagerAdapter() {

    private var inflater: LayoutInflater
    init {
        inflater = LayoutInflater.from(context)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return myImageList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val imageLayout = inflater!!.inflate(R.layout.slider_images_layout, view, false)
        val imageView = imageLayout.findViewById(R.id.imageView) as ImageView
        imageView.setImageResource(myImageList[position].getImageDrawables())
        view.addView(imageLayout, 0)
        return imageLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }
}
