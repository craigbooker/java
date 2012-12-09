package com.eatrightapp.external.era;


public class ERAUser {
        public enum Status { 
                NOT_YET_VALIDATED, 
                VALIDATED, 
                LOCKED, 
                BLOCKED, 
                DELETED
        }
        
        private String email;
        private String nickname;
        private String passwordMD5;
        private Status status;

        public ERAUser() {
                
        }
        
        public ERAUser(String email, String nickname, String passwordMD5) {
                this.email = email;
                this.nickname = nickname;
                this.passwordMD5 = passwordMD5;
        }
        
        public String getEmail() {
                return email;
        }
        
        public void setEmail(String email) {
                this.email = email;
        }
        
        public String getNickname() {
                return nickname;
        }
        
        public void setNickname(String nickname) {
                this.nickname = nickname;
        }
        
        public String getPasswordMD5() {
                return passwordMD5;
        }
        
        public void setPasswordMD5(String passwordMD5) {
                this.passwordMD5 = passwordMD5;
        }

        public void setStatus(Status status) {
                this.status = status;
        }

        public Status getStatus() {
                return status;
        }
        
        public String toString() {
                StringBuffer sb = new StringBuffer();
                sb.append("com.eatrightapp.service.api.model.ERAUser: ");
                sb.append("email=\"").append(email).append("\", ");
                sb.append("nickname=\"").append(nickname).append("\", ");
                sb.append("passwordMD5=\"").append(passwordMD5).append("\", ");
                sb.append("status=\"").append(status).append("\"");
                return sb.toString();
        }
}