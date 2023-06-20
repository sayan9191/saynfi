package com.example.sanify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.realteenpatti.sanify.R
import com.realteenpatti.sanify.databinding.FragmentDashBoardBinding

import com.realteenpatti.sanify.ui.lottery.LotteryBuyFragment
import com.realteenpatti.sanify.ui.profile.Profile_Fragment

import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen.Companion.hideLoadingDialog
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen.Companion.showLoadingDialog
import com.realteenpatti.sanify.ui.spin.LuckyDrawActivity

class DashBoardFragment : Fragment() {


    lateinit var binding: FragmentDashBoardBinding

    lateinit var viewModel: DashBoardViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(layoutInflater, container, false)

        // Initiate viewModel
        viewModel = ViewModelProvider(this)[DashBoardViewModel::class.java]

        viewModel.getCurrentUserInfo()

        viewModel.userInfo.observe(viewLifecycleOwner) {
            if (it != null){
                binding.dashBoardUserName.text = it.name
            }

            viewModel.getCoinBalance()
        }

        viewModel.coinBalance.observe(viewLifecycleOwner) {
            binding.coinAmount.text = it.toString()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { s ->
            if (s != "") {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoadingDialog(requireContext())
            } else {
                try {
                    hideLoadingDialog()
                }catch (e : Exception){
                    e.stackTrace
                }
            }
        }


    binding.lotteryOption.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("Home")
                .replace(
                    R.id.fragmentContainerView,
                    LotteryBuyFragment()
                )
                .commit()
        }

        binding.spinOption.setOnClickListener {
            val intent = Intent(requireContext(), LuckyDrawActivity::class.java)
            startActivity(intent)
        }

        binding.horseOption.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("Home")
                .replace(R.id.fragmentContainerView,HorseRaceFragment()).commit()
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
        viewModel.getCoinBalance()
        super.onResume()
    }

}