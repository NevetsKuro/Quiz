/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iocl.quiz;

import java.util.ArrayList;

/**

 */

public class QuizQuestion {
	
    int questionNumber;
    String question;
    ArrayList<String> questionOptions = null;
    int correctOptionIndex;

    public String getQuestion()
    { 
        return question;
    }

    public int getQuestionNumber()
    {
        return questionNumber;
    }

    public void setQuestionNumber(int i)
    {
        questionNumber = i;
    }

    public int getCorrectOptionIndex()
    {
        return correctOptionIndex;
    }

    public ArrayList<String> getQuestionOptions()
    {
        return questionOptions;
    }

    public void setQuestion(String s)
    {
        question = s;
    }
    public void setCorrectOptionIndex(int i)
    {
        correctOptionIndex=i;
    }
    public void setQuestionOptions(ArrayList<String> questionOptions)
    {
        this.questionOptions = questionOptions;
    }

}
