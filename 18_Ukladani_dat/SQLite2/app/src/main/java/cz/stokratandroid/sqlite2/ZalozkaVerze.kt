package cz.stokratandroid.sqlite2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ZalozkaVerze : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_zalozka_verze, container, false)

        // nacteme data z DB pres adapter do gridu
        val mainActivity = activity as MainActivity?
        mainActivity!!.fragmentZalozkaVerze = view
        mainActivity.nacistData()
        return view
    }
}

