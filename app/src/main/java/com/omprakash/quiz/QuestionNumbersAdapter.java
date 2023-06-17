package com.omprakash.quiz;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omprakash.quiz.databinding.QuestionNumberItemBinding;
import com.omprakash.quiz.model.Question;

import java.util.List;

public class QuestionNumbersAdapter extends RecyclerView.Adapter<QuestionNumberViewHolder> {

    private List<Question> questions;

    private OnItemActionListener onItemActionListener;

    public Integer currentQuestionPosition = 0;

    void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    @NonNull
    @Override
    public QuestionNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionNumberItemBinding binding = QuestionNumberItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        QuestionNumberViewHolder questionNumberViewHolder = new QuestionNumberViewHolder(binding);
        return questionNumberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionNumberViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.binding.questionNumberTxt.setText(String.valueOf(question.getNumber()));
        if (currentQuestionPosition == position) {
            holder.binding.questionNumberTxt.setTextColor(Color.parseColor("#F57C00"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#F57C00"));
        } else {
            holder.binding.questionNumberTxt.setTextColor(Color.parseColor("#000000"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#000000"));
        }
        holder.binding.getRoot().setOnClickListener(v -> {
            onItemActionListener.onItemClicked(question);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
