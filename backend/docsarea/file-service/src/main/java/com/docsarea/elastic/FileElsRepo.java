package com.docsarea.elastic;

import com.docsarea.module.ElsDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface FileElsRepo extends ElasticsearchRepository<ElsDocument , String> {

    @Query("""
{
  "bool": {
    "must": [
      {
        "multi_match": {
          "query": "?0",
          "fields": ["title^3", "description"]
        }
      }
    ],
    "filter": [
      { "terms": { "accessibility.keyword": ["PUBLIC", "USERS"] } },
      { "term": { "status.keyword": "ACCEPTED" } }
    ]
  }
}
""")
    Page<ElsDocument> searchByQuery(String searchQuery, Pageable pageable);

    @Query("""
    {
      "function_score": {
        "query": {
          "bool": {
            "must": [
              {
                "multi_match": {
                  "query": "?0",
                  "fields": ["title^3", "description"]
                }
              }
            ],
            "filter": [
               { "terms": { "accessibility.keyword": ["PUBLIC", "USERS"] } },
               { "term": { "status.keyword": "ACCEPTED" } }
            ],
            "must_not": [
              { "term": { "id": "?1" } }
            ]
          }
        },
        "functions": [
          {
            "random_score": {
              "seed": 12345,
              "field": "_seq_no"
            },
            "weight": 0.1
          }
        ],
        "score_mode": "sum",
        "boost_mode": "sum"
      }
    }
    """)
    Page<ElsDocument> searchWithFallback(String query, String excludeId, Pageable pageable);

}
