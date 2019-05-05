package client;
/**
 * Created by Mr on 2019/4/22.
 */
public class User {
    private String username;
    private String password;
    private String[] friendList;

    public User(){}

    public boolean register(String [] userName, String username, String password){
        //检查文件中是否有相同用户名
        //在文件中添加相关信息
        return true;
    }

    public boolean login(String [] userName, String username, String password){
        //检查文件中是否有该用户名
        //用户名密码是否匹配
        //匹配->连接服务器
        return true;
    }

    public boolean check_user(String[] userName, String admin, String password){
        boolean flag = false;
        int i;
        for(i = 0; i<userName.length; i++){
            String[] array = userName[i].split(",");
            if(array.length==2){
                if(admin.equals(array[0]) && password.equals(array[1])){
                    flag = true;
                    break;
                }
            }

        }
        return flag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getFriendList() {
        return friendList;
    }

    public void setFriendList(String[] friendList) {
        this.friendList = friendList;
    }
}