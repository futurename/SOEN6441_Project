package riskgame.model.Utils;

import com.sun.deploy.util.FXLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import riskgame.Main;
import riskgame.model.BasicClass.Continent;
import riskgame.model.BasicClass.Country;
import riskgame.model.BasicClass.Player;

import java.util.ArrayList;

/**
 * model class that includes all renderer methods for generating required effects for listviews
 *
 * @author WW
 */
public class ListviewRenderer {

    public static void renderCountryItems(ListView<Country> lsv_countries) {
        lsv_countries.setCellFactory(cell -> {
            return new ListCell<Country>() {
                private Text text;

                @Override
                protected void updateItem(Country item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {
                        String countryName = item.getCountryName();
                        int armyNumber = item.getCountryArmyNumber();
                        int playerIndex = item.getCountryOwnerIndex();

                        System.out.println(">>>in renderer, player:  " + playerIndex + ", countryname: " + countryName + ", army: " + armyNumber);

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
            };
        });
    }

     /**
     * @param playerIndex      player's index number
     * @param datalist         an ObservableList from a ListView UI control
     * @param avgListviewWidth allowed width of this ListView UI control
     * @return a new rendered ListView for display in worldmap or player's information HBox pane
     */
    public static ListView<String> getRenderedStartview(int playerIndex, ObservableList<String> datalist, double avgListviewWidth) {
        ListView<String> result = new ListView<>();

        result.setItems(datalist);
        result.setPrefWidth(avgListviewWidth);

        result.setCellFactory(cell -> {
            return new ListCell<String>() {
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
            };
        });
        return result;
    }
}
