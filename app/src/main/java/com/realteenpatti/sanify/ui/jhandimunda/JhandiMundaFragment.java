package com.realteenpatti.sanify.ui.jhandimunda;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.R;
import com.realteenpatti.sanify.databinding.FragmentJhandiMundaBinding;
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMMyBidResponseModel;
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMMyBidResponseModelItem;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;

import java.util.Objects;
import java.util.function.Consumer;


public class JhandiMundaFragment extends Fragment {
    FragmentJhandiMundaBinding binding;
    JhandiMundaViewModel viewModel;
    private CountDownTimer countDownTimer;
    long localRemainingTime = 0;
    MediaPlayer mPlayer;

    boolean isWinnerFetched = false;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJhandiMundaBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(this).get(JhandiMundaViewModel.class);

        if (mPlayer == null || !mPlayer.isPlaying()) {
            mPlayer = MediaPlayer.create(requireContext(), R.raw.sound_horse_race);
            mPlayer.setLooping(true);
            mPlayer.start();
        }

        //back Button
        binding.backBtn.setOnClickListener(view -> getParentFragmentManager().popBackStackImmediate());


        viewModel.getMyBidDetails();
        viewModel.getMyJmBids();
        viewModel.getCoinBalance();

        viewModel.getMyBidDetails().observe(getViewLifecycleOwner(), new Observer<JMMyBidResponseModel>() {
            @Override
            public void onChanged(JMMyBidResponseModel biddingDetails) {
                biddingDetails.forEach(new Consumer<JMMyBidResponseModelItem>() {
                    @Override
                    public void accept(JMMyBidResponseModelItem bidDetail) {
                        switch (bidDetail.getCard_id()) {
                            case 1:
                                if (Integer.parseInt(binding.bidAmountOne.getText().toString().trim()) > 0) {
                                    binding.bidingAmountOne.setText("Bid");
                                } else if (Integer.parseInt(binding.bidAmountOne.getText().toString().trim()) == 0) {
                                    binding.bidingAmountOne.setText(String.valueOf(bidDetail.getBid_amount()));
                                }
                                break;
                            case 2:
                                if (Integer.parseInt(binding.bidAmountTwo.getText().toString().trim()) > 0) {
                                    binding.bidingAmountTwo.setText("Bid");
                                } else if (Integer.parseInt(binding.bidAmountTwo.getText().toString().trim()) == 0) {
                                    binding.bidingAmountTwo.setText(String.valueOf(bidDetail.getBid_amount()));
                                }
                                break;
                            case 3:
                                if (Integer.parseInt(binding.bidAmountThree.getText().toString().trim()) > 0) {
                                    binding.bidingAmountThree.setText("Bid");
                                } else if (Integer.parseInt(binding.bidAmountThree.getText().toString().trim()) == 0) {
                                    binding.bidingAmountThree.setText(String.valueOf(bidDetail.getBid_amount()));
                                }
                                break;
                            case 4:
                                if (Integer.parseInt(binding.bidAmountFour.getText().toString().trim()) > 0) {
                                    binding.bidingAmountFour.setText("Bid");
                                } else if (Integer.parseInt(binding.bidAmountFour.getText().toString().trim()) == 0) {
                                    binding.bidingAmountFour.setText(String.valueOf(bidDetail.getBid_amount()));
                                }
                                break;
                            case 5:
                                if (Integer.parseInt(binding.bidAmountFive.getText().toString().trim()) > 0) {
                                    binding.bidingAmountFive.setText("Bid");
                                } else if (Integer.parseInt(binding.bidAmountFive.getText().toString().trim()) == 0) {
                                    binding.bidingAmountFive.setText(String.valueOf(bidDetail.getBid_amount()));
                                }
                                break;
                            case 6:
                                if (Integer.parseInt(binding.bidAmountSix.getText().toString().trim()) > 0) {
                                    binding.bidingAmountSix.setText("Bid");
                                } else if (Integer.parseInt(binding.bidAmountSix.getText().toString().trim()) == 0) {
                                    binding.bidingAmountSix.setText(String.valueOf(bidDetail.getBid_amount()));
                                }
                                break;
                        }
                    }
                });
            }
        });

