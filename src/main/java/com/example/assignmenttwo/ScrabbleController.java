package com.example.assignmenttwo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class ScrabbleController implements Initializable {

    @FXML
    private TextField enterText;
    @FXML
    private GridPane rootPane;
    @FXML
    private Label displayErrors,previousWordsLabel,userPoints,theGameIsOver;
    @FXML
    private ListView letterValuesDisplay;
    Map<String,Integer> newMap;
    List<Button> listOfButtons = new ArrayList<>();
    Scrabble myScrabble;
    @FXML
    void handleLetterClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText().substring(0,1).toLowerCase();
        enterText.setText(enterText.getText() + buttonText);
    }

    @FXML
    void submitClick(ActionEvent event) {

        String theWord = enterText.getText();
        displayErrors.setText("");
        try {
            myScrabble.validateWord(theWord);
            newMap = new HashMap<>(myScrabble.checkCount(theWord));
            setValues(newMap);
            myScrabble.addPreviousWord(theWord);
            setPreviousWords();
            userPoints.setText(Integer.toString(myScrabble.getUserPoints(theWord)));
        }
        catch (Exception e)
        {
            displayErrors.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
        if(myScrabble.gameOverCheck())
        {
            theGameIsOver.setVisible(true);
        }
    }

    private void setPreviousWords() {
        Set<String> previousWords = myScrabble.getMyPreviousWords();
        String previousWordsText = String.join(", ", previousWords);
        previousWordsLabel.setText(previousWordsText);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myScrabble = new Scrabble();
        for (Map.Entry<String, Integer> entry : myScrabble.getMyLetterScores().entrySet()) {
            letterValuesDisplay.getItems().add(entry);
        }
        setAllButtons();
        setValues(myScrabble.getMyLetterCounts());
        theGameIsOver.setVisible(false);
    }
    private void setValues(Map<String,Integer> myMap)
    {
        for (Button button: listOfButtons)
        {
            String firstLetter = button.getText().substring(0,1);
            button.setText(firstLetter+ myMap.get(firstLetter) );
            if(myMap.get(firstLetter)==0)
            {
                button.setDisable(true);
            }
        }
        myScrabble.setMyLetterCounts(myMap);
    }
    private void setAllButtons()
    {
        for (Node node : rootPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                listOfButtons.add(button);
            }
        }
    }
}
