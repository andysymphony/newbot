package com.stampbot.domain;

import edu.stanford.nlp.ie.util.RelationTriple;
import jersey.repackaged.com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope("request")
public class UserInput {

    private String inputSentence;

    private String userId;

    private List<UserInputWord> words = Lists.newArrayList();

    private boolean negativeSentiment = true;

    private RelationTriple relationTriple;

    private String actionHandlerClass;

    public UserInput() {

    }

    public UserInput(String inputMessage) {
        this.inputSentence = inputMessage;
    }

    public void addWord(UserInputWord userInputWord) {
        this.words.add(userInputWord);
    }

}
