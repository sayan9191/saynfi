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
                                binding.bidingAmountOne.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 2:
                                binding.bidingAmountTwo.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 3:
                                binding.bidingAmountThree.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 4:
                                binding.bidingAmountFour.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 5:
                                binding.bidingAmountFive.setText(String.valueOf(bidDetail.getBid_amount()));
                                break;
                            case 6:
                                binding.bidingAmountSix.setText(String.valueOf(bidDetail.getBid_amount()));
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


        binding.increaseOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Integer amount1 = Integer.valueOf(binding.bidAmountOne.getText().toString().trim());
                amount1 = amount1 + 10;
                binding.bidAmountOne.setText(amount1.toString());
            }
        });

        binding.decreaseOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Integer amount1 = Integer.valueOf(binding.bidAmountOne.getText().toString().trim());
                if (amount1 > 10) {
                    amount1 = amount1 - 10;
                    binding.bidAmountOne.setText(amount1.toString());
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceOneBidAmount = Integer.parseInt(binding.bidAmountOne.getText().toString().trim());
                binding.bidAmountOne.setText("10");
                viewModel.jmBid(diceOneBidAmount, 1);
            }
        });

        binding.increaseTwo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount2 = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                amount2 = amount2 + 10;
                binding.bidAmountTwo.setText(Integer.toString(amount2));
            }
        });

        binding.decreaseTwo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount2 = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                if (amount2 > 10) {
                    amount2 = amount2 - 10;
                    binding.bidAmountTwo.setText(Integer.toString(amount2));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceTwoBidAmount = Integer.parseInt(binding.bidAmountTwo.getText().toString().trim());
                binding.bidAmountTwo.setText("10");
                viewModel.jmBid(diceTwoBidAmount, 2);
            }
        });

        binding.increaseThree.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount3 = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                amount3 = amount3 + 10;
                binding.bidAmountThree.setText(Integer.toString(amount3));
            }
        });

        binding.decreaseThree.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount3 = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                if (amount3 > 10) {
                    amount3 = amount3 - 10;
                    binding.bidAmountThree.setText(Integer.toString(amount3));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceThreeBidAmount = Integer.parseInt(binding.bidAmountThree.getText().toString().trim());
                binding.bidAmountThree.setText("10");
                viewModel.jmBid(diceThreeBidAmount, 3);
            }
        });

        binding.increaseFour.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount4 = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                amount4 = amount4 + 10;
                binding.bidAmountFour.setText(Integer.toString(amount4));
            }
        });

        binding.decreaseFour.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int amount4 = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                if (amount4 > 10) {
                    amount4 = amount4 - 10;
                    binding.bidAmountFour.setText(Integer.toString(amount4));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceFourBidAmount = Integer.parseInt(binding.bidAmountFour.getText().toString().trim());
                binding.bidAmountFour.setText("10");
                viewModel.jmBid(diceFourBidAmount, 4);
            }
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
                if (amount5 > 10) {
                    amount5 = amount5 - 10;
                    binding.bidAmountFive.setText(Integer.toString(amount5));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bidFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceFiveBidAmount = Integer.parseInt(binding.bidAmountFive.getText().toString().trim());
                binding.bidAmountFive.setText("10");
                viewModel.jmBid(diceFiveBidAmount, 5);
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
                if (amount6 > 10) {
                    amount6 = amount6 - 10;
                    binding.bidAmountSix.setText(Integer.toString(amount6));
                } else {
                    Toast.makeText(requireContext(), "Minimum Bid amount is 10", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.bidSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int diceSixBidAmount = Integer.parseInt(binding.bidAmountSix.getText().toString().trim());
                binding.bidAmountSix.setText("10");
                viewModel.jmBid(diceSixBidAmount, 6);
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