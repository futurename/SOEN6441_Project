package riskgame.model.Utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import riskgame.Main;
import riskgame.model.BasicClass.Card;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;

/**
 * This class includes methods for rendering itmes displayed in ListViews
 *
 * @author WW
 * @since build1
 **/
public class ListviewRenderer {

    /**
     * This method renders each player and its country name with its assigned color
     *
     * @param lsv_countries Country items in the ListView
     */
    public static void renderCountryItems(ListView<Country> lsv_countries) {
        lsv_countries.setCellFactory(cell -> new ListCell<Country>() {
            private Text text;

            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    String countryName = item.getCountryName();
                    int armyNumber = item.getCountryArmyNumber();
                    int playerIndex = item.getCountryOwnerIndex();

                    Color curPlayerColor = Main.playersList.get(playerIndex).getPlayerColor();

                    text = new Text(item.getCountryName() + " : " + armyNumber);
                    text.setFill(curPlayerColor);
                    setGraphic(text);
                }
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    public static void renderReachableCountryItems(ListView<Country> lsv_reachableCountry) {
        lsv_reachableCountry.setCellFactory(cell -> new ListCell<Country>() {
            private Text text;

            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    String countryName = item.getCountryName();
                    int armyNumber = item.getCountryArmyNumber();
                    int playerIndex = item.getCountryOwnerIndex();

                    Color curPlayerColor = Main.playersList.get(playerIndex).getPlayerColor();

                    text = new Text();

                    ArrayList<Country> emptyList = InfoRetriver.getAdjacentEnemy(playerIndex, item);
                    int totalEmptyArmyNbr = 0;

                    if (!emptyList.isEmpty()) {

                        for (Country country : emptyList) {
                            totalEmptyArmyNbr += country.getCountryArmyNumber();
                        }
                    }

                    String textContent = item.getCountryName() + " : " + armyNumber;
                    if (totalEmptyArmyNbr != 0) {
                        textContent += " (Empty: " + totalEmptyArmyNbr + ")";
                    }
                    text = new Text(textContent);

                    text.setFill(curPlayerColor);
                    setGraphic(text);
                }
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    /**
     * This method renders world map information in startview. Country information will be colored by its owner color if player number is confirmed
     *
     * @param playerIndex      player's index number
     * @param datalist         an ObservableList from a ListView UI control
     * @param avgListviewWidth allowed width of this ListView UI control
     * @return a new rendered ListView for display in worldmap or player's information HBox pane
     */
    public static ListView<String> getRenderedStartview(int playerIndex, ObservableList<String> datalist, double avgListviewWidth) {
        ListView<String> result = new ListView<>();

        result.setItems(datalist);
        result.setPrefWidth(avgListviewWidth);

        result.setCellFactory(cell -> new ListCell<String>() {
            private Text text;

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null && !empty) {
                    text = new Text(item);

                    if (playerIndex != -1) {
                        if (playerIndex == Main.totalNumOfPlayers) {
                            if (getIndex() != 0 && getIndex() != 1) {
                                String curCountryName = text.toString().split("\"")[1];

                                Country curCountry = Main.graphSingleton.get(curCountryName).getCountry();
                                int curCountryOwnerIndex = curCountry.getCountryOwnerIndex();

                                Color curCountryColor = Main.playersList.get(curCountryOwnerIndex).getPlayerColor();
                                text.setFill(curCountryColor);
                            }
                        } else {
                            Player curPlayer = Main.playersList.get(playerIndex);
                            Color curPlayerColor = curPlayer.getPlayerColor();
                            text.setFill(curPlayerColor);
                        }
                    }
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                    text.setWrappingWidth(avgListviewWidth - 10);
                    text.setTextAlignment(TextAlignment.CENTER);
                    if (getIndex() == 0) {
                        text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        setStyle("-fx-background-color: yellow");
                        text.setUnderline(true);

                    }
                    setGraphic(text);
                } else if (empty) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
        return result;
    }


    public static void renderCardsListView(ListView lsv_cardsListView, Player curPlayer) {
        Color curPlayerColor = curPlayer.getPlayerColor();

        lsv_cardsListView.setCellFactory(cell -> new ListCell<Card>() {
            private Text text;

            @Override
            protected void updateItem(Card item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    text = new Text(item.toString());

                    text.setFill(curPlayerColor);
                    setGraphic(text);
                }
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }


}
