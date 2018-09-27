package nuanliu.com.modao.bean;

/**
 * @描述 ${TODO}
 * @更新者 ${Author} on ${DATA}
 * @描述 ${TODO}
 */

public class User extends Dto {

    /**
     * user : {"account":"aaa","passwd":"123456","name":null,"telphone":"15088888888","IDcardNo":null,"age":0,"gender":null,"inserttime":1529942400000,"updatetime":1529942400000,"veriCode":null,"residents":null}
     */

    private UserBean user;

    public User(String userName, String telphone, String passwd, String name, String IDcardNo, int age, String gender, String residents, String headPortrait) {
        this.user = new UserBean();
        user.account = userName;
        user.telphone = telphone;
        user.passwd = passwd;
        user.name = name;
        user.IDcardNo = IDcardNo;
        user.age = age;
        user.gender = gender;
        user.residents = residents;
        user.headPortrait = headPortrait;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getPasswd() {
        if (user == null)
            return "";
        return user.passwd;
    }

    public String getUsername() {
        if (user == null)
            return "";
        return user.account;
    }

    public String getTelphone() {
        if (user == null)
            return "";
        return user.telphone;
    }

    public String getIDcardNo() {
        if (user == null)
            return "";
        return user.IDcardNo;
    }

    public int getAge() {
        if (user == null)
            return 0;
        return user.age;
    }

    public String getGender() {
        if (user == null)
            return "";
        return user.gender;
    }

    public String getName() {
        if (user == null)
            return "";
        return user.name;
    }

    public String getResidents() {
        if (user == null)
            return "";
        return user.residents;
    }

    public String getHeadPortrait() {
        if (user == null)
            return "";
        return user.headPortrait;
    }

    public static class UserBean {
        /**
         * account : aaa
         * passwd : 123456
         * name : null
         * telphone : 15088888888
         * IDcardNo : null
         * age : 0
         * gender : null
         * inserttime : 1529942400000
         * updatetime : 1529942400000
         * veriCode : null
         * residents : null
         * headPortrait: 头像路径
         */

        private String account;
        private String passwd;
        private String telphone;
        private String IDcardNo;
        private int age;
        private String gender;
        private String name;
        private String residents;
        private String headPortrait;

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public void setResidents(String residents) {
            this.residents = residents;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public void setIDcardNo(String IDcardNo) {
            this.IDcardNo = IDcardNo;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
