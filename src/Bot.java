//import javax.swing.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.function.Function;
//
//
////1 заменить все print на ret string
//
//// вынести логику с ресурсами в другого "бота"
//
//class Bot {
//
//    private final Map<String, String> MOVIESandDIRECTORS = new HashMap<String, String>(){{
//        put("Pulp Fiction","Tarantino");
//        put("A Clockwork Orange","Kubrick");
//        put("Blue Velvet","Lynch");
//    }};
//
//    //поле quizbot
//
//
//    private final String INSTRUCTION = "I'd like you to play the game!\n" +
//            "I will send you the name of movie and you'll name the director." +
//            "You've got 3 attemps for every movie";
//    // должна подваться в конструкторе и быть полем
//
//
//
//    public void  start(){
//        Player player = new Player(getNewUserName());
//        start(player);
//    }
//
//    public void start(Player player){
//        greetUser(player);
//        while (player.wantsToPlay){
//            playOneSession(player);
//        }
//    }
//
//
//    private String getNewUserName(){
//        System.out.println("Enter your name");
//        return new Scanner(System.in).next();
//    }
//
//    private String greetUser(Player player){
//        System.out.println("Hello, " + player.name);
//        System.out.println(INSTRUCTION);
//    }
//    private String getMovieToOffer(Player player){
//        String movie = getRandomMovie();
//        while (player.KnownMovies.contains((movie))){
//            movie = getRandomMovie();
//        }
//        return movie;
//    }
//
//    private void playOneSession(Player player){
//        String movie = getMovieToOffer(player);
//        System.out.println("Next movie: " + movie);
//        String correctAnswer = MOVIESandDIRECTORS.get(movie);
//        int attemptsLeft = 3;
//        Scanner scan = new Scanner(System.in);
//        while (attemptsLeft > 0 && player.wantsToPlay) {
//            String userAnswer = scan.nextLine();
//
//            if (isCommand(userAnswer)){
//                processCommand(userAnswer, player);
//                return;
//            }
//
//            if (userAnswer.equalsIgnoreCase(correctAnswer)){
//                praiseUser(player, attemptsLeft, movie);
//                return;
//            }
//            System.out.print("Nope.");
//            attemptsLeft--;
//            if (attemptsLeft > 0)
//                System.out.println(" Try again");
//        }
//        noticeUserFail(player, correctAnswer);
//    }
//
//    private boolean isCommand(String userInput){
////        return COMMANDS.containsKey(userInput);
//        return userInput.startsWith("\\");
//    }
//
//    private void processCommand(String command, Player currentPlayer){
//        switch(command) {
//            case "\\start" :
//                start();
//                break;
//
//            case "\\help" :
//                System.out.println(INSTRUCTION);
//                break;
//
//            case "\\score" : //in quiz bot
//                System.out.println(currentPlayer.score);
//                break;
//
//            case "\\stop" :
//                currentPlayer.wantsToPlay = false;
//                break;
//
//            default :
//                System.out.println("Incorrect command");
//        }
//    }
//
//
//    private  void praiseUser(Player player, int attemptsLeft, String guessedMovie){
//        System.out.println("Nice job, " + player.name);
//        player.score += attemptsLeft;
//        player.KnownMovies.add(guessedMovie);
//    }
//
//    private void noticeUserFail(Player player, String movie){
//        System.out.println("Okay... Let's try another movie");
//        player.Failures.add(movie);
//    }
//
//    private String getRandomMovie(){
//        Random rnd = new Random();
//        int rndNumber = rnd.nextInt(MOVIESandDIRECTORS.size());
//        return MOVIESandDIRECTORS.keySet().toArray()[rndNumber].toString();
//    }
//
//}
