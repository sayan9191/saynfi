package com.realteenpatti.sanify.ui.lottery;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.MainActivity;
import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.databinding.FragmentLotteryBuyBinding;
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel;
import com.realteenpatti.sanify.retrofit.models.lottery.LotteryNoticeGetResponseModel;
import com.realteenpatti.sanify.ui.bottomsheet.BottomSheetLotteryNo;
import com.realteenpatti.sanify.ui.bottomsheet.listeners.LotteryBuyListener;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;
import com.realteenpatti.sanify.ui.lottery.allParticipants.ParticipantFragment;
import com.realteenpatti.sanify.ui.lottery.prizepool.PrizePoolFragment;
import com.realteenpatti.sanify.ui.lottery.result.ResultFragment;
import com.realteenpatti.sanify.ui.lottery.yourtickets.MyTicketFragment;

import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class LotteryBuyFragment extends Fragment implements LotteryBuyListener {

    FragmentLotteryBuyBinding binding;
    private CountDownTimer countDownTimer = null;
    LotteryBuyFragmentViewModel viewModel;
    long remainingMillis =0;
    MediaPlayer mPlayer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLotteryBuyBinding.inflate(getLayoutInflater(), container, false);

        viewModel = new ViewModelProvider(this).get(LotteryBuyFragmentViewModel.class);

        viewModel.getCurrentCoinBalance();
        viewModel.getLotteryNoticeMessage();

        viewModel.getNoticeBoardMessage().observe(getViewLifecycleOwner(), new Observer<LotteryNoticeGetResponseModel>() {
            @Override
            public void onChanged(LotteryNoticeGetResponseModel lotteryNoticeGetResponseModel) {
                binding.noticeText.setText(lotteryNoticeGetResponseModel.getNotice_text());
            }
        });

        if (mPlayer == null || !mPlayer.isPlaying()) {
            mPlayer = MediaPlayer.create(requireContext(), R.raw.sound_spinner);
            mPlayer.setLooping(true);
            mPlayer.start();
        }


        viewModel.getCoinBalance().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer balance) {
                binding.coinAmount.setText(String.valueOf(balance));
                viewModel.getCountDownDetails();
            }
        });

        viewModel.isBuyLotterySuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
//                    Toast.makeText(requireContext(), "Lottery entry (+1)", Toast.LENGTH_SHORT).show();
                    viewModel.getCurrentCoinBalance();
                }
            }
        });


//
        viewModel.getCountDownTime().observe(getViewLifecycleOwner(), new Observer<CountDownTimeResponseModel>() {
            @Override
            public void onChanged(CountDownTimeResponseModel countDownTimeResponseModel) {

                TimeZone tz = TimeZone.getDefault();
                Date now = new Date();
                long offsetFromUtc = tz.getOffset(now.getTime());

                Log.d("___________ offset", String.valueOf(offsetFromUtc));

                remainingMillis = Long.parseLong(countDownTimeResponseModel.getTime_left_in_millis()) - offsetFromUtc;
                if (countDownTimer != null){
                    countDownTimer.cancel();
                }

                countDownTimer = new CountDownTimer(remainingMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long seconds = millisUntilFinished / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;

                        String countdownText = "Lottery closes in: " + String.format("%02d", hours % 24) + "h " + String.format("%02d", minutes % 60) + "m " + String.format("%02d", seconds % 60) + "s";

                        binding.countdownTextView.setText(countdownText);
                    }

                    @Override
                    public void onFinish() {
                        binding.countdownTextView.setText("Congratulations Winners");
                    }
                };

                countDownTimer.start();
            }
        });
        viewModel.getErrorMessage().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.isLoading().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    LoadingScreen.Companion.showLoadingDialog(requireContext());
                } else {
                    try {
                        LoadingScreen.Companion.hideLoadingDialog();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        });

        //Single Buy Lottery
        binding.buyLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //call the api here Please
                viewModel.buyLottery(20);
            }
        });

        //Multiple Buy Lottery
        binding.buyMLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SELECT QUANTITY CODE HERE
                BottomSheetLotteryNo bottomSheetLotteryNo = new BottomSheetLotteryNo(LotteryBuyFragment.this);


                bottomSheetLotteryNo.show(getParentFragmentManager(), "TAG");
            }
        });
        //all participants
        binding.allParticipantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().setReorderingAllowed(true).addToBackStack("PrizePool").replace(R.id.fragmentContainerView, new ParticipantFragment()).commit();
            }
        });

        //PrizePool Button
        binding.prizePoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().setReorderingAllowed(true).addToBackStack("PrizePool").replace(R.id.fragmentContainerView, new PrizePoolFragment()).commit();
            }
        });

        //YourTicket Button
        binding.yourTicketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().setReorderingAllowed(true).addToBackStack("YourTickets").replace(R.id.fragmentContainerView, new MyTicketFragment())
                        .commit();
            }
        });

        //result button
        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("___________", String.valueOf(remainingMillis));
                if (remainingMillis - 18000000 > 0 || remainingMillis < 0) {

                    ResultFragment resultFragment = new ResultFragment();

                    getParentFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("Result")
                            .replace(R.id.fragmentContainerView, resultFragment)
                            .commit();
                }else {
                    Toast.makeText(requireContext(), "Winner will be announced after lottery completion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //back Button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onMultipleLotteryBuySuccess() {
        viewModel.getCurrentCoinBalance();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }
}