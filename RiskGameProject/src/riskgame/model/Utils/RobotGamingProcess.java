package riskgame.model.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import riskgame.controllers.TournamentModeResultViewController;
import riskgame.model.BasicClass.GameRunningResult;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.TournamentGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * This class process multithreading of tournament mode games.
 *
 * Created on 2019-03-30 030
 */
public class RobotGamingProcess {

    /**
     * init robot game parameters
     * @param mapFileList map path string
     * @param robotPlayerList robot player list
     * @param gamesValue game values
     * @param gameRoundValue maximum round of each game
     */
    public static void initRobotGaming(ArrayList<String> mapFileList, ArrayList<Strategy> robotPlayerList, int gamesValue, int gameRoundValue) {
        TournamentModeResultViewController tournamentModeResultViewController = initRobotFinalView(gamesValue, gameRoundValue, mapFileList, robotPlayerList);

        int threadCount = mapFileList.size() * gamesValue;
        ExecutorService executorPool = Executors.newFixedThreadPool(threadCount);

        BlockingQueue<Future<GameRunningResult>> gameResultQueue = new LinkedBlockingQueue<>(threadCount);

        CompletionService<GameRunningResult> completionService = new ExecutorCompletionService<>(executorPool, gameResultQueue);

        for (int mapIndex = 0; mapIndex < mapFileList.size(); mapIndex++) {
            for (int gameIndex = 0; gameIndex < gamesValue; gameIndex++) {
                String fileName = mapFileList.get(mapIndex);
                String mapFileName = fileName;
                String gameName = "Game " + gameIndex;

                int finalGameIndex = gameIndex;
                int finalMapIndex = mapIndex;

                Future<GameRunningResult> future = executorPool.submit(new Callable<GameRunningResult>() {
                    @Override
                    public GameRunningResult call() throws Exception {
                        TournamentGame oneTournamentGame = new TournamentGame(fileName, robotPlayerList, gameRoundValue);
                        oneTournamentGame.run();

                        String winnerName = oneTournamentGame.getGameWinner();
                        GameRunningResult curGameResult = new GameRunningResult(fileName, finalMapIndex, gameName, finalGameIndex, winnerName);

                        System.out.println("\n\n>>>>>>>cur game result object: " + curGameResult + "<<<<<<\n\n");

                        return curGameResult;
                    }
                });
                synchronized (gameResultQueue) {
                    tournamentModeResultViewController.setOneGameResult(future);
                }
                gameResultQueue.add(future);

                System.out.println("\nmap:" + mapFileName + ", gameSeq: " + gameIndex + "\n");
            }
        }

      /*  try {
            executorPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        executorPool.shutdown();
        processAllGamesResult(gameResultQueue, completionService, threadCount);
    }

    /**
     * display final tournament mode results
     * @param gamesValue game value
     * @param gameRoundValue maximum round of each game
     * @param mapFileList map path string
     * @param robotPlayerList robot player list
     * @return
     */
    private static TournamentModeResultViewController initRobotFinalView(int gamesValue, int gameRoundValue, ArrayList<String> mapFileList, ArrayList<Strategy> robotPlayerList) {
        Stage resultStage = new Stage();
        FXMLLoader loader = new FXMLLoader(RobotGamingProcess.class.getResource("../../view/TournamentResultView.fxml"));
        TournamentModeResultViewController controller = new TournamentModeResultViewController(gamesValue, gameRoundValue);
        loader.setController(controller);
        controller.setMapFileList(mapFileList);
        controller.setStrategyArrayList(robotPlayerList);

        Pane resultPane = null;
        try {
            resultPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene resultScene = new Scene(resultPane, 800, 600);
        resultScene.getStylesheets().add(RobotGamingProcess.class.getResource("../../view/css/finalresult.css").toExternalForm());
        resultStage.setScene(resultScene);
        resultStage.show();

        return controller;
    }

    /**
     * process games result
     * @param gameResultQueue a queue for storing games result
     * @param completionService competion service
     * @param threadCount total game threads
     */
    private static void processAllGamesResult(BlockingQueue<Future<GameRunningResult>> gameResultQueue, CompletionService<GameRunningResult> completionService, int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            Future<GameRunningResult> future = null;
            try {
                future = completionService.take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameRunningResult curGameResult = null;
            try {
                curGameResult = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(curGameResult);

            String mapFileName = curGameResult.getMapFileName();
            String gameName = curGameResult.getGameName();
            String winnerName = curGameResult.getWinnerName();

        }
    }


}
