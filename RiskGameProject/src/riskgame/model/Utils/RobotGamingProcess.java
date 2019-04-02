package riskgame.model.Utils;

import riskgame.model.BasicClass.GameRunningResult;
import riskgame.model.BasicClass.StrategyPattern.Strategy;
import riskgame.model.BasicClass.TournamentGame;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created on 2019-03-30 030
 */
public class RobotGamingProcess {

    public static void initRobotGaming(ArrayList<String> mapFileList, ArrayList<Strategy> robotPlayerList, int gamesValue, int gameRoundValue) {
        System.out.println(mapFileList + "\n");

        int threadCount = mapFileList.size() * gamesValue;
        ExecutorService executorPool = Executors.newFixedThreadPool(threadCount);

        BlockingQueue<Future<GameRunningResult>> gameResultQueue = new LinkedBlockingQueue<>(threadCount);

        CompletionService<GameRunningResult> completionService = new ExecutorCompletionService<>(executorPool, gameResultQueue);

        for (String fileName : mapFileList) {
            for (int gameIndex = 0; gameIndex < gamesValue; gameIndex++) {

                String mapFileName = fileName;
                String gameName = "Game" + gameIndex;

                Future<GameRunningResult> future = executorPool.submit(new Callable<GameRunningResult>() {
                    @Override
                    public GameRunningResult call() throws Exception {
                        TournamentGame oneTournamentGame = new TournamentGame(fileName, robotPlayerList, gameRoundValue);
                        oneTournamentGame.run();

                        String winnerName = oneTournamentGame.getGameWinner();
                        GameRunningResult curGameResult = new GameRunningResult(fileName, gameName, winnerName);

                        System.out.println("\n\n>>>>>>>cur game result object: " + curGameResult + "<<<<<<\n\n");

                        return curGameResult;
                    }
                });

                gameResultQueue.add(future);

                System.out.println("\nmap:" + mapFileName + ", gameSeq: " + gameIndex + "\n");
            }


        }

        executorPool.shutdown();
        try {
            executorPool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n\n\n\n-------------FINAL RESULT:--------------");
        processAllGamesResult(gameResultQueue, completionService, threadCount);
    }

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

        }
    }


}
