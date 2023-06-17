package com.omprakash.quiz;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omprakash.quiz.databinding.QuestionNumberItemBinding;

public class QuestionNumberViewHolder extends RecyclerView.ViewHolder {

    QuestionNumberItemBinding binding;

    public QuestionNumberViewHolder(QuestionNumberItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
