package com.example.sanify.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanify.Profile_Fragment
import com.example.sanify.R
import com.example.sanify.databinding.FragmentDashBoardBinding
import com.example.sanify.ui.lottery.LotteryBuyFragment
import com.example.sanify.ui.spin.LuckyDrawActivity

class DashBoardFragment : Fragment() {


    lateinit var binding: FragmentDashBoardBinding
//    private val repo = Repository()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(layoutInflater, container, false)

        binding.lotteryOption.setOnClickListener {
//            repo.getAllItems()
            val intent = Intent(requireContext(), LotteryBuyFragment::class.java)
            startActivity(intent)
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



        return binding.root
    }

}