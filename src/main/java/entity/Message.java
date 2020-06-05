package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Message {
  int id;
  int from;
  int to;
  String text;
  LocalDateTime sentTime;
}
