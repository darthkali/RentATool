package de.rat.common;

import java.time.LocalDate;
/**Represents a class account.
 * @author Marco Petzold, Christian König, Danny Steinbrecher, Bilal Alnani
 */

public class Account {
    private Role role;
    private String email;
    private String password;
    private LocalDate changed;


    public Account(Role role,String email) {
        this.email = email;
        this.changed = LocalDate.now();
        this.role=role ;
        this.password="ddd";
    }


    public Account (Role role, String email, String password){
        this.role= role;
        this.email= email;
        this.password= password;
    }


    public Role getRole() {
        return this.role;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public LocalDate getChanged() {
        return changed;
    }


    public void setRole(Role role) {
        this.role = role;
    }


    public void setPassword(String password) {
        this.password = password;
    }



}
