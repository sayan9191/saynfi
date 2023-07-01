package com.realteenpatti.sanify.ui.home

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
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen.Companion.hideLoadingDialog
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen.Companion.showLoadingDialog
import com.realteenpatti.sanify.ui.horserace.HorseRaceFragment
import com.realteenpatti.sanify.ui.lottery.LotteryBuyFragment
import com.realteenpatti.sanify.ui.profile.Profile_Fragment
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
                .replace(R.id.fragmentContainerView, HorseRaceFragment()).commit()
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
            R.drawable.poster1,
            ScaleTypes.FIT
        )
    )
    slideModels.add(
        SlideModel(
            R.drawable.poster2,
            ScaleTypes.FIT
        )
    )
    slideModels.add(
        SlideModel(
            R.drawable.poster3,
            ScaleTypes.FIT
        )
    )
    slideModels.add(
        SlideModel(
            R.drawable.poster4,
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