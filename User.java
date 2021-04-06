import java.util.*;

class Tweet{
    private int idTw;
    private String username;
    private String msg;
    private TreeSet<String> likes;

    public Tweet(int idTw, String username, String msg) {
        this.idTw = idTw;
        this.username = username;
        this.msg = msg;
        likes = new TreeSet<>();
    }
    
    public int getIdTw() {
        return idTw;
    }

    public String getUsername() {
        return username;
    }

    public String getMsg() {
        return msg;
    }
    
    void like(String username){
        likes.add(username);
    }
    
    public String toString(){
        return "Id: "+idTw+", Nome: "+username+", Mensagem: "+msg+", Likes:"+likes;
    }
}

class Controller{ // Guarda usuarios -- tweet
    Map<String, User> users;
    Map<Integer, Tweet> tweets;
    int nextTwId;

    public Controller(int nextTwId) {
        this.nextTwId = nextTwId;
        users = new TreeMap<>();
        tweets = new TreeMap<>();
    }
    
    void sendTweet(String username, String msg){
        Tweet tweet = new Tweet(nextTwId, username, msg);
        User user = users.get(username);
        tweets.put(nextTwId, tweet);
        user.sendTweet(tweet);
        this.nextTwId += 1;
    }

    void addUser(String username){
        users.put(username, new User(username, 0));
    }

    public User getUsers(String name) {
        return users.get(name);
    }

    public Map<Integer, Tweet> getTweets() {
        return tweets;
    }

    public String toString(){
        return "Usuarios: "+users+", Tweets: "+tweets;
    }
    
    public static void main(String[] args) {
        Controller control = new Controller(0);
        Scanner tcl = new Scanner(System.in);
        while(true){
            String[] ui = tcl.nextLine().split(" ");
            if(ui[0].equals("stop")){
                break;
            }else if(ui[0].equals("addUser")){
                control.addUser(ui[1]);
            }else if(ui[0].equals("show")){
                System.out.println(control);
            }else if(ui[0].equals("follow")){
                User one = control.getUsers(ui[1]);
                User two = control.getUsers(ui[2]);
                one.follow(two);
            }else if(ui[0].equals("tweet")){
                String username = ui[1];
                String msg = "";
                for (int i = 2; i < ui.length; i++) {
                    msg += ui[i] + " ";
                }
                control.sendTweet(username, msg);
            }else if(ui[0].equals("timeline")){
                User user = control.getUsers(ui[1]);
                System.out.println(user.getTimeline());
            }else if(ui[0].equals("unfollow")){
                User user = control.getUsers(ui[1]);
                User user2 = control.getUsers(ui[2]);
                user.unfollow(user2);
            }else{
                System.out.println("Comando invalido");
            }
        }
    }
}

public class User {
    private String username;
    private Map<String, User> followers;
    private Map<String, User> following;
    private Map<Integer, Tweet> timeline;
    private int inreadCount;

    public User(String username, int inreadCount) {
        this.username = username;
        this.inreadCount = inreadCount;
        followers = new TreeMap<>();
        following = new TreeMap<>();
        timeline = new TreeMap<>();
    }
    
    void follow(User user){
        this.following.put(user.username, user);
        user.followers.put(this.username, this);
    }
    
    void unfollow(User user){
        if(following.get(user.username) == null){
            System.out.println("Não estou seguindo essa pessoa");
        }else{
            following.remove(user.username);
            user.followers.remove(this.username);
        }
    }

    Tweet getTweet(int idTw){
        Tweet tw;
        return null;
    }
    
    void sendTweet(Tweet tw){
        timeline.put(tw.getIdTw(), tw);
    }
    
    public Map<Integer, Tweet> getTimeline() {
        return timeline;
    }
    
    public String getUnread(){
        return "";
    }
    
    public String getUsername() {
        return username;
    }
    
    public String toString(){
        return username+" Seguindo:"+following.keySet()+", Seguidores:"+followers.keySet();
    }
}
