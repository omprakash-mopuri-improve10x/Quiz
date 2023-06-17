package com.omprakash.quiz;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omprakash.quiz.databinding.QuestionNumberItemBinding;
import com.omprakash.quiz.model.Question;

import java.util.List;

public class QuestionNumbersAdapter extends RecyclerView.Adapter<QuestionNumberViewHolder> {

    private List<Question> questions;

    void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