        viewModel.getCurrentCoinBalance().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer balance) {
                binding.coinAmount.setText(String.valueOf(balance));
            }
        });

        viewModel.isBidSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    viewModel.getCoinBalance();
                    viewModel.getMyJmBids();
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")) {
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                    if (Objects.equals(s, "You haven't participated in this slot")) {
                        isWinnerFetched = true;
                    }
                }
            }
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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


        binding.increaseOne.setOnClickListener(view -> {
            int amount1 = Integer.parseInt(binding.bidAmountOne.getText().toString().trim());
            amount1 = amount1 + 10;
            binding.bidAmountOne.setText(Integer.toString(amount1));
        });

        binding.decreaseOne.setOnClickListener(view -> {
            int amount1 = Integer.parseInt(binding.bidAmountOne.getText().toString().trim());
            if (amount1 > 0) {
                amount1 = amount1 - 10;
                binding.bidAmountOne.setText(Integer.toString(amount1));
            }
        });

        binding.bidingAmountOne.setOnClickListener(view -> {
            int jmOneBidAmount = Integer.parseInt(binding.bidAmountOne.getText().toString().trim());
            binding.bidAmountOne.setText("0");
            viewModel.jmBid(jmOneBidAmount, 1);
        });

        binding.increaseTwo.setOnClickListener(view -> {
            int amount2 = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
            amount2 = amount2 + 10;
            binding.bidAmountTwo.setText(Integer.toString(amount2));
        });

        binding.decreaseTwo.setOnClickListener(view -> {
            int amount2 = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
            if (amount2 > 0) {
                amount2 = amount2 - 10;
                binding.bidAmountTwo.setText(Integer.toString(amount2));
            }
        });

        binding.bidingAmountTwo.setOnClickListener(view -> {
            int jmTwoBidAmount = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
            binding.bidAmountTwo.setText("0");
            viewModel.jmBid(jmTwoBidAmount, 2);
        });

        binding.increaseThree.setOnClickListener(view -> {
            int amount3 = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
            amount3 = amount3 + 10;
            binding.bidAmountThree.setText(Integer.toString(amount3));
        });

        binding.decreaseThree.setOnClickListener(view -> {
            int amount3 = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
            if (amount3 > 0) {
                amount3 = amount3 - 10;
                binding.bidAmountThree.setText(Integer.toString(amount3));
            }
        });

        binding.bidingAmountThree.setOnClickListener(view -> {
            int jmThreeBidAmount = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
            binding.bidAmountThree.setText("0");
            viewModel.jmBid(jmThreeBidAmount, 3);
        });

        binding.increaseFour.setOnClickListener(view -> {
            int amount4 = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
            amount4 = amount4 + 10;
            binding.bidAmountFour.setText(Integer.toString(amount4));
        });

        binding.decreaseFour.setOnClickListener(view -> {
            int amount4 = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
            if (amount4 > 0) {
                amount4 = amount4 - 10;
                binding.bidAmountFour.setText(Integer.toString(amount4));
            }
        });

        binding.bidingAmountFour.setOnClickListener(view -> {
            int horseFourBidAmount = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
            binding.bidAmountFour.setText("0");
            viewModel.jmBid(horseFourBidAmount, 4);
        });

        binding.increaseFive.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount5 = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                amount5 = amount5 + 10;
                binding.bidAmountFive.setText(Integer.toString(amount5));
            }
        });

        binding.decreaseFive.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount5 = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                if (amount5 > 0) {
                    amount5 = amount5 - 10;
                    binding.bidAmountFive.setText(Integer.toString(amount5));
                }
            }
        });

        binding.bidingAmountFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseFiveBidAmount = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                binding.bidAmountFive.setText("0");
                viewModel.jmBid(horseFiveBidAmount, 5);
            }
        });

        binding.increaseSix.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount6 = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                amount6 = amount6 + 10;
                binding.bidAmountSix.setText(Integer.toString(amount6));
            }
        });

        binding.decreaseSix.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount6 = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                if (amount6 > 0) {
                    amount6 = amount6 - 10;
                    binding.bidAmountSix.setText(Integer.toString(amount6));
                }
            }
        });
        binding.bidingAmountSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int horseSixBidAmount = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                binding.bidAmountSix.setText("0");
                viewModel.jmBid(horseSixBidAmount, 6);
            }
        });


        return binding.getRoot();

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