package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Data
@AllArgsConstructor
public class User {
  int id;
  String username;
  String email;
  String fullName;
  LocalDateTime lastLogin;
  String workInfo = "";
  String photo;

  public String daysAfterLogin(){
    return String.valueOf(DAYS.between(this.lastLogin.toLocalDate(), LocalDate.now()));
  }
}
