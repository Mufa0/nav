package com.five.nav.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest implements Request {

  private static final String NAME_NOT_VALID = "Exception.group.nameNotValid";

  @NotBlank(message = NAME_NOT_VALID)
  String name;

  List<Long> articles;

}
