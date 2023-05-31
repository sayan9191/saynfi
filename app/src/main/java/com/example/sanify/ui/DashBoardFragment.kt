package com.example.sanify.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.sanify.LotteryBuyFragment
import com.example.sanify.Profile_Fragment
import com.example.sanify.R
import com.example.sanify.databinding.FragmentDashBoardBinding
import com.example.sanify.ui.spin.LuckyDrawActivity
import com.example.sanify.utils.StorageUtil

class DashBoardFragment : Fragment() {


    lateinit var binding: FragmentDashBoardBinding
    lateinit var localStorage: StorageUtil
//    private val repo = Repository()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(layoutInflater, container, false)

        localStorage = StorageUtil()
        localStorage.sharedPref = requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        binding.coinAmount.text = localStorage.coins.toString()

        binding.lotteryOption.setOnClickListener {
//            repo.getAllItems()
//            val intent = Intent(requireContext(), LotteryBuyFragment::class.java)
//            startActivity
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("Home")
                .replace(R.id.fragmentContainerView,
                    LotteryBuyFragment()
                )
                .commit()
        }

        binding.spinOption.setOnClickListener {
            val intent = Intent(requireContext(), LuckyDrawActivity::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {
            parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("Home")
                    .replace(R.id.fragmentContainerView,
                        Profile_Fragment()
                    )
                    .commit()
        }


    //Slider of offers
    val slideModels = ArrayList<SlideModel>()
    slideModels.add(
        SlideModel(
            "https://cdn.pixabay.com/photo/2014/02/27/16/10/flowers-276014__480.jpg",
            ScaleTypes.FIT
        )
    )
    slideModels.add(
        SlideModel(
            "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__480.jpg",
            ScaleTypes.FIT
        )
    )
    slideModels.add(
        SlideModel(
            "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
            ScaleTypes.FIT
        )
    )
    slideModels.add(
        SlideModel(
            "https://cdn.pixabay.com/photo/2014/04/14/20/11/pink-324175__480.jpg",
            ScaleTypes.FIT
        )
    )
    binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT)

        return binding.root
    }

    override fun onResume() {
        binding.coinAmount.text = localStorage.coins.toString()
        super.onResume()
    }

}