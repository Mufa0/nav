package com.five.nav.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedArticleAuditResponse {

  long articleId;

  List<ArticleAuditResponse> audits;

}
